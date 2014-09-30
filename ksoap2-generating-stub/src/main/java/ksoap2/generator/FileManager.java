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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class to manage the manipulation on the file.
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class FileManager {
    /**
     * The separator char.
     */
    private static char separatorChar = File.separatorChar;

    /**
     * The error message about creating folder.
     */
    private static final String FOLDER_CREATION_ERR = "Error for creating folder: ";

    /**
     * Prohibits instantiation.
     */
    private FileManager() {
    }

    /**
     * The method is used to get all file names from <tt>inputFolder</tt>.
     *
     * @param inputFolder
     *              The path to input folder.
     * @return The array of all file names.
     *
     * @throws GeneratorException
     *              The generation exception.
     */
    public static List <String> getFileNames(final String inputFolder) throws GeneratorException {
        Util.checkNull(inputFolder);
        final File file = new File(inputFolder);
        if (!(file.isFile() || file.isDirectory())) {
            throw new GeneratorException();
        }
        List <String> filenames = new LinkedList <String>();
        getFileNames(filenames, file);
        return filenames;
    }

    /**
     * Gets all files and stores them to <tt>files</tt>.
     *
     * @param files
     *              The chain of file names.
     * @param file
     *              The file which contains itself and all the files and
     *              sub-directories accessed from it.
     */
    private static void getFileNames(List <String> filenames, final File file) {
        if (file.isFile()) {
            filenames.add(file.getAbsolutePath());
        } else if (file.isDirectory()) {
            File childFiles [] = file.listFiles();
            for (File childFile : childFiles) {
                getFileNames(filenames, childFile);
            }
        }
    }

    /**
     * 
     * @param input
     *              The input stream.
     * @return The content of input stream.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static String getContent(final InputStream input) throws GeneratorException {
        Util.checkNull(input);
        int readBytes = 0;
        byte b[] = new byte[1024];
        StringBuffer buffer = new StringBuffer();
        try {
            while ((readBytes = input.read(b)) >= 0) {
                String tmp = new String(b, 0, readBytes);
                buffer.append(tmp);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }

    /**
     *
     * @param fullname
     *              The full path to file.
     * @return The array of byte for class needed.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static byte [] loadClassData(final String fullname) throws GeneratorException {
        Util.checkNull(fullname);
        try {
            File file = new File(fullname);
            DataInputStream input = new DataInputStream(new FileInputStream(
                    file));
            int len = (int) file.length();
            byte data [] = new byte[len];
            int readBytes = 0;
            int totalBytes = 0;
            while (true) {
                readBytes = input.read(data, totalBytes, len - totalBytes);
                if ((readBytes < 0) || (len == totalBytes)) {
                    break;
                }
                totalBytes += readBytes;
            }
            return data;
        } catch (FileNotFoundException e) {
            throw new GeneratorException(e);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }

    /**
     * Creates the folder with given path. It also creates parent folders if
     * necessary.
     *
     * @param path
     *              The path. 
     * @throws GeneratorException
     *              The generation exception.
     */
    public static void createFolder(final String path) throws GeneratorException {
        Util.checkNull(path);
        StringTokenizer tokens = new StringTokenizer(path, separatorChar + "");
        StringBuffer buffer = new StringBuffer();
        if (separatorChar == '/') {
            buffer.append(separatorChar);
        }
        while (tokens.hasMoreElements()) {
            buffer.append((String) tokens.nextElement() + separatorChar);
            String subpath = buffer.toString();
            File file = new File(subpath);
            /*
             * if folder doesn't exist and doesn't be created, return false.
             */
            if ((!file.mkdir()) && (!file.exists())) {
                throw new GeneratorException(FOLDER_CREATION_ERR + subpath);
            }
        }
    }

    /**
     * Removes the given directory.
     *
     * @param path
     *              The given directory.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static void removeFolder(final String path) throws GeneratorException {
        Util.checkNull(path);
        File file = new File(path);
        if (file == null) {
            return;
        }
        File [] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File child : listFiles) {
            if (child.isFile()) {
                child.delete();
            } else if (child.isDirectory()) {
                removeFolder(child.getAbsolutePath());
            }
        }
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Creates file in J2ME.
     *
     * @param clazz
     *              The class.
     * @param writer
     *              The writer.
     * @param generatedFolder
     *              The generated folder.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static void createFileInJ2me(final Class <?> clazz, final Writer writer, final String generatedFolder) throws GeneratorException {
        Util.checkNull(clazz, writer, generatedFolder);
        String name = clazz.getName();
        String simpleName = clazz.getSimpleName();
        int index = name.lastIndexOf('.');
        if (index < 0) {
            createFolder(generatedFolder);
            saveFile(generatedFolder + separatorChar + simpleName + ".java",
                    writer);
        } else {
            String packageName = name.substring(0, index).replace('.',
                    separatorChar);
            String dir = generatedFolder + separatorChar + packageName;
            createFolder(dir);
            saveFile(dir + separatorChar + simpleName + ".java", writer);
        }
    }

    /**
     * Creates temp file.
     *
     * @return Path to temp file.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static String createTempFile() throws GeneratorException {
        try {
            return File.createTempFile("__ksoap2", "__wsclientStub.jar")
                    .getCanonicalPath();
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }

    /**
     * Creates the Configuration class.
     *
     * @param serviceName
     *              The service name.
     * @param generatedFolder
     *              The generated folder.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static void copyConf(final String serviceName, final String generatedFolder) throws GeneratorException {

        Util.checkNull(serviceName);
        int index = serviceName.lastIndexOf('.');
        String insertedPackageName = null;
        String path = null;
        String simpleName = "Configuration.java";
        if (index < 0) {
            insertedPackageName = "";
            path = generatedFolder + separatorChar + simpleName;
        } else {
            insertedPackageName = "package " + serviceName.substring(0, index) + ";\n";
            path = generatedFolder + separatorChar + serviceName.substring(0,
                    index).replace('.', separatorChar) + separatorChar
                    + simpleName;
        }
        String packageName = "package ksoap2.generator;"; // defined in Conf
        String content = getContent(FileManager.class.getResourceAsStream("Configuration.txt"));
        content = content.replace(packageName, insertedPackageName);

	    createFolder(path.replace(simpleName, ""));
        saveFile(path, content);
    }

    /**
     *
     * @param generatedFolder
     *              The generated folder.
     * @return Canonical path.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static String getCanonicalPath(final String generatedFolder) throws GeneratorException {
        Util.checkNull(generatedFolder);
        File file = new File(generatedFolder);
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }

    /**
     * Saves content in <tt>writer</tt> into file of given <tt>path</tt>.
     *
     * @param path
     *              The path.
     * @param writer
     *              The writer.
     * @throws GeneratorException
     *              The generation exception.
     */
    private static void saveFile(final String path, final Writer writer) throws GeneratorException {
        saveFile(path, writer.toString());
    }

    /**
     * Saves content in <tt>content</tt> into file of given <tt>path</tt>.
     *
     * @param path
     *              The path.
     * @param content
     *              The content.
     * @throws GeneratorException
     *              The generation exception.
     */
    private static void saveFile(final String path, final String content) throws GeneratorException {
        try {
            DataOutputStream output = new DataOutputStream(new FileOutputStream(path));
            output.writeBytes(content);
        } catch (FileNotFoundException e) {
            throw new GeneratorException(e);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }
}
