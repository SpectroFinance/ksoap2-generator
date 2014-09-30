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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to generate classes in J2ME based on them in J2SE.
 *
 * @author Cong Kinh Nguyen
 *
 */
public abstract class AbstractGenerator {

    /**
     * Attribute to contain class input.
     */
    private Class <?> clazz;
	List<String> serviceClasses;
    /**
     * Attribute to contain the writer.
     */
    private Writer writer;

    /**
     * The generated folder to store generated code.
     */
    private String generatedFolder;

    /**
     * Public constructor.
     *
     * @param serviceClasses
     * @param clazz
     *              The class.
     * @param writer
 *              The writer.
     * @param generatedFolder
     */
    public AbstractGenerator(List<String> serviceClasses, final Class<?> clazz, Writer writer, final String generatedFolder) {
        this.clazz = clazz;
        this.writer = writer;
        this.generatedFolder = generatedFolder;
	    getClassNames(serviceClasses);
    }

	private void getClassNames(List<String> classFiles) {

		this.serviceClasses = new ArrayList<String>();

		for (String serviceClass : classFiles) {
			String[] split = serviceClass.split("\\\\");
			String fileName = split[split.length - 1];
			serviceClasses.add(fileName.replace(".java", ""));
		}
	}

	/**
     * Runs this method to generate classes in J2ME based on them in J2SE.
     *
     * @throws GeneratorException
     *              The generation exception.
     */
    protected void run() throws GeneratorException {
        Util.checkNull(clazz, writer);
        writePackage(clazz);
        writeImportedClasses(clazz, writer);
        writeClassDeclaration(clazz);
        writeAttributes(clazz.getDeclaredFields());
        writeClass(clazz);
	    writeCustomMethods(writer);
        writeClassClose();
        FileManager.createFileInJ2me(clazz, writer, generatedFolder);
    }

	protected void writeCustomMethods(Writer writer){

	}

	/**
     * Outs class's package.
     *
     * @param clazz
     *              The class.
     */
    private void writePackage(final Class <?> clazz) {
        String classname = clazz.getName();
        int index = classname.lastIndexOf('.');
        if (index >= 0) {
            String packageName = classname.substring(0, index);
            writer.append("package " + packageName + ";\n\n");
        }
    }

    /**
     * Outs imported classes.
     *
     * @param clazz
     *              The class.
     * @param writer
     *              The writer.
     * @throws GeneratorException
     *              The generation exception.
     */
    protected abstract void writeImportedClasses(final Class <?> clazz, Writer writer) throws GeneratorException;

    /**
     * Declares the class.
     *
     * @param clazz
     *              The class which is taken by Java reflection API.
     * @throws GeneratorException
     *              The generation exception.
     */
    protected void writeClassDeclaration(final Class <?> clazz) throws GeneratorException {
        Util.checkNull(clazz);
        writer.append("public final class " + clazz.getSimpleName() + " {\n\n");
    }

    /**
     * Outs the declared attributes.
     *
     * @param declaredFields
     *              The declared fields.
     * @throws GeneratorException
     *              The generation exception.
     */
    protected void writeAttributes(final Field declaredFields []) throws GeneratorException {
        Util.checkNull(declaredFields);
        for (Field declaredField : declaredFields) {
            writer.append(getModifier(declaredField.getModifiers())
                    + declaredField.getType().getCanonicalName()
                    + " " + declaredField.getName() + ";\n");
        }
    }

    /**
     *
     * @param modifier
     *              The modifier value.
     * @return one of three values: <tt>private</tt>, <tt>protected</tt>,
     * and <tt>public</tt>.
     */
    protected final String getModifier(int modifier) {
        if (modifier == Modifier.PRIVATE) {
            return "private ";
        } else if (modifier == Modifier.PROTECTED) {
            return "protected ";
        } else {
            return "public ";
        }
    }

    /**
     * Implements this method to generate classes in J2Me based on them in J2SE.
     * What we have to implement is the constructor(s) and the method(s).
     *
     * @param clazz
     *              The class.
     * @throws GeneratorException
     *              The generation exception.
     */
    protected abstract void writeClass(final Class <?> clazz) throws GeneratorException;

    /**
     * Outs the class's close.
     */
    private void writeClassClose() {
        writer.append("}\n");
    }

    /**
     *
     * @return The input class of the constructor.
     */
    public final Class<?> getClazz() {
        return clazz;
    }

    /**
     *
     * @return The input writer of constructor.
     */
    public final Writer getWriter() {
        return writer;
    }

    /**
     *
     * @return The path to store generated code.
     */
    public final String getGeneratedFolder() {
        return this.generatedFolder;
    }
}
