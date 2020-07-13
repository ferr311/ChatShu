package com.shukhaev.chatshu.utils

import androidx.recyclerview.widget.DiffUtil
import com.shukhaev.chatshu.models.CommonModel

class DiffUtilCallback(
    private val oldList:List<CommonModel>,
    private val newList:List<CommonModel>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].timeStamp == newList[newItemPosition].timeStamp
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}