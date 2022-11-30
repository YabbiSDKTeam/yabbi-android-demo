# Адаптер Yabbi для AppLovin

## Шаг 1. Установка SDK


### 1.1 Подготовьте Gradle сборки для Android 11
>
>В Android 11 изменился способ запроса приложений и взаимодействия с другими.
приложениями, установленными пользователем на устройстве.
По этой причине убедитесь, что вы используете версию Gradle,
которая соответствует одной из перечисленных [здесь](https://developer.android.com/studio/releases/gradle-plugin#4-0-0).

Вы можете использовать радаптеры для медиации выборочно. Чем больше их подключено - тем больше филрейт.

В зависимости от используемой версии Android Studio вставьте зависимость в файл Gradle:

1. Вставьте следующий код в settings.gradle в корне проекта.  
   **Начиная с Arctic Fox и выше**
    ```gradle
    // Пример project-level settings.gradle
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            // ... другие репозитории
            maven {
                url "https://mobileadx.ru/maven"
            }
        }
    }
    ```

   **Для версий до Arctic Fox**
    ```gradle
    // Пример project-level settings.gradle
    allprojects {
        repositories {
            // ... other project repositories
            maven {
                url "https://mobileadx.ru/maven"
            }
        }
    }
    ```


2. Вставьте следующий код в app-level build.gradle
   ```gradle
    // Пример app-level build.gradle (excerpt)
    android {
        // ... другие опции
        
        defaultConfig {
          // ... другие конфигурации
          
          multiDexEnabled true
        }
    
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }
    
    dependencies {
        // ... другие зависимости проекта

        implementation 'me.yabbi.ads.adapters:applovin:+'

    }
   ```

Как только gradle config будет сгенерирован, сохраните файл и нажмите **Gradle sync**.

## Шаг 2. Настройка проекта

### 2.1 Настройка Network security config
**Android 9.0 (API 28)** по умолчанию блокирует HTTP трафик. Это может мешать правильному показу рекламы.  
Подробнее вы можете ознакомиться по [ссылке](https://developer.android.com/training/articles/security-config).

Чтобы предотвратить блокировку http-трафика, выполните следующие шаги:

1. Добавьте **Network Security Configuration** файл в **AndroidManifest.xml**:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest>
    <application 
		...
        android:networkSecurityConfig="@xml/network_security_config">
    </application>
</manifest>
```
2. Добавьте конфиг, который передает **cleartextTrafficPermitted** true в **network_security_config.xml** файл:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">127.0.0.1</domain>
    </domain-config>
</network-security-config>
```

## Шаг 3. Инициализация SDK
Инициализируйте **AppLovin**, следуя официальной [документации AppLovin](https://dash.applovin.com/documentation/mediation/android/getting-started/integration).

Для добавления рекламной сети **YabbiAds** следуйте инструкции по добавлению кастомной рекламной сети - [клик](https://dash.applovin.com/documentation/mediation/android/mediation-setup/custom-sdk)

**YabbiAds** поддерживает 2 типа рекламы
- Полноэкранный баннер
- Полноэкранный видео баннер с вознаграждением

## MaxInterstitialAd
> [Документация AppLovin](https://dash.applovin.com/documentation/mediation/android/ad-formats/interstitials)

Для инициализации **YabbiAds** следуйте следующим инструкциям:

1. Создайте рекламный контейнер **MaxInterstitialAd**, как описано в официальной документации.
```java
 private MaxInterstitialAd interstitialAd;
 
 interstitialAd = new MaxInterstitialAd( "YOUR_AD_UNIT_ID", activity );
```
2. Установите параметры **Yabbi** с помощью метода **setLocalExtraParameter**.
```java

 // !!! Установите для инициализации SDK
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.publisherID, "YOUR_PUBLISHER_ID");
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.interstitialId, "YOUR_INTERSTITIAL_ID");
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.rewardedId, "YOUR_REWARDED_ID");

 // Установите для показа полноэкранной рекламы Яндекса
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.yandexInterstitialID, "замените_на_свой_id");
    
 // Установите для показа рекламы с вознаграждением Яндекса
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.yandexRewardedID, "замените_на_свой_id");

 // Установите для показа рекламы от Mintegral
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralAppID, "замените_на_свой_id");
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralApiKey, "замените_на_свой_id");
    
 // Установите для показа полноэкранной рекламы Mintegral
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralInterstitialPlacementId, "замените_на_свой_id");
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralInterstitialUnitId, "замените_на_свой_id");
    
 // Установите для показа рекламы с вознаграждением Mintegral
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralRewardedPlacementId, "замените_на_свой_id");
interstitialAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralRewardedUnitId, "замените_на_свой_id");
```
3. Замените **YOUR_PUBLISHER_ID** на ключ издателя из [личного кабинета](https://mobileadx.ru).
4. Замените **YOUR_INTERSTITIAL_ID** на ключ соответствующий баннерной рекламе из [личного кабинета](https://mobileadx.ru).
5. Замените **YOUR_REWARDED_ID** на ключ соответствующий видео с вознаграждением из [личного кабинета](https://mobileadx.ru).

6. Реклама **YabbiAds** готова к показу, используйте метод **loadAd**, для загрузки рекламы.
```java
interstitialAd.loadAd();
```

## MaxRewardedAd
- [Документация AppLovin](https://dash.applovin.com/documentation/mediation/android/ad-formats/rewarded-ads)

Для инициализации **YabbiAds** следуйте следующим инструкциям:

1. Создайте рекламный контейнер **MaxRewardedAd**, как описано в официальной документации.
```java
 private MaxRewardedAd rewardedAd;
 
 rewardedAd = new MaxRewardedAd( "YOUR_AD_UNIT_ID", activity );
```
2. Установите параметры **Yabbi** с помощью метода **setLocalExtraParameter**.
```java

 // !!! Установите для инициализации SDK
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.publisherID, "YOUR_PUBLISHER_ID");
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.interstitialId, "YOUR_INTERSTITIAL_ID");
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.rewardedId, "YOUR_REWARDED_ID");

 // Установите для показа полноэкранной рекламы Яндекса
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.yandexInterstitialID, "замените_на_свой_id");
    
 // Установите для показа рекламы с вознаграждением Яндекса
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.yandexRewardedID, "замените_на_свой_id");

 // Установите для показа рекламы от Mintegral
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralAppID, "замените_на_свой_id");
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralApiKey, "замените_на_свой_id");
    
 // Установите для показа полноэкранной рекламы Mintegral
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralInterstitialPlacementId, "замените_на_свой_id");
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralInterstitialUnitId, "замените_на_свой_id");
    
 // Установите для показа рекламы с вознаграждением Mintegral
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralRewardedPlacementId, "замените_на_свой_id");
rewardedAd.setLocalExtraParameter(YbiAdaptersParameters.mintegralRewardedUnitId, "замените_на_свой_id");
```
3. Замените **YOUR_PUBLISHER_ID** на ключ издателя из [личного кабинета](https://mobileadx.ru).
4. Замените **YOUR_INTERSTITIAL_ID** на ключ соответствующий баннерной рекламе из [личного кабинета](https://mobileadx.ru).
5. Замените **YOUR_REWARDED_ID** на ключ соответствующий видео с вознаграждением из [личного кабинета](https://mobileadx.ru).

6. Реклама **YabbiAds** готова к показу, используйте метод **loadAd**, для загрузки рекламы.
```java
rewardedAd.loadAd();
```