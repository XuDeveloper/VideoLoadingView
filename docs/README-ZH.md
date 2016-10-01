# VideoLoadingView

[English Version](https://github.com/XuDeveloper/VideoLoadingView/blob/master/README.md)

这是一个加载界面，可以自定义颜色，可以改变转动的速度或者通过注册监听器的方式去控制这个界面。

这个加载界面适合于播放视频的界面中。（比如加载或者缓存视频时引入此界面）

### 预览

 ![VideoLoadingView-screenshot](https://raw.githubusercontent.com/xudeveloper/VideoLoadingView/master/docs/screen.gif)
 
### 导入

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

> 可以复制我的源码到你的项目中！

### 使用

可以在layout.xml文件中声明：

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

或者使用Java代码动态生成VideoLoadingView对象：

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

你可以注册一个监听器：

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

##**License**

```license
Copyright [2016] XuDeveloper

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

