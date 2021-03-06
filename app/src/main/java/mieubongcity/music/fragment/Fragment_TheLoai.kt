package mieubongcity.music.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mieubongcity.music.R
import mieubongcity.music.acitivity.DanhSachPhatBaiHatActivity
import mieubongcity.music.adapter.AdapterTheLoai
import mieubongcity.music.model.Model_TheLoai
import mieubongcity.music.util.APIService
import mieubongcity.music.util.ItemClickTheLoaiListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_TheLoai : Fragment(), ItemClickTheLoaiListener {
    private lateinit var mView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterTheLoai :AdapterTheLoai
    private var mList = mutableListOf<Model_TheLoai>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_theloai, container, false)
        recyclerView = mView.findViewById(R.id.rc_theloai)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        getDataTheLoai()
        return mView
    }

    private fun getDataTheLoai() {
        var api  = APIService.getDataService()
        var data = api.getDataTheLoai()
        data.enqueue(object : Callback<List<Model_TheLoai>> {
            override fun onResponse(
                call: Call<List<Model_TheLoai>>,
                response: Response<List<Model_TheLoai>>
            ) {
                mList = response.body() as MutableList<Model_TheLoai>
                adapterTheLoai = AdapterTheLoai(mList, this@Fragment_TheLoai)
                recyclerView.adapter = adapterTheLoai
            }

            override fun onFailure(call: Call<List<Model_TheLoai>>, t: Throwable) {
                Log.e("aaa", t.message.toString())
            }

        })
    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        this.activity = context as MainActivity
//    }

    override fun onClick(theloai: Model_TheLoai) {
        activity?.let {
            var intent = Intent(activity, DanhSachPhatBaiHatActivity::class.java)
            intent.putExtra("theloai", theloai)
            it.startActivity(intent)
        }
    }

}