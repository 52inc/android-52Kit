/*
 * Copyright (c) 2018 52inc.
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

package com.ftinc.kit.recycler

import androidx.recyclerview.widget.DiffUtil


interface RecyclerViewItem {

    val layoutId: Int
    val viewType get() = layoutId

    fun isItemSame(new: RecyclerViewItem): Boolean
    fun isContentSame(new: RecyclerViewItem): Boolean

    companion object {

        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<RecyclerViewItem>() {

            override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
                return oldItem.isItemSame(newItem)
            }

            override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
                return oldItem.isContentSame(newItem)
            }
        }
    }
}
