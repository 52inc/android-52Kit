# Winds - ChangeLog

Winds, as in _Winds of Change_, is a utility for displaying a changelog to the user.

#### Usages

*	**Configuration File**  
Default: `R.xml.changelog`



	```xml
	<?xml version="1.0" encoding="utf-8"?>
	<Changelog>
	
	    <Version code="1" name="1.0.0" date="Mar 13, 2015">
	        <Change type="new">Added 'Winds' changelog library</Change>
	        <Change type="update">Added 'Attributr' third party license attribution library</Change>
	        <Change type="fix">Bug Fixes</Change>
	        <Change type="mythical">To admit defeat is to blaspheme against the Emperor</Change>
	        <Change>Refactored the [b]Enhance[/b] mechanism to include more [i]Awesome[/i]</Change>
	    </Version>
	
	    <Version code="2" name="1.0.1" date="Mar 14, 2015">
	        <Change type="new">Added Bacon for xtreme flavor</Change>
	        <Change type="update">Changed the cheese on the burg`er</Change>
	        <Change type="fix">Fixed the beef to pork ratio bug</Change>
	        <Change type="mythical">Even in death I still serve fries</Change>
	        <Change>Refactored the [h1]Bun[/h1] mechanism to include more [h2]Sesame[/h2]</Change>
	    </Version>
	
	</Changelog>
		
	```

* **Implementation**  
You can show the changelog as easily as calling:

	```java
	Winds.gust(context);
	```  
	
	This will use the default changelog configuration located at `R.xml.changelog` which you can override or create your own an d implement by calling:
	
	```java
	Winds.gust(context, R.xml.someother_changelog);
	```
	
 	If you want to show a changelog on app startup for new versions/updates all you have to do is call this one line:
 	
 	```java
 	Winds.checkChangelogActivity(context); 
 	```
 	
 	Which will check the default `R.xml.changelog` resource. If you want to specify a different name just call the following
 	
 	```java
 	Winds.checkChangelogActivity(context, R.xml.other_changelog);
 	```
 	
*	**Screenshot**

![Winds Screenshot](../art/winds_screen.png)