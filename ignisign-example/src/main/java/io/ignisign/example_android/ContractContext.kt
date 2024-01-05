package io.ignisign.example_android

import com.google.gson.Gson
import io.ignisign.ignisign_android.IgnisignApplicationEnv

data class ContractContext(
    val signatureRequestId: String,
    val ignisignSignerId: String,
    val ignisignSignatureToken: String,
    val ignisignUserAuthSecret: String,
    val ignisignAppId: String,
    val ignisignAppEnv: IgnisignApplicationEnv,
    val signerEmail: String? = null,
    val documentId: String? = null
) {
    fun jsonToContractContext(json: String): ContractContext? {
        return try {
            Gson().fromJson(json, ContractContext::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun toString(): String {
        return "ContractContext(signatureRequestId='$signatureRequestId', ignisignSignerId='$ignisignSignerId', ignisignSignatureToken='$ignisignSignatureToken', ignisignUserAuthSecret='$ignisignUserAuthSecret', ignisignAppId='$ignisignAppId', ignisignAppEnv=$ignisignAppEnv, signerEmail=$signerEmail, documentId=$documentId)"
    }


}
