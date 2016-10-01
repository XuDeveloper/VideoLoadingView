# VideoLoadingView

It's a loading view which can customize its color, change its speed or control it by yourselves(register a listener).

Maybe it's suitable for those video playing layouts.

### Preview

![VideoLoadingView-screenshot](https://raw.githubusercontent.com/xudeveloper/VideoLoadingView/master/docs/screen.gif)

### Integration

#### Android Studio

``` xml
  allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
  }

  dependencies {
	    compile 'com.github.XuDeveloper:VideoLoadingView:v1.0'
  }

```
#### Eclipse

> Maybe you can copy my code to your project!

### Usage

#### VideoLoadingView

Declare a VideoLoadingView inside your XML layout file:

``` xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.xu.library.VideoLoadingView
        android:id="@+id/videoloadingview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:ArcColor="@color/green"
        app:TriangleColor="@color/green"
        />

</RelativeLayout>

```

Or use Java code dynamically.

``` java

    view = (VideoLoadingView) findViewById(R.id.videoLoadingView);
    view.setArcColor(Color.GREEN);
    view.setTriangleColor(Color.GREEN);
    view.setSpeed(VideoLoadingViewSpeed.SPEED_FAST);  //Default: VideoLoadingViewSpeed.SPEED_MEDIUM
    // view.setSpeed(VideoLoadingViewSpeed.SPEED_SLOW); 
    view.start();  //Default: stop()
    // view.pause();
    // view.stop();

```

You can register a listener:

```
    view.registerVideoViewListener(new VideoLoadingView.VideoViewListener() {
         @Override
         public void onStart() {
             Log.i(TAG, "video_loading_view onStart");
         }

         @Override
         public void onPause(double progress) {
             Log.i(TAG, "video_loading_view onPause, progress:" + progress);
         }

         @Override
         public void onStop() {
             Log.i(TAG, "video_loading_view onStop");
         }
     });

```
