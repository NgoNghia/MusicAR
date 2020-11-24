package mieubongcity.music.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.util.ItemClickPlayListListener

class AdapterPlayList(
    private var mList: MutableList<Model_PlayList>,
    private var iClick: ItemClickPlayListListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var cardView = itemView.findViewById<CardView>(R.id.card_playlist)
        val image_playlist = itemView.findViewById<ImageView>(R.id.image_playlist)
        val txt_playlist = itemView.findViewById<TextView>(R.id.txt_playlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View = LayoutInflater.from(parent?.context).inflate(
            R.layout.item_playlist_recyclerview,
            parent, false
        )
        return MyItemView(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val music = mList.get(position)
        (holder as MyItemView).txt_playlist.text = music.tenPlayList
        Picasso.get()
            .load(music.hinhNenPlayList)
            .error(R.drawable.error)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .fit()
            .into((holder as MyItemView).image_playlist)
        (holder as MyItemView).itemView.setOnClickListener {
            iClick?.let {
                it.onClick(music)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}