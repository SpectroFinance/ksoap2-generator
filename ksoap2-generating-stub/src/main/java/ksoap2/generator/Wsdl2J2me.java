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

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import org.apache.axis.wsdl.WSDL2Java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.Remote;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class to generate code in J2ME from the WSDL document.
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class Wsdl2J2me {

    /**
     * The arguments.
     */
    private String [] args;

    /**
     *
     */
    private JSAP jsap;

    /**
     *
     */
    private JSAPResult config;

    /**
     * The separator char.
     */
    private final char separatorChar = java.io.File.separatorChar;

    /**
     * The default folder to generate web services client in J2ME.
     */
    private final String DEFAULT_FOLDER = System.getProperty("user.dir") + separatorChar + "target";

    /**
     * The temp generated folder will be created and deleted while executing
     * the program.
     */
    private final String TEMP_GEN_FOLDER = System.getProperty("user.dir") + separatorChar + "___tmp__gen__ksoap";

    /**
     * The temp compiled folder will be created and deleted while executing
     * the program.
     */
    private final String TEMP_COM_FOLDER = System.getProperty("user.dir") + separatorChar + "___tmp__com__ksoap";

    /**
     * The logger.
     */
    private final Logger logger = Logger.getLogger(Wsdl2J2me.class.getName());

    /**
     * Public constructor.
     *
     * @param args
     *              The arguments.
     */
    public Wsdl2J2me(String [] args) {
        this.args = args;
    }

    /**
     * Runs this method to generate code in J2ME from the WSDL document.
     *
     * @throws Exception
     *              The exception.
     */
    public void run() throws Exception {
        register();
        parseArgs();
        FileManager.removeFolder(TEMP_COM_FOLDER);
        FileManager.removeFolder(TEMP_GEN_FOLDER);
        FileManager.createFolder(TEMP_GEN_FOLDER);
        FileManager.createFolder(TEMP_COM_FOLDER);
        generateCodeInJ2SE();
        new WsCompiler(TEMP_GEN_FOLDER, TEMP_COM_FOLDER).run();
        String tmpJar = FileManager.createTempFile();
        CreatingJar.run(TEMP_COM_FOLDER, tmpJar);
        generateCodeInJ2me(tmpJar);
        FileManager.removeFolder(TEMP_GEN_FOLDER);
        FileManager.removeFolder(TEMP_COM_FOLDER);
    }

    /**
     * Registers the arguments.
     *
     * @throws JSAPException
     *              The exception.
     */
    private void register() throws JSAPException {
        logger.info("register method");
        jsap = new JSAP();
        FlaggedOption wOpt = new FlaggedOption("wsdl")
                .setStringParser(JSAP.STRING_PARSER)
                .setShortFlag('w')
                .setRequired(true);
        jsap.registerParameter(wOpt);
        FlaggedOption genOpt = new FlaggedOption("generatedFolder")
                .setStringParser(JSAP.STRING_PARSER)
                .setShortFlag('g')
                .setRequired(false)
                .setDefault(DEFAULT_FOLDER);
        jsap.registerParameter(genOpt);
    }

    /**
     * Parses the arguments.
     */
    private void parseArgs() {
        logger.info("parseArgs method");
        config = jsap.parse(args);
        if (!config.success()) {
            System.err.println();
            printUsages();
            System.exit(1);
        }
    }

    /**
     * Generates web services client in J2SE.
     *
     */
    private void generateCodeInJ2SE() {
        logger.info("generateCodeInJ2SE method");
        String [] args = new String[] {"-o", TEMP_GEN_FOLDER, config.getString("wsdl")};
        logger.info("arguments: -o " + TEMP_GEN_FOLDER + " " + config.getString("wsdl"));
        WSDL2Java.main(args);
    }

    /**
     * Generates code in J2ME.
     *
     * @param tmpJar
     *              The jar library.
     * @throws GeneratorException
     *              The exception.
     */
    private void generateCodeInJ2me(final String tmpJar) throws GeneratorException {
        logger.info("generateCodeInJ2me method");
        ClassLoader prevCl = Thread.currentThread().getContextClassLoader();
        File file = new File(tmpJar);
        try {
            @SuppressWarnings("deprecation")
            URLClassLoader loader = new URLClassLoader(new URL[] {file.toURL()}, prevCl);
            Thread.currentThread().setContextClassLoader(loader);
            char separatorChar = java.io.File.separatorChar;
            String filetype = ".java";
            List <String> names = FileManager.getFileNames(TEMP_GEN_FOLDER);
            String serviceName = getServiceName(names, TEMP_GEN_FOLDER, loader);
            String generatedFolder = FileManager.getCanonicalPath(config.getString("generatedFolder"));
            Class <?> serviceClass = loader.loadClass(serviceName);
            Class <?> proxyClass = loader.loadClass(getProxyName(names, serviceClass, TEMP_GEN_FOLDER, loader));
            logger.info("web services interface: " + serviceName);
            logger.info("web services proxy: " + proxyClass.getCanonicalName());

	        for (String name : names) {
                int index = name.indexOf(TEMP_GEN_FOLDER);
                if (index < 0) {
                    throw new GeneratorException();
                }
                String className = name.substring(index + TEMP_GEN_FOLDER.length() + 1).replace(separatorChar, '.');
                // ignore .java
                className = className.substring(0, className.length() - filetype.length());
                Class <?> clazz = loader.loadClass(className);
                if (className.equals(serviceName)) {
	                new WsClientGenerator(names, clazz, proxyClass, true, generatedFolder).run();
                } else {
                    if (javax.xml.rpc.Service.class.isAssignableFrom(clazz)
                            || org.apache.axis.client.Stub.class.isAssignableFrom(clazz)
		                    || org.apache.axis.client.Service.class.isAssignableFrom(clazz)
                            ) {
                        continue;
                    }
                    logger.info("class name to generate code: " + className);
	                new WsClientGenerator(names, clazz, proxyClass, false, generatedFolder).run();
                }
            }
        } catch (MalformedURLException e) {
            throw new GeneratorException(e);
        } catch (ClassNotFoundException e) {
            throw new GeneratorException(e);
        } catch (Exception e) {
	        e.printStackTrace();
        } finally {
            Thread.currentThread().setContextClassLoader(prevCl);
        }
    }

   private String getServiceName(final List <String> names, final String prefixPath, final ClassLoader loader) throws GeneratorException {

       String filetype = ".java";
       String className = null;
       for (String name : names) {
           int index = name.indexOf(prefixPath);
           if (index >= 0) {
               className = name.substring(index + prefixPath.length() + 1).replace(separatorChar, '.');
               int typeIndex = className.lastIndexOf(filetype);
               if (typeIndex >= 0) {
                   className = className.substring(0, typeIndex);
                   Class<?> clazz;
                   try {
                       clazz = loader.loadClass(className);
                       if ((clazz.isInterface()) && Remote.class.isAssignableFrom(clazz)) {
                           break;
                       }
                   } catch (ClassNotFoundException e) {
                   }
               }
           }
       }
       return className;
   }

   private String getProxyName(final List <String> names, final Class <?> serviceClass, final String prefixPath, final ClassLoader loader) throws GeneratorException {

       String filetype = ".java";
       String className = null;
       for (String name : names) {
           int index = name.indexOf(prefixPath);
           if (index >= 0) {
               className = name.substring(index + prefixPath.length() + 1).replace(separatorChar, '.');
               int typeIndex = className.lastIndexOf(filetype);
               if (typeIndex >= 0) {
                   className = className.substring(0, typeIndex);
                   if (className.equals(serviceClass.getName())) {
                       continue;
                   }
                   Class<?> clazz;
                   try {
                       clazz = loader.loadClass(className);
                       if (serviceClass.isAssignableFrom(clazz)) {
                           break;
                       }
                   } catch (ClassNotFoundException e) {
                   }
               }
           }
       }
       return className;
   }

    /**
     * Prints the usages in the program.
     */
    private void printUsages() {
        System.err.println(jsap.getHelp());
    }

    public static void main(String [] args) throws Exception {
        ServiceClientGenerator.HTTP_TRANSPORT = ServiceClientGenerator.HTTP_TRANSPORT_J2ME;
        InputStream input = Wsdl2J2me.class.getResourceAsStream("logging.properties");
        if (input == null) {
            System.err.println("missing the logging configuration file");
            System.exit(1);
        }
        try {
            LogManager.getLogManager().readConfiguration(input);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Wsdl2J2me(args).run();
    }
}
