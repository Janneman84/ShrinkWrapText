<img id="badge" src="https://jitpack.io/v/Janneman84/ShrinkWrapTextView.svg"> <img height="1" img width="1" alt="shrinkwrap" src="https://github.com/user-attachments/assets/10178d16-cfbf-465a-a08a-9cbd39a636c3"/>

# ShrinkWrapTextView
Fixes oversized TextViews (and Buttons) in Android apps:

<img width="203" height="336" alt="shrinkwrap" src="https://github.com/user-attachments/assets/43707776-48f7-40ad-9e27-3315c1a4386a" />

# 
This is particularly useful for chat bubbles!

![shrinkwrap](https://github.com/user-attachments/assets/10178d16-cfbf-465a-a08a-9cbd39a636c3)

## Explanation

Have you ever noticed that all chat apps on iOS and Android have one common difference? Once you see it you can't unsee! The difference is sizing of the chat bubbles. On Android chat bubbles that have more than one line of text are always maxed out to their maximum width. This often results in bubbles that are just too big. `ShrinkWrapTextView` fixes this issue, it's super easy to install so give it a try!

This shrink wrapping technology is already being used by Signal messenger.

`ShrinkWrapTextView` is a subclass of `AppCompatTextView`.
<br>
`ShrinkWrapButton` is a subclass of `AppCompatButton`.


## Installation


This is using jitpack.io. 
Add below lines to root's `build.gradle`:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add below lines to apps' `build.gradle`:

```groovy
dependencies {
	implementation 'com.github.Janneman84:ShrinkWrapTextView:0.3.1'
}
```

## How to use
There are three ways to shrink wrap your TextViews and Buttons.
### Option 1
In your layout xml replace the `TextView`/`Button` class with `ShrinkWrapTextView`/`ShrinkWrapButton`. You can optionally use the custom attribute `shrinkWrap` to turn shrink wrapping on and off. You should see it in action in the Designer (preview) pane.

```xml
...
xmlns:custom="http://schemas.android.com/apk/res-auto"
...

<com.janneman84.shrinkwraptextview.ShrinkWrapTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:maxWidth="200dp"
    android:text="This is a ShrinkWrapped TextView"
    custom:shrinkWrap="true"
    ...
/>
```
### Option 2
If you have subclassed from `AppCompatTextView` or `AppCompatButton`, have them subclass from `ShrinkWrapTextView`/`ShrinkWrapButton` instead. You can use the property `shrinkWrap` to turn shrink wrapping on or off.
```kotlin
class MyTextView(context: Context, attrs: AttributeSet?) : ShrinkWrapTextView(context, attrs) {
    ...
}

class MyButton(context: Context, attrs: AttributeSet?) : ShrinkWrapButton(context, attrs) {
    ...
}
```

### Option 3
If changing the superclass isn't an option you can also override `onMeasure()` in your `TextView`/`Button` subclass instead and call `setMeasuredDimension()` like this:
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
    setMeasuredDimension(ShrinkWrapTextViewKt.measureShrinkWrappedWidth(this), getMeasuredHeight());
}
```
### Compose
This is not a Compose component, but it is possible to use traditionals Views like this inside a Compose layout. Shrink wrapping will work properly if you do this. Perhaps a Compose version will become available in the future.

## License
ShrinkWrapTextView is available under the MIT license. See the [LICENSE](./LICENSE)
file for more info.
