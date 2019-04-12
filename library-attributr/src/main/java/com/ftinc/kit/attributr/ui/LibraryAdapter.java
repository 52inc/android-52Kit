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

package com.ftinc.kit.attributr.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftinc.kit.attributr.R;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by r0adkll on 3/11/15.
 */
public class LibraryAdapter extends BetterRecyclerAdapter<com.ftinc.kit.attributr.model.Library, LibraryAdapter.LibraryViewHolder>
        implements StickyRecyclerHeadersAdapter<LibraryAdapter.HeaderViewHolder> {

    private List<List<com.ftinc.kit.attributr.model.Library>> mHeaders = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public LibraryAdapter(){
        super();
        registerAdapterDataObserver(mDataObserver);
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_two_line_list_item, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibraryViewHolder holder, int i) {
        super.onBindViewHolder(holder, i);
        com.ftinc.kit.attributr.model.Library lib = getItem(i);
        holder.name.setText(lib.name);
        holder.author.setText(lib.author);
    }

    @Override
    public long getHeaderId(int i) {
        if(i < getItemCount()) {

            com.ftinc.kit.attributr.model.Library data = getItem(i);

            for (List<com.ftinc.kit.attributr.model.Library> items : mHeaders) {
                if (items.contains(data)) {
                    return mHeaders.indexOf(items);
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
        long id = getHeaderId(i);
        String artist = mTitles.get((int)id);
        holder.title.setText(artist);
    }

//    @Override
//    protected void onFiltered() {
//        buildSectionHeaders();
//    }



    /**
     * Data change observer
     */
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            buildSectionHeaders();
        }
    };


    /**
     * Build the section headers for use in creating the headers
     */
    private void buildSectionHeaders(){
        // Update Artist maps
        HashMap<String, List<com.ftinc.kit.attributr.model.Library>> currMap = new HashMap<>();

        // Loop through tuneRefs
        for(int i=0; i<getItemCount(); i++){
            com.ftinc.kit.attributr.model.Library library = getItem(i);
            String license = library.license.formalName();
            List<com.ftinc.kit.attributr.model.Library> libraries = currMap.get(license);
            if(libraries != null){
                libraries.add(library);
                currMap.put(license, libraries);
            }else{
                libraries = new ArrayList<>();
                libraries.add(library);
                currMap.put(license, libraries);
            }
        }

        // Update maps
        mHeaders.clear();
        mHeaders.addAll(currMap.values());
        mTitles.clear();
        mTitles.addAll(currMap.keySet());
    }

    public static class LibraryViewHolder extends RecyclerView.ViewHolder{
        TextView name, author;
        public LibraryViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.line_1);
            author = itemView.findViewById(R.id.line_2);
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
