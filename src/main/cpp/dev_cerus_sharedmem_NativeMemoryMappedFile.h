/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class dev_cerus_sharedmem_NativeMemoryMappedFile */

#ifndef _Included_dev_cerus_sharedmem_NativeMemoryMappedFile
#define _Included_dev_cerus_sharedmem_NativeMemoryMappedFile
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     dev_cerus_sharedmem_NativeMemoryMappedFile
 * Method:    readNative
 * Signature: (II)[B
 */
JNIEXPORT jbyteArray JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_readNative
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     dev_cerus_sharedmem_NativeMemoryMappedFile
 * Method:    writeNative
 * Signature: (I[B)I
 */
JNIEXPORT jint JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_writeNative
  (JNIEnv *, jobject, jint, jbyteArray);

/*
 * Class:     dev_cerus_sharedmem_NativeMemoryMappedFile
 * Method:    open
 * Signature: (Ljava/lang/String;IIJ)I
 */
JNIEXPORT jint JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_open
  (JNIEnv *, jobject, jstring, jint, jint, jlong);

/*
 * Class:     dev_cerus_sharedmem_NativeMemoryMappedFile
 * Method:    closeNative
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_dev_cerus_sharedmem_NativeMemoryMappedFile_closeNative
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
