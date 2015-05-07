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

package com.ftinc.kit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public abstract class BetterRecyclerAdapter<M, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    /***********************************************************************************************
     *
     * Constants
     *
     */


    /***********************************************************************************************
     *
     * Variables
     *
     */

    private OnItemClickListener<M> itemClickListener;

    protected ArrayList<M> items = new ArrayList<>();
    protected ArrayList<M> filteredItems = new ArrayList<>();
    protected String filter = "";

    private View mEmptyView;

    /**
     * Default Constructor
     */
    public BetterRecyclerAdapter() {
        setHasStableIds(true);
    }

    /***********************************************************************************************
     *
     * Protected Methods
     *
     */

    /**
     * Call this to trigger the user set item click listener
     *
     * @param view          the view that was clicked
     * @param position      the position that was clicked
     */
    protected void onItemClick(View view, int position){
        if(itemClickListener != null) itemClickListener.onItemClick(view, getItem(position), position);
    }

    /**
     * Override this method to apply filtering to your content
     * so you can supply queries to the adapter to filter your content out
     * for search
     *
     * @param item      the item to filter check
     * @param query     the query to check with
     * @return          true if the item matches the query in any way
     */
    protected boolean onQuery(M item, String query){
        return true;
    }

    /**
     * Override so you can sort the items in the array according
     * to your specification. Do nothing if you choose not to sort, or
     * plan to on your own accord.
     *
     * @param items     the list of items needing sorting
     */
    protected void onSort(List<M> items){}

    /**
     * Override this method to be notified when the adapter is filtered
     */
    protected void onFiltered(){}

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Set the empty view to be used so that
     * @param emptyView
     */
    public void setEmptyView(View emptyView){
        if(mEmptyView != null){
            unregisterAdapterDataObserver(mEmptyObserver);
        }
        mEmptyView = emptyView;
        registerAdapterDataObserver(mEmptyObserver);
    }

    /**
     * Check if we should show the empty view
     */
    private void checkIfEmpty(){
        if(mEmptyView != null){
            mEmptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Data change observer
     */
    private RecyclerView.AdapterDataObserver mEmptyObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }
    };

    /**
     * Set the item click listener for this adapter
     */
    public void setOnItemClickListener(OnItemClickListener<M> itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    /***********************************************************************************************
     *
     * Query Methods
     *
     */

    /**
     * Apply a query to this adapter
     *
     * @param query     query
     */
    public void query(String query){
        filter = query;

        // Filter results
        filter();

        // Force an application of the query
        notifyDataSetChanged();

    }

    /**
     * Clear out the current query
     */
    public void clearQuery(){
        filter = "";
        filter();
    }

    /**
     * Apply a filter to this adapters subset of content
     */
    private void filter(){
        if((filter != null && !filter.isEmpty())){
            if(filter == null) filter = "";

            // Filter out the items
            filteredItems.clear();
            for(M item: items){
                if(onQuery(item, filter)){
                    filteredItems.add(item);
                }
            }

        }else{
            filteredItems.clear();
            filteredItems.addAll(items);
        }

        // Notify of filtration
        onFiltered();
    }

    /***********************************************************************************************
     *
     * Array Methods
     *
     */

    /**
     * Add a single object to this adapter
     * @param object    the object to add
     */
    public void add(M object) {
        items.add(object);
        filter();
    }

    /**
     * Add a single object at the given index
     *
     * @param index     the position to add the object at
     * @param object    the object to add
     */
    public void add(int index, M object) {
        items.add(index, object);
        filter();
    }

    /**
     * Add a collection of objects to this adapter
     *
     * @param collection        the collection of objects to add
     */
    public void addAll(Collection<? extends M> collection) {
        if (collection != null) {
            items.addAll(collection);
            filter();
        }
    }

    /**
     * Clear this adapter of all items
     */
    public void clear() {
        clearQuery();
        items.clear();
        filteredItems.clear();
        filter = null;
    }

    /**
     * Remove a specific object from this adapter
     *
     * @param object        the object to remove
     */
    public void remove(M object) {
        items.remove(object);
        filter();
    }

    /**
     * Remove an item at the given index
     *
     * @param index     the index of the item to remove
     * @return          the removed item
     */
    public M remove(int index){
        M item = items.remove(index);
        filter();
        return item;
    }

    /**
     * Move an item around in the underlying array
     *
     * @param start     the item to move
     * @param end       the position to move to
     */
    public void moveItem(int start, int end){
        int max = Math.max(start, end);
        int min = Math.min(start, end);
        if (min >= 0 && max < items.size()) {
            M item = items.remove(min);
            items.add(max, item);
        }

        // Assume no filtering when you are rearranging items in the adapter
        filteredItems.clear();
        filteredItems.addAll(items);

        notifyItemMoved(min, max);
    }

    /**
     * Sort the items in this adapter by a given
     * {@link java.util.Comparator}
     *
     * @param comparator        the comparator to sort with
     */
    public void sort(Comparator<M> comparator){
        Collections.sort(items, comparator);
        filter();
    }

    /**
     * Get an item from this adapter at a specific index
     *
     * @param position      the position of the item to retrieve
     * @return              the item at that position, or null
     */
    public M getItem(int position) {
        return filteredItems.get(position);
    }

    /**
     * Get all the items in this adapter
     *
     * @return      the all the 'filtered' items in the adapter
     */
    public List<M> getItems(){
        return filteredItems;
    }

    /**
     * Get all the unfiltered items in this adapter
     *
     * @return      the list of unfiltered items
     */
    public List<M> getUnfilteredItems(){
        return items;
    }

    /***********************************************************************************************
     *
     * Header/Footer Methods
     *
     */



    /***********************************************************************************************
     *
     * Adapter Methods
     *
     */

    /**
     * Get the active number of items in this adapter, i.e. the number of
     * filtered items
     *
     * @return      the number of filtered items (i.e. the displayable) items in this adapter
     */
    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    /**
     * Intercept the bind View holder method to wire up the item click listener only if
     * the listener is set by the user
     *
     * CAVEAT: Be sure that you still override this method and call it's super (or don't if you want
     * to override this functionality and use the {@link #onItemClick(android.view.View, int)} method)
     *
     * @param vh        the view holder
     * @param i         the position being bound
     */
    @Override
    public void onBindViewHolder(VH vh, final int i) {
        if(itemClickListener != null){
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, getItem(i), i);
                }
            });
        }
    }

    /**
     * Called to get the view type of an item at a given position
     *
     * @param position      the position of the item to get the view type of
     * @return              the view type identifier
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        M item = getItem(position);
        if(item != null) return item.hashCode();
        return position;
    }

    /***********************************************************************************************
     *
     * Interfaces
     *
     */

    /**
     * The interface for detecting item click events from within the adapter, this listener
     * is triggered by {@link #onItemClick(android.view.View, int)}
     */
    public interface OnItemClickListener<T>{
        void onItemClick(View v, T item, int position);
    }

}
