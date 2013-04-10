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
package org.neociclo.isdn;

import java.net.InetSocketAddress;

import org.neociclo.capi20.Capi;
import org.neociclo.capi20.remote.RemoteCapi;

/**
 * @author Rafael Marins
 */
public class RemoteCapiFactory implements CapiFactory {

    private String host;
    private int port;
    private String user;
    private String password;

    public RemoteCapiFactory(String host, int port) {
        this(host, port, null, null);
    }

    public RemoteCapiFactory(String host, int port, String user, String passwd) {
        super();
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = passwd;
    }

    public Capi getCapi() {
        return new RemoteCapi(new InetSocketAddress(host, port), user, password);
    }

    public void releaseExternalResources() {
        // do nothing
    }

}
