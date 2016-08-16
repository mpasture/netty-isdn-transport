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
package org.neociclo.capi20.message;

import static java.lang.String.format;
import static org.neociclo.capi20.message.MessageType.CONNECT_B3_RESP;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeNcci;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeNcpi;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeReject;
import net.sourceforge.jcapi.message.parameter.NCCI;
import net.sourceforge.jcapi.message.parameter.NCPI;

import org.jboss.netty.buffer.ChannelBuffer;
import org.neociclo.capi20.parameter.Reject;

/**
 * @author Rafael Marins
 */
public class ConnectB3Resp extends SendMessage {

    private NCCI ncci;
    private Reject response;
    private NCPI ncpi;

    public ConnectB3Resp() {
        super(CONNECT_B3_RESP);
    }

    public NCCI getNcci() {
        return ncci;
    }

    public void setNcci(NCCI ncci) {
        this.ncci = ncci;
    }

    public Reject getResponse() {
        return response;
    }

    public void setResponse(Reject response) {
        this.response = response;
    }

    public NCPI getNcpi() {
        return ncpi;
    }

    public void setNcpi(NCPI ncpi) {
        this.ncpi = ncpi;
    }

    // -------------------------------------------------------------------------
    // SendMessage implementation overriding
    // -------------------------------------------------------------------------

    @Override
    protected void writeValues(ChannelBuffer buf) {
        writeNcci(buf, getNcci());
        writeReject(buf, getResponse());
        writeNcpi(buf, getNcpi());
    }
    
    @Override
    public String toString() {
        return format("%s(appID: %d, msgNum: %d, ncci: 0x%04x)", getClass().getSimpleName(), getAppID(),
                getMessageID(), getNcci() != null ? getNcci().getRawValue() : 0);
    }
}
