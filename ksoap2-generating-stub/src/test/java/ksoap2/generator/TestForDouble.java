/**
 Ksoap2-generator-stub: the generator to generate web services client using
 ksoap2 (http://ksoap2.sourceforge.net/) on J2ME/CLDC 1.1.
 
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

import org.junit.Test;

/**
 * Tests web services using 'double' for the parameter types or return type.
 * 
 * @author Cong Kinh Nguyen
 *
 */
public class TestForDouble extends AbstractTest {

    @Test
    public void generate() throws Exception {
        String [] args = new String[] {"-w", prefixPath + separatorChar
                + "ForDouble.wsdl", "-g", System.getProperty("user.dir")
                + separatorChar + "target" + separatorChar
                + "generate-test-sources"};
        ClassLoader loader = GeneratorLoader.getLoader();
        Thread.currentThread().setContextClassLoader(loader);
        ServiceClientGenerator.HTTP_TRANSPORT = ServiceClientGenerator
                .HTTP_TRANSPORT_J2ME;
        new Wsdl2J2me(args).run();
    }
}
