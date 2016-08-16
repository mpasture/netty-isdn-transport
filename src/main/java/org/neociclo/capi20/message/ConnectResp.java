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
import static org.neociclo.capi20.message.MessageType.CONNECT_RESP;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeAdditionalInfo;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeBProtocol;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeConnectedNumber;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeConnectedSubAddress;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeLowLayerCompatibility;
import static org.neociclo.capi20.parameter.ParameterBuffers.writePlci;
import static org.neociclo.capi20.parameter.ParameterBuffers.writeReject;
import net.sourceforge.jcapi.message.parameter.AdditionalInfo;
import net.sourceforge.jcapi.message.parameter.BProtocol;
import net.sourceforge.jcapi.message.parameter.ConnectedNumber;
import net.sourceforge.jcapi.message.parameter.ConnectedSubAddress;
import net.sourceforge.jcapi.message.parameter.LowLayerCompatibility;
import net.sourceforge.jcapi.message.parameter.PLCI;

import org.jboss.netty.buffer.ChannelBuffer;
import org.neociclo.capi20.parameter.Reject;

/**
 * @author Rafael Marins
 */
public class ConnectResp extends SendMessage {

    private PLCI plci;
    private Reject response;
    private BProtocol bProtocol;
    private ConnectedNumber connectedPartyNumber;
    private ConnectedSubAddress connectedPartySubAddress;
    private LowLayerCompatibility lowLayerCompatibility;
    private AdditionalInfo additionalInfo;

    public ConnectResp() {
        super(CONNECT_RESP);
    }

    public PLCI getPlci() {
        return plci;
    }

    public void setPlci(PLCI plci) {
        this.plci = plci;
    }

    public Reject getResponse() {
        return response;
    }

    public void setResponse(Reject response) {
        this.response = response;
    }

    public BProtocol getBProtocol() {
        return bProtocol;
    }

    public void setBProtocol(BProtocol bProtocol) {
        this.bProtocol = bProtocol;
    }

    public ConnectedNumber getConnectedPartyNumber() {
        return connectedPartyNumber;
    }

    public void setConnectedPartyNumber(ConnectedNumber connectedPartyNumber) {
        this.connectedPartyNumber = connectedPartyNumber;
    }

    public ConnectedSubAddress getConnectedPartySubAddress() {
        return connectedPartySubAddress;
    }

    public void setConnectedPartySubAddress(ConnectedSubAddress connectedPartySubAddress) {
        this.connectedPartySubAddress = connectedPartySubAddress;
    }

    public LowLayerCompatibility getLowLayerCompatibility() {
        return lowLayerCompatibility;
    }

    public void setLowLayerCompatibility(LowLayerCompatibility lowLayerCompatibility) {
        this.lowLayerCompatibility = lowLayerCompatibility;
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
        writeReject(buf, getResponse());
        writeBProtocol(buf, getBProtocol());
        writeConnectedNumber(buf, getConnectedPartyNumber());
        writeConnectedSubAddress(buf, getConnectedPartySubAddress());
        writeLowLayerCompatibility(buf, getLowLayerCompatibility());
        writeAdditionalInfo(buf, getAdditionalInfo());
    }

    @Override
    public String toString() {
        return format("%s(appID: %d, msgNum: %d, plci: 0x%04x)", getClass().getSimpleName(), getAppID(),
                getMessageID(), getPlci() != null ?getPlci().getRawValue() : 0);
    }
}
