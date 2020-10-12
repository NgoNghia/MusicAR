package mieubongcity.music.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.util.OnClick

class AdapterBaiHat(
    private var mListBaiHat: List<Model_BaiHat>,
    private var iClick: OnClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var VIEW_ITEM = 1
    private var VIEW_LOADING = 2

    private var isLoading: Boolean = false
    private var totalView = 0
    private var lastView = 0
    private var mVisibleView = 5

    public class LoadingViewHoldel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar = itemView.findViewById<ProgressBar>(R.id.progressbar)
    }

    public class ItemViewHoldel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img_baihat = itemView.findViewById<ImageView>(R.id.image_baihat)
        var image_morebahat = itemView.findViewById<ImageView>(R.id.image_morebahat)
        var txt_tenbaihat = itemView.findViewById<TextView>(R.id.txt_tenbaihat)
        var txt_tencasy = itemView.findViewById<TextView>(R.id.txt_tencasy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        var layoutInflater = LayoutInflater.from(parent?.context)
        when (viewType) {

            VIEW_ITEM -> {
                view = layoutInflater.inflate(R.layout.item_baihat_recyclerview, parent, false);
                return ItemViewHoldel(view)
            }
            VIEW_LOADING -> {
                view = layoutInflater.inflate(R.layout.item_loading_recyclerview, parent, false);
                return LoadingViewHoldel(view)
            }
        }
        return null!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHoldel -> ((holder as LoadingViewHoldel)).progressBar.isIndeterminate =
                true
            is ItemViewHoldel -> {
                ((holder as ItemViewHoldel)).txt_tenbaihat.setText(mListBaiHat.get(position).tenBaiHat)
                ((holder as ItemViewHoldel)).txt_tencasy.setText(mListBaiHat.get(position).caSy)
                Picasso.get()
                    .load(mListBaiHat.get(position).hinhBaiHat)
                    .placeholder(R.drawable.ic_file_download_black_24dp)
                    .error(R.drawable.error)
                    .fit()
                    .into((holder as ItemViewHoldel).img_baihat)
                (holder as ItemViewHoldel).image_morebahat.setOnClickListener {
                    iClick?.let {
                        iClick!!.clickImageViewMore(position)
                    }
                }
                (holder as ItemViewHoldel).itemView.setOnClickListener {
                    iClick?.let {
                        iClick!!.clickItem(position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (mListBaiHat.isEmpty())
            return 0
        else
            return mListBaiHat.size
    }

    override fun getItemViewType(position: Int): Int {
        var i = when {
            mListBaiHat.get(position) == null -> VIEW_LOADING
            else -> VIEW_ITEM
        }
        return i
//        return mList?.size ?: 0
    }
}