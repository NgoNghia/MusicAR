package mieubongcity.music.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.util.ILoadMore

class AdapterBaiHat : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var mListBaiHat: MutableList<Model_BaiHat>
    private var mContext: Context
    private var VIEW_RANDOM = 0;
    private var VIEW_ITEM = 1;
    private var VIEW_LOADING = 2
    private var isLoading: Boolean = false
    private var totalView = 0
    private var lastView = 0
    private var mVisibleView = 5
    private var iLoadMore: ILoadMore? = null

    public constructor(mListBaiHat: MutableList<Model_BaiHat>, mContext: Context) : super() {
        this.mListBaiHat = mListBaiHat
        this.mContext = mContext
    }

    public class RandomViewHoldel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_random = itemView.findViewById<Button>(R.id.btn_random)
    }

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
            VIEW_RANDOM -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_random_recyclerview, parent, false);
//                view = layoutInflater.inflate(R.layout.item_random_recyclerview, parent, false)
                return RandomViewHoldel(view)
            }
            VIEW_ITEM -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_baihat_recyclerview, parent, false);
                return ItemViewHoldel(view)
            }
            VIEW_LOADING -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_loading_recyclerview, parent, false);
                return LoadingViewHoldel(view)
            }
        }

        return null!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHoldel -> ((holder as LoadingViewHoldel)).progressBar.isIndeterminate =
                true
            is RandomViewHoldel -> {
                ((holder as RandomViewHoldel)).btn_random.setOnClickListener {
                    Toast.makeText(mContext, "Random", Toast.LENGTH_SHORT).show()
                }
            }
            is ItemViewHoldel ->{
                ((holder as ItemViewHoldel)).txt_tenbaihat.setText(mListBaiHat.get(position).tenBaiHat)
                ((holder as ItemViewHoldel)).txt_tencasy.setText(mListBaiHat.get(position).caSy)
                Picasso.get()
                    .load(mListBaiHat.get(position).hinhBaiHat)
                    .placeholder(R.drawable.ic_play_for_work_black_24dp)
                    .error(R.drawable.error)
                    .fit()
                    .into(((holder as ItemViewHoldel)).img_baihat);
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
        if (position == 0)
            return VIEW_RANDOM
        else if (mListBaiHat.get(position) == null)
            return VIEW_LOADING
        return VIEW_ITEM
//        return mList?.size ?: 0
    }
}