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

class AdapterPlayList : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    lateinit var mList: MutableList<Model_PlayList>
    var mContext: Context
    private var iClick: ItemClickPlayListListener? = null

    constructor(
        mList: MutableList<Model_PlayList>?, mContext: Context,
        iClick: ItemClickPlayListListener
    ) : super() {
        if (mList != null) {
            this.mList = mList
        }
        this.mContext = mContext
        this.iClick = iClick
    }

    inner class MyItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var cardView = itemView.findViewById<CardView>(R.id.card_playlist)
        var image_playlist = itemView.findViewById<ImageView>(R.id.image_playlist)
        var txt_playlist = itemView.findViewById<TextView>(R.id.txt_playlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View = LayoutInflater.from(mContext).inflate(
            R.layout.item_playlist_recyclerview,
            parent, false
        )
        return MyItemView(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyItemView).txt_playlist.setText(mList.get(position).tenPlayList)
        Picasso.get()
            .load(mList.get(position).hinhNenPlayList)
            .error(R.drawable.error)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .fit()
            .into((holder as MyItemView).image_playlist)
        (holder as MyItemView).itemView.setOnClickListener {
            iClick?.let {
                var playlist = mList.get(position)
                it.onClick(playlist)
            }
        }

//        iClick?.let {
//            (holder as MyItemView).itemView.setOnClickListener {
//                var playlist = mList.get(position)
//                playlist?.let {iClick!!.onClick(playlist)
//                }
//            }
//        }
        //        (holder as MyItemView).cardView.set

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}