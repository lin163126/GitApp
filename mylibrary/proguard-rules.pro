# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

#下面代码中的路径配置,你要修改成与你相对应的路径

#引入依赖包rt.jar（jdk路径）(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
-libraryjars 'C:\Program Files\Java\jdk1.8.0_111\jre\lib\rt.jar'

#引入依赖包android.jar(android SDK路径)(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'C:\Android_Develop_Tools\sdk\platforms\android-23\android.jar'

#如果用到Appcompat包，需要引入(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'D:\AndroidStudioProjects\MyApplication\mylibrary\build\intermediates\exploded-aar\com.android.support\appcompat-v7\23.4.0\jars\classes.jar'
#-libraryjars 'D:\AndroidStudioProjects\MyApplication\mylibrary\build\intermediates\exploded-aar\com.android.support\support-v4\23.4.0\jars\classes.jar'

#忽略警告
-ignorewarnings
#保证是独立的jar,没有任何项目引用,如果不写就会认为我们所有的代码是无用的,从而把所有的代码压缩掉,导出一个空的jar
#-dontshrink
#保护泛型
-keepattributes Signature

-keep class com.example.mylibrary.JsonUtils{
    public *;
}