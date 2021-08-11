#include "dev_cerus_etstelemetry_sharedmem_NativeMemoryMappedFile.h"
#include <string>

using namespace std;

JNIEXPORT jobject JNICALL Java_dev_cerus_etstelemetry_sharedmem_NativeMemoryMappedFile_read
  (JNIEnv *env, jobject thisObj, jstring mapName, jint offset, jint len) {
   /*jbyteArray result;
   result = (env)->NewByteArray(len);
   if (result == NULL) {
       return NULL;  out of memory error thrown
   }

   jbyte a[len];
   for (int i = 0; i < len; i++) {
       a[i] = i;
   }
   env->SetByteArrayRegion (result, 0, len, a);

   HANDLE mem = OpenFileMapping(FILE_MAP_READ, // assuming you only want to read
           false, jstring2string(mapName));
   void* addr = MapViewOfFile(mem, FILE_MAP_READ, 0, 0, len);

   return env->NewDirectByteBuffer(addr, len);*/

   // No clue how to implement this
   return NULL;
}

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