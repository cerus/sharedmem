package dev.cerus.etstelemetry.sharedmem;

import java.nio.ByteBuffer;

public interface MemoryMappedFile {

    ByteBuffer read(int offset, int length);

}
