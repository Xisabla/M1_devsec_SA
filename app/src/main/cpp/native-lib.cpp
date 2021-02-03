//
// Created by Le Willou on 03/02/2021.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_io_github_xisabla_appdevsec_1secureapp_MainActivity_getUrl(JNIEnv *env, jclass clazz) {
    std::string baseURL = "aHR0cHM6Ly82MDA3ZjFhNDMwOWY4YjAwMTdlZTUwMjIubW9ja2FwaS5pby9hcGkvbTEv";
    return env->NewStringUTF(baseURL.c_str());
}