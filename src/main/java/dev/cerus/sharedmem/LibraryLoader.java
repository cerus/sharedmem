package dev.cerus.sharedmem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LibraryLoader {

    private LibraryLoader() {
    }

    public static void loadLib(final String name) {
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

}
