package mieubongcity.music.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mieubongcity.music.R
import mieubongcity.music.acitivity.MainActivity
import mieubongcity.music.acitivity.DanhSachPhatBaiHatActivity
import mieubongcity.music.adapter.AdapterPlayList
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.util.APIService
import mieubongcity.music.util.ItemClickPlayListListener
import retrofit2.Call
import retrofit2.Response

class Fragment_PlayList : Fragment() {
    var activity: MainActivity? = null

    //    private var iLoadMore : ItemClickPlayListListener  = this
    private lateinit var mView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterPlayList: AdapterPlayList
    private var mList = mutableListOf<Model_PlayList>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_playlist, container, false)
        recyclerView = mView.findViewById(R.id.rc_playlist)
        var layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager
        getDataPlayList()
        iClickItem()
        return mView

    }

    private fun iClickItem() {

    }

    private fun getDataPlayList() {
        var getJson = APIService.getDataService()
        var iDataService = getJson.getDataPlayList()
        iDataService.enqueue(object : retrofit2.Callback<List<Model_PlayList>>,
            ItemClickPlayListListener {
            override fun onResponse(
                call: Call<List<Model_PlayList>>,
                response: Response<List<Model_PlayList>>
            ) {
                mList = response.body() as MutableList<Model_PlayList>
                adapterPlayList = AdapterPlayList(mList,  this)
                recyclerView.adapter = adapterPlayList
            }

            override fun onFailure(call: Call<List<Model_PlayList>>, t: Throwable) {
                Log.e("aaa", t.message.toString())
                Toast.makeText(activity, t.message.toString(), Toast.LENGTH_SHORT).show()

            }

            override fun onClick(playList: Model_PlayList) {
                activity?.let {
                    var intent = Intent(it, DanhSachPhatBaiHatActivity::class.java)
                    intent.putExtra("playList", playList)
                    it.startActivity(intent)
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as MainActivity
    }

}