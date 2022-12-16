# About Ap Multidex Application

Ap Mobile SDK uses [ACRA](https://github.com/ACRA/acra) Lib. to detect & report any errors in both Ap Mobile SDK as well as in your app. We keep tracking these errors to ensure smooth app experiences for your users.  This is essential to initialize AdPushup’s ACRA Module for the proper working of Ap Mobile SDK. 

This also adds support for Multidex in your app without the need to extend your application to MultidexApplication. 

To do that you can follow the simple steps,

Depending on whether you override the `[Application](https://developer.android.com/reference/android/app/Application)` class, perform one of the following:

- If you *do not* override the `[Application](https://developer.android.com/reference/android/app/Application)` class OR If you override the `[Application](https://developer.android.com/reference/android/app/Application)` class just for enabling **multidex**, edit your manifest file to set `android:name` in the `<application>` tag as follows:
    
    ```xml
    <manifest>
        <application
                android:name="com.adpushup.apmobilesdk.ApMultiDexApplication" >
            ...
        </application>
    </manifest>
    ```
    
- If you do override the `[Application](https://developer.android.com/reference/android/app/Application)` class, change it to extend `ApMultiDexApplication` (if possible) as follows:
    
    > Don’t forget to add the Multidex dependency in your `build.gradle` file.
    > 
    
    *Kotlin Example:*
    
    ```kotlin
    class MyApplication : ApMultiDexApplication() {...}
    ```
    
    *JAVA Example:*
    
    ```java
    public class MyApplication extends ApMultiDexApplication { }
    ```
    
- Or if you do override the `[Application](https://developer.android.com/reference/android/app/Application)` class but it's not possible to change the base class, then you can instead override the `[attachBaseContext()](https://developer.android.com/reference/android/content/ContextWrapper#attachBaseContext(android.content.Context))` method and call `ApMobileSdk.initACRA(**this**)` to configure Ap Mobile SDK:
    
    > Don’t forget to add the Multidex dependency in your `build.gradle` file.
    > 
    
    *Kotlin Example:*
    
    ```kotlin
    class MyApplication : SomeOtherApplication() {
    
        override fun attachBaseContext(base: Context) {
            super.attachBaseContext(base)
    
    				// Configure Ap Mobile SDK
            ApMobileSdk.initACRA(**this**)
        }
    }
    ```
    
    *JAVA Example:*
    
    ```java
    public class MyApplication extends SomeOtherApplication {
        @Override
        protected void attachBaseContext(@NonNull Context base) {
            super.attachBaseContext(base);
    
    				// Configure Ap Mobile SDK
            ApMobileSdk.initACRA(this);
        }
    }
    ```