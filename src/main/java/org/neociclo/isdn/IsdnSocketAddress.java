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
package org.neociclo.isdn;

import java.net.SocketAddress;

/**
 * @author Rafael Marins
 * @version $Rev$ $Date$
 */
public class IsdnSocketAddress extends SocketAddress {

    private static final long serialVersionUID = -838183852292629142L;

    private IsdnAddress address;

    public IsdnSocketAddress(IsdnAddress address) {
        super();
        this.address = address;
    }
    
    public IsdnSocketAddress(String isdnNumber) {
        this(isdnNumber, null);
    }

    public IsdnSocketAddress(String isdnNumber, String isdnSubAddress) {
        super();
        this.address = new IsdnAddress(isdnNumber, isdnSubAddress);
    }

    public IsdnAddress getAddress() {
        return address;
    }

    public String getNumber() {
        return address.getNumber();
    }

    public String getSubAddress() {
        return address.getSubAddress();
    }

    @Override
    public String toString() {
        return getNumber() + (getSubAddress() == null ? "" : ':' + getSubAddress());
    }

	/**
     * @param msn
     * @return
     */
    public static IsdnSocketAddress valueOf(String msn) {
    	if (msn == null) {
    		return null;
    	} else {
    		return new IsdnSocketAddress(msn);
    	}
    }
}
