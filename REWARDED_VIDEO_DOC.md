# Полноэкранный видео баннер с вознаграждением
Баннер показывается пользователю на весь экран, с возможностью закрыть баннер и перейти по рекламной ссылке.  
Когда пользователь досмотрит такую рекламу, он может получить вознаграждение

## Загрузка рекламы
Для загрузки рекламы используйте следующий код
```java
YabbiAds.loadAd(activity, YbiAdType.REWARDED);
```

## Методы обратного вызова
Для работы с рекламой необходимо предоставить класс для передачи событий жизненного цикла рекламного контейнера.
Для инициализации рекламного контейнера выполните следующие действия:
```java
YabbiAds.setRewardedListener(new YbiRewardedListener(){
    @Override
    public void onRewardedLoaded(){
        // Вызывется при загрузке рекламы
    }
    
    @Override
    public void onRewardedLoadFail(String message){
        // Вызывется если при загрузке рекламы произошла ошибка
    }
    
    @Override
    public void onRewardedShown(){
        // Вызывается при показе рекламы
    }
    
    @Override
    public void onRewardedShowFailed(String message){
       // Вызывется если при показе рекламы произошла ошибка
    }
    
    @Override
    public void onRewardedClosed(){
        // Вызывается при закрытии рекламы
    }
    
    @Override
    public void onRewardedFinished(){
        // Вызывется когда реклама закончилась
    }
}
```

## Проверка загрузки рекламы
Вы можете проверить статус загрузки перед работы с рекламой.
```java
YabbiAds.isAdLoaded(YbiAdType.REWARDED);
```

Рекомендуем всегда проверять статус загрузки рекламы, прежде чем пытаться ее показать.
```java
if(YabbiAds.isAdLoaded(YbiAdType.REWARDED)) {
    YabbiAds.showAd(activity, YbiAdType.REWARDED);
}
```

## Показ рекламы
Для показа рекламы используйте метод:
```java
YabbiAds.showAd(activity, YbiAdType.REWARDED);
```

## Уничтожение рекламного контейнера
Для уничтожения рекламы добавьте следующий код в вашем приложении.
```java
YabbiAds.destroyAd(YbiAdType.REWARDED);
```