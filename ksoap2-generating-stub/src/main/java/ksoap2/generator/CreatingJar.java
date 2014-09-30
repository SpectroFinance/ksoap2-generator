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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Class to create jar archive from folder contains classes.
 *
 * @author Cong Kinh Nguyen
 *
 */
public final class CreatingJar {

    /**
     * Prohibits instantiation.
     */
    private CreatingJar() {
    }

    /**
     * Creates jar archive.
     * <p>
     * @param folder
     *              The folder contains packages.
     * @param jarArchive
     *              The jar archive.
     * @throws GeneratorException
     *              The generation exception.
     */
    public static void run(final String folder, final String jarArchive) throws IOException {

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        File file = new File(jarArchive);
        FileOutputStream output = new FileOutputStream(file);
        JarOutputStream target = new JarOutputStream(output, manifest);
        add(new File(folder), target, folder, file.getCanonicalPath());
        target.close();
    }
    
    /**
     * 
     * @param source
     *              The source is either directory or file.
     * @param target
     *              The jar object.
     * @param folder
     *              The folder contains package which needs to be packaged.
     * @param jarArchive
     *              The canonical file name.
     * @throws IOException
     *              The exception.
     */
    private static void add(File source, JarOutputStream target,  final String folder, final String jarArchive) throws IOException {
        BufferedInputStream in = null;
        try {
            String canonicalPath = source.getCanonicalPath();
            String name = null;
            int index = canonicalPath.indexOf(folder);
            if (index >= 0) {
                name = canonicalPath.substring(index + folder.length());
                if (name.startsWith(File.separatorChar + "")) {
                    name = name.substring(1);
                }
            } else {
                name = canonicalPath;
            }
            name = name.replace("\\", "/");
            if (source.isDirectory())
            {
                if (!name.isEmpty())
                {
                    if (!name.endsWith("/"))
                        name += "/";
                    JarEntry entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile: source.listFiles())
                    add(nestedFile, target, folder, jarArchive);
                return;
            }
            // ignore itself the jarAchive.
            if (source.getCanonicalPath().equals(jarArchive)) {
                return;
            }
            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));
            byte[] b = new byte[1024];
            while (true) {
                int readBytes = in.read(b);
                if (readBytes == -1)
                    break;
                target.write(b, 0, readBytes);
            }
            target.closeEntry();
        }
        finally
        {
            if (in != null) {
                in.close();
            }
        }
    }
}
