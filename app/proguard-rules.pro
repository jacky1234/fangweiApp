# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**

-dontwarn com.bumptech.glide.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


#---OkHttp3---
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


-dontwarn com.android.volley.toolbox.**
#s2icode
-keep class com.s2icode.**{*;}
-dontwarn com.s2icode.**

#retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keep public class retrofit2.** { *; }


#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}


#---Gson---
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
#---zxing---
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**

##-----wx------
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}

#bugly 
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


#s2icode
-keep class com.s2icode.**{*;}
-dontwarn com.s2icode.**
-keep class com.j256.ormlite.**{*;}
-dontwarn com.j256.ormlite.**
-keep class com.loc.ad.**{*;}
-dontwarn com.loc.ad.**
-keep class com.amap.apis.**{*;}
-dontwarn com.amap.apis.**
-keep class com.serenegiant.**{*;}
-dontwarn com.serenegiant.**
-keep class org.greenrobot.eventbus.**{*;}
-dontwarn org.greenrobot.eventbus.**
#
-dontwarn com.facebook.fresco.**
-keep class com.facebook.fresco.**{ *;}
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip # Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class * -keepclassmembers class * {
@com.facebook.common.internal.DoNotStrip *; }
# Keep native methods
-keepclassmembers class * {native <methods>; }
-dontwarn okio.**
-dontwarn com.squareup.okhttp.** -dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
