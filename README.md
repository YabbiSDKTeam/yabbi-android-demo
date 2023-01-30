# YabbiAds Android SDK

## Руководство по Интеграции

Версия релиза **2.3.2** | Дата релиза **30.01.2023**

> Минимальные требования:
>
>* Используйте Android API level 19 (Android OS 4.4) и выше.

## Демо приложение
Используйте наше [демо приложение](https://github.com/YabbiSDKTeam/yabbiads-android-demo) в качестве примера.


## Шаг 1. Установка SDK

> **Рекламные адаптеры**.
>
>SDK предоставляет адаптеры для интеграции в сторонние медиации.  
>Вы можете узнать о них подробнее по ссылке - [клик](https://github.com/YabbiSDKTeam/additional-documentation/blob/master/adapters/ADAPTERS.md)

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

        // Используйте для подключения всех рекламных адаптеров
        implementation 'me.yabbi.ads:sdk:+'

        // Добавьте если хотите подключить рекламные адаптеры выборочно
        implementation 'me.yabbi.ads:core:+'

        // Добавьте если вы испольузете адаптер для Яндекса
        implementation 'me.yabbi.ads.networks:yandex:+'
   
        // Добавьте если вы испольузете адаптер для Mintegral
        implementation 'me.yabbi.ads.networks:mintegral:+'
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
Мы рекомендуем вызывать инициализацию SDK в вашей MainActivity - в onCreate методе.
Вы можете указывать доступные настройки SDK с поомощью метода **setCustomParams**

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    // Установите для показа полноэкранной рекламы Яндекса
    YabbiAds.setCustomParams(YbiAdaptersParameters.yandexInterstitialID, "замените_на_свой_id");
    
    // Установите для показа рекламы с вознаграждением Яндекса
    YabbiAds.setCustomParams(YbiAdaptersParameters.yandexRewardedID, "замените_на_свой_id");

    // Установите для показа рекламы от Mintegral
    YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralAppID, "замените_на_свой_id");
    YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralApiKey, "замените_на_свой_id");
    
    // Установите для показа полноэкранной рекламы Mintegral
    YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralInterstitialPlacementId, "замените_на_свой_id");
    YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralInterstitialUnitId, "замените_на_свой_id");
    
    // Установите для показа рекламы с вознаграждением Mintegral
    YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralRewardedPlacementId, "замените_на_свой_id");
    YabbiAds.setCustomParams(YbiAdaptersParameters.mintegralRewardedUnitId, "замените_на_свой_id");

    final YabbiConfiguration config = new YabbiConfiguration(
        "YOUR_PUBLISHER_ID", 
        "YOUR_INTERSTITIAL_ID", 
        "YOUR_REWARDED_ID"
    );
    
    // Установите если пользователь дал согласие на сбор персональных данных
    YabbiAds.setUserConsent(true);
    
    YabbiAds.initialize(this, config);
}
```

1. Замените **YOUR_PUBLISHER_ID** на ключ издателя из [личного кабинета](https://mobileadx.ru).
2. Замените **YOUR_INTERSTITIAL_ID** на ключ соответствующий баннерной рекламе из [личного кабинета](https://mobileadx.ru).
3. Замените **YOUR_REWARDED_ID** на ключ соответствующий видео с вознаграждением из [личного кабинета](https://mobileadx.ru).
4. С помощью метода **setCustomParams** установите параметры для показа рекламы из других рекламных сетей.

## Шаг 4. GDPR и CCPA
**GDPR** - Это набор правил, призванных дать гражданам ЕС больше контроля над своими личными данными. Любые издатели приложений, которые созданы в ЕС или имеющие пользователей, базирующихся в Европе, обязаны соблюдать GDPR или рискуют столкнуться с большими штрафами

Для того чтобы **YabbiAds** и наши поставщики рекламы могли предоставлять рекламу, которая наиболее релевантна для ваших пользователей, как издателю мобильных приложений, вам необходимо получить явное согласие пользователей в регионах, попадающих под действие законов GDPR и CCPA.

Чтобы получить согласие на сбор персональных данных ваших пользователей, мы предлагаем вам воспользоваться готовым решением - **YbiConsentManager**.

**YbiConsentManager** поставляется с заранее подготовленным окном согласия, которое вы можете легко показать своим пользователям. Это означает, что вам больше не нужно создавать собственное окно согласия.

Ознакомьтесь с использованием **YbiConsentManager** по ссылке - [клик](CONSENT_MANAGER_DOC.md).

## Шаг 5. Настройка типов рекламы
YabbiAds SDK готов к использованию.  
YabbiAds предоставляет на выбор 2 типа рекламы.
Вы можете ознакомиться с установкой каждого типа в соответствующей документации:

1. [Полноэкранный баннер](INTERSTITIAL_DOC.md)
2. [Полноэкранный видео баннер с вознаграждением](REWARDED_VIDEO_DOC.md)

## Шаг 6. Подготовьте ваше приложение к публикации

В соответствии с [политикой Google](https://support.google.com/googleplay/android-developer/answer/9857753?hl=ru), разрешения на определения местоположения могут запрашиваться только для функций, имеющих отношение к основному функционалу приложения. Вы не можете запрашивать доступ к данным о местоположении исключительно с целью предоставления рекламы или аналитики.

**Если вы не используете местоположения как одну из основных функций вашего приложения:**
* Удалите разрешение местоположения добавив следующий код в AndroidManifest.xml файл вашего приложения:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"
        tools:node="remove" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
tools:node="remove" />
```
* Обновите приложение в Google Play. В процессе публикации убедитесь, что в Google Play Console нет предупреждений о наличии разрешения местоположения.

**Если ваше приложение использует местоположение, как одну из основных функций:**
* Заполните форму декларации разрешений на местоположение в [Google Play Console](https://play.google.com/console/u/0/developers/app/app-content/permission-declarations). Подробнее о форме декларации вы можете прочитать [здесь](https://support.google.com/googleplay/android-developer/answer/9799150?hl=en#zippy=%2Cwhere-do-i-find-the-declaration).
* Обновите приложение в Google Play. В процессе публикации убедитесь, что в Google Play Console нет предупреждений о наличии разрешения местоположения.
