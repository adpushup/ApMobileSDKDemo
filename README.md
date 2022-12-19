# Getting Started Guide

# **Get started**

**Release version:  1.0.6 | Release date: 16.12.2022**

Follow this guide to get started with Ap Mobile SDK.

The Ap Mobile SDK allows you to add multiple demand sources in your app and makes them compete against each other in real-time auctions, maximizing your ad revenues. 

The following document shows how to integrate Ap Mobile SDK in your Android App.

You can use our demo apps as a reference project.

<aside>
📎 Demo Apps Link: https://github.com/adpushup/ApMobileSDKDemo

</aside>

---

## Before you begin

To prepare your app, complete the steps in the following sections.

### **App prerequisites**

- Use Android Studio Dolphin (2021.3.1) or higher
- Make sure that your app's build file uses the following values:
    - A `minSdkVersion` of `21` or higher
    - A `compileSdkVersion` of `33` or higher
- Requires java 8 or higher
- **(Important)** Get the following IDs from AdPushup:
    - **Ad Manager App Id**: For Android Manifest File.
    - **Ap App Id**: Used in initializing Ap Mobile SDK.
    - **Ap Placement Ids**: Each Ad Unit has a unique placement Id.

---

## Configure your app

1. In your project-level `build.gradle` file, including [Google's Maven repository](https://maven.google.com/web/index.html), [Maven central repository](https://search.maven.org/artifact), and [Jitpack.io repository](http://jitpack.io/) in both your `buildscript` and `allprojects` sections:
    
    ```groovy
    buildscript {
        repositories {
            google()
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }
    
    allprojects {
        repositories {
            google()
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }
    ```
    
2. Add the dependencies for the **Ap Mobile SDK** and **Google Mobile Ads SDK** to your module's app-level [Gradle](https://gradle.org/) file, normally `app/build.gradle`:
    
    ```groovy
    dependencies {
      implementation 'com.adpushup:apmobilesdk:1.0.6'
      implementation 'com.google.android.gms:play-services-ads:21.3.0'
    }
    ```
    
3. Add your **Ad manager app ID** (provided by AdPushup) to your app’s `AndroidManifest.xml` file. To do so, add a `<meta-data>` tag with `android:name="com.google.android.gms.ads.APPLICATION_ID"`. For `android:value`, insert your own **Ad manager app ID**, surrounded by quotation marks.
    
    ```xml
    <manifest>
        <application>
            <!-- Ad Manager app ID will be provided by AdPushup -->
            <meta-data
                android:name="com.google.android.gms.ads.APPLICATION_ID"
                android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"/>
        </application>
    </manifest>
    ```
    
4. Change your app’s Application Class with ApMultidexApplication. 
    
    ```xml
    <manifest>
        <application
            android:name="com.adpushup.apmobilesdk.ApMultiDexApplication" >
            ...
        </application>
    </manifest>
    ```
    
    Refer [Here](ApMultiDexApplicationGuide.md) for more information:
    
    [About Ap Multidex Application](ApMultiDexApplicationGuide.md)
    

---

## **Initialize the Ap Mobile SDK**

Before loading ads, have your app initialize the Ap Mobile SDK by calling `ApMobileSdk.init()` which initializes the SDK and calls back a completion listener once initialization is complete. This needs to be done only once, ideally at the app launch.

**Ads may be preloaded by the Ap Mobile SDK upon calling** `ApMobileSdk.init()`. If you need to obtain consent from users in the European Economic Area (EEA), or otherwise take action before loading ads, ensure you do so before initializing the Ap Mobile SDK.

Here's an example of how to call the `init()` method in an Activity:

*Kotlin Example:*

```kotlin
import com.adpushup.apmobilesdk.ApMobileSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
				
	// apAppId is your app ID in AdPushUp. It will be provided to you by AdPushup.
        ApMobileSdk.init(this, "apAppId")
    }
}
```

*JAVA Example:*

```java
import com.adpushup.apmobilesdk.ApMobileSdk;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // apAppId is your app ID in AdPushUp. It will be provided to you by AdPushup.
        ApMobileSdk.init(this, "apAppId");
    }
}
```

---

# Implementing ads to your app

[Banner Ads](BannerAdExample/README.md)

[Adaptive Banner Ads](AdaptiveBannerAdExample/README.md)

[Interstitial Ads](InterstitialAdExample/README.md)

[Rewarded Ads](RewardedAdExample/README.md)

[Rewarded Interstitial Ads](RewardedInterstitialAdExample/README.md)

[Native Ads](NativeAdExample/README.md)
