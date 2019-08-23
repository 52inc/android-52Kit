# 52Kit  
A set of libraries that provide useful tools and building blocks for 
our applications

## v2.x
This version has been completely overhauled and converted entirely to Kotlin. It is 
now Kotlin first as well and will likely not provide Java support. Most of the old
widgets and tools have been deprecated and removed from the project leaving just 
the most useful and barebone features.

## Including in your project

Include this line in your gradle build file:

```groovy
// The core library with useful utilities and extension functions
implementation 'com.ftinc:kit:2.0.0'

// The architecture library that uses RxJava2 based MVI setup
implementation 'com.ftinc:kit-arch:2.0.0'
```
