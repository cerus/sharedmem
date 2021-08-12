package dev.cerus.sharedmem;

public class NativeMemoryMappedFile implements MemoryMappedFile {

    static {
        LibraryLoader.loadLib("sharedmem");
    }

    private final String mapName;

    public NativeMemoryMappedFile(final String mapName) {
        this.mapName = mapName;
    }

    @Override
    public void open(final OpenMode openMode, final RWMode rwMode, final long capacity) {
        final int open;
        switch (openMode) {
            case OPEN:
                open = 0;
                break;
            case CREATE:
                open = 1;
                break;
            case CREATE_OR_OPEN:
                open = 2;
                break;
            default:
                throw new RuntimeException();
        }

        final int rw;
        switch (rwMode) {
            case READ_ONLY:
                rw = 0;
                break;
            case READ_WRITE:
                rw = 1;
                break;
            default:
                throw new RuntimeException();
        }

        final int openResult = this.open(this.mapName, open, rw, capacity);
        if (openResult == 1) {
            throw new UnsupportedOperationException("File is already open");
        } else if (openResult == 2) {
            throw new IllegalStateException("Internal error: " + this.lastError());
        }
    }

    @Override
    public void close() {
        final int closeResult = this.closeNative();
        if (closeResult == 1) {
            throw new UnsupportedOperationException("File is not open");
        }
    }

    @Override
    public byte[] read(final int offset, final int length) {
        return this.readNative(offset, length);
    }

    @Override
    public void write(final byte[] data) {
        this.write(0, data);
    }

    @Override
    public void write(final int offset, final byte[] data) {
        this.writeNative(offset, data);
    }

    @Override
    public void write(final int srcOffset, final int len, final int dstOffset, final byte[] data) {
        final byte[] arr = new byte[len];
        System.arraycopy(data, srcOffset, arr, 0, len);
        this.writeNative(dstOffset, arr);
    }

    private native byte[] readNative(int offset, int length);

    private native int writeNative(int offset, byte[] data);

    private native int open(String mapName, int openMode, int rwMode, long capacity);

    private native int closeNative();

    private native String lastError();

}
