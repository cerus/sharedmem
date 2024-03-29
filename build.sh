#!/bin/sh

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk/

# Compile native
echo "[C++]"
./build_native.sh
mkdir src/main/resources/lib
mv target/libsharedmem.so src/main/resources/lib
rm target/dev_cerus_sharedmem_NativeMemoryMappedFile.o

# Compile Java
echo "[JAVA]"
echo "Compiling..."
mvn clean package