# Attributr

Attributr, formaly found [here](https://github.com/52inc/Attributr), is a library for easily displaying a list of license attributions for the 3rd party libraries in your application.

#### Usages

*	**Configuration File**

```xml
	<Library license="apache">
	    <Name>52Kit</Name>
	    <Author>52inc</Author>
	    <Year>2015</Year>
	    <Description>A library with common tools for building Android applications</Description>
	    <Url>https://github.com/52inc/android-52Kit</Url>
	    <Email>support@52inc.com</Email>
	</Library>
```
		
*	**Implementation**

	```java
	Attributr.openLicenses(context, R.xml.config);
	```
	
* 	**Screenshot**

![Attributr Screenshot](../art/attributr_screen.png)

## Include

```groovy
implementation 'com.52inc:52Kit-attributr:0.5.3'
```