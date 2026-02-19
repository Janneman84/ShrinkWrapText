<img id="badge" src="https://jitpack.io/v/Janneman84/ShrinkWrapTextView.svg"> [![XML Compose](https://img.shields.io/badge/XML-Compose-brightgreen)](#)
[![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](#)
[![KMP](https://img.shields.io/badge/Kotlin-Multiplatform-%237f52ff?logo=kotlin)](#)<img height="1" img width="1" alt="shrinkwrap" src="https://github.com/user-attachments/assets/10178d16-cfbf-465a-a08a-9cbd39a636c3"/>

# ShrinkWrapText
Fixes oversized Text/TextViews (and Buttons) in <b>Android</b> and <b>KMP</b> apps:

<img width="203" height="336" alt="shrinkwrap" src="https://github.com/user-attachments/assets/43707776-48f7-40ad-9e27-3315c1a4386a" />

# 
This is particularly useful for chat bubbles!

![shrinkwrap](https://github.com/user-attachments/assets/10178d16-cfbf-465a-a08a-9cbd39a636c3)

## Explanation

Have you ever noticed that all chat apps on iOS and Android have one common difference? Once you see it you can't unsee! The difference is sizing of the chat bubbles. On Android chat bubbles that have more than one line of text are always maxed out to their maximum width. This often results in bubbles that are just too big. `ShrinkWrapText` fixes this issue, it's super easy to install so give it a try!

This shrink-wrapping technology is already being used by Signal messenger.

This package offers a solution for both traditional XML and Compose based apps.

## Installation


This is using jitpack.io. If you haven't already, add `maven { url = uri("https://jitpack.io")}` to `settings.gradle`/`settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io")} // Add this
    }
}
```

<details>
  <summary><b>Add dependencies (Android)</b></summary>
<br>

Add one or both of below's implementations to your apps' `build.gradle`:

```kotlin
dependencies {
	implementation("com.github.Janneman84.ShrinkWrapText:XML:0.4.0") // XML
	implementation("com.github.Janneman84.ShrinkWrapText:Compose:0.4.0") // Compose
}
```
</details>

<details>
  <summary><b>Add dependencies (Kotlin Multiplatform)</b></summary>
<br>

With shared UI, use commonMain in `build.gradle` to target all platforms:

```kotlin
commonMain.dependencies {
	implementation("com.github.Janneman84.ShrinkWrapText:Compose:0.4.0")
}
```
Or use specific targets:
```
com.github.Janneman84.ShrinkWrapText:Compose-wasm-js:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-macosarm64:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-iosx64:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-macosx64:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-android:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-jvm:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-js:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-iossimulatorarm64:0.4.0
com.github.Janneman84.ShrinkWrapText:Compose-iosarm64:0.4.0
```
</details>

# 
<details>
  <summary><b>How to use (XML)</b></summary>
<br>
	
There are three ways to shrink-wrap your TextViews and Buttons.
### Option 1
In your layout xml replace `TextView`/`MaterialTextView` with `ShrinkWrapTextView`/`ShrinkWrapMaterialTextView`.
You can also replace `Button`/`MaterialButton` with `ShrinkWrapButton`/`ShrinkWrapMaterialButton` respectively.

You can optionally use the custom attribute `shrinkWrap` to turn shrink-wrapping on and off. You should see it in action in the Designer (preview) pane.

```xml
xmlns:custom="http://schemas.android.com/apk/res-auto"
```
```xml
<shrinkwrap.xml.ShrinkWrapTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:maxWidth="200dp"
    android:text="This is a ShrinkWrapped TextView"
    custom:shrinkWrap="true"
    ...
/>
```
### Option 2
If you have subclassed from `AppCompatTextView`/`AppCompatButton`/`MaterialTextView`/`MaterialButton`, have them subclass from `ShrinkWrapTextView`/`ShrinkWrapButton`/`ShrinkWrapMaterialTextView`/`ShrinkWrapMaterialButton` respectively.

You can use the property `shrinkWrap` to turn shrink-wrapping on or off.
```kotlin
import shrinkwrap.xml.*
```
```kotlin
class MyTextView(context: Context, attrs: AttributeSet?) : ShrinkWrapTextView(context, attrs) {
    ...
}

class MyButton(context: Context, attrs: AttributeSet?) : ShrinkWrapButton(context, attrs) {
    ...
}
```

### Option 3
If changing the superclass isn't an option you can also override `onMeasure()` in your own `TextView`/`Button` subclass instead and call `setMeasuredDimension()` like this:
```kotlin
import shrinkwrap.xml.*
```
```kotlin
// Kotlin
override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec) // Call super first!
    setMeasuredDimension(measureShrinkWrappedWidth(), measuredHeight)
}
```

```java
// Java
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec); // Call super first!
    setMeasuredDimension(
		ShrinkWrapTextViewKt.measureShrinkWrappedWidth(this),
		getMeasuredHeight()
	);
}
```
</details>

<details>
  <summary><b>How to use (Compose)</b></summary>

### Option 1
In Compose just add the `shrinkWrap` argument to enable shrink-wrapping of `Text` and `BasicText` elements:
```kotlin
import shrinkwrap.compose.*
```
```kotlin
Column {
    Text(
        text = "This is ShrinkWrapped Text",
        modifier = Modifier.background(Color.Cyan).widthIn(0.dp, 150.dp),
        shrinkWrap = true,
    )
    BasicText(
        text = "This is ShrinkWrapped BasicText",
        modifier = Modifier.background(Color.Cyan).widthIn(0.dp, 150.dp),
        shrinkWrap = true,
    )
}
```

### Option 2
If option 1 doesn't work for you you can also use the `ShrinkWrap` element and place your own (custom) Text element inside:
```kotlin
import import shrinkwrap.compose.*
```
```kotlin
ShrinkWrap { measureText, onTextLayout ->
     CustomText(
         "This is shrink-wrapped text.",
         modifier = Modifier.layout(measureText), // Put layout(measureText) last.
         onTextLayout = onTextLayout,
     )
 }
```
Make sure to put `layout(measureText)` at the end of the modifier chain, or else you may get unexpected results.

You can optionally turn shrink-wrapping on/off with the first argument, like `ShrinkWrap(false) {...}`.
</details>

## License
ShrinkWrapText is available under the MIT license. See the [LICENSE](./LICENSE)
file for more info.

