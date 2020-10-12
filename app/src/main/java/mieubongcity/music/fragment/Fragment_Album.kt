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
import mieubongcity.music.acitivity.MainActivity
import mieubongcity.music.adapter.AdapterAlbum
import mieubongcity.music.model.Model_Album
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.util.APIService
import mieubongcity.music.util.ItemClickAlbumListener
import retrofit2.Call
import retrofit2.Response


class Fragment_Album : Fragment(),ItemClickAlbumListener {
    private lateinit var mView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterAlbum : AdapterAlbum
    private var mList = mutableListOf<Model_Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_album, container, false)
        recyclerView = mView.findViewById(R.id.rc_album)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        getDataAlbum()
        return mView
    }

    private fun getDataAlbum() {
        var api = APIService.getDataService()
        var data: Call<List<Model_Album>> = api.getDataAlbum()
        data.enqueue(object : retrofit2.Callback<List<Model_Album>> {
            override fun onResponse(
                call: Call<List<Model_Album>>,
                response: Response<List<Model_Album>>
            ) {
                mList = response.body() as MutableList<Model_Album>
                adapterAlbum = AdapterAlbum( mList, this@Fragment_Album)
                recyclerView.adapter = adapterAlbum
            }

            override fun onFailure(call: Call<List<Model_Album>>, t: Throwable) {
                Log.d("error", t.message.toString())
            }
        })
    }

    override fun onClick(album: Model_Album) {
        if(album!=null){
            Toast.makeText(activity, album.tenAlbum, Toast.LENGTH_SHORT).show()
        }
        activity?.let {
            var intent = Intent(it, DanhSachPhatBaiHatActivity::class.java)
            intent.putExtra("album", album)
            it.startActivity(intent)
        }
    }

}