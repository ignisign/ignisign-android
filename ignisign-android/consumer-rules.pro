# Applied to apps that depend on the IgniSign Android SDK when they enable R8/ProGuard.
# Do NOT add -assumenosideeffects or other global rules here: they would affect the host app.

# WebView JavaScript bridge: methods are called reflectively from JS.
-keepattributes JavascriptInterface
-keepclassmembers class io.ignisign.ignisign_android.IgnisignAndroid$JavaScriptInterface {
    @android.webkit.JavascriptInterface <methods>;
}

# Gson TypeToken used to parse bridge messages needs generic signatures.
-keepattributes Signature
