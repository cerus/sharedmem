#!/bin/sh

# Compile Java
echo "[JAVA]"
echo "Compiling..."
mvn clean package

# Compile native
echo "[C++]"
./build_native.sh

# Copy lib artifact and clean up
echo "[CLEANUP]"
rm -rf lib
mkdir lib
mv target/libtelemets.so ./lib
rm target/dev_cerus_etstelemetry_sharedmem_NativeMemoryMappedFile.o
echo "lib/libtelemets.so"