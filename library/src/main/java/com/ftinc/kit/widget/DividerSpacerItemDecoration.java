/*
 * Copyright (c) 2017 52inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ftinc.kit.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerSpacerItemDecoration extends RecyclerView.ItemDecoration {

    private boolean mDrawFirst = false;
    private float mHeight = -1;

    public DividerSpacerItemDecoration(float height, boolean drawFirst) {
        mHeight = height;
        mDrawFirst = drawFirst;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mHeight == -1) {
            return;
        }
        if (parent.getChildPosition(view) < 1 && !mDrawFirst) {
            return;
        }
 
        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            outRect.top = (int) mHeight;
        } else {
            outRect.left = (int) mHeight;

            if(parent.getChildPosition(view) == parent.getChildCount()-1){
                outRect.right = (int) mHeight;
            }

        }
    }
 
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
 
    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager.");
        }
    }
}