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
import mieubongcity.music.model.Model_Album
import mieubongcity.music.util.ItemClickAlbumListener

class AdapterAlbum : RecyclerView.Adapter<AdapterAlbum.ViewAlbum> {
    var mContext: Context? = null
    lateinit var mList: MutableList<Model_Album>
    var iClick : ItemClickAlbumListener ? = null

    constructor(mContext: Context?, mList: MutableList<Model_Album>, iClick : ItemClickAlbumListener) : super() {
        this.mContext = mContext
        this.mList = mList
        this.iClick = iClick
    }

    public inner class ViewAlbum(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.image_album)
        var txtTenAblum = itemView.findViewById<TextView>(R.id.txt_ten_album)
        var txtTenCaSyAblum = itemView.findViewById<TextView>(R.id.txt_casy_album)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAlbum {
        var view = LayoutInflater.from(mContext).inflate(
            R.layout.item_album_recyclerview,
            parent, false
        );
        return ViewAlbum(view)
    }

    override fun onBindViewHolder(holder: ViewAlbum, position: Int) {
        holder.txtTenAblum.setText(mList.get(position).tenAlbum)
        holder.txtTenCaSyAblum.setText(mList.get(position).tenCaSyAlbum)
        Picasso.get()
            .load(mList.get(position).hinhAlbum)
            .error(R.drawable.error)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .into(holder.imageView)
        (holder as ViewAlbum).itemView.setOnClickListener {
            iClick?.let {
                var album = mList.get(position)
                iClick!!.onClick(album)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}