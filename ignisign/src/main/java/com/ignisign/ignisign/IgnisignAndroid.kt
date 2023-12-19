package com.ignisign.ignisign

import android.content.ContentValues
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

data class IgnisignSignatureSessionDimensions (
    var width: String,
    var height: String
)

enum class IgnisignJsEvents {
    IGNISIGN_LOADED,
    IFRAME_TOO_SMALL
}

data class IgnisignJSSignatureSessionsDisplayOptions (
    var showTitle: Boolean,
    var showDescription: Boolean,
    var darkMode: Boolean = false,
    var forceLanguage: IgnisignSignatureLanguages,
    var forceShowDocumentInformations: Boolean = false
) {
    fun convertToQueryString(): String {
        val propertiesMap = mapOf(
            "showTitle" to showTitle,
            "showDescription" to showDescription,
            "darkMode" to darkMode,
            "forceLanguage" to forceLanguage?.name,
            "forceShowDocumentInformations" to forceShowDocumentInformations
        )

        return propertiesMap.entries
            .filter { it.value != null }
            .joinToString("&") { "${it.key}=${it.value}" }
    }

}

data class IgnisignInitParams (
    var signatureRequestId: String,
    var signerId: String,
    var signatureSessionToken: String,
    var signerAuthSecret: String,
    var sessionCallbacks: ISessionCallbacks,
    var closeOnFinish: Boolean,
    var dimensions: IgnisignSignatureSessionDimensions,
    var displayOptions: IgnisignJSSignatureSessionsDisplayOptions
) {
    override fun toString(): String {
        return "IgnisignInitParams(signatureRequestId='$signatureRequestId', signerId='$signerId', signatureSessionToken='$signatureSessionToken', signerAuthSecret='$signerAuthSecret', sessionCallbacks=$sessionCallbacks, closeOnFinish=$closeOnFinish, dimensions=$dimensions, displayOptions=$displayOptions)"
    }
}

class IgnisignAndroid: WebView {
    private val TAG: String = "IgnisignAndroid"
    private val IGNISIGN_CLIENT_SIGN_URL = "https://sign.ignisign.io"

    lateinit var ignisignClientSignUrl: String
    lateinit var signatureRequestId: String
    lateinit var signerId: String
    lateinit var signatureSessionToken: String
    lateinit var signerAuthToken: String
    lateinit var sessionCallbacks: ISessionCallbacks
    var closeOnFinish: Boolean = false
    lateinit var dimensions: IgnisignSignatureSessionDimensions
    lateinit var displayOptions: IgnisignJSSignatureSessionsDisplayOptions

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun setValues(appId: String, env: IgnisignApplicationEnv, ignisignClientSignUrl: String? = null) {
        if (ignisignClientSignUrl != null) {
            this.ignisignClientSignUrl = ignisignClientSignUrl
        } else {
            this.ignisignClientSignUrl = Config.defaultIgnisignClientSignUrl
        }
    }

    fun initSignatureSession(initParams: IgnisignInitParams) {
        settings.javaScriptEnabled = true

        Log.d(TAG, "trace initSignatureSession : params: " + initParams.toString())

        this.signerId = initParams.signerId
        this.signatureRequestId = initParams.signatureRequestId
        this.closeOnFinish = initParams.closeOnFinish
        this.displayOptions = initParams.displayOptions

        this.signerAuthToken = initParams.signerAuthSecret
        this.signatureSessionToken = initParams.signatureSessionToken
        this.sessionCallbacks = initParams.sessionCallbacks
        this.dimensions = initParams.dimensions

        this.ignisignClientSignUrl = IGNISIGN_CLIENT_SIGN_URL

        val signatureSessionLink = getUrlSessionLink(signatureRequestId, signerId, signatureSessionToken, signerAuthToken, displayOptions)
        Log.d(TAG, "trace ignisign_android : url : " + signatureSessionLink)

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                Log.d(ContentValues.TAG, "trace webview : shouldOverrideUrlLoading : " + request?.url?.path)

                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        val iFrame = "<iframe style=\"margin: 0 auto;\"allow=\"publickey-credentials-create allow-scripts allow-same-origin allow-popups allow-forms allow-popups-to-escape-sandbox allow-top-navigation\"src=\"${signatureSessionLink}\"title='Ignisign'${dimensions.width}${this.dimensions.height}/>"

        loadData(iFrame, "text/html", null)
    }

    private fun getUrlSessionLink(signatureRequestId: String, signerId: String, signatureSessionToken: String, signerAuthSecret: String, displayOptions: IgnisignJSSignatureSessionsDisplayOptions): String {
        return "${this.ignisignClientSignUrl}/signature-requests/${signatureRequestId}/signers/${signerId}/sign?token=${signatureSessionToken}&signerSecret=${signerAuthSecret}&${displayOptions.convertToQueryString()}"
    }
}