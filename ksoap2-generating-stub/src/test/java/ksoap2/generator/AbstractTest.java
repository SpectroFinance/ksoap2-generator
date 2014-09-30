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

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * Abstract class.
 *
 * @author Cong Kinh Nguyen
 *
 */
public abstract class AbstractTest {

    /**
     * The separator char, which is '/' in Linux and '\' in Windows. 
     */
    protected static final char separatorChar = java.io.File.separatorChar;

    /**
     * The prefix path of all the WSDL document.
     */
    protected static final String prefixPath = "src" + separatorChar + "test"
            + separatorChar + "resources";

    /**
     * The loader.
     */
    private static ClassLoader prevLoader;

    /**
     * The method is executed before each method test.
     */
    @BeforeClass
    public static final void before() throws Exception {
        prevLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = prevLoader.getResourceAsStream("logging.properties");//new FileInputStream(prefixPath + separatorChar + "logging.properties");

        if (input == null) {
            System.err.println("missing the logging configuration file");
            System.exit(1);
        }
        LogManager.getLogManager().readConfiguration(input);
    }

    /**
     * The method is executed after each method test.
     */
    @AfterClass
    public static final void after() {
        Thread.currentThread().setContextClassLoader(prevLoader);
    }
}
