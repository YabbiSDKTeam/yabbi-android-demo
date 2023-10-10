# Полноэкранный видео баннер с вознаграждением
Баннер показывается пользователю на весь экран, с возможностью закрыть баннер и перейти по рекламной ссылке.

Когда пользователь досмотрит такую рекламу, он может получить вознаграждение.

## Загрузка рекламы
Для загрузки рекламы используйте следующий код
```java
YabbiAds.loadAd(activity, YabbiAds.REWARDED, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

## Методы обратного вызова
Для обработки событий жизненного цикла необходимо предоставить класс для работы.
```java
YabbiAds.setRewardedListener(new YbiRewardedListener(){
    @Override
    public void onRewardedLoaded(){
        // Вызывется при загрузке рекламы
    }
    
    @Override
    public void onRewardedLoadFail(AdException error){
        // Вызывется если при загрузке рекламы произошла ошибка
        // С помощью AdException error можно получить подробную информацию об ошибке
    }
    
    @Override
    public void onRewardedShown(){
        // Вызывается при показе рекламы
    }
    
    @Override
    public void onRewardedShowFailed(AdException error){
       // Вызывется если при показе рекламы произошла ошибка
       // С помощью AdException error можно получить подробную информацию об ошибке
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
YabbiAds.isAdLoaded(YabbiAds.REWARDED, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

Рекомендуем всегда проверять статус загрузки рекламы, прежде чем пытаться ее показать.
```java
if(YabbiAds.isAdLoaded(YabbiAds.REWARDED, "placement_name")) {
    YabbiAds.showAd(activity, YabbiAds.REWARDED, "placement_name");
}
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

## Показ рекламы
Для показа рекламы используйте метод:
```java
YabbiAds.showAd(activity, YabbiAds.REWARDED, "placement_name");
```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).

## Уничтожение рекламного контейнера
* Для уничтожения рекламы с выбранным `placement_name` добавьте следующий код в вашем приложении.
    ```java
    YabbiAds.destroyAd(YabbiAds.REWARDED, "placement_name");
    ```
* Для уничтожения всех рекламных контейнеров добавьте следующий код в вашем приложении.
    ```java
    YabbiAds.destroyAd(YabbiAds.REWARDED);
    ```
Замените `placement_name` на идентификатор рекламного блока из [личного кабинета](https://mobileadx.ru).