/**
 * The Accord Project, http://accordproject.org
 * Copyright (C) 2005-2013 Rafael Marins, http://rafaelmarins.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neociclo.capi20.util;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rafael Marins
 */
public class CapiBuffers {

    private static final Logger LOGGER = LoggerFactory.getLogger(CapiBuffers.class);

    public static final int OCTET_SIZE = 1;

    public static final int WORD_SIZE = 2;

    public static final int DWORD_SIZE = 4;

    public static final int QWORD_SIZE = 8;

    public static final byte EMPTY_STRUCT = 0;

    public static final String CAPI_STRING_ENCODING = "US-ASCII";

    public static byte readOctet(ChannelBuffer buf) {
        return buf.readByte();
    }

    public static short readWord(ChannelBuffer buf) {
        short word = (short) (readOctet(buf) & 0xff);
        word |= (short) (readOctet(buf) << 8);
        return word;
    }

    public static int readDword(ChannelBuffer buf) {
        int dword = (readWord(buf) & 0xffff);
        dword |= ((readWord(buf) & 0xffff) << 16);
        return dword;
    }

    public static long readQword(ChannelBuffer buf) {
        long qword = (readDword(buf) & 0xffffffff);
        qword |= ((readDword(buf) & 0xffffffff) << 32);
        return qword;
    }

    public static byte[] readStruct(ChannelBuffer buf) {

        int size = (readOctet(buf) & 0xff);

        if (size == 0) {
            return null;
        } else if (size == 0xff) {
            // escape code for word length field
            size = (readWord(buf) & 0xffff);
        }

        byte[] struct = new byte[size];
        buf.readBytes(struct);
        return struct;
    }

    public static void writeStruct(ChannelBuffer buf, byte[] struct) {

        if (struct == null || struct.length == 0) {
            writeOctet(buf, (byte) 0);
            return;
        }

        int size = struct.length;
        if (size < 0xff) {
            writeOctet(buf, (byte) (size & 0xff));
        } else {
            // scape code 0xff required and size greater than 254
            writeOctet(buf, (byte) 0xff);
            writeWord(buf, size);
        }

        buf.writeBytes(struct);
    }

    public static void writeOctet(ChannelBuffer buf, byte octet) {
        buf.writeByte(octet);
    }

    public static void writeWord(ChannelBuffer buf, int word) {
        writeWord(buf, (short) (word & 0xffff));
    }
    public static void writeWord(ChannelBuffer buf, short word) {
        byte leastSignificant = (byte) (word & 0xff);
        byte mostSignificant = (byte) ((word >> 8) & 0xff);
        buf.writeByte(leastSignificant);
        buf.writeByte(mostSignificant);
    }

    public static void writeDword(ChannelBuffer buf, int dword) {
        short leastSiginificantWord = (short) (dword & 0xffff);
        short mostSiginificantWord = (short) ((dword >> 16) & 0xffff);
        writeWord(buf, leastSiginificantWord);
        writeWord(buf, mostSiginificantWord);
    }

    public static void writeQword(ChannelBuffer buf, long qword) {
        int leastSiginificantDword = (int) (qword & 0xffffffff);
        int mostSiginificantDword = (int) ((qword >> 32) & 0xffffffff);
        writeDword(buf, leastSiginificantDword);
        writeDword(buf, mostSiginificantDword);
    }

    public static String capiString(byte[] octets) {

        if (octets == null) {
            return null;
        }

        // find endOctet
        int endOctet = -1;
        for (int i = 0; i < octets.length; i++) {
            byte b = octets[i];
            if (b == 0) {
                endOctet = i;
                break;
            }
        }

        // construct the ASCII string
        try {
            if (endOctet >= 0) {
                return (new String(octets, 0, endOctet, CAPI_STRING_ENCODING));
            } else {
                return (new String(octets, CAPI_STRING_ENCODING));                
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("capiString()", CAPI_STRING_ENCODING);
            return null;
        }

    }

    private CapiBuffers() { }

}
