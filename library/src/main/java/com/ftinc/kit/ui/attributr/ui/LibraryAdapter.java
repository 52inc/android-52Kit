/*
 * Copyright (c) 2015 52inc
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
 */

package com.ftinc.kit.ui.attributr.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftinc.kit.R;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.ui.attributr.model.Library;

/**
 * Created by r0adkll on 3/11/15.
 */
public class LibraryAdapter extends BetterRecyclerAdapter<Library, LibraryAdapter.LibraryViewHolder> {

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_library_item, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibraryViewHolder libraryViewHolder, int i) {
        super.onBindViewHolder(libraryViewHolder, i);
        Library lib = getItem(i);

        // TODO: Bind data to UI

    }

    public static class LibraryViewHolder extends RecyclerView.ViewHolder{

        // TODO: Build and Inflate UI item

        public LibraryViewHolder(View itemView) {
            super(itemView);
        }
    }

}
