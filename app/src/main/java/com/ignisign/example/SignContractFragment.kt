package com.ignisign.example

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ignisign.ignisign.*

class SignContractFragment : Fragment(), ISessionCallbacks {
    private val TAG: String?  = "SignContractFragment"
    lateinit var ignisignAndroid: IgnisignAndroid

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_a_contract, container, false)

        ignisignAndroid = view.findViewById(R.id.embedded_signature)

        val signatureRequestId = "6582c695e11636001dfeda8e"//"6582fc71e11636001dfeddd4"//"6582c695e11636001dfeda8e"//"6582f682e11636001dfedcd4"////"6582b8bc851baa001bb26a5a"//"65819f7a6770b7001ca423bc"//"6580180bf9b5c7001c3b641a"//"658050446770b7001ca41a28"//"658018d06770b7001ca416a8"
        val signatureSessionToken = "NM9jc0C3SZZyhAJyBBTGk6ufXXRiDO7kQygmMW6ZZM0IaA1ZL3QoUJDnKF9079ER7rd"//"7oWYZZE7ZZR82Xb6EmRBBEkjtwBB3iZ0m0GgoRtVK0M1kMtNJA6me9tPjaiwYGAPCA3z"//"NM9jc0C3SZZyhAJyBBTGk6ufXXRiDO7kQygmMW6ZZM0IaA1ZL3QoUJDnKF9079ER7rd"//"7y5V2DghRzGwJz8xZRnal6jWWNS9AkIfqMSUmfJfRJhrqkfas2hIM58x1y6H5eKB"//"kDjW5vIXToyy1gMk0ULYPBBjvm3fzkUFGiqEnbuTC2KahPlo2ZH9CqZgomVRQXTJn"//"DIuq6Z7hR0yAAeC6BB8TA1h1oL6PA0EVZZtYWEFRR7ph9tyWxvvjBLh53VloDiE2Fs"//"BBCLy1wbZTpCZZgVRzBBZpQOs9Ho5Z9A0eBjO1toOBzELMJb2eP83ZCRbXcRKTVROhd"//"0OhJTGA9SEq8W1ZcNtE5JwJEZ62q3EZZ9qssFcBBBTWu6FdBjk7nlEgqtZRncqwCt7"

        val signerId = "6582c681e11636001dfeda77"//"6582fc68ef3841001b92e413"//"6582c681e11636001dfeda77"//"6582f675ef3841001b92e292"////"65801468851baa001bb252f5"//"6580147792c43e001c0eabbf"
        val authSecret = "fd2b2d0c-1cc6-4dd8-8c99-6b6551e84fdd"//"ffbdae8b-8ead-4fed-a601-d4ea62dcda50"//"fd2b2d0c-1cc6-4dd8-8c99-6b6551e84fdd"//"78db1620-dc7c-411d-84a0-b5f81978827d"////"36cbb059-f660-4c14-876a-7019aab3cab0"

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

    override fun handlePrivateFileInfoProvisioning(
        documentId: String,
        externalDocumentId: String,
        signerId: String,
        signatureRequestId: String
    ) {
        Log.d(TAG, "trace handlePrivateFileInfiProvisioning : documentId = " + documentId + " externalDocumentId : " + externalDocumentId + " signerId : " + signerId + " signatureRequestId : " + signatureRequestId)
    }

    override fun handleSignatureSessionError(
        errorCode: String,
        errorContext: Any,
        signerId: String,
        signatureRequestId: String
    ) {
        Log.d(TAG, "trace handleSignatureSessionError : errorCode : " + errorCode + " errorContext : " + errorContext + " signerId : " + signerId + " signatureRequestId : " + signatureRequestId)
    }

    override fun handleSignatureSessionFinalized(
        signatureIds: List<String>,
        signerId: String,
        signatureRequestId: String
    ) {
        Log.d(TAG, "trace handleSignatureSessionFinalized : signatureIds : " + signatureIds + " signerId : " + signerId + " signatureRequestId : " + signatureRequestId)
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