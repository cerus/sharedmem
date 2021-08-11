#!/bin/sh

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk/

# Compile
echo "Compiling..."
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux \
  src/main/cpp/dev_cerus_etstelemetry_sharedmem_NativeMemoryMappedFile.cpp \
  -o target/dev_cerus_etstelemetry_sharedmem_NativeMemoryMappedFile.o

# Link
echo "Linking..."
g++ -shared -fPIC -o target/libtelemets.so target/dev_cerus_etstelemetry_sharedmem_NativeMemoryMappedFile.o -lc
