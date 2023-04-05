# Полноэкранный видео баннер с вознаграждением
Видео, после просмотра которого пользователю можно выдать награду.

## Загрузка рекламы
Для загрузки рекламы используйте следующий код
```java
YabbiAds.loadAd(activity, YabbiAds.REWARDED);
```

## Методы обратного вызова
Для обработки событий жизненного цикла необходимо предоставить класс для работы.
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
YabbiAds.isAdLoaded(YabbiAds.REWARDED);
```

Рекомендуем всегда проверять статус загрузки рекламы, прежде чем пытаться ее показать.
```java
if(YabbiAds.isAdLoaded(YabbiAds.REWARDED)) {
    YabbiAds.showAd(activity, YabbiAds.REWARDED);
}
```

## Показ рекламы
Для показа рекламы используйте метод:
```java
YabbiAds.showAd(activity, YabbiAds.REWARDED);
```

## Уничтожение рекламного контейнера
Для уничтожения рекламы добавьте следующий код в вашем приложении.
```java
YabbiAds.destroyAd(YabbiAds.REWARDED);
```