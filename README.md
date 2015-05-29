# 52Kit
[![Build Status](https://travis-ci.org/52inc/android-52Kit.svg?branch=master)](https://travis-ci.org/52inc/android-52Kit) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.52inc/52Kit-core/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.52inc/52Kit)  
Library that acts as the foundation to building our android applications.

## Sections

### Preferences

This library includes several dependency injection ready Preference helper classes and an AES-256 Encryption wrapper for SharedPreferences. See [here](https://github.com/52inc/android-52Kit/tree/master/library/src/main/java/com/ftinc/kit/preferences) for further documentation.

### Utilities

This library also includes a handful of utility classes that contain convienence functions for a variety of tasks. You can view the source [here](https://github.com/52inc/android-52Kit/tree/master/library/src/main/java/com/ftinc/kit/util) which includes these utilities:

*	`Utils.java` - A generic utility class that contains basic helper functions
*	`BuildUtils.java` - A utility class with functions that focus around the build of the device, i.e. the OS.
*	`ColorUtils.java` - A utility class that deals with colors
*	`FileUtils.java` - A utility class that contains helper functions for dealing with file transactions
*	`IntentUtils.java` - A utility class that provides a lot of `Intent` creating functions
*	`TimeUtils.java` - A utility class for functions that deal with time and formatting time
*	`UIUtils.java` - A utility class for helper functions that deal with the UI and views
*	`FormatUtils.java` - A utility class for formatting text and generating hash strings

---

### Attributr

Read more here: [https://github.com/52inc/android-52Kit/tree/master/library-attributr](https://github.com/52inc/android-52Kit/tree/master/library-attributr)


---

### Winds

Read more here: [https://github.com/52inc/android-52Kit/tree/master/library-winds](https://github.com/52inc/android-52Kit/tree/master/library-winds)

---

### Drawer

Read more here: [https://github.com/52inc/android-52Kit/tree/master/library-drawer](https://github.com/52inc/android-52Kit/tree/master/library-drawer)

---

### Widgets

See [here](https://github.com/52inc/android-52Kit/tree/master/library/src/main/java/com/ftinc/kit/widget) for a list of pre-built widgets and views. 

### Font

See [here](https://github.com/52inc/android-52Kit/tree/master/library/src/main/java/com/ftinc/kit/font) for a Utility, FontLoader, that can easily apply Roboto typefaces to textviews.

### Adapters

See [here](https://github.com/52inc/android-52Kit/tree/master/library/src/main/java/com/ftinc/kit/adapter) for Subclassed List and Recycler Adapters that make it easier to build list views

### Pre-built layout files
Pre-built layouts for common list item configurations includeing, one line, two line, and three line items with and without avatars.

## Including in your project

Include this line in your gradle build file:

```groovy
compile 'com.52inc:52Kit-core:0.3.0'
```

**Snapshot:**  
`0.3.1-SNAPSHOT`
