# YABBI mobile SDK for Android

## Оглавление
* [Системные требования](#системные-требования)
* [Установка](#установка)
   * [Maven](#maven)
   * [File](#file)
* [Использование](#использование)
* [SDK API](#sdk-api)
  * [AdEvents](#adevents)
  * [YabbyAdsException](#yabbiadsexception)
  * [InterstitialAdContainer](#interstitialadcontainer)
    * [Конструктор](#конструктор)
    * [Метод load](#метод-load)
    * [Метод show](#метод-show)
    * [Метод isLoaded](#метод-isloaded)
    * [Метод setAlwaysRequestLocation](#метод-setAlwaysRequestLocation)
  * [VideoAdContainer](#videoadcontainer)

## Системные требования

Android 4.2.0+, Jelly Bean, API level 19.

## Установка

Скачиваем SDK из личного кабинета.
В папке с нашим приложением создаем папку `libs`, и закидываем в нее SDK.
В `build.gradle` добавляем следуюищй код:
```gradle
android {
    // Ваш код

    defaultConfig {
        multiDexEnabled true

        // Ваш код
    }

    // Ваш код
}

dependencies {

    // Ваш код

    // Yabbi SDK Dependencies
    implementation 'org.greenrobot:eventbus:3.3.1'
    implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.6'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.21'
    implementation 'com.google.android.gms:play-services-ads-identifier:18.0.1'
    implementation 'com.google.android.gms:play-services-location:19.0.1'
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
    implementation 'com.google.android.gms:play-services-ads:18.1.1'

    implementation (files("libs/sdk.aar"))
}
```

## Настройка AndroidManifest.xml

Мы различаем 2 типа разрешений: обязательные разрешения, без которых SDK не может работать, и дополнительные, необходимые для улучшения таргетинга.

Это обязательные разрешения:

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
```
Это дополнительные разрешения:

```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"  />
```

Все разрешения уже добавлены в плагин.  
Если вы не хотите отслеживать геопозицию ваших пользователей, добавьте в **AndroidManifest.xml** приложения следующий код:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"
	tools:node="remove" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
	tools:node="remove" />
```

## Использование
Для использования вам потребуется получить идентификатор издателя и идентификатор размещения, они выдаются в личном кабинете пользователя.  
Пример использования показа полноэкранной баннерной рекламы показан ниже. _([VideoAdContainer](#videoadcontainer) используется аналогично, просто замените InterstitialAdContainer на VideoAdContainer в коде ниже.)_  
Необходимо заменить `YOUR_PUBLISHER_ID` на ваш идентификатор издателя и `YOUR_UNIT_ID` на ваш идентификатор размещения.  

```java
import me.yabbi.ads.sdk.AdContainer;
import me.yabbi.ads.sdk.AdEvents;
import me.yabbi.ads.sdk.InterstitialAdContainer;
import me.yabbi.ads.sdk.VideoAdContainer;

final InterstitialAdContainer interstitialAdContainer = new InterstitialAdContainer(activity, YOUR_PUBLISHER_ID, YOUR_UNIT_ID);
interstitialAdContainer.load(new AdEvents() {
    @Override
    public void onLoad() {
        Log.d("YabbiADS Test", "auto ad loaded");
        interstitialAdContainer.show();
    }

    @Override
    public void onFail(String error) {
        Log.d("YabbiADS Test", "auto ad fail " + error);
    }

    @Override
    public void onShow() {
        Log.d("YabbiADS Test", "auto ad show");
    }

    @Override
    public void onClose() {
        Log.d("YabbiADS Test", "auto ad close");
    }
});
```

## SDK API

#### AdEvents:
Класс AdEvents используется для получения callback-ов о взаимодействии с рекламой.
```
void onLoad(); //метод вызывается когда рекламный контент был успешно загружен
void onFail(String error); //вызывается при происхождении ошибки препятствующей загрузке рекламы
void onShow(); //вызывается в момент показа рекламы пользователю
void onComplete(); //вызывается в VideoAdContainer в момент окончания видео, в случае rewarded типа видео можно награждать пользователя по этому событию
void onClose(); //вызывается при закрытии пользователем рекламного окна
```

#### YabbiAdsException:
Класс YabbiAdsException используется для выброски исключений связанных с SDK.

#### InterstitialAdContainer:
Класс InterstitialAdContainer используется для показа "промежуточной" (межэкранной) рекламы в виде фуллскрин баннера.  
Примечание: В дальнейшем этот-же объект может переиспользоваться для показа рекламы, если вызвать load после закрытия предыдущей рекламы.

##### Конструктор
Параметры:  
`activity` -- активити через которую будет вызываться показ баннерной активити  
`publisherId` -- ID паблишера, который был получен в личном кабинете  
`unitId` -- ID рекламного юнита, получается в личном кабинете
```
InterstitialAdContainer(Activity activity, String publisherId, String unitId);
```

##### Метод load
Инициирует загрузку рекламы.  
Когда реклама будет загружена вызовется колбек adEvents.onLoad().  
При ошибке вызовет колбек adEvents.onFail(string).  
По умолчанию контейнер запрашивает доступ к геолокации во время вызова этого метода, при помощи метода [setAlwaysRequestLocation](#метод-setAlwaysRequestLocation) данный функционал можно отключить.
```
void load(final AdEvents adEvents);
```

##### Метод show
Вызывает показ рекламного баннера пользователю, при показе вызовется колбек adEvents.onShow().  
Может выбросить исключение если попытаться показать рекламу которая не была загружена.  
Также выбрасывает исключение при попытке повторного показа одной и той же рекламы.  
Когда пользователь закроет рекламу будет вызван колбек adEvents.onClose().  
```
void show();
```

##### Метод isLoaded
Проверяет была-ли загружена реклама, если кому-то удобно проверять это поллингом.  
```
boolean isLoaded();
```

##### Метод setAlwaysRequestLocation
Позволяет включить/отключить функционал автоматического запроса разрешения на доступ к гео функциям.
```
void setAlwaysRequestLocation(boolean isEnabled);
```

#### VideoAdContainer:
Класс VideoAdContainer используется для показа fullscreen video (полноэкранного видео) и fullscreen rewarded video (полноэкранное награждаемое видео).  
Тип показываемого рекламного видео (награждаемое/обычное) определяется исходят из unitID, которое было получено в административной панели при настройке рекламного места в инвентаре, поэтому уделите особое внимание выбору типа рекламного места и убедитесь, что оно выбранно именно такое какое вам нужно.  
В остальном использование контейнера (и особенности поведения) схоже с использованием [InterstitialAdContainer](#interstitialadcontainer), см. главу [Использование](#использование).  
Вознаграждаемое видео отличается тем, что закрытие видео возможно только после его полного просмотра, либо в случае ошибки. Награждать пользователя можно по происхождению события `adEvents.onComplete`.  
Примечание: В дальнейшем этот-же объект может переиспользоваться для показа рекламы, если вызвать load после закрытия предыдущей рекламы.