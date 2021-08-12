<div style="text-align: center;">
    <pre><code>
███████ ██   ██  █████  ██████  ███████ ██████  ███    ███ ███████ ███    ███
██      ██   ██ ██   ██ ██   ██ ██      ██   ██ ████  ████ ██      ████  ████
███████ ███████ ███████ ██████  █████   ██   ██ ██ ████ ██ █████   ██ ████ ██
     ██ ██   ██ ██   ██ ██   ██ ██      ██   ██ ██  ██  ██ ██      ██  ██  ██
███████ ██   ██ ██   ██ ██   ██ ███████ ██████  ██      ██ ███████ ██      ██
Shared memory for Java
    </code></pre>

   <p align="center"><img src="https://img.shields.io/github/license/cerus/sharedmem" alt="GitHub"> <a href="https://github.com/cerus/sharedmem/issues"><img src="https://img.shields.io/github/issues/cerus/sharedmem" alt="GitHub issues"></a> <a href="https://github.com/cerus/sharedmem/releases/latest"><img src="https://img.shields.io/github/v/release/cerus/sharedmem" alt="GitHub release (latest by date)"></a> <img src="https://img.shields.io/github/stars/cerus/sharedmem" alt="GitHub Repo stars"> <a href="https://github.com/sponsors/cerus"><img src="https://img.shields.io/github/sponsors/cerus" alt="GitHub Sponsors"></a></p>
</div>

## Table of contents

- What is sharedmem?
- Compatibility
- Installation
- Usage
- Examples
- Contributing
- Licenses

## What is sharedmem?

sharedmem allows you to access shared memory / memory mapped files in Java. sharedmem is basically an abstraction layer on top of
the [boost interprocess](https://www.boost.org/doc/libs/1_76_0/doc/html/interprocess.html) library.

> **WARNING**\
> I have basically zero experience with C++ so the native code is probably awful. Someone with more experience than me has to clean that up eventually.

## Compatibility

sharedmem is technically compatible with Linux and Windows, but no native Windows libraries exist yet. You could compile your own though.

## Installation

Simply integrate sharedmem with your favorite build tool into your project, and you are good to go.

Maven:

```xml

<dependencies>
    <dependency>
        <groupId>dev.cerus</groupId>
        <artifactId>sharedmem</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Usage

[Javadocs](https://cerus.dev/api/sharedmem)

Create a new memory mapped file object:\
`MemoryMappedFile mmf = MemoryMappedFile.of("name");`

Don't forget to open before you start reading / writing:\
`mmf.open(MemoryMappedFile.OpenMode.CREATE_OR_OPEN, MemoryMappedFile.RWMode.READ_WRITE, 16);`

Read:\
`byte[] data = mmf.read(0, -1);`

Write:\
`mmf.write(0, new byte[] { 1, 2, 3 }`

In order to load the native library, sharedmem needs to save it to a temporary folder on the disk. If you don't want that you can enable "primitive
loading" by calling `LibraryLoader.enablePrimitiveLoading()`. Ensure that the native library is in Java's library folder if you enable this.

## Examples

```java
class Example {

    public static void main(String[] args) {
        // Opens or creates "Local\\test_map" and writes a sequence of [1, 2, 3] at random places

        // Create and open file
        final MemoryMappedFile file = MemoryMappedFile.of("Local\\test_map");
        long capacity = 32; // capacity = size in bytes - Only used when creating a memory mapped file
        file.open(MemoryMappedFile.OpenMode.CREATE_OR_OPEN, MemoryMappedFile.RWMode.READ_WRITE, capacity);

        // Write sequence and read whole file
        file.write(ThreadLocalRandom.current().nextInt(0, 29), new byte[] {1, 2, 3});
        int length = -1; // -1 if you want to read the whole file
        final byte[] read = file.read(0, length);

        // Close file (will *not* delete/remove the memory mapped file)
        file.close();

        // Print contents
        System.out.println(Arrays.toString(read));

        // Example output after running this for a few times:
        // [0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 3, 1, 2, 3, 0, 0, 0, 1, 2, 3, 0, 0, 0, 1, 2, 3, 0, 1, 2, 3]
    }
}
```

## Licenses

This project is licenses under the [GPL v3 license](LICENSE.txt).

Thirdparty licenses: [Boost](BOOST_LICENSE.txt)