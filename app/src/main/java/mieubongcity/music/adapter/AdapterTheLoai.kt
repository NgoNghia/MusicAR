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
import mieubongcity.music.model.Model_TheLoai
import mieubongcity.music.util.ItemClickTheLoaiListener

class AdapterTheLoai(
    private var mList: MutableList<Model_TheLoai>,
    private var iClick: ItemClickTheLoaiListener
) : RecyclerView.Adapter<AdapterTheLoai.ViewTheLoai>() {

//    var mContext: Context? = null
//    lateinit var mList: MutableList<Model_TheLoai>
//    var iClick : ItemClickTheLoaiListener ? =null
//    constructor(mContext: Context?, mList: MutableList<Model_TheLoai>?,
//                iClick : ItemClickTheLoaiListener ?) : super() {
//        this.mContext = mContext
//        if (mList != null) {
//            this.mList = mList
//        }
//        this.iClick = iClick
//    }

    class ViewTheLoai(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.image_theloai)
        var txtView = itemView.findViewById<TextView>(R.id.txt_theloai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTheLoai {
        var view = LayoutInflater.from(parent?.context).inflate(
            R.layout.item_theloai_recyclerview,
            parent, false
        )
        return ViewTheLoai(view)
    }

    override fun onBindViewHolder(holder: ViewTheLoai, position: Int) {
        holder.txtView.setText(mList.get(position).tenTheLoai)
        Picasso.get()
            .load(mList.get(position).hinhTheLoai)
            .error(R.drawable.error)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            iClick?.let {
//                var theloai = mList.get(position)
                it.onClick(mList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
