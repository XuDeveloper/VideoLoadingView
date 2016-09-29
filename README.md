# VideoLoadingView

It's a video_loading_view which can customize its color, change its speed or control it by yourselves(register a listener).

Maybe it's suitable for those video playing layouts.

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
        app:ArcColor="@color/cpb_blue"
        app:TriangleColor="@color/cpb_blue_dark"
        />

</RelativeLayout>

```

Or use Java code dynamically.

``` java

        view = (VideoLoadingView) findViewById(R.id.videoLoadingView);
        view.setArcColor(Color.GREEN);
        view.setTriangleColor(Color.GREEN);

```
