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
import static org.neociclo.capi20.parameter.ParameterBuffers.writeAdditionalInfo;
import static org.neociclo.capi20.parameter.ParameterBuffers.writePlci;
import net.sourceforge.jcapi.message.parameter.AdditionalInfo;
import net.sourceforge.jcapi.message.parameter.PLCI;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author Rafael Marins
 */
public class DisconnectReq extends SendMessage {

    private PLCI plci;
    private AdditionalInfo additionalInfo;

    public DisconnectReq() {
        super(MessageType.DISCONNECT_REQ);
    }

    public PLCI getPlci() {
        return plci;
    }

    public void setPlci(PLCI plci) {
        this.plci = plci;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    // -------------------------------------------------------------------------
    // SendMessage implementation overriding
    // -------------------------------------------------------------------------

    @Override
    protected void writeValues(ChannelBuffer buf) {
        writePlci(buf, getPlci());
        writeAdditionalInfo(buf, getAdditionalInfo());
    }

    @Override
    public String toString() {
        return format("%s(appID: %d, msgNum: %d, plci: 0x%04x)", getClass().getSimpleName(), getAppID(),
                getMessageID(), getPlci() != null ?getPlci().getRawValue() : 0);
    }

}
