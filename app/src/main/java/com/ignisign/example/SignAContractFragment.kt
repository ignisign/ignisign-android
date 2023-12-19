package com.ignisign.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ignisign.ignisign.*

class SignAContractFragment : Fragment(), ISessionCallbacks {

    lateinit var ignisignAndroid: IgnisignAndroid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_a_contract, container, false)

        ignisignAndroid = view.findViewById(R.id.embedded_signature)

        val signatureRequestId = "658018d06770b7001ca416a8"
        val signatureSessionToken = "0OhJTGA9SEq8W1ZcNtE5JwJEZ62q3EZZ9qssFcBBBTWu6FdBjk7nlEgqtZRncqwCt7"
        val signerId = "65801468851baa001bb252f5"
        val authSecret = "fd2b2d0c-1cc6-4dd8-8c99-6b6551e84fdd"
        //test values

        /*embeddedSignature.initSignature(
            signatureRequestId = signatureRequestId,
            signatureSessionToken = signatureSessionToken,
            signerId = signerId,
            signatureAuthSecret = authSecret
        )*/
        val dimensions = IgnisignSignatureSessionDimensions(
            width = "300",
            height = "400"
        )

        val displayOptions = IgnisignJSSignatureSessionsDisplayOptions(
            showTitle = true,
            showDescription = true,
            darkMode = false,
            forceLanguage = IgnisignSignatureLanguages.FR,
            forceShowDocumentInformations = true
        )

        val params = IgnisignInitParams(
            signatureRequestId = signatureRequestId,
            signerId = signerId,
            signatureSessionToken = signatureSessionToken,
            signerAuthSecret = authSecret,
            sessionCallbacks = this,
            dimensions = dimensions,
            displayOptions =displayOptions,
            closeOnFinish = true
        )

        ignisignAndroid.initSignatureSession(params)

        return view
    }

    override fun handlePrivateFileInfoProvisionning(
        documentId: String,
        externalDocumentId: String,
        signerId: String,
        signatureRequestId: String
    ) /*: Deferred<IgnisignDocumentPrivateFileDto>*/ {

    }

    override fun handleSignatureSessionError(
        errorCode: String,
        errorContext: Any,
        signerId: String,
        signatureRequestId: String
    ) /*: Deferred<Void>*/ {

    }

    override fun handleSignatureSessionFinalized(
        signatureIds: List<String>,
        signerId: String,
        signatureRequestId: String
    ) /*: Deferred<Void>*/ {

    }
}