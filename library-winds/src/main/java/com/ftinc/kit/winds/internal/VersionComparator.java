package com.ftinc.kit.winds.internal;

import com.ftinc.kit.util.Utils;
import com.ftinc.kit.winds.model.Version;

import java.util.Comparator;

/**
 * Created by r0adkll on 7/11/15.
 */
public final class VersionComparator implements Comparator<Version> {
    @Override
    public int compare(Version lhs, Version rhs) {
        return Utils.compare(rhs.code, lhs.code);
    }
}
