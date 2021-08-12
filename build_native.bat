@echo off

g++ -c -I%JAVA_HOME%\\include -I%JAVA_HOME%\\include\\win32 \
  -Iboost/interprocess/include \
  -Iboost/core/include \
  -Iboost/config/include \
  -Iboost/move/include \
  -Iboost/static_assert/include \
  -Iboost/assert/include \
  -Iboost/intrusive/include \
  dev_cerus_sharedmem_NativeMemoryMappedFile.cpp \
  -o dev_cerus_sharedmem_NativeMemoryMappedFile.o
g++ -shared -o target/libsharedmem.dll dev_cerus_sharedmem_NativeMemoryMappedFile.o -Wl,--add-stdcall-alias -lrt -lboost_system