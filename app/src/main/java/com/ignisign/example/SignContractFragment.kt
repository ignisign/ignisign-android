package com.ignisign.example

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ignisign.ignisign.*

class SignContractFragment : Fragment(), ISessionCallbacks {
    lateinit var ignisignAndroid: IgnisignAndroid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_a_contract, container, false)

        ignisignAndroid = view.findViewById(R.id.embedded_signature)

        val signatureRequestId = "65819f7a6770b7001ca423bc"//"6580180bf9b5c7001c3b641a"//"658050446770b7001ca41a28"//"658018d06770b7001ca416a8"
        val signatureSessionToken = "MLyxdUIBBROmckS4bQBB4qftW5qmkzIUyokLRJQKlGmnGMZofV0GdCE68pAhhanJ2s"//"DIuq6Z7hR0yAAeC6BB8TA1h1oL6PA0EVZZtYWEFRR7ph9tyWxvvjBLh53VloDiE2Fs"//"BBCLy1wbZTpCZZgVRzBBZpQOs9Ho5Z9A0eBjO1toOBzELMJb2eP83ZCRbXcRKTVROhd"//"0OhJTGA9SEq8W1ZcNtE5JwJEZ62q3EZZ9qssFcBBBTWu6FdBjk7nlEgqtZRncqwCt7"

        val signerId = "65801468851baa001bb252f5"//"6580147792c43e001c0eabbf"
        val authSecret = "fd2b2d0c-1cc6-4dd8-8c99-6b6551e84fdd"//"36cbb059-f660-4c14-876a-7019aab3cab0"

        val dimensions = IgnisignSignatureSessionDimensions(
            width = "${getScreenWidthDp(requireContext())-20}",
            height = "${getScreenHeightDp(requireContext())-80}"
        )

        val displayOptions = IgnisignJSSignatureSessionsDisplayOptions(
            showTitle = true,
            showDescription = true,
            darkMode = false,
            forceLanguage = IgnisignSignatureLanguages.FR,
            forceShowDocumentInformations = false
        )

        val params = IgnisignInitParams(
            idFrame = "test-ignisign-sdk",
            signatureRequestId = signatureRequestId,
            signerId = signerId,
            signatureSessionToken = signatureSessionToken,
            signerAuthSecret = authSecret,
            sessionCallbacks = this,
            dimensions = dimensions,
            displayOptions =displayOptions,
            closeOnFinish = true
        )

        ignisignAndroid.setValues("com.ignisign.example", IgnisignApplicationEnv.DEVELOPMENT)
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

    fun getScreenWidthDp(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.widthPixels / displayMetrics.density).toInt()
    }

    fun getScreenHeightDp(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.heightPixels / displayMetrics.density).toInt()
    }

}