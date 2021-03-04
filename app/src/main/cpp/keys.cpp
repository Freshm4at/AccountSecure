#include <climits>
//
// Created by Freshmeat on 24/02/2021.


#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_accountsecure_Login_00024Companion_getAPIKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "aHR0cHM6Ly82MDA3ZjFhNDMwOWY4YjAwMTdlZTUwMjIubW9ja2FwaS5pby9hcGkvbTEvL2FjY291bnRzLw==";
    return env->NewStringUTF(api_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_accountsecure_Login_00024Companion_getPwKey(JNIEnv *env, jobject thiz) {
    std::string pw_key = "aGVsbG8=";
    return env->NewStringUTF(pw_key.c_str());
}