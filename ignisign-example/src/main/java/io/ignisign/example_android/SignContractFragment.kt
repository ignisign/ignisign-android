package io.ignisign.example_android

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import io.ignisign.ignisign_android.*

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

        // SECURITY: Never hardcode credentials in production code.
        // These should be fetched securely from your backend API.
        val signatureRequestId = "YOUR_SIGNATURE_REQUEST_ID"
        val signatureSessionToken = "YOUR_SESSION_TOKEN"

        val signerId = "YOUR_SIGNER_ID"
        val authSecret = "YOUR_AUTH_SECRET"

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

        ignisignAndroid.setValues("io.ignisign.example_android", IgnisignApplicationEnv.DEVELOPMENT)
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
        errorContext: Any?,
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