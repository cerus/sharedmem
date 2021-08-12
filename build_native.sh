#!/bin/sh

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk/

# Preparation
echo "Preparing..."
if ! [ -d boost ]
then
    echo "Downloading Boost library"
    mkdir boost && cd boost

    # This was tedious to figure out
    git clone https://github.com/boostorg/core.git
    git clone https://github.com/boostorg/config.git
    git clone https://github.com/boostorg/move.git
    git clone https://github.com/boostorg/static_assert.git
    git clone https://github.com/boostorg/assert.git
    git clone https://github.com/boostorg/intrusive.git
    git clone https://github.com/boostorg/interprocess.git
    cd ..
fi

# Compile
echo "Compiling..."
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux \
  -Iboost/interprocess/include \
  -Iboost/core/include \
  -Iboost/config/include \
  -Iboost/move/include \
  -Iboost/static_assert/include \
  -Iboost/assert/include \
  -Iboost/intrusive/include \
  src/main/cpp/dev_cerus_sharedmem_NativeMemoryMappedFile.cpp \
  -o target/dev_cerus_sharedmem_NativeMemoryMappedFile.o \
  -lrt -lboost_system

# Link
echo "Linking..."
g++ -shared -fPIC -o target/libsharedmem.so target/dev_cerus_sharedmem_NativeMemoryMappedFile.o -lc -lrt -lboost_system
