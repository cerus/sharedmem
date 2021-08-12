package dev.cerus.sharedmem;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Test {

    public static void main(final String[] args) {
        final NativeMemoryMappedFile file = new NativeMemoryMappedFile("Local\\test_map");
        file.open(MemoryMappedFile.OpenMode.CREATE_OR_OPEN, MemoryMappedFile.RWMode.READ_WRITE, 128);
        final byte[] read = file.read(0, -1);

        file.write(ThreadLocalRandom.current().nextInt(0, 125), new byte[] {1, 2, 3});

        file.close();

        System.out.println(Arrays.toString(read));
    }

}
