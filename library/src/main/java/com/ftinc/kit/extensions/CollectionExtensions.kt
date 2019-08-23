package com.ftinc.kit.extensions

/**
 * Find and replace the first occurence of an item, as defined by the [selector] and if no item was found add it to
 * the end of the list
 *
 * @param item the item you wish to swap with
 * @param selector the selector to determine which item to swap with
 * @return a list with the first item found by [selector] replaced with [item], or added to end of list if no item found to replace
 */
fun <Item> List<Item>.findAndReplace(item: Item, selector: (Item) -> Boolean): List<Item> {
    val items = toMutableList()
    var didReplace = false
    for(index in (0 until items.size)) {
        if (selector(items[index])) {
            items[index] = item
            didReplace = true
            break
        }
    }

    if (!didReplace) {
        items += item
    }

    return items
}

fun <Item> List<Item>.findAndUpdate(selector: (Item) -> Boolean, updater: (Item?) -> Item): List<Item> {
    val items = toMutableList()
    var didReplace = false
    for(index in (0 until items.size)) {
        if (selector(items[index])) {
            items[index] = updater(items[index])
            didReplace = true
        }
    }

    if (!didReplace) {
        items += updater(null)
    }

    return items
}
