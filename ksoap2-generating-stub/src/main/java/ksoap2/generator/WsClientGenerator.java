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

import java.util.List;

/**
 * Class to generate stub ws client in J2ME.
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class WsClientGenerator {

    /**
     * The class.
     */
    private Class <?> clazz;

    private Class <?> stubClass;

	List<String> serviceClasses;
    /**
     *
     */
    private boolean isService;

    /**
     * The generated folder.
     */
    private String generatedFolder;

    private Writer writer = new Writer();

    public WsClientGenerator(List<String> serviceClasses, final Class<?> clazz, final Class<?> stubClass, boolean isService, final String generatedFolder) {
        this.clazz = clazz;
        this.stubClass = stubClass;
        this.isService = isService;
        this.generatedFolder = generatedFolder;
	    this.serviceClasses = serviceClasses;
    }

    /**
     * Loads context class loader, and then generates code.
     *
     * @throws GeneratorException
     *              The generation exception.
     */
    protected void run() throws GeneratorException {

	    if(isEnum()){

	    }

        if (isService) {
            new ServiceClientGenerator(clazz, stubClass, writer, generatedFolder).run();
        } else {
            new ComplexTypeGenerator(serviceClasses, clazz, writer, generatedFolder).run();
        }
    }

	private boolean isEnum() {

		clazz.isEnum();


		return false;
	}

}