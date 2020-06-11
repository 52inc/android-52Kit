package com.ftinc.kit.recycler

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class EmptyViewListAdapter<Item, Holder : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<Item>,
    var emptyChangeModifier: (emptyView: View, isEmpty: Boolean) -> Unit = defaultEmptyChangeModifier
) : ListAdapter<Item, Holder>(diffCallback) {

    var emptyView: View? = null
        set(value) {
            field = value
            if (value != null) {
                emptyChangeModifier(value, currentList.isEmpty())
            }
        }

    override fun onCurrentListChanged(previousList: MutableList<Item>, currentList: MutableList<Item>) {
        if (emptyView != null) {
            emptyChangeModifier(emptyView!!, currentList.isEmpty())
        }
    }

    companion object {

        val defaultEmptyChangeModifier: (View, Boolean) -> Unit = { view, isEmpty ->
            view.isVisible = isEmpty
        }
    }
}
