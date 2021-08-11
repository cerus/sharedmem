package dev.cerus.etstelemetry;

import dev.cerus.etstelemetry.sharedmem.NativeMemoryMappedFile;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Test {

    public static void main(final String[] args) {
        System.loadLibrary("telemets");

        final NativeMemoryMappedFile mappedFile = new NativeMemoryMappedFile("");
        final ByteBuffer buffer = mappedFile.read(0, 6);
        System.out.println(buffer.array().length);
        System.out.println(Arrays.toString(buffer.array()));
    }

}
