package mieubongcity.music.acitivity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_danhsachphatbaihat.*
import mieubongcity.music.R
import mieubongcity.music.adapter.AdapterDanhSachPhatBaiHat
import mieubongcity.music.model.Model_Album
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.model.Model_TheLoai
import mieubongcity.music.util.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DanhSachPhatBaiHatActivity : AppCompatActivity() {
    var mList = mutableListOf<Model_BaiHat>()
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterDanhSachPhatBaiHat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danhsachphatbaihat)
        initView()
        getDataIntent()
        addEventsButton()
    }

    private fun getDataIntent() {
        var intent = intent
        intent?.let {
            if (it.hasExtra("playList")) {
                setViewPlayList(it)
            } else if (it.hasExtra("album")) {
                setViewAlbum(it)
            } else if (it.hasExtra("theloai")) {
                setViewTheLoai(it)
            }
        }
    }

    private fun setViewTheLoai(it: Intent) {
        var theloai = it.getSerializableExtra("theloai") as Model_TheLoai
        theloai?.let {
            theloai.hinhTheLoai?.let { it1 -> setLayoutView(it1) }
            theloai.idTheLoai?.let { it1 -> getDataTheLoai(it1) }
        }

    }

    private fun getDataTheLoai(id: String) {
        var api = APIService.getDataService()
        var data = api.getDataBaiHatTheoTheLoai(id)
        data.enqueue(object :  Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mList = response.body() as MutableList<Model_BaiHat>
                mList?.let {
                    adapter = AdapterDanhSachPhatBaiHat(mList, this@DanhSachPhatBaiHatActivity)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Model_BaiHat>>, t: Throwable) {
                Log.d("loi", t.message.toString())
            }

        })
    }

    private fun setViewAlbum(it: Intent) {
        var album = it.getSerializableExtra("album") as Model_Album
        album?.let {
            album.hinhAlbum?.let { it1 -> setLayoutView(it1) }
            album.idAlbum?.let { it1 -> getDataAlbum(it1) }
        }
    }

    private fun getDataAlbum(id: String) {
        var api = APIService.getDataService()
        var data = api.getDataBaiHatTheAblum(id)
        data.enqueue(object : Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mList = response.body() as MutableList<Model_BaiHat>
                mList?.let {
                    adapter = AdapterDanhSachPhatBaiHat(mList, this@DanhSachPhatBaiHatActivity)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Model_BaiHat>>, t: Throwable) {
                Log.d("loi", t.message.toString())
            }

        })

    }

    private fun setViewPlayList(it: Intent) {
        var playList = it.getSerializableExtra("playList") as Model_PlayList
        playList?.let {
            playList.hinhNenPlayList?.let { it1 -> setLayoutView(it1) }
            playList.idPlayList?.let { it1 -> getDataPlayList(it1) }
        }
    }

    private fun getDataPlayList(id: String) {
        var iDataService = APIService.getDataService()
        var data: Call<List<Model_BaiHat>> = iDataService.getDataBaiHatTheoPlayList(id)
        data.enqueue(object : Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mList = response.body() as MutableList<Model_BaiHat>
                mList?.let {
                    adapter = AdapterDanhSachPhatBaiHat(mList, this@DanhSachPhatBaiHatActivity)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Model_BaiHat>>, t: Throwable) {
                Log.d("loi", t.message.toString())
            }
        })
    }

    private fun setLayoutView(hinhNen: String) {
        Picasso.get()
            .load(hinhNen)
            .error(R.drawable.error)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    bitmap?.let {
                        var bitmapDrawable: Drawable = BitmapDrawable(resources, it)
                        collapsingToolbarLayout.background = bitmapDrawable
                    }
                }

                override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }
            })
    }

    private fun addEventsButton() {
        floatingbutton.setOnClickListener {
            val url = mList.get(0).linkBaiHat
            val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                }
                setDataSource(url)
                prepare()
                start()
            }
        }
    }

    private fun initView() {
//        var coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinatorlayout)
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar)
        var toolbar = findViewById<Toolbar>(R.id.toolbarplaylist)
        recyclerView = findViewById(R.id.rc_playlist_runsong)
        var floatActionButton = findViewById<FloatingActionButton>(R.id.floatingbutton)
        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}
