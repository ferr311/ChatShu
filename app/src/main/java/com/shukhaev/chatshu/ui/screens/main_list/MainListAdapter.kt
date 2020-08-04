package com.shukhaev.chatshu.ui.screens.main_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shukhaev.chatshu.R
import com.shukhaev.chatshu.models.CommonModel
import com.shukhaev.chatshu.ui.screens.single_chat.SingleChatFragment
import com.shukhaev.chatshu.utils.downloadAndSetImage
import com.shukhaev.chatshu.utils.replaceFragment
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListHolder>() {

    private var listItems = mutableListOf<CommonModel>()

    class MainListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.main_list_item_name
        val itemLastMessage = view.main_list_item_last_message
        val itemPhoto = view.main_list_item_photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        val holder = MainListHolder(view)
        holder.itemView.setOnClickListener {
            replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
        }
        return holder
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        holder.itemName.text = listItems[position].fullname
        holder.itemLastMessage.text = listItems[position].lasrMessage
        holder.itemPhoto.downloadAndSetImage(listItems[position].photoUrl)
    }

    fun updateListItems(item: CommonModel) {
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }

}