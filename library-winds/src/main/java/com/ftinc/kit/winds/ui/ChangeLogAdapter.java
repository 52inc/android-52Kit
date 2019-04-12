/*
 * Copyright (c) 2019 52inc.
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

package com.ftinc.kit.winds.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.util.SizeUtils;
import com.ftinc.kit.winds.R;
import com.ftinc.kit.winds.internal.VersionComparator;
import com.ftinc.kit.winds.model.Change;
import com.ftinc.kit.winds.model.ChangeLog;
import com.ftinc.kit.winds.model.Version;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Collections;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.winds.ui
 * Created by drew.heavner on 3/13/15.
 */
public class ChangeLogAdapter extends BetterRecyclerAdapter<Change, ChangeLogAdapter.VersionViewHolder>
        implements StickyRecyclerHeadersAdapter<ChangeLogAdapter.HeaderViewHolder> {

    private ChangeLog mChangeLog;

    public void setChangeLog(ChangeLog log){
        mChangeLog = log;

        // Clear out any existing entries
        clear();

        //sort all the changes
        Collections.sort(mChangeLog.versions, new VersionComparator());

        // Iterate and add all the 'Change' objects in the adapter
        for(Version version : mChangeLog.versions){
            addAll(version.changes);
        }

        // Notify content has changed
        notifyDataSetChanged();
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_change_item, parent, false);
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VersionViewHolder holder, int i) {
        Change data = getItem(i);
        holder.text.setText(data.getDisplayText(holder.itemView.getContext()));

        long hId = getHeaderId(i);
        long nextHID = getHeaderId(i+1);

        if(hId != nextHID){
            holder.itemView.setPadding(0, 0, 0, (int) SizeUtils.dpToPx(holder.itemView.getContext(), 16f));
        }else{
            holder.itemView.setPadding(0, 0, 0, 0);
        }

    }

    @Override
    public long getHeaderId(int position) {
        if(position < getItemCount()) {
            Change data = getItem(position);
            for (int i=0; i<mChangeLog.versions.size(); i++) {
                Version version = mChangeLog.versions.get(i);
                if (version.changes.contains(data)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_sticky_subheader, viewGroup, false);
        return new HeaderViewHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int i) {
        int headerPosition = (int) getHeaderId(i);
        if(headerPosition != -1) {
            Version header = mChangeLog.versions.get(headerPosition);
            holder.title.setText(header.getDisplayString());
        }
    }

    public static class VersionViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public VersionViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text.setSingleLine(false);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            FontLoader.apply(title, Face.ROBOTO_MEDIUM);
        }
    }

}
