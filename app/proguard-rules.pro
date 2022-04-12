# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class firstName to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepclassmembernames public class * implements com.bumptech.glide.module.GlideModule
-keepclassmembernames public class * extends com.bumptech.glide.module.AppGlideModule
-keepclassmembernames public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.* { *; }

-dontwarn org.xmlpull.v1.XmlPullParser
-dontwarn org.xmlpull.v1.XmlSerializer
-keep class org.xmlpull.v1.* {*;}

-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

-dontwarn com.google.android.material.**
-keepclassmembernames class com.google.android.material.** { *; }

-dontwarn androidx.**
-keepclassmembernames class androidx.** { *; }
-keepclassmembernames interface androidx.** { *; }

-dontwarn com.github.PhilJay**
-keepclassmembernames class com.github.PhilJay** { *; }
-keepclassmembernames interface com.github.PhilJay** { *; }

-keepclassmembernames class com.squareup.** { *; }
-keepclassmembernames interface com.squareup.** { *; }
-keepclassmembernames class retrofit2.** { *; }
-keepclassmembernames interface retrofit2.** { *;}
-keepclassmembernames interface com.squareup.** { *; }

-keep class org.otwebrtc.** { *; }
-keep interface org.otwebrtc.** { *; }

-keepclassmembernames class com.squareup.okhttp.** { *; }
-keepclassmembernames interface com.squareup.okhttp.** { *; }

-dontwarn com.squareup.okhttp.**

-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

-dontwarn okhttp3.**


-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keep class com.example.ghostzilla.models.** { *; }
-keep class com.example.ghostzilla.utils.** { *; }

-dontwarn rx.**
-dontwarn retrofit2.**
-dontwarn okhttp3.**

-keepnames class androidx.lifecycle.ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver { <init>(...); }
-keepclassmembers class * implements androidx.lifecycle.LifecycleOwner { <init>(...); }
-keepclassmembers class androidx.lifecycle.Lifecycle$State { *; }
-keepclassmembers class androidx.lifecycle.Lifecycle$Event { *; }
-keep class * implements androidx.lifecycle.LifecycleOwner { public <init>(...); }
-keep class * implements androidx.lifecycle.LifecycleObserver { public <init>(...); }
-keep class androidx.lifecycle.* { *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
# -keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file firstName.
# -renamesourcefileattribute SourceFile
