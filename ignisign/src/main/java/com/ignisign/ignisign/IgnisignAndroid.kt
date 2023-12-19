package com.ignisign.ignisign

import android.content.ContentValues
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.*

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
    var idFrame: String,
    var signatureRequestId: String,
    var signerId: String,
    var signatureSessionToken: String,
    var signerAuthSecret: String,
    var sessionCallbacks: ISessionCallbacks,
    var closeOnFinish: Boolean,
    var dimensions: IgnisignSignatureSessionDimensions,
    var displayOptions: IgnisignJSSignatureSessionsDisplayOptions
)

class IgnisignAndroid: WebView {
    private val TAG: String = "IgnisignAndroid"
    private val IGNISIGN_CLIENT_SIGN_URL = "https://sign.ignisign.io"

    lateinit var appId: String
    lateinit var env: IgnisignApplicationEnv

    lateinit var idFrame: String
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
        this.appId = appId
        this.env = env
        if (ignisignClientSignUrl != null) {
            this.ignisignClientSignUrl = ignisignClientSignUrl
        } else {
            this.ignisignClientSignUrl = Config.defaultIgnisignClientSignUrl
        }
    }

    fun initSignatureSession(initParams: IgnisignInitParams) {
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.allowFileAccess = true
        settings.pluginState = WebSettings.PluginState.ON
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportMultipleWindows(true)
        //settings.loadWithOverviewMode = true
        /*settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.allowContentAccess = true
        settings.allowUniversalAccessFromFileURLs = true
        settings.setSupportMultipleWindows(true)*/

        Log.d(TAG, "trace initSignatureSession : params: " + initParams.toString())

        this.idFrame = initParams.idFrame
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



        val myWebChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // Gestion de la progression du chargement
                super.onProgressChanged(view, newProgress)

                loadUrl("javascript:(function() { " +
                        "var iframe = document.getElementById('${idFrame}'); " +
                        "iframe.contentWindow.addEventListener('message', function(event) { " +
                        "    AndroidInterface.postMessage(event.data);" +
                        "}, false);" +
                        "})()")
            }



            // Autres méthodes de surcharge au besoin
        }

        webChromeClient = myWebChromeClient

        // Charger une URL ou des données HTML
        //myWebView.loadUrl("https://www.example.com")

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                Log.d(TAG, "trace webview : shouldOverrideUrlLoading : " + request?.url?.path)

                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d(TAG, "trace webview : onPageFinished : " + url)

                loadUrl("javascript:(function() { " +
                        "var iframe = document.getElementById('${idFrame}'); " +
                        "iframe.contentWindow.addEventListener('message', function(event) { " +
                        "    AndroidInterface.postMessage(event.data);" +
                        "}, false);" +
                        "})()")
            }
        }

        val iFrame = "<iframe style=\"margin: 0 auto; " +
                "id=${idFrame} " +
                "allow=\"publickey-credentials-create allow-scripts allow-same-origin allow-popups allow-forms allow-popups-to-escape-sandbox allow-top-navigation " +
                "src=\"${signatureSessionLink}\" " +
                "title='Ignisign' " +
                "width=${dimensions.width} " +
                "height=${dimensions.height}" +
                " />"

        Log.d(TAG, "trace webview - display iFrame with id : " + idFrame)

        addJavascriptInterface(JavaScriptInterface(), "AndroidInterface")
        loadDataWithBaseURL(Config.url, iFrame, "text/html", "UTF-8", null)
        //loadData(iFrame, "text/html", null)

        //todo gestion taille

    }

    public fun cancelSignatureSession() {
        closeIFrame()
    }

    /*** private ***/

    private fun handleEvent() {

    }

    private fun closeIFrame() {
        loadUrl("")
        destroy()
    }

    private class JavaScriptInterface {
        private val TAG: String = "JavascriptInterface"
        @JavascriptInterface
        fun postMessage(message: String) {
            Log.d(TAG, "trace webview - listen iFrame : " + message)
        }
    }


    private fun getUrlSessionLink(signatureRequestId: String, signerId: String, signatureSessionToken: String, signerAuthSecret: String, displayOptions: IgnisignJSSignatureSessionsDisplayOptions): String {
        return "${this.ignisignClientSignUrl}/signature-requests/${signatureRequestId}/signers/${signerId}/sign?token=${signatureSessionToken}&signerSecret=${signerAuthSecret}&${displayOptions.convertToQueryString()}"
    }
}