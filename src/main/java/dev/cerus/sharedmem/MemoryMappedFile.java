package dev.cerus.sharedmem;

public interface MemoryMappedFile {

    byte[] read(int offset, int length);

    void write(byte[] data);

    void write(int offset, byte[] data);

    /**
     * Attempts to write data to the memory mapped file
     *
     * @param srcOffset
     * @param len
     * @param dstOffset
     * @param data
     */
    void write(int srcOffset, int len, int dstOffset, byte[] data);

    /**
     * Attempts to open this memory mapped file
     *
     * @param openMode OPEN, CREATE or CREATE_OR_OPEN
     * @param rwMode   READ_ONLY or READ_WRITE
     * @param capacity The capacity of the file (only used if openMode is not OPEN)
     */
    void open(OpenMode openMode, RWMode rwMode, long capacity);

    /**
     * Attempts to close the handle to the memory mapped file
     */
    void close();

    enum OpenMode {
        OPEN, CREATE, CREATE_OR_OPEN
    }

    enum RWMode {
        READ_ONLY, READ_WRITE
    }

}
