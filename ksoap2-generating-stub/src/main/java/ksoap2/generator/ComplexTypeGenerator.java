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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class to generate complex type.
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class ComplexTypeGenerator extends AbstractGenerator {

    /**
     *
     */
    private static int char_distance = 'A' - 'a';

    /**
     *
     */
    private static Map <String, String> primitive2ObjectMappings =
            new HashMap <String, String>();

    static {
        primitive2ObjectMappings.put("boolean", "Boolean");
        primitive2ObjectMappings.put("byte", "Byte");
        primitive2ObjectMappings.put("short", "Short");
        primitive2ObjectMappings.put("int", "Integer");
        primitive2ObjectMappings.put("long", "Long");
        primitive2ObjectMappings.put("float", "Float");
        primitive2ObjectMappings.put("double", "Double");
    }

    /**
     *
     */
    private static Map <String, String> object2PrimitiveMappings =
            new HashMap <String, String> ();

    static {
        object2PrimitiveMappings.put("boolean", "\"true\".equals");
        object2PrimitiveMappings.put("byte", "Byte.parseByte");
        object2PrimitiveMappings.put("short", "Short.parseShort");
        object2PrimitiveMappings.put("int", "Integer.parseInt");
        object2PrimitiveMappings.put("long", "Long.parseLong");
        object2PrimitiveMappings.put("float", "Float.parseFloat");
        object2PrimitiveMappings.put("double", "Double.parseDouble");
    }

    /**
     * Ignored attributes.
     */
    private static final String [] ignoredAttributes = {"__equalsCalc", "__hashCodeCalc", "typeDesc"};

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
    public ComplexTypeGenerator(List<String> serviceClasses, final Class<?> clazz, Writer writer, final String generatedFolder) {
        super(serviceClasses, clazz, writer, generatedFolder);
    }

    /**
     * Runs this method to generate classes in J2ME based on them in J2SE.
     *
     * @throws GeneratorException
     *              The generation exception.
     * @see AbstractGenerator#run()
     */

    @Override
    protected void run() throws GeneratorException {
        super.run();
    }

    /**
     *
     * {@link AbstractGenerator#writeClass(Class)}
     */
    @Override
    protected void writeClass(Class<?> clazz) throws GeneratorException {
        writeConstructor(clazz);
	    writeConstructorWithParameters(clazz);
        writeSetGetMethods(clazz);
        writeSpecialMethodsOnSoapObject(clazz);
    }

    /**
     * {@link AbstractGenerator#writeImportedClasses(Class, Writer)}
     */
    @Override
    protected void writeImportedClasses(Class<?> clazz, Writer writer) throws GeneratorException {
        Util.checkNull(clazz, writer);
        writer.append("import java.util.Hashtable;\n");
        writer.append("import org.ksoap2.serialization.PropertyInfo;\n");
        writer.append("import org.ksoap2.serialization.SoapObject;\n\n");
    }

    /**
     * Declares the class.
     *
     * @param clazz
     *              The class which is taken by Java reflection API.
     * @throws GeneratorException
     *              The generation exception.
     */

    @Override
    protected final void writeClassDeclaration(final Class <?> clazz) throws GeneratorException {
        Writer writer = getWriter();
        Util.checkNull(clazz, writer);
        writer.append("public final class " + clazz.getSimpleName() + " extends SoapObject {\n\n");
    }

    /**
     * Outs the declared attributes. This method is overridden due to generated
     * attributes, for example: __equalsCalc, __hashCodeCalc, typeDesc.
     *
     * @param declaredFields
     *              The declared fields.
     * @throws GeneratorException
     *              The generation exception.
     */
    @Override
    protected final void writeAttributes(final Field declaredFields []) throws GeneratorException {

        Writer writer = getWriter();
        Util.checkNull(declaredFields, writer);
        for (Field declaredField : declaredFields) {
            if (!isIgnored(declaredField.getName())) {
                writer.append("    ");
                writer.append(getModifier(declaredField.getModifiers()) + declaredField.getType().getCanonicalName() + " " + declaredField.getName() + ";\n");
            }
        }
	    writer.append("\n");
    }

    /**
     *
     * @param attrName
     *              The attribute name.
     * @return <tt>true</tt> if the attribute is ignored, and <tt>false</tt>
     * otherwise.
     */
    private boolean isIgnored(final String attrName) {
        for (String ignored : ignoredAttributes) {
            if (ignored.equals(attrName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Writes constructor.
     *
     * @param clazz
     *              The class.
     */
    private void writeConstructor(final Class <?> clazz) {
        Writer writer = getWriter();
        writer.append("    public " + clazz.getSimpleName() + "() {\n");
        writer.append("    }\n\n");
    }

	private void writeConstructorWithParameters(final Class <?> clazz) {
		Writer writer = getWriter();

			writer.append("    public " + clazz.getSimpleName() + "(SoapObject soapObject) {\n");
			writer.append("         int _len = soapObject.getPropertyCount();\n");
			writer.append("         for (int _i = 0; _i < _len; _i++) {\n");
			writer.append("             setProperty(_i, soapObject.getProperty(_i));\n");
			writer.append("         }\n");
			writer.append("    }\n\n");
	}


	private boolean isServiceClass(String className){
		for (String serviceClass : serviceClasses) {
			if(serviceClass.equals(className)){
				return true;
			}
		}
		return false;
	}
    /**
     * Only outs the 'set' and 'get' methods for attributes.
     *
     * @param clazz
     *              The class.
     */
    private void writeSetGetMethods(final Class <?> clazz) {
        Writer writer = getWriter();
        Field [] attributes = clazz.getDeclaredFields();
        if (attributes == null) {
            return;
        }
        for (Field attribute : attributes) {
            String name = attribute.getName();
            if (isIgnored(name)) {
                continue;
            }
            writer.append("    public void set"
                    + getCapsChar(name.charAt(0)) + name.substring(1) + "("
                    + attribute.getType().getCanonicalName() + " " + name
                    + ") {\n");
            writer.append("        this." + name + " = " + name + ";\n");
            writer.append("    }\n\n");
            writer.append("    public " + attribute.getType().getCanonicalName()
                    + " get" + getCapsChar(name.charAt(0)) + name.substring(1)
                    + "(" + attribute.getType().getCanonicalName() + " " + name
                    + ") {\n");
            writer.append("        return this." + name + ";\n");
            writer.append("    }\n\n");
        }
    }

    /**
     * Outs the special methods to extend SoapObject.
     *
     * @param clazz
     *              The class.
     */
    private void writeSpecialMethodsOnSoapObject(final Class <?> clazz) {
        writeGetPropertyCount(clazz);
        writeGetProperty(clazz);
        writeSetProperty(clazz);
        writeGetPropertyInfo(clazz);
    }

    /**
     * Outs the getPropertyCount method.
     *
     * @param clazz
     *              The class.
     */
    private void writeGetPropertyCount(final Class <?> clazz) {
        Writer writer = getWriter();
        // public int getPropertyCount()
        writer.append("    public int getPropertyCount() {\n");
        writer.append("        return " + (clazz.getDeclaredFields().length - ignoredAttributes.length) + ";\n");
        writer.append("    }\n\n");
    }

    /**
     * Outs the getProperty method.
     *
     * @param clazz
     *              The class.
     */
    private void writeGetProperty(final Class <?> clazz) {
        Writer writer = getWriter();
        writer.append("    public Object getProperty(int __index) {\n");
        writer.append("        switch(__index)  {\n");
        int index = 0;
        for (Field attribute : clazz.getDeclaredFields()) {
            if (isIgnored(attribute.getName())) {
                continue;
            }
            if (attribute.getType().isPrimitive()) {
                writer.append("        case " + index + ": return new "
                        + primitive2ObjectMappings.get(attribute.getType()
                                .getCanonicalName()) + "(" + attribute.getName()
                                + ");\n");
            } else if (!attribute.getType().isArray()) { // not array
                /*
                 * In the case where there are attributes are complex type,
                 * need to verify this code?
                 */
                writer.append("        case " + index + ": return "
                        + attribute.getName() + ";\n");
            } else { // array
                
            }
            index++;
        }
        writer.append("        }\n");
        writer.append("        return null;\n");
        writer.append("    }\n\n");
    }

    /**
     * Outs the setProperty method.
     *
     * @param clazz
     *              The class.
     */
    private void writeSetProperty(final Class <?> clazz) {
        Writer writer = getWriter();
        writer.append("    public void setProperty(int __index, Object __obj) {\n");
        writer.append("        switch(__index)  {\n");
        int index = 0;
	    int soapObjectIndex = 1;
        for (Field attribute : clazz.getDeclaredFields()) {
            if (isIgnored(attribute.getName())) {
                continue;
            }
            if (attribute.getType().isPrimitive()) {
                writer.append("        case " + index + ": " + attribute.getName() + " = " + object2PrimitiveMappings.get(attribute.getType().getCanonicalName()) + "(__obj.toString()); break;\n");
            } else if (!attribute.getType().isArray()) { // not array
                /*
                 * In the case where there are attributes are complex type,
                 * need to verify this code?
                 */
	            String canonicalName = attribute.getType().getCanonicalName();
	            if(!attribute.getType().isPrimitive()){
		            canonicalName = canonicalName.replace(attribute.getType().getPackage().getName() + ".", "");
	            }
	            if(isServiceClass(canonicalName)){
		            writer.append("             case " + index + ": " + "SoapObject soapObject_" + soapObjectIndex + " = (SoapObject) __obj;" + attribute.getName() + " = new " + attribute.getType().getCanonicalName() + "(soapObject_" + soapObjectIndex + "); break;\n");
		            soapObjectIndex ++;
	            } else {
		            if(attribute.getType() == BigDecimal.class || attribute.getType() == BigInteger.class){
			            writer.append("             case " + index + ": " + attribute.getName() + " = new " + attribute.getType().getCanonicalName() + "(__obj.toString()); break;\n");
		            } else if(attribute.getType() == Object.class){
			            writer.append("             case " + index + ": " + attribute.getName() + " = __obj; break;\n");
		            } else if(attribute.getType() == Date.class){

			            writer.append("             case " + index + ":\n");
			            writer.append("                 try {\n");
			            writer.append("                     java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss\");\n");
			            writer.append("                     " + attribute.getName() + " = dateFormat.parse(__obj.toString()); break;\n");
			            writer.append("                 } catch (java.text.ParseException e) {}\n");


		            } else {
			            writer.append("             case " + index + ": " + attribute.getName() + " = " + attribute.getType().getCanonicalName() + ".valueOf(__obj.toString()); break;\n");
		            }
	            }
            } else { // array
                
            }
            index++;
        }
        writer.append("        }\n");
        writer.append("    }\n\n");
    }

    /**
     * Outs the getPropertyInfo method.
     *
     * @param clazz
     *              The class.
     */
    private void writeGetPropertyInfo(final Class <?> clazz) {
        Writer writer = getWriter();
        writer.append("    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {\n");
        writer.append("        switch(__index)  {\n");
        int index = 0;
        for (Field attribute : clazz.getDeclaredFields()) {
            if (isIgnored(attribute.getName())) {
                continue;
            }
            writer.append("             case " + index + ":\n");
            writer.append("            __info.name = \"" + attribute.getName() + "\";\n");
            if (attribute.getType().isPrimitive()) {
                writer.append("            __info.type = " + primitive2ObjectMappings.get(attribute.getType().getCanonicalName()) + ".class; break;\n");
            } else if (!attribute.getType().isArray()) { // not array
                /*
                 * In the case where there are attributes are complex type,
                 * need to verify this code?
                 */
                writer.append("            __info.type = " + attribute.getType().getCanonicalName() + ".class; break;\n");
            } else { // array
                
            }
            index++;
        }
        writer.append("        }\n");
        writer.append("    }\n\n");
    }

    /**
     *
     * @param ch
     *              The input.
     * @return The caps char.
     */
    private char getCapsChar(char ch) {
        if (('a' <= ch) && (ch <= 'z')) {
            return (char) (ch + char_distance);
        }
        return ch;
    }
}
