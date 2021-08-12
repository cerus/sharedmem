package dev.cerus.sharedmem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LibraryLoader {

    private static boolean primitiveLoading = false;

    private LibraryLoader() {
    }

    /**
     * Loads the library with the specified name
     * If primitive loading is enabled this just calls {@link System#loadLibrary(String)}
     *
     * @param name The name of the library
     */
    public static void loadLib(final String name) {
        if (primitiveLoading) {
            System.loadLibrary(name);
            return;
        }

        final File tempFolder = new File(System.getProperty("java.io.tmpdir"), "javasharedmem");
        if (tempFolder.exists()) {
            System.load(tempFolder.getAbsolutePath() + File.separator + "lib" + name + ".so");
            return;
        }
        tempFolder.mkdirs();

        final InputStream stream = LibraryLoader.class.getClassLoader().getResourceAsStream("lib/lib" + name + ".so");
        write(stream, new File(tempFolder, "lib" + name + ".so"));
        //stream = LibraryLoader.class.getClassLoader().getResourceAsStream("/lib" + name + ".dll");
        //write(stream, new File(tempFolder, "lib" + name + ".dll"));

        loadLib(name);
    }

    /**
     * Attempts to remove the sharedmem temp folder
     */
    public static void cleanup() {
        final File tempFolder = new File(System.getProperty("java.io.tmpdir"), "javasharedmem");
        if (tempFolder.exists()) {
            // Very primitive folder deletion, assuming that there are no directories in the folder
            for (final File file : tempFolder.listFiles()) {
                file.delete();
            }
            tempFolder.delete();
        }
    }

    private static void write(final InputStream stream, final File file) {
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            final byte[] bytes = stream.readAllBytes();
            System.out.println(bytes.length);
            outputStream.write(bytes);
            outputStream.flush();
            stream.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enabled primitive loading. Call this if you don't want sharedmem to create a temp folder.
     * Requires the presence of the native library in one of the specified Java library folders.
     */
    public static void enablePrimitiveLoading() {
        primitiveLoading = true;
    }

}
