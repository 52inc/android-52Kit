# Drawer

This is an easy to use implementation of the [Material Navigation Drawer Pattern](http://www.google.com/design/spec/patterns/navigation-drawer.html). The goal of this library is to provide a simple API for integrating the navigation drawer onto any activity without having to set certain attributes in the theme style or abstracting the drawer functionality into a base activity class.

## Usage

First, you will need to create a `Config` class that defines the configuration of the drawer, i.e.; Header, items, click functionality, etc. This `Config` interface looks like this:

```java
public abstract class Config {

    /**
     * Inflate the drawer items to construct the drawer with
     *
     * @param items     the items to inflate the drawer with
     */
    protected abstract void inflateItems(List<DrawerItem> items);

    /**
     * Called when a user selects a drawer item from the drawer
     *
     * @param item      the item that was clicked
     */
    protected abstract void onDrawerItemClicked(DrawerItem item);

    /**
     * Inflate the header for drawer, return null for no header
     *
     * @param inflater      the layout inflater
     * @param parent        the parent layout
     * @return              the header view, or null for none
     */
    protected abstract View inflateHeader(LayoutInflater inflater, ViewGroup parent);

    /**
     * Override this to bind data to a previously inflated header view
     *
     * @param headerView        the inflated header view to bind data to
     */
    protected void bindHeader(View headerView){}

    /**
     * Override to get notified when the the nav drawer's system bar insets callback
     * is called so we can manipulate the drawer header into drawing behind the status
     * bar
     *
     * @param insets        the status bar insets
     */
    protected void onInsetsChanged(View headerView, Rect insets){}

}
```

So just simply subclass the `Config` and implement it's methods such as `inflateItems(List<DrawerItem> items)`.
Here you just add your drawer items to the list in the order that you want them to appear in the drawer. There are 4 included `DrawerItem` included in the library:

-	`IconDrawerItem` - Drawer item that is just a title and an icon.
-	`ActionDrawerItem` - Drawer item that is a title and an icon but can't be set as selected, e.g.; used for opening the settings or help & feedback screens.
-	`SeperatorDrawerItem` - Item for rendering a seperator line between items
-	`SwitchDrawerItem` - Drawer item that has a title and a switch which is backed with one of the TypedPreference objects.

---

Now that you built your drawer configuration we can now attach the drawer to an activity like this:

```java
{
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_example);
		
		Drawer.with(new ExampleDrawerConfig())
			.toolbar(getAppBar())
			.callbacks(this)
           	.item(DRAWER_ITEM_0)
           	.attach(this);
	}
}
	
```

## Include in your project

Just add this line to your gradle file:

```groovy
compile 'com.52inc:52Kit-drawer:0.4.0'
```