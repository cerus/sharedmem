package dev.cerus.etstelemetry.sharedmem;

import java.nio.ByteBuffer;

public class NativeMemoryMappedFile implements MemoryMappedFile {

    private final String mapName;

    public NativeMemoryMappedFile(final String mapName) {
        this.mapName = mapName;
    }

    @Override
    public native ByteBuffer read(final int offset, final int length);

}
