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

package com.ftinc.kit.kotlin.adapter


import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View

import java.util.ArrayList
import java.util.Collections


abstract class ListRecyclerAdapter<M, VH : RecyclerView.ViewHolder>(
        protected val context: Context
) : RecyclerView.Adapter<VH>() {

    protected var inflater: LayoutInflater

    var items: MutableList<M> = ArrayList(15)

    protected var itemClickListener: OnItemClickListener<M>? = null
    private var itemLongClickListener: OnItemLongClickListener<M>? = null
    private var emptyView: View? = null

    init {
        this.setHasStableIds(true)
        this.inflater = LayoutInflater.from(context)
    }

    /***********************************************************************************************
     *
     * ArrayList Data Methods
     *
     */

    /**
     * Add a single object to this adapter
     * @param item    the object to add
     */
    fun add(item: M) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     * Add a single object at the given index

     * @param index     the position to add the object at
     * *
     * @param item    the object to add
     */
    fun add(index: Int, item: M) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    /**
     * Add a collection of objects to this adapter

     * @param collection        the collection of objects to add
     */
    fun addAll(collection: Collection<M>?) {
        if (collection != null) {
            //val index = items.size
            items.addAll(collection)
            //notifyItemRangeInserted(index, collection.size());
        }
    }

    /**
     * Update an item in the collection of objects with a new object

     * @param index      the index of the object to update
     * *
     * @param item     the new object to replace at index position
     */
    operator fun set(index: Int, item: M) {
        items[index] = item
        notifyItemChanged(index)
    }

    /**
     * Clear this adapter of all items

     * THIS DOES NOT TRIGGER A NOTIFY EVENT
     */
    fun clear() {
        items.clear()
    }

    /**
     * Remove a specific object from this adapter

     * @param item        the object to remove
     */
    fun remove(item: M) {
        val index = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(index)
    }

    /**
     * Remove an item at the given index

     * @param index     the index of the item to remove
     * *
     * @return          the removed item
     */
    fun remove(index: Int): M {
        val item = items.removeAt(index)
        notifyItemRemoved(index)
        return item
    }

    /**
     * Move an item around in the underlying array

     * @param start     the item to move
     * *
     * @param end       the position to move to
     */
    fun moveItem(start: Int, end: Int) {
        Collections.swap(items, start, end)
        notifyItemMoved(start, end)
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    protected fun getListUpdateCallback(): ListUpdateCallback = DiffUpdateCallback()

    /**
     * Call this to trigger the user set item click listener

     * @param view          the view that was clicked
     * *
     * @param position      the position that was clicked
     */
    protected fun onItemClick(view: View, position: Int) {
        if (itemClickListener != null) itemClickListener!!.onItemClick(view, items[position], position)
    }

    /**
     * Set the empty view to be used so that
     * @param emptyView
     */
    fun setEmptyView(emptyView: View) {
        if (this.emptyView != null) {
            unregisterAdapterDataObserver(mEmptyObserver)
        }
        this.emptyView = emptyView
        registerAdapterDataObserver(mEmptyObserver)
    }

    /**
     * Check if we should show the empty view
     */
    private fun checkIfEmpty() {
        if (emptyView != null) {
            emptyView!!.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
        }
    }

    /**
     * Data change observer
     */
    private val mEmptyObserver = EmptyObserver()

    inner class EmptyObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }
    }

    /**
     * Set the item click listener for this adapter
     */
    fun setOnItemClickListener(itemClickListener: OnItemClickListener<M>?) {
        this.itemClickListener = itemClickListener
    }


    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener<M>?) {
        this.itemLongClickListener = itemLongClickListener
    }


    fun setOnItemClickListener(listener: (M) -> Unit) {
        this.itemClickListener = object : OnItemClickListener<M> {
            override fun onItemClick(v: View, item: M, position: Int) {
                listener.invoke(item)
            }
        }
    }


    fun setOnViewItemClickListener(listener: (View, M) -> Unit) {
        this.itemClickListener = object : OnItemClickListener<M> {
            override fun onItemClick(v: View, item: M, position: Int) {
                listener.invoke(v, item)
            }
        }
    }


    fun setOnItemLongClickListener(listener: (View, M) -> Boolean) {
        this.itemLongClickListener = object : OnItemLongClickListener<M> {
            override fun onItemLongClick(v: View, item: M, position: Int): Boolean {
                return listener.invoke(v, item)
            }
        }
    }

    /***********************************************************************************************
     *
     * Adapter Methods
     *
     */

    /**
     * Get the active number of items in this adapter, i.e. the number of
     * filtered items

     * @return      the number of filtered items (i.e. the displayable) items in this adapter
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * Intercept the bind View holder method to wire up the item click listener only if
     * the listener is set by the user

     * CAVEAT: Be sure that you still override this method and call it's super (or don't if you want
     * to override this functionality and use the [.onItemClick] method)

     * @param vh        the view holder
     * *
     * @param i         the position being bound
     */
    override fun onBindViewHolder(vh: VH, i: Int) {
        if (itemClickListener != null) {
            vh.itemView.setOnClickListener { v ->
                val position = vh.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener!!.onItemClick(v, items[position], position)
                }
            }
        }


        if (itemLongClickListener != null) {
            vh.itemView.setOnLongClickListener { v ->
                val position = vh.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemLongClickListener!!.onItemLongClick(v, items[position], position)
                } else {
                    false
                }
            }
        }
    }

    /**
     * Get the item Id for a given position
     * @param position
     * *
     * @return
     */
    override fun getItemId(position: Int): Long {
        if (position > -1 && position < itemCount) {
            val item = items[position]
            if (item != null) return item.hashCode().toLong()
            return position.toLong()
        }
        return RecyclerView.NO_ID
    }


    /**
     * The interface for detecting item click events from within the adapter, this listener
     * is triggered by [.onItemClick]
     */
    interface OnItemClickListener<in T> {
        fun onItemClick(v: View, item: T, position: Int)
    }


    interface OnItemLongClickListener<in T> {
        fun onItemLongClick(v: View, item: T, position: Int): Boolean
    }


    protected inner class DiffUpdateCallback: ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(position, count, payload)
            checkIfEmpty()
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
            checkIfEmpty()
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
            checkIfEmpty()
        }
    }


    companion object {
        fun <T : RecyclerItem> calculateDiff(new: List<T>, old: List<T>): RecyclerViewBinding<T> {
            val diff = DiffUtil.calculateDiff(DiffCallback(old, new))
            return RecyclerViewBinding(new = new, diff = diff)
        }
    }
}
