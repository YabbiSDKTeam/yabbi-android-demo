# Полноэкранный баннер
Баннер показывается пользователю на весь экран, с возможностью закрыть баннер и перейти по рекламной ссылке.

## Загрузка рекламы
Для загрузки рекламы используйте следующий код
```java
YabbiAds.loadAd(this, YabbiAds.INTERSTITIAL, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

## Методы обратного вызова
Для обработки событий жизненного цикла необходимо предоставить класс для работы.
```java
YabbiAds.setInterstitialListener(new YbiInterstitialListener(){
    @Override
    public void onInterstitialLoaded(){
        // Вызывется при загрузке рекламы
    }
    
    @Override
    public void onInterstitialLoadFail(AdException error){
        // Вызывется если при загрузке рекламы произошла ошибка
        // С помощью AdException error можно получить подробную информацию об ошибке
    }
    
    @Override
    public void onInterstitialShown(){
        // Вызывается при показе рекламы
    }
    
    @Override
    public void onInterstitialShowFailed(AdException error){
       // Вызывется если при показе рекламы произошла ошибка
       // С помощью AdException error можно получить подробную информацию об ошибке
    }
    
    @Override
    public void onInterstitialClosed(){
        // Вызывается при закрытии рекламы
    }
}
```

## Проверка загрузки рекламы
Вы можете проверить статус загрузки перед работы с рекламой.
```java
YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

Рекомендуем всегда проверять статус загрузки рекламы, прежде чем пытаться ее показать.
```java
if(YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL, "placement_name")) {
    YabbiAds.showAd(activity, YabbiAds.INTERSTITIAL, "placement_name");
}
```

## Показ рекламы
Для показа рекламы используйте метод:
```java
YabbiAds.showAd(activity, YabbiAds.INTERSTITIAL, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

## Уничтожение рекламного контейнера
Для уничтожения рекламы добавьте следующий код в вашем приложении.
```java
YabbiAds.destroyAd(YabbiAds.INTERSTITIAL, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).