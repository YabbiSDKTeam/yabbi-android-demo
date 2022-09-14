# YabbiAds Android SDK

## Руководство по Интеграции

Версия релиза **2.1.2** | Дата релиза **13.09.2022**

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

        // Используйте при подключении всех рекламных адаптеров
        implementation 'me.yabbi.ads:sdk:2.1.2'

        // Используйте при подключении всех рекламных адаптеров
        implementation 'me.yabbi.ads:core:1.1.2'

        // Добавьте если вы испольузете адаптер для Яндекса
        implementation 'me.yabbi.ads.networks:yandex:1.0.0'

    }
    ```

Как только gradle config будет сгенерирован, сохраните файл и нажмите **Gradle sync**.

## Шаг 2. Инициализация SDK
Мы рекомендуем вызывать инициализацию SDK в вашей MainActivity - в onCreate методе.
Вы можете указывать доступные настройки SDK с поомощью метода **setCustomParams**

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final YabbiConfiguration config = new YabbiConfiguration("YOUR_PUBLISHER_ID", "YOUR_INTERSTITIAL_ID", "YOUR_REWARDED_ID");
    YabbiAds.initialize(config);

    // Установите для показа полноэкранной рекламы Яндекса
    // YabbiAds.setCustomParams("yandex_interstitial_id", "YANDEX_INTERSTITIAL_ID");

    // Установите для показа рекламы с вознаграждением Яндекса
    // YabbiAds.setCustomParams("yandex_rewarded_id", "YANDEX_REWARDED_ID");
}
```

1. Замените YOUR_PUBLISHER_ID на ключ издателя из [личного кабинета](https://mobileadx.ru).
2. Замените YOUR_INTERSTITIAL_ID на ключ соответствующий баннерной рекламе из [личного кабинета](https://mobileadx.ru).
3. Замените YOUR_REWARDED_ID на ключ соответствующий видео с вознаграждением из [личного кабинета](https://mobileadx.ru).

## Шаг 3. Запрос геолокации пользователя
Для эффективного таргетирования рекламы SDK собирает данные геолокации.  
Начиная с **Android 6.0 (API level 23)** для доступа к геолокации требуется разрешение пользователя.
Для запроса разрешения необходимо добавить следующий код в вашей **Activity**
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
}
```
Для обработки результата добавьте слудющий код в **Activity**
```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    
    // ваш код
}
```

Вы можете ознакомиться подробнее о геолокации и разрешениях на [5 шаге](Шаг-5.-Подготовьте-ваше-приложение-к-публикации).

## Шаг 4. Настройка типов рекламы
YabbiAds SDK готов к использованию.  
YabbiAds предоставляет на выбор 2 типа рекламы.
Вы можете ознакомиться с установкой каждого типа в соответствующей документации:

1. [Полноэкранный баннер](INTERSTITIAL_DOC.md)
2. [Полноэкранный видео баннер с вознаграждением](REWARDED_VIDEO_DOC.md)

## Шаг 5. Подготовьте ваше приложение к публикации

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
