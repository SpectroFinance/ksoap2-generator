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

/**
 * Generator Exception class.
 *
 * @author Cong Kinh Nguyen
 *
 */
public class GeneratorException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public GeneratorException() {
        super();
    }

    /**
     * Constructor
     *
     * @param msg
     *              The message exception.
     */
    public GeneratorException(String msg) {
        super(msg);
    }

    /**
     * Constructor
     *
     * @param cause
     *              The cause.
     */
    public GeneratorException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     *
     * @param msg
     *              The message exception.
     * @param cause
     *              The cause.
     */
    public GeneratorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
