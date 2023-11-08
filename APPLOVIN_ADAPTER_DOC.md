# Адаптер YabbiAds для AppLovin

## Руководство по Интеграции

Версия релиза **1.1.0** | Дата релиза **10.10.2023**

> Минимальные требования:
>
>* Используйте Android API level 19 (Android OS 4.4) и выше.

## Установка SDK

### Подготовьте Gradle сборки для Android 11
>
>В Android 11 изменился способ запроса приложений и взаимодействия с другими.
приложениями, установленными пользователем на устройстве.
По этой причине убедитесь, что вы используете версию Gradle,
которая соответствует одной из перечисленных [здесь](https://developer.android.com/studio/releases/gradle-plugin#4-0-0).

Вы можете использовать адаптеры для медиации выборочно. Чем больше их подключено - тем больше филрейт.

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

        implementation 'me.yabbi.ads.adapters:applovin:1.1.0' // Это рекламная сеть ябби

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
Инициализируйте **AppLovin**, следуя официальной [документации AppLovin](https://dash.applovin.com/documentation/mediation/android/getting-started/integration).

Для добавления рекламной сети **YabbiAds** следуйте инструкции по добавлению кастомной рекламной сети - [клик](https://dash.applovin.com/documentation/mediation/android/mediation-setup/custom-sdk).

Заполните поля следующими параметрами
* **Network Type** - `SDK`
* **Custom Network Name** - `YabbiAds`
* **Android Adapter Class Name** - `com.applovin.mediation.adapters.YabbiMediationAdapter`
* **iOS Adapter Class Name** - `AppLovinMediationYabbiAdsCustomAdapter`


В поле **App ID** указывайте ваш **Publisher ID** из кабинета Yabbi.

## Тестовая реклама
Для демонстрации работы SDK используйте тестовые идентификаторы.

(Работают только для Android)

* **Publisher ID** - `65057899-a16a-4877-989b-38c432a7fa15`
* **Yabbi Interstitial ID** - `b8359c60-9bde-47c9-85ff-3c7afd2bd982`
* **Yabbi Rewarded ID** - `eaac7a7f-b0b0-46d2-ac95-bd58578e9e29`
* **Yandex Interstitial ID** - `cd1dff91-76d0-44c2-a6ca-fd3f446ef9b5`
* **Yandex Rewarded ID** - `7e6334fc-ef08-45e9-9581-d18026a2fadb`
* **IronSource Interstitial ID** - `ec9decde-58c8-4d1b-885f-479b05f39dcb`
* **IronSource Rewarded ID** - `9b45ac1a-ca72-4d57-9f0f-7bab924ad1b4`

Установите у адаптера YabbiAds CPM Price 5000$ чтобы увидеть рекламу YabbiAds.

## Типы рекламы

Вы можете подключить 2 типа рекламы в свое приложение.

* Полноэкранная реклама - баннер на весь экран, который можно закрыть через несколько секунд.
* Полноэкранная реклама с вознаграждением - видео, после просмотра которого пользователю можно выдать награду.

Ознакомьтесь с детальной документацией по каждому типу рекламы

1. [Полноэкранная реклама](https://dash.applovin.com/documentation/mediation/android/ad-formats/interstitials)
2. [Полноэкранная реклама с вознаграждением](https://dash.applovin.com/documentation/mediation/android/ad-formats/rewarded-ads)