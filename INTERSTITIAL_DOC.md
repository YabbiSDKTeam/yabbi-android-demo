# Полноэкранный баннер
Баннер показывается пользователю на весь экран, с возможностью закрыть баннер и перейти по рекламной ссылке.

## Загрузка рекламы
Для загрузки рекламы используйте следующий код
```java
YabbiAds.loadAd(activity, YabbiAds.INTERSTITIAL);
```

## Методы обратного вызова
Для работы с рекламой необходимо предоставить класс для передачи событий жизненного цикла рекламного контейнера.
Для инициализации рекламного контейнера выполните следующие действия:
```java
YabbiAds.setInterstitialCallbacks(new InterstitialAdCallbacks(){
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
YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL);
```

Рекомендуем всегда проверять статус загрузки рекламы, прежде чем пытаться ее показать.
```java
if(YabbiAds.isAdLoaded(YabbiAds.INTERSTITIAL)) {
    YabbiAds.showAd(YabbiAds.INTERSTITIAL);
}
```

## Показ рекламы
Для показа рекламы используйте метод:
```java
YabbiAds.showAd(YabbiAds.INTERSTITIAL);
```

## Уничтожение рекламного контейнера
Для уничтожения рекламы добавьте следующий код в вашем приложении.
```java
YabbiAds.destroyAd(YabbiAds.INTERSTITIAL);
```