# Полноэкранный баннер
Баннер показывается пользователю на весь экран, с возможностью закрыть баннер и перейти по рекламной ссылке.

## Загрузка рекламы
Для загрузки рекламы используйте следующий код
```java
YabbiAds.loadAd(activity, YbiAdType.INTERSTITIAL);
```

## Методы обратного вызова
Для обработки событий жизненного цикла необходимо предоставить класс для работы.
```java
YabbiAds.setInterstitialListener(new YbiInterstitialListener(){
    @Override
    public void onInterstitialLoaded(){
        // Вызывется при загрузке рекламы
    }
    
    @Override
    public void onInterstitialLoadFail(String message){
        // Вызывется если при загрузке рекламы произошла ошибка
    }
    
    @Override
    public void onInterstitialShown(){
        // Вызывается при показе рекламы
    }
    
    @Override
    public void onInterstitialShowFailed(String message){
       // Вызывется если при показе рекламы произошла ошибка
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
YabbiAds.isAdLoaded(YbiAdType.INTERSTITIAL);
```

Рекомендуем всегда проверять статус загрузки рекламы, прежде чем пытаться ее показать.
```java
if(YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL)) {
    YabbiAds.showAd(activity, YbiAdType.INTERSTITIAL);
}
```

## Показ рекламы
Для показа рекламы используйте метод:
```java
YabbiAds.showAd(activity, YbiAdType.INTERSTITIAL);
```

## Уничтожение рекламного контейнера
Для уничтожения рекламы добавьте следующий код в вашем приложении.
```java
YabbiAds.destroyAd(YbiAdType.INTERSTITIAL);
```