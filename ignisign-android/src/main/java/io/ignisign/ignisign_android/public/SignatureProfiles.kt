package io.ignisign.ignisign_android.public

import io.ignisign.ignisign_android.IgnisignApplicationEnv
import io.ignisign.ignisign_android.IgnisignSignatureLanguages

enum class IgnisignSignatureProfileStatus {
    PUBLISHED,
    ARCHIVED
}

data class IgnisignSignatureProfile(
    val _id: String? = null,
    val appId: String,
    val appEnv: IgnisignApplicationEnv,
    val orgId: String,
    val name: String,
    val status: IgnisignSignatureProfileStatus,
    val integrationMode: IgnisignIntegrationMode,
    val signatureMethodRef: IgnisignSignatureMethodRef,
    val idProofings: List<IgnisignIdProofingMethodRef>,
    val authMethods: List<IgnisignAuthFullMechanismRef>,
    val documentTypes: List<IgnisignDocumentType>,
    val defaultLanguage: IgnisignSignatureLanguages,
    val documentRequestActivated: Boolean,
    val languageCanBeChanged: Boolean,
    val authSessionEnabled: Boolean,
    val statementsEnabled: Boolean,
    val templateDisplayerId: String? = null,
    val createdByDefault: Boolean? = null
)

data class IgnisignSignatureProfileStatusWrapper(
    val status: IgnisignSignatureProfileStatus
)

data class IgnisignSignatureProfileSignerInputsConstraints(
    val inputsNeeded: List<IgnisignSignerCreationInputRef>
)

data class IgnisignSignatureProfileIdContainerDto(
    val signatureProfileId: String
)


