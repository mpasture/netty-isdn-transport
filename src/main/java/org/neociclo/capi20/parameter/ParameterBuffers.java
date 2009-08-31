/**
 * Neociclo Accord, Open Source B2Bi Middleware
 * Copyright (C) 2005-2009 Neociclo, http://www.neociclo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 */
package org.neociclo.capi20.parameter;

import static org.neociclo.capi20.util.CapiBuffers.*;

import net.sourceforge.jcapi.message.parameter.AdditionalInfo;
import net.sourceforge.jcapi.message.parameter.BProtocol;
import net.sourceforge.jcapi.message.parameter.BearerCapability;
import net.sourceforge.jcapi.message.parameter.CalledPartyNumber;
import net.sourceforge.jcapi.message.parameter.CalledPartySubAddress;
import net.sourceforge.jcapi.message.parameter.CallingPartyNumber;
import net.sourceforge.jcapi.message.parameter.CallingPartySubAddress;
import net.sourceforge.jcapi.message.parameter.ConnectedNumber;
import net.sourceforge.jcapi.message.parameter.ConnectedSubAddress;
import net.sourceforge.jcapi.message.parameter.Controller;
import net.sourceforge.jcapi.message.parameter.HighLayerCompatibility;
import net.sourceforge.jcapi.message.parameter.LowLayerCompatibility;
import net.sourceforge.jcapi.message.parameter.NCCI;
import net.sourceforge.jcapi.message.parameter.NCPI;
import net.sourceforge.jcapi.message.parameter.PLCI;

import org.jboss.netty.buffer.ChannelBuffer;
import org.neociclo.capi20.message.MessageType;

/**
 * @author Rafael Marins
 * @version $Rev$ $Date$
 */
public class ParameterBuffers {

    public static ConnectedSubAddress readConnectedSubAddress(ChannelBuffer buf) {
        byte[] elements = readStruct(buf);
        if (elements == null) {
            return null;
        }
        return new ConnectedSubAddress(elements);
    }

    public static ConnectedNumber readConnectedNumber(ChannelBuffer buf) {
        byte[] elements = readStruct(buf);
        if (elements == null) {
            return null;
        }
        return new ConnectedNumber(elements);
    }

    public static Reason readReason(ChannelBuffer buf) {
        return Reason.valueOf(readWord(buf));
    }

    public static Info readInfo(ChannelBuffer buf) {
        return Info.valueOf(readWord(buf));
    }

    public static NCCI readNcci(ChannelBuffer buf) {
        return new NCCI(readDword(buf));
    }

    public static void writeNcci(ChannelBuffer buf, NCCI ncci) {
        if (ncci == null) {
            writeDword(buf, 0);
            return;
        }
        writeDword(buf, ncci.getRawValue());
    }

    public static NCPI readNcpi(ChannelBuffer buf, B3Protocol b3, MessageType messageType) {
        byte[] elements = readStruct(buf);
        if (elements == null) {
            return null;
        }
        int b3Value = b3Protocol(b3);
        return new NCPI(elements, b3Value, messageType.intValue());
    }

    public static int b3Protocol(B3Protocol b3) {
        if (b3 == null) {
            b3 = B3Protocol.TRANSPARENT;
        }
        return b3.getBitField();
    }

    public static void writeNcpi(ChannelBuffer buf, NCPI ncpi) {
        if (ncpi == null) {
            writeDword(buf, 0);
            return;
        }
        byte[] elements = ncpi.getBytes();
        writeStruct(buf, elements);
    }

    public static PLCI readPlci(ChannelBuffer buf) {
        return new PLCI(readDword(buf));
    }

    public static void writePlci(ChannelBuffer buf, PLCI plci) {
        if (plci == null) {
            writeDword(buf, 0);
            return;
        }

        int dword = (plci.getPlciValue() & 0xff) << 8;

        Controller ctrl = plci.getController();
        if (ctrl != null) {
            dword |= (ctrl.isExternal() ? 0x80 : 0);
            dword |= (ctrl.getController() & 0x7f);
        }

        writeDword(buf, dword);
    }

    public static void writeCalledPartyNumber(ChannelBuffer buf, CalledPartyNumber number) {
        if (number == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = number.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeCallingPartyNumber(ChannelBuffer buf, CallingPartyNumber number) {
        if (number == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = number.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeCalledPartySubAddress(ChannelBuffer buf, CalledPartySubAddress subAddress) {
        if (subAddress == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = subAddress.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeCallingPartySubAddress(ChannelBuffer buf, CallingPartySubAddress subAddress) {
        if (subAddress == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = subAddress.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeAdditionalInfo(ChannelBuffer buf, AdditionalInfo info) {
        if (info == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = info.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeController(ChannelBuffer buf, Controller controller) {
        if (controller == null) {
            writeDword(buf, 0);
            return;
        }
        int dword = (controller.isExternal() ? 0x80 : 0);
        dword |= (controller.getController() & 0x7f);
        writeDword(buf, dword);
    }

    public static void writeCipValue(ChannelBuffer buf, CompatibilityInformationProfile cip) {
        if (cip == null) {
            writeWord(buf, CompatibilityInformationProfile.NO_PREDEFINED_PROFILE.getValue());
            return;
        }
        writeWord(buf, cip.getValue());
    }

    public static void writeBProtocol(ChannelBuffer buf, BProtocol bProtocol) {
        if (bProtocol == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = bProtocol.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeBearerCapability(ChannelBuffer buf, BearerCapability bc) {
        if (bc == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = bc.getBytes();
        writeStruct(buf, elements);
    }

    public static LowLayerCompatibility readLowLayerCompatibility(ChannelBuffer buf) {
        byte[] elements = readStruct(buf);
        if (elements == null) {
            return null;
        }
        return new LowLayerCompatibility(elements);
    }

    public static void writeLowLayerCompatibility(ChannelBuffer buf, LowLayerCompatibility llc) {
        if (llc == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = llc.getBytes();
        writeStruct(buf, elements);
    }

    public static void writeHighLayerCompatibility(ChannelBuffer buf, HighLayerCompatibility hlc) {
        if (hlc == null) {
            writeOctet(buf, EMPTY_STRUCT);
            return;
        }
        byte[] elements = hlc.getBytes();
        writeStruct(buf, elements);
    }


}