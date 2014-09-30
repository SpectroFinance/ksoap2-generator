/**
 Ksoap2-generator-stub: the generating to generate web services client using
 ksoap2 (http://ksoap2.sourceforge.net/) in J2ME/CLDC 1.1 and Android
 (http://code.google.com/p/ksoap2-android/).
 
 Copyright: Copyright (C) 2010
 Contact: kinhnc@gmail.com

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 USA 

 Initial developer(s): Cong Kinh Nguyen.
 Contributor(s):
 */

package ksoap2.generator;

import java.util.logging.Logger;

/**
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class Writer {

    /**
     * The string buffer.
     */
    private StringBuffer buffer = new StringBuffer();

    /**
     * Java logger.
     */
    private Logger logger = Logger.getLogger(Writer.class.getName());

    public void append(final String str) {
        if (str != null) {
            buffer.append(str);
        }
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        logger.info("the content of class:");
        logger.info(buffer.toString());
        return buffer.toString();
    }
}
