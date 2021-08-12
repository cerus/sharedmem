package dev.cerus.sharedmem;

public interface MemoryMappedFile {

    byte[] read(int offset, int length);

    void write(byte[] data);

    void write(int offset, byte[] data);

    void write(int srcOffset, int len, int dstOffset, byte[] data);

    void open(OpenMode openMode, RWMode rwMode, long capacity);

    void close();

    enum OpenMode {
        OPEN, CREATE, CREATE_OR_OPEN
    }

    enum RWMode {
        READ_ONLY, READ_WRITE
    }

}
