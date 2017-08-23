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

package com.ftinc.kit.attributr.ui.widget;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ftinc.kit.attributr.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import butterknife.ButterKnife;

public class StickyRecyclerHeadersElevationDecoration extends RecyclerView.ItemDecoration {
    private final StickyRecyclerHeadersAdapter mAdapter;
    private final LongSparseArray<RecyclerView.ViewHolder> mHeaderViews = new LongSparseArray<>();
    private final SparseArray<Rect> mHeaderRects = new SparseArray<>();

    public StickyRecyclerHeadersElevationDecoration(StickyRecyclerHeadersAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int orientation = getOrientation(parent);
        int itemPosition = parent.getChildPosition(view);
        if (hasNewHeader(itemPosition)) {
            View header = getHeaderView(parent, itemPosition);
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = header.getHeight();
            } else {
                outRect.left = header.getWidth();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        int orientation = getOrientation(parent);
        mHeaderRects.clear();

        if (parent.getChildCount() > 0 && mAdapter.getItemCount() > 0) {

            // draw the first visible child's header at the top of the view
            View firstView = parent.getChildAt(0);
            int firstPosition = parent.getChildPosition(firstView);

            if (mAdapter.getHeaderId(firstPosition) > -1) {

                View firstHeader = getHeaderView(parent, firstPosition);
                View nextView = getNextView(parent);

                int translationX = Math.max(parent.getChildAt(0).getLeft() - firstHeader.getWidth(), 0);
                int translationY = Math.max(parent.getChildAt(0).getTop() - firstHeader.getHeight(), 0);
                int nextPosition = parent.getChildPosition(nextView);

                if (nextPosition > 0 && hasNewHeader(nextPosition)) {

                    View secondHeader = getHeaderView(parent, nextPosition);

                    //Translate the topmost header so the next header takes its place, if applicable
                    if (orientation == LinearLayoutManager.VERTICAL &&
                            nextView.getTop() - secondHeader.getHeight() - firstHeader.getHeight() < 0) {

                        translationY += nextView.getTop() - secondHeader.getHeight() - firstHeader.getHeight();

                    } else if (orientation == LinearLayoutManager.HORIZONTAL &&
                            nextView.getLeft() - secondHeader.getWidth() - firstHeader.getWidth() < 0) {

                        translationX += nextView.getLeft() - secondHeader.getWidth() - firstHeader.getWidth();

                    }
                }

                boolean shouldDrawShadow = true;
                if(firstPosition == 0 && (firstView.getTop() == firstHeader.getHeight())){
                    shouldDrawShadow = false;
                }

                if(translationY == 0 && shouldDrawShadow){

                    ImageView shadow = ButterKnife.findById(firstHeader, R.id.shadow);
                    shadow.setVisibility(View.VISIBLE);

                }else{

                    ImageView shadow = ButterKnife.findById(firstHeader, R.id.shadow);
                    shadow.setVisibility(View.GONE);

                }

                canvas.save();
                canvas.translate(translationX, translationY);
                firstHeader.draw(canvas);
                canvas.restore();
                mHeaderRects.put(firstPosition, new Rect(translationX, translationY,
                        translationX + firstHeader.getWidth(), translationY + firstHeader.getHeight()));

            }

            if(parent.getChildCount() > 1 && mAdapter.getItemCount() > 1) {
                for (int i = 1; i < parent.getChildCount(); i++) {

                    int position = parent.getChildPosition(parent.getChildAt(i));
                    if (hasNewHeader(position)) {

                        // this header is different than the previous, it must be drawn in the correct place
                        int translationX = 0;
                        int translationY = 0;
                        View header = getHeaderView(parent, position);

                        if (orientation == LinearLayoutManager.VERTICAL) {

                            translationY = parent.getChildAt(i).getTop() - header.getHeight();

                        } else {

                            translationX = parent.getChildAt(i).getLeft() - header.getWidth();

                        }

                        // don't render shadow
                        ImageView shadow = ButterKnife.findById(header, R.id.shadow);
                        shadow.setVisibility(View.GONE);

                        canvas.save();
                        canvas.translate(translationX, translationY);
                        header.draw(canvas);
                        canvas.restore();
                        mHeaderRects.put(position, new Rect(translationX, translationY,
                                translationX + header.getWidth(), translationY + header.getHeight()));
                    }
                }
            }
        }
    }

    /**
     * Returns the first item currently in the recyclerview that's not obscured by a header.
     * @param parent
     * @return
     */
    private View getNextView(RecyclerView parent) {
        View firstView = parent.getChildAt(0);

        // draw the first visible child's header at the top of the view
        int firstPosition = parent.getChildPosition(firstView);
        View firstHeader = getHeaderView(parent, firstPosition);

        for (int i = 0; i < parent.getChildCount(); i++) {

            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {

                if (child.getTop() - layoutParams.topMargin > firstHeader.getHeight()) {
                    return child;
                }

            } else {

                if (child.getLeft() - layoutParams.leftMargin > firstHeader.getWidth()) {
                    return child;
                }

            }
        }
        return null;
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException("StickyListHeadersDecoration can only be used with a " +
                    "LinearLayoutManager.");
        }
    }

    /**
     * Gets the position of the header under the specified (x, y) coordinates.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return position of header, or -1 if not found
     */
    public int findHeaderPositionUnder(int x, int y) {
        for (int i = 0; i < mHeaderRects.size(); i++) {
            Rect rect = mHeaderRects.get(mHeaderRects.keyAt(i));
            if (rect.contains(x, y)) {
                return mHeaderRects.keyAt(i);
            }
        }
        return -1;
    }

    /**
     * Gets the header view for the associated position.  If it doesn't exist yet, it will be
     * created, measured, and laid out.
     * @param parent
     * @param position
     * @return Header view
     */
    public View getHeaderView(RecyclerView parent, int position) {
        long headerId = mAdapter.getHeaderId(position);

        RecyclerView.ViewHolder viewHolder = mHeaderViews.get(headerId);
        if (viewHolder == null) {

            viewHolder = mAdapter.onCreateHeaderViewHolder(parent);
            View header = viewHolder.itemView;
            header.setTag(viewHolder);

            if (header.getLayoutParams() == null) {

                header.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }

            int widthSpec;
            int heightSpec;

            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
            } else {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.UNSPECIFIED);
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY);
            }

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            mHeaderViews.put(headerId, viewHolder);
        }

        // Rebind content to the view holder
        mAdapter.onBindHeaderViewHolder(viewHolder, position);

        return viewHolder.itemView;
    }

    private boolean hasNewHeader(int position) {
        if (getFirstHeaderPosition() == position) {
            return true;
        } else if (mAdapter.getHeaderId(position) < 0) {
            return false;
        } else if (position > 0 && position < mAdapter.getItemCount()) {
            return mAdapter.getHeaderId(position) != mAdapter.getHeaderId(position - 1);
        } else {
            return false;
        }
    }

    private int getFirstHeaderPosition() {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getHeaderId(i) >= 0) {
                return i;
            }
        }
        return -1;
    }
}
