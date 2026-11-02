package io.ignisign.ignisign_android

class Config {
    companion object {
        // SECURITY: Changed from http:// to https:// to prevent cleartext traffic
        // Use localhost for emulator testing - configure via network_security_config.xml
        val url = "https://10.0.2.2:4242"
        val defaultIgnisignClientSignUrl = "https://sign.ignisign.io"
    }
}