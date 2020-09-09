package mieubongcity.music.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mieubongcity.music.R
import mieubongcity.music.adapter.AdapterBaiHat
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.model.Model_QuangCao
import mieubongcity.music.util.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_BaiHat : Fragment() {
    private lateinit var adapterBaiHat: AdapterBaiHat
    private lateinit var mView: View
    private lateinit var mRecyclerView: RecyclerView
    private var mListBanner: MutableList<Model_QuangCao> = mutableListOf()
    private var mListSong: MutableList<Model_BaiHat> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_baihat, container, false)
        mRecyclerView = mView.findViewById(R.id.rc_baihat)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        getDataSong()
        return mView
    }


    private fun getDataSong() {
        var iData = APIService.getDataService()
        var call = iData.getDataSong()
        call.enqueue(object : Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mListSong = response.body() as MutableList<Model_BaiHat>
                if (mListSong.isEmpty())
                    Toast.makeText(activity, "rỗng", Toast.LENGTH_SHORT).show()
                adapterBaiHat = activity?.let { AdapterBaiHat(mListSong, it) }!!
                mRecyclerView.adapter = adapterBaiHat
            }

            override fun onFailure(call: Call<List<Model_BaiHat>>, t: Throwable) {
                Toast.makeText(activity, "lỗi " + t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.d("loi", " " + t.message.toString())
            }

        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}