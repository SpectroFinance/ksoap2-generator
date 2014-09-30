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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Class to load the libraries.
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class GeneratorLoader {

    /**
     * The loader.
     */
    private static URLClassLoader loader;

    /**
     * Prohibits instantiation.
     */
    private GeneratorLoader() {
    }

    /**
     * Gets class loader with the libraries.
     *
     * @return The class loader.
     * @throws GeneratorException
     *              The generation exception.
     */
    @SuppressWarnings("deprecation")
    public static ClassLoader getLoader() throws GeneratorException {
        if (loader == null) {
            String [] keys = new String[] {"axis", "logging", "discovery",
                    "activation", "stax", "mail", "wsdl4j", "jaxrpc", "saaj"};
            String userhome = System.getProperty("user.home");
            char separatorChar = java.io.File.separatorChar;
            String m2repo = ".m2" + separatorChar + "repository";
            int len = keys.length;
            URL [] urls = new URL[len];
            for (int i = 0; i < len; i++) {
                String key = keys[i];
                String value = System.getProperty(key);
                String [] arr = value.split(":");
                if (arr.length != 3) {
                    throw new GeneratorException();
                }
                StringBuffer pathBuffer = new StringBuffer(userhome).append(
                        separatorChar).append(m2repo).append(separatorChar)
                        .append(arr[0].replace('.', separatorChar))
                        .append(separatorChar).append(arr[1])
                        .append(separatorChar).append(arr[2])
                        .append(separatorChar).append(arr[1]).append('-')
                        .append(arr[2]).append(".jar");
                File file = new File(pathBuffer.toString());
                try {
                    urls[i] = file.toURL();
                } catch (MalformedURLException e) {
                    throw new GeneratorException(e);
                }
            }
            loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
        }
        return loader;
    }

}
