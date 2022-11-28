# YBIConsentManager

## Руководство по Интеграции

Версия релиза **1.0.0** | Дата релиза **12.11.2022**

> Минимальные требования:
>
>* Используйте Android API level 19 (Android OS 4.4) и выше.

## Демо приложение
Используйте наше [демо приложение](https://github.com/YabbiSDKTeam/yabbiads-android-demo) в качестве примера.

## GDPR
**GDPR** - Это набор правил, призванных дать гражданам ЕС больше контроля над своими личными данными. Любые издатели приложений, которые созданы в ЕС или имеющие пользователей, базирующихся в Европе, обязаны соблюдать GDPR или рискуют столкнуться с большими штрафами.

Для того чтобы YabbiAds и наши поставщики рекламы могли предоставлять рекламу, которая наиболее релевантна для ваших пользователей, как издателю мобильных приложений, вам необходимо получить явное согласие пользователей в регионах, попадающих под действие законов GDPR и CCPA.

Чтобы получить согласие на сбор персональных данных ваших пользователей, мы предлагаем вам воспользоваться готовым решением - **YbiConsentManager**.

**YbiConsentManager** поставляется с заранее подготовленным окном согласия, которое вы можете легко показать своим пользователям. Это означает, что вам больше не нужно создавать собственное окно согласия.

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

        implementation 'me.yabbi.ads.consent:+'
    }
   ```

Как только gradle config будет сгенерирован, сохраните файл и нажмите **Gradle sync**.

## Шаг 2. Инициализация SDK
Создайте в вашей **Activity** переменную **YbiConsentManager**

```java
final YbiConsentManager consentManager = YbiConsentManager.getInstance(this);
```

Мы рекомендуем вызывать инициализацию SDK в вашей MainActivity - в onCreate методе.
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    consentManager.loadManager();
}
```

## Методы обратного вызова
Для обработки событий жизненного цикла необходимо предоставить класс для работы.
```java
consentManager.setListener(new YbiConsentListener() {
    @Override
    public void onConsentManagerLoaded() {
        // Вызывается когда менеджер готов к показу
    }

    @Override
    public void onConsentManagerLoadFailed(String error) {
        // Вызывется если при загрузке произошла ошибка
    }

    @Override
    public void onConsentWindowShown() {
        // Вызывается при показе экрана
    }

    @Override
    public void onConsentManagerShownFailed(String error) {
        // Вызывется если при показе экрана произошла ошибка
    }

    @Override
    public void onConsentWindowClosed(boolean hasConsent) {
        // Вызывается при закрытии экрана
        // hasConsent - определяет дал ли пользователь согласие
    }
});
```

## Обновление статуса
Для обновления статуса **YabbiAds** используется метод **setUserConsent**.
```java
YabbiAds.setUserConsent(consentManager.hasConsent());
```


## Кастомизация экрана
**SDK** позволяет кастомизировать экран, в зависимости от выставленных параметров. Для кастомизации, вызовите метод **registerCustomVendor** как показано ниже.
```java
final YbiConsentBuilder builder = new YbiConsentBuilder();
consentManager.registerCustomVendor(builder);
```
Для того чтобы задать параметры, вызывайте методы переменной **builder**. Ниже представлен список всех доступных методов.

* **appendPolicyURL** - устанавливает политику конфидециальности вашего приложения.
* **appendBundle** - устанавливает кастомный **Bundle**. Он используется для установки иконки и названия приложения.
* **appendName** - устанавливает кастомное имя вашего приложения.
* **appendGDPR** - включает или выключает **GDPR**.

Полный пример представлен ниже:
```java
final YbiConsentBuilder builder = new YbiConsentBuilder()
        .appendPolicyURL("https://yabbi.me/privacy-policies")
        .appendGDPR(true)
        .appendBundle("me.yabbi.ads")
        .appendName("Test name");
        
consentManager.registerCustomVendor(builder);
```

## Показ экрана с запросом разрешений.
Чтобы показать экран, необходимо вызывать метод **showConsentWindow**.

```java
consentManager.showConsentWindow()
```

## Вспомогательные методы
* **hasConsent()** - если пользователь давал согласие, возвращает **true**.
* **enableLog(boolean value)** - включает/выключает принты в консоль.