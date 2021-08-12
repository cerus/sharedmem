package dev.cerus.sharedmem;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Test {

    public static void main(final String[] args) throws InterruptedException {
        final MemoryMappedFile file = MemoryMappedFile.of("Local\\taest_map");
        file.open(MemoryMappedFile.OpenMode.CREATE_OR_OPEN, MemoryMappedFile.RWMode.READ_WRITE, 32);

        for (int i = 1; i < 20; i++) {
            if (i % 2 == 0) {
                final int offset = ThreadLocalRandom.current().nextInt(0, 32) + 30;
                System.out.println(offset);
                file.write(offset, new byte[] {1, 2, 3});
            } else {
                final byte[] read = file.read(0, -1);
                System.out.println(Arrays.toString(read));
            }

            Thread.sleep(5000);
        }

        file.close();

    }

}
