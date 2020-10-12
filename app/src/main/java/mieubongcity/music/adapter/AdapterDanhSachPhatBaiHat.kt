package mieubongcity.music.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.util.OnClick

class AdapterDanhSachPhatBaiHat(
    private var mList: MutableList<Model_BaiHat>,
    private var iclick: OnClick
) : RecyclerView.Adapter<AdapterBaiHat.ItemViewHoldel>() {

//    lateinit var mList: MutableList<Model_BaiHat>
//    lateinit var context: Context
//
//    constructor(mList: MutableList<Model_BaiHat>, context: Context) : super() {
//        this.mList = mList
//        this.context = context
//    }

//    class MyView(itemView: View) : RecyclerView.ViewHolder(itemView){
//
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterBaiHat.ItemViewHoldel {
        var view = LayoutInflater.from(parent?.context).inflate(
            R.layout.item_baihat_recyclerview, parent, false
        )
        return AdapterBaiHat.ItemViewHoldel(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterBaiHat.ItemViewHoldel, position: Int) {
        (holder as AdapterBaiHat.ItemViewHoldel).txt_tenbaihat.setText(mList.get(position).tenBaiHat)
        (holder as AdapterBaiHat.ItemViewHoldel).txt_tencasy.setText(mList.get(position).caSy)
        Picasso.get()
            .load(mList.get(position).hinhBaiHat)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .error(R.drawable.error)
            .fit()
            .into((holder as AdapterBaiHat.ItemViewHoldel).img_baihat)
        (holder as AdapterBaiHat.ItemViewHoldel).image_morebahat.setOnClickListener {
            iclick?.let {
                iclick.clickImageViewMore(position)
            }
        }
        (holder as AdapterBaiHat.ItemViewHoldel).itemView.setOnClickListener {
            iclick?.let {
                iclick.clickItem(position)
            }
        }

    }

}