package io.ignisign.ignisign_android.public

import io.ignisign.ignisign_android.IgnisignApplicationEnv
import io.ignisign.ignisign_android.IgnisignApplicationVariationColor

enum class IgnisignSignatureMode {
    SERVER_SIDE_SIGNATURE,
    CLIENT_SIDE_SIGNATURE
}

enum class IgnisignSignatureStatus {
    INIT,
    SIGNED,
    FAILED
}

enum class IgnisignIntegrationMode {
    BY_SIDE,
    EMBEDDED
}

data class IgnisignSignature(
    val _id: String? = null,
    val appId: String,
    val appEnv: IgnisignApplicationEnv,
    val signerId: String,
    val signerKeyId: String,
    val sessionId: String? = null,
    val documentId: String,
    val status: IgnisignSignatureStatus,
    val mode: IgnisignSignatureMode,
    val ocspCheckValue: Any? = null,
    val contentHash: String? = null,
    val signature: String? = null,
    val signatureValue: String? = null,
    val signedProperties: String? = null,
    val signedPropertiesHash: String? = null,
    val signingIp: String? = null,
    val signingTime: String? = null,
    val certificate: String? = null
)

data class IgnisignApplicationSignatureMetadata(
    val appName: String,
    val logoB64: String? = null,
    val logoDarkB64: String? = null,
    val rootUrl: String? = null,
    val primaryColor: IgnisignApplicationVariationColor? = null,
    val secondaryColor: IgnisignApplicationVariationColor? = null
)

data class IgnisignSignatureImagesDto(
    val documentId: String,
    val signatures: List<SignatureImage>
) {
    data class SignatureImage(
        val signerId: String,
        val imgB64: String
    )
}

data class IgnisignDocumentSignatureProofCustomizationDto(
    val html: String
)
