# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# SECURITY: WebView JavaScript Interface Protection
# Keep the JavaScript interface methods accessible from WebView
-keepclassmembers class io.ignisign.ignisign_android.IgnisignAndroid$JavaScriptInterface {
   public *;
   @android.webkit.JavascriptInterface <methods>;
}

# Keep callback interfaces used by the SDK
-keep interface io.ignisign.ignisign_android.ISessionCallbacks { *; }
-keep interface io.ignisign.ignisign_android.IJSEventListener { *; }

# Preserve line numbers for debugging stack traces (security incident analysis)
-keepattributes SourceFile,LineNumberTable

# Hide the original source file name in production
-renamesourcefileattribute SourceFile

# SECURITY: Strip out logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}