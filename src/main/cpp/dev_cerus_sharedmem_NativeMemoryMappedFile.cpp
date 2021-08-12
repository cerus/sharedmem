#include "dev_cerus_sharedmem_NativeMemoryMappedFile.h"
#include "boost/interprocess/shared_memory_object.hpp"
#include "boost/interprocess/mapped_region.hpp"
#include "boost/interprocess/detail/os_file_functions.hpp"
#include <iostream>
#include <string>

using namespace std;
using namespace boost::interprocess;

std::string jstring2string(JNIEnv*, jstring);

shared_memory_object shm;
mapped_region region;

JNIEXPORT jint JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_open  (JNIEnv *env, jobject obj, jstring mapName, jint modeOpen, jint modeRw, jlong capacity) {
    if(!&shm || !&region) {
        return 1;
    }

    boost::interprocess::mode_t rwMode;
    if(modeRw == 0) {
        rwMode = read_only;
    } else {
        rwMode = read_write;
    }

    try {
        // This is really ugly. Is there a way to make this better?
        if(modeOpen == 0) {
            shm = shared_memory_object(open_only, jstring2string(env, mapName).c_str(), rwMode);
        } else if(modeOpen == 1) {
            shm = shared_memory_object(create_only, jstring2string(env, mapName).c_str(), rwMode);
        } else if(modeOpen == 2) {
            shm = shared_memory_object(open_or_create, jstring2string(env, mapName).c_str(), rwMode);
        }
        region = mapped_region(shm, rwMode);

        if(modeOpen != 0) {
            // Truncate shm after creation
		    shm.truncate(capacity);
        }
    } catch (const boost::interprocess::interprocess_exception& e) {
        std::cout << e.what() << std::endl;
        return 2;
    }
    return 0;
}

JNIEXPORT jint JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_closeNative (JNIEnv *env, jobject obj) {
    if(&shm || &region) {
        return 1;
    }

    region.~mapped_region();
    shm.~shared_memory_object();
    return 0;
}

JNIEXPORT jbyteArray JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_readNative  (JNIEnv *env, jobject thisObj, jint offset, jint len) {
    int actualLen;
    if(len == -1) {
        actualLen = region.get_size();
    } else {
        actualLen = len;
    }

    jbyteArray result = (env)->NewByteArray(actualLen);
    if (result == NULL) {
        return NULL;
    }

    jbyte a[actualLen];
    char *mem = static_cast<char*>(region.get_address())+offset;
    for (int i = 0; i < actualLen; i++) {
        a[i] = *mem++;
    }
    env->SetByteArrayRegion (result, 0, actualLen, a);

    return result;
}

JNIEXPORT jint JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_writeNative  (JNIEnv *env, jobject obbj, jint offset, jbyteArray data) {
    size_t length = (size_t) env->GetArrayLength(data);
    jbyte* pBytes = env->GetByteArrayElements(data, NULL);

    std::memcpy(region.get_address(), (char*) pBytes, length);

    env->ReleaseByteArrayElements(data, pBytes, JNI_ABORT);
    return 0;
}

// Author: Slerte
// Source: https://stackoverflow.com/a/41820336/10821925
std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}