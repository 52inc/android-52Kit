package com.ftinc.kit.drawer;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.drawer
 * Created by drew.heavner on 8/18/15.
 */
public interface DrawerInteractor {

    void openDrawer();
    void closeDrawer();

    void invalidate();

    void lockDrawer(@Drawer.LockMode int lockMode);

}
