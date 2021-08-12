package dev.cerus.sharedmem;

import java.util.Arrays;

public class Test {

    public static void main(final String[] args) {
        final NativeMemoryMappedFile file = new NativeMemoryMappedFile("Local\\\\test_map");
        file.open(MemoryMappedFile.OpenMode.OPEN, MemoryMappedFile.RWMode.READ_ONLY, 0);
        final byte[] read = file.read(0, -1);
        file.close();
        final byte[] read2 = file.read(0, -1);

        System.out.println(Arrays.toString(read));
        System.out.println(Arrays.toString(read2));
    }

}
