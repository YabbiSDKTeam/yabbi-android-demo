# YabbiAds Android SDK

## Руководство по Интеграции

Версия релиза **3.0.1** | Дата релиза **10.10.2023**

> Минимальные требования:
>
>* Используйте Android API level 19 (Android OS 4.4) и выше.

## Демо приложение
Используйте наше [демо приложение](https://github.com/YabbiSDKTeam/yabbiads-android-demo) в качестве примера.


## Установка SDK

### Подготовьте Gradle сборки для Android 11
>
>В Android 11 изменился способ запроса приложений и взаимодействия с другими.
приложениями, установленными пользователем на устройстве.
По этой причине убедитесь, что вы используете версию Gradle,
которая соответствует одной из перечисленных [здесь](https://developer.android.com/studio/releases/gradle-plugin#4-0-0).

В зависимости от используемой версии Android Studio вставьте зависимость в файл Gradle.

Если вы используете адаптеры для сторонних рекламных сетей,так же добавьте их зависимости.

1. Вставьте следующий код в settings.gradle в корне проекта.  
   **Начиная с Arctic Fox и выше**
    ```gradle
    // Пример project-level settings.gradle
    
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            // ... другие репозитории
             maven {
                url "https://mobileadx.ru/maven" // Это репозиторий YabbiAds
            }
            maven {
                url 'https://android-sdk.is.com' // Это репозиторий IronSource
            }
            maven {
                url  "https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea"  // Это репозиторий для Mintegral
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
                url "https://mobileadx.ru/maven" // Это репозиторий YabbiAds
            }
            maven {
                url 'https://android-sdk.is.com' // Это репозиторий IronSource
            }
            maven {
                url  "https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea"  // Это репозиторий для Mintegral
            }
        }
    }
    ```


2. Вставьте следующий код в app-level build.gradle
   ```gradle
    // Пример app-level build.gradle
   
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
   ```
3. В этом же файле обновите раздел `dependencies`
   * Для подключения плагина со всеми рекламными сетями вставьте следующий код.
   ```gradle
    // Пример app-level build.gradle
    
    dependencies {
        // ... другие зависимости проекта

        implementation 'me.yabbi.ads:sdk:3.0.0' // Это плагин YabbiAds SDK
    }
   ```
   * Вы можете подключить рекламные сети выборочно. Для этого вставьте следующий код.
   ```gradle
    // Пример app-level build.gradle
    
    dependencies {
        // ... другие зависимости проекта

        implementation 'me.yabbi.ads:core:3.0.0' // Это обязательная зависимость SDK
        implementation 'me.yabbi.ads.networks:yandex:1.3.0' // Это рекламная сеть Yandex
        implementation 'me.yabbi.ads.networks:ironsource:1.2.0' // Это рекламная сеть IronSource
        implementation 'me.yabbi.ads.networks:mintegral:1.3.0' // Это рекламная сеть Mintegral
    }
   ```

Как только gradle config будет сгенерирован, сохраните файл и нажмите **Gradle sync**.

## Настройка проекта

### Настройка Network security config
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

## Инициализация SDK
Импортируйте `YabbiAds`.
```java
import me.yabbi.ads.YabbiAds;
```

### Сбор данных пользователя

#### GDPR и CCPA
GDPR - Это набор правил, призванных дать гражданам ЕС больше контроля над своими личными данными. Любые издатели приложений, которые созданы в ЕС или имеющие пользователей, базирующихся в Европе, обязаны соблюдать GDPR или рискуют столкнуться с большими штрафами

Для того чтобы YabbiAds и наши поставщики рекламы могли предоставлять рекламу, которая наиболее релевантна для ваших пользователей, как издателю мобильных приложений, вам необходимо получить явное согласие пользователей в регионах, попадающих под действие законов GDPR и CCPA.

#### YBIConsentManager

Чтобы получить согласие на сбор персональных данных ваших пользователей, мы предлагаем вам воспользоваться готовым решением - `YBIConsentManager`.

`YBIConsentManager` поставляется с заранее подготовленным окном согласия, которое вы можете легко показать своим пользователям. Это означает, что вам больше не нужно создавать собственное окно согласия.

Ознакомьтесь с использованием `YBIConsentManager` по ссылке - [клик](CONSENT_MANAGER_DOC.md).

#### Установка разрешения на сбор данных
Если пользователь дал согласие на сбор данных, установите `setUserConsent` в `true`
```java
YabbiAds.setUserConsent(true);
```

### Работа сторонних рекламных сетей
Для работы сторонних рекламных сетей необходимо добавить идентификаторы для каждой рекламной сети.

Импортируйте `ExternalInfoStrings` в начале файла и установите идентификаторы выбранных вами сетей.

```java
import me.yabbi.ads.common.ExternalInfoStrings;

// Установите для показа полноэкранной рекламы Яндекса
YabbiAds.setCustomParams(ExternalInfoStrings.yandexInterstitialUnitID, "замените_на_свой_id");

// Установите для показа рекламы с вознаграждением Яндекса
YabbiAds.setCustomParams(ExternalInfoStrings.yandexRewardedUnitID, "замените_на_свой_id");

// Установите для показа рекламы от IronSource
YabbiAds.setCustomParams(ExternalInfoStrings.ironSourceAppID, "замените_на_свой_id");

// Установите для показа полноэкранной рекламы IronSource
YabbiAds.setCustomParams(ExternalInfoStrings.ironSourceInterstitialPlacementID, "замените_на_свой_id");

// Установите для показа рекламы с вознаграждением IronSource
YabbiAds.setCustomParams(ExternalInfoStrings.ironSourceRewardedPlacementID, "замените_на_свой_id");

// Установите для показа рекламы от Mintegral
YabbiAds.setCustomParams(ExternalInfoStrings.mintegralAppID, "замените_на_свой_id");
YabbiAds.setCustomParams(ExternalInfoStrings.mintegralApiKey, "замените_на_свой_id");

// Установите для показа полноэкранной рекламы Mintegral
YabbiAds.setCustomParams(ExternalInfoStrings.mintegralInterstitialPlacementId, "замените_на_свой_id");
YabbiAds.setCustomParams(ExternalInfoStrings.mintegralInterstitialUnitId, "замените_на_свой_id");

// Установите для показа рекламы с вознаграждением Mintegral
YabbiAds.setCustomParams(ExternalInfoStrings.mintegralRewardedPlacementId, "замените_на_свой_id");
YabbiAds.setCustomParams(ExternalInfoStrings.mintegralRewardedUnitId, "замените_на_свой_id");
```
> Используйте метод `setCustomParams` до вызова метода `initialize`.

### Инициализация
Теперь `YabbiAds` готова к инициализации.

Используйте код ниже, чтобы SDK заработал в вашем проекте.
```java
YabbiAds.initialize("publisher_id");
```
Замените `publisher_id` на идентификатор издателя из [личного кабинета](https://mobileadx.ru/settings).

Ниже представлен полный код.

Мы рекомендуем вызывать инициализацию SDK в вашей `MainActivity` - в `onCreate` методе.

```java
import me.yabbi.ads.YabbiAds;
import me.yabbi.ads.common.ExternalInfoStrings;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    YabbiAds.setCustomParams(ExternalInfoStrings.yandexInterstitialUnitID, "замените_на_свой_id");
    YabbiAds.setCustomParams(ExternalInfoStrings.yandexRewardedUnitID, "замените_на_свой_id");

    YabbiAds.setCustomParams(ExternalInfoStrings.ironSourceAppID, "замените_на_свой_id");

    YabbiAds.setCustomParams(ExternalInfoStrings.ironSourceInterstitialPlacementID, "замените_на_свой_id");

    YabbiAds.setCustomParams(ExternalInfoStrings.ironSourceRewardedPlacementID, "замените_на_свой_id");

    YabbiAds.setCustomParams(ExternalInfoStrings.mintegralAppID, "замените_на_свой_id");
    YabbiAds.setCustomParams(ExternalInfoStrings.mintegralApiKey, "замените_на_свой_id");

    YabbiAds.setCustomParams(ExternalInfoStrings.mintegralInterstitialPlacementId, "замените_на_свой_id");
    YabbiAds.setCustomParams(ExternalInfoStrings.mintegralInterstitialUnitId, "замените_на_свой_id");

    YabbiAds.setCustomParams(ExternalInfoStrings.mintegralRewardedPlacementId, "замените_на_свой_id");
    YabbiAds.setCustomParams(ExternalInfoStrings.mintegralRewardedUnitId, "замените_на_свой_id");

    YabbiAds.setUserConsent(true);
    YabbiAds.initialize("publisher_id");
}
```

## Режим отладки
В режиме отладки SDK логирует ошибки и события. По умолчанию выключен.

Для включения режима отладки используйте метод `enableDebug`.

```java
YabbiAds.enableDebug(true);
```

## Тестовая реклама
Для демонстрации работы SDK используйте тестовые идентификаторы.

* **Publisher ID** - `65057899-a16a-4877-989b-38c432a7fa15`
* **Yabbi Interstitial ID** - `b8359c60-9bde-47c9-85ff-3c7afd2bd982`
* **Yabbi Rewarded ID** - `eaac7a7f-b0b0-46d2-ac95-bd58578e9e29`
* **Yandex Interstitial ID** - `cd1dff91-76d0-44c2-a6ca-fd3f446ef9b5`
* **Yandex Rewarded ID** - `7e6334fc-ef08-45e9-9581-d18026a2fadb`
* **IronSource Interstitial ID** - `ec9decde-58c8-4d1b-885f-479b05f39dcb`
* **IronSource Rewarded ID** - `9b45ac1a-ca72-4d57-9f0f-7bab924ad1b4`

## Типы рекламы

Вы можете подключить 2 типа рекламы в свое приложение.

* Полноэкранная реклама - баннер на весь экран, который можно закрыть через несколько секунд.
* Полноэкранная реклама с вознаграждением - видео, после просмотра которого пользователю можно выдать награду.

Ознакомьтесь с детальной документацией по каждому типу рекламы

1. [Полноэкранная реклама](INTERSTITIAL_DOC.md)
2. [Полноэкранная реклама с вознаграждением](REWARDED_VIDEO_DOC.md)

## Подготовьте ваше Android приложение к публикации

В соответствии с [политикой Google](https://support.google.com/googleplay/android-developer/answer/9857753?hl=ru), разрешения на определения местоположения могут запрашиваться только для функций, имеющих отношение к основному функционалу приложения. Вы не можете запрашивать доступ к данным о местоположении исключительно с целью предоставления рекламы или аналитики.



**Если вы не используете местоположения как одну из основных функций вашего приложения:**
* Добавьте следующий код в `AndroidManifest.xml` вашего приложения:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" tools:node="remove" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" tools:node="remove" />
```
* Обновите приложение в Google Play. В процессе публикации убедитесь, что в Google Play Console нет предупреждений о наличии разрешения местоположения.

**Если ваше приложение использует местоположение, как одну из основных функций:**
* Заполните форму декларации разрешений на местоположение в [Google Play Console](https://play.google.com/console/u/0/developers/app/app-content/permission-declarations). Подробнее о форме декларации вы можете прочитать [здесь](https://support.google.com/googleplay/android-developer/answer/9799150?hl=en#zippy=%2Cwhere-do-i-find-the-declaration).
* Обновите приложение в Google Play. В процессе публикации убедитесь, что в Google Play Console нет предупреждений о наличии разрешения местоположения.
