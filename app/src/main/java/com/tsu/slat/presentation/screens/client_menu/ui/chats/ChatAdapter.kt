package com.tsu.itindr.MainScreen.ui.chats

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tsu.slat.R
import com.tsu.slat.data.entity.ChatResponse
import com.tsu.slat.databinding.ChatItemBinding
import com.tsu.slat.presentation.screens.chat.ChatActivity

class ChatAdapter(val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    val chatList = ArrayList<ChatResponse>()

    inner class ChatHolder(item: View): RecyclerView.ViewHolder(item),
    View.OnClickListener {
        val binding = ChatItemBinding.bind(item)
        private lateinit var userId: String
        fun bind(chatResponse: ChatResponse) = with(binding){
            if (chatResponse.chat.avatar != null && chatResponse.chat.avatar != "")
                loadImageWithUri(imageAvatar, chatResponse.chat.avatar)
            else
                imageAvatar.setImageResource(R.drawable.ic_account_circle_black_36dp)
            textName.text = chatResponse.chat.title
            if (chatResponse.lastMessage?.text != "null")
                textLastMessage.text = chatResponse.lastMessage?.text
            else
                textLastMessage.text = "-"
            userId = chatResponse.chat.id
        }
        private fun loadImageWithUri(imageView: ImageView, imageUri: String){
            Glide.with(imageView.context).load(Uri.parse(imageUri)).diskCacheStrategy(
                DiskCacheStrategy.NONE).skipMemoryCache(true).into(imageView)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(userId)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(userId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
            return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(chatList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("title", chatList[position].chat.title)
            intent.putExtra("chatId", chatList[position].chat.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun addChat(chat: ChatResponse) {
        chatList.add(chat)
        notifyDataSetChanged()
    }

}