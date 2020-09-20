package mieubongcity.music.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.model.Model_BaiHat

class AdapterDanhSachPhatBaiHat : RecyclerView.Adapter<AdapterBaiHat.ItemViewHoldel> {

    lateinit var mList: MutableList<Model_BaiHat>
    lateinit var context: Context

    constructor(mList: MutableList<Model_BaiHat>, context: Context) : super() {
        this.mList = mList
        this.context = context
    }


//    class MyView(itemView: View) : RecyclerView.ViewHolder(itemView){
//
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterBaiHat.ItemViewHoldel {
        var view = LayoutInflater.from(context).inflate(
            R.layout.item_baihat_recyclerview, parent, false
        )
        return AdapterBaiHat.ItemViewHoldel(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterBaiHat.ItemViewHoldel, position: Int) {
        ((holder as AdapterBaiHat.ItemViewHoldel)).txt_tenbaihat.setText(mList.get(position).tenBaiHat)
        ((holder as AdapterBaiHat.ItemViewHoldel)).txt_tencasy.setText(mList.get(position).caSy)
        Picasso.get()
            .load(mList.get(position).hinhBaiHat)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .error(R.drawable.error)
            .fit()
            .into((holder as AdapterBaiHat.ItemViewHoldel).img_baihat)
    }
}