@echo off

if not exist "boost_1_76_0" (
    echo Could not find boost 1.76.0
    echo Please download boost
	echo https://boostorg.jfrog.io/artifactory/main/release/1.76.0/source/boost_1_76_0.zip
    exit 0
)

mkdir target
x86_64-w64-mingw32-gcc -c -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -Iboost_1_76_0 src\\main\\cpp\\dev_cerus_sharedmem_NativeMemoryMappedFile.cpp -o target\\dev_cerus_sharedmem_NativeMemoryMappedFile.o
x86_64-w64-mingw32-gcc.exe -shared -o target\\libsharedmem.dll target\\dev_cerus_sharedmem_NativeMemoryMappedFile.o -Wl,--add-stdcall-alias -lstdc++ -static-libstdc++ -static-libgcc -static