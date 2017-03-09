# Giphy Image Results Preview
Library for GIF results, preview, play, share everything at one place!

 ![Giphy Image Results Preview - Example](https://github.com/myinnos/GiphyImageResultsPreview/raw/master/gif/GiphyImageResultsPreview.gif)

#### Download Demo APK from [HERE](https://github.com/myinnos/GiphyImageResultsPreview/raw/master/apk/demo-GiphyImageResults.apk "APK")
#### Kindly use the following links to use this library:

In build.gradle (Project)
```java
allprojects {
  repositories {
			...
		maven { url "https://jitpack.io" }
	}
}
```
And then in the other gradle file(may be your app gradle or your own module library gradle, but never add in both of them to avoid conflict.)
```java
dependencies {
	compile 'com.github.myinnos:GiphyImageResultsPreview:1.0'
}
```
How to use
-----
**GiphyTask Will gives you list of results as list** [#Example](https://github.com/myinnos/GiphyImageResultsPreview/blob/master/app/src/main/java/in/myinnos/gifimageresults/MainActivity.java "Example")
```java
String SEARCH_QUERY = "<SEARCH_QUERY>"; //ex: Funny
String GIPHY_KEY = "<GIPHY_KEY>"; //you can pass empty parameter to use default key
int limit = 100; //results limit can set from 1 to 100

new GiphyTask(Helper.getGiphyQueryUrl(SEARCH_QUERY,
                limit, GiphyQueryBuilder.EndPoint.SEARCH, GIPHY_KEY), new GiphyTask.Callback() {
            @Override
            public void onResponse(List<Gif> gifs) {
                // play with gif results
            }
        }).execute();
```
**GET YOUR GIPHY KEY BY SUBMIT YOUR DEATILS FROM** [HERE - api.giphy.com/submit](http://api.giphy.com/submit "GIPHY")

**To preview gif image to imageView from results** : used glide here | you can use picasso etc., [#Example](https://github.com/myinnos/GiphyImageResultsPreview/blob/master/app/src/main/java/in/myinnos/gifimageresults/GifAdapter.java "Example")
```java
Glide.with(getContext()).load(gif.getPreviewImageUrl()).into(previewImage);
```
**To play/pause gif image from results** [#Example-XML](https://github.com/myinnos/GiphyImageResultsPreview/blob/master/app/src/main/res/layout/list_item_gif.xml "Example") | [#Example](https://github.com/myinnos/GiphyImageResultsPreview/blob/master/app/src/main/java/in/myinnos/gifimageresults/GifAdapter.java "Example")
```xml
 <in.myinnos.gifimages.gif.GifView
        android:id="@+id/gif_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/black"
        android:visibility="gone"
        app:loop="true"
        app:muted="true"
        app:showSpinner="true"
        app:stopSystemAudio="false" />

GifView gifView = (GifView) itemView.findViewById(R.id.gif_view);
gifView.start(gif.getPreviewMp4Url());
```
**Share gif globally** [#Example](https://github.com/myinnos/GiphyImageResultsPreview/blob/master/app/src/main/java/in/myinnos/gifimageresults/GifAdapter.java "Example")
```java
new ShareGif(getContext(), gif.getGifUrl()).execute();
```

##### Any Queries? or Feedback, please let me know by opening a [new issue](https://github.com/myinnos/GiphyImageResultsPreview/issues/new)!

## Contact
#### Prabhakar Thota
* :globe_with_meridians: Website: [myinnos.in](http://www.myinnos.in "Prabhakar Thota")
* :email: e-mail: contact@myinnos.in
* :mag_right: LinkedIn: [PrabhakarThota](https://www.linkedin.com/in/prabhakarthota "Prabhakar Thota on LinkedIn")
* :thumbsup: Twitter: [@myinnos](https://twitter.com/myinnos "Prabhakar Thota on twitter")    

License
-------

    Copyright 2017 MyInnos

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
