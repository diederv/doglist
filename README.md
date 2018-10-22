EgeniQ Code Assessment:
(https://github.com/egeniq/standards/wiki/Android-code-assessment)

“Create an Android application written in Kotlin (but Java is also fine) that fetches a list of dogs from an API endpoint and displays it in a nice looking list using up-to-date common Android UI practices.”

Source:
https://api.thedogapi.co.uk/v2/dog.php?limit=100:

- First challenge: The source API is down:

<p>SQLSTATE[HY000] [2002] Connection refused</p><p>SQLSTATE[HY000] [2002] Connection refused</p><p>SQLSTATE[HY000] [2002] Connection refused</p><p>SQLSTATE[HY000] [2002] Connection refused</p><p>SQLSTATE[HY000] [2002] Connection refused</p>

Solution:
Alternative Dog API #1:
https://dog.ceo/api/breeds/list/all
New Problem: Data isn’t properly structured, see: https://dog.ceo/dog-api/documentation/

Alternative Dog (related) API #2 (found on https://any-api.com/):
http://www.omdbapi.com
This API provides movie information based on a title-query, year and page. I will query this API with the keyword ‘dog’ and with year ‘2017’ and with page ‘1’ to mimic a dog related API service.

Second challenge: This will be my first Kotlin project. 
What a perfect opportunity to get to know this promising awesome new language better! :-) 

Third challenge: What architecture should I use?
I’m a big fan of the MVVM architecture as part of the Android Architecture Components.
The newly added lifecycle-management gives android that long-awaited (some would say: “opinionated”) structure that gives android development the robustness that was missing.
The ViewModel is a perfect component for retrieving the data from the Dog API in a asynchronous way using RxJava. Any sudden configuration change, like the famous rotation of a tablet, can result in the destruction and re-creation of Activities and/or Fragments, but the ViewModel will survive this event, preventing annoying memory leaks of unnecessary extra remote calls. The new lifecycle management will make sure that the (re-)created Views will reconnect to the ViewModel that itself didn’t stop listening to the response of the initial API request. 
Another powerful addition to the MVVM architectural pattern is the Android Binding Library. This library enables us to bind UI components (in our layout files) to data sources in our app using a declarative format. This makes any project simpler and easier to maintain.

Source of Inspiration:
Since this project is my first Kotlin project, I looked for an example project that contains all the architectural elements that I listed above. I came up with the following github project:
https://github.com/gahfy/MVVMPosts

Libraries used:
Retrofit, the best option for an easy connection to a REST Api
OkHttp, to be able to add request interceptors to Retrofit
RxJava, still the most commonly used Reactive component for Android
Dagger 2, the most efficient dependency injection framework for Android
Picasso, an efficient library for lazy loading and caching of images
GSON, for data (de-)serialization

Fourth challenge:
I wanted to use the efficient Constraint Layout but somehow adding its support-library caused a version conflict with other support-libraries. The versions of these libraries need to be exactly the same. Solution: I commented-out the library in the gradle file and used the Linear- and Relative layout instead.

Fifth challenge:
Initially I tried to use Moshy for the data (de-)serialization, but somehow I had difficulties mapping the unusual data-field names (with capitals) to lowercase (object) member names. With GSON this wasn’t a problem.

Expanding on the use of the OkHttp library:
Retrofit allows us to define and create our own HttpClient, doing so enables us to intercept any request before sending it to the server. This way we can easily debug these requests which is very useful during development and it allows us to add the APIKey credentials to all requests without exposing them in the Retrofit interface.

Sixth challenge:
Unit and UI Testing is a bit different with Kotlin. I noticed that Kotlin adds a few powerful possibilities to testing but it also demands going through a learning curve..
The Kotlin tests in the included project are not ready yet. I wasn’t able to finish them in the limited time to create this project but please keep an eye on the project on guthub because I certainly will make them work! :-)

Seventh challenge:
The example project uses (Mutable)LiveData objects in the ViewModel and connects to them from within the layout-files using the combination of data binding library and using custom BindingAdapters, as the following example shows:

Layout file (item_post.xml):
```xml
<TextView
    android:id="@+id/post_body"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    app:mutableText="@{viewModel.getPostBody()}"
	... />
```

Utility class: BindingsAdapters.kt
```kotlin
@BindingAdapter("mutableText")
fun setMutableText(view: TextView,  text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}
```

Normally, the ViewModel contains Observable members or the ViewModel itself is an Observable object. After changing the value of any member, we need to notify its listeners as follows:

```kotlin
notifyPropertyChanged(BR.loading);
```

But in the case of MutableLiveData, this should be managed automatically. The following line seems abundant:

```kotlin
text.observe(parentActivity, Observer { value -> view.text = value?:""})
```
I will investigate this further..
