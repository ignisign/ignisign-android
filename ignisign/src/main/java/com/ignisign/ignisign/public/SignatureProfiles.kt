package com.ignisign.ignisign.public

import com.ignisign.ignisign.IgnisignApplicationEnv
import com.ignisign.ignisign.IgnisignSignatureLanguages

enum class IgnisignSignatureProfileStatus {
    PUBLISHED,
    ARCHIVED
}

data class IgnisignSignatureProfile(
    val _id: String? = null,
    val appId: String,
    val appEnv: IgnisignApplicationEnv, // Assurez-vous que cette énumération est définie
    val orgId: String,
    val name: String,
    val status: IgnisignSignatureProfileStatus,
    val integrationMode: IgnisignIntegrationMode, // Remplacer par l'énumération appropriée
    val signatureMethodRef: IgnisignSignatureMethodRef, // Remplacer par l'énumération appropriée
    val idProofings: List<IgnisignIdProofingMethodRef>, // Remplacer par l'énumération appropriée
    val authMethods: List<IgnisignAuthFullMechanismRef>, // Remplacer par l'énumération appropriée
    val documentTypes: List<IgnisignDocumentType>, // Remplacer par l'énumération appropriée
    val defaultLanguage: IgnisignSignatureLanguages, // Remplacer par l'énumération appropriée
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
    val inputsNeeded: List<IgnisignSignerCreationInputRef> // Remplacer par l'énumération appropriée
)

data class IgnisignSignatureProfileIdContainerDto(
    val signatureProfileId: String
)


