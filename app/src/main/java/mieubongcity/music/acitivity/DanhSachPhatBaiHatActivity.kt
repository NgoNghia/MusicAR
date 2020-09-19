package mieubongcity.music.acitivity

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_danhsachphatbaihat.*
import mieubongcity.music.R
import mieubongcity.music.adapter.AdapterDanhSachPhatBaiHat
import mieubongcity.music.model.Model_DanhSachPhatBaiHat
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.util.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DanhSachPhatBaiHatActivity : AppCompatActivity() {
    var mList = mutableListOf<Model_DanhSachPhatBaiHat>()
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var recyclerView : RecyclerView
    lateinit var adapter : AdapterDanhSachPhatBaiHat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danhsachphatbaihat)
        initView()
        getDataIntent()

    }

    private fun getDataIntent() {
        var playList: Model_PlayList? =
            intent?.let { it.getSerializableExtra("playList") as Model_PlayList }

        playList?.let {
                playList.hinhNenPlayList?.let { it1 -> setLayoutView(it1) }
                playList.idPlayList?.let { it1 -> getDataPlayList(it1) }
        }
    }

    private fun getDataPlayList(id: String) {
        var iDataService = APIService.getDataService()
        var data: Call<List<Model_DanhSachPhatBaiHat>> = iDataService.getDataBaiHatTheoPlayList(id)
        data.enqueue(object : Callback<List<Model_DanhSachPhatBaiHat>> {
            override fun onResponse(
                call: Call<List<Model_DanhSachPhatBaiHat>>,
                response: Response<List<Model_DanhSachPhatBaiHat>>
            ) {
                mList = response.body() as MutableList<Model_DanhSachPhatBaiHat>
                mList?.let {
                    adapter = AdapterDanhSachPhatBaiHat(mList, this@DanhSachPhatBaiHatActivity)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Model_DanhSachPhatBaiHat>>, t: Throwable) {
                Toast.makeText(this@DanhSachPhatBaiHatActivity, t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setLayoutView( hinhNen: String) {
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

        floatingbutton.setOnClickListener {
            Toast.makeText(this, hinhNen, Toast.LENGTH_SHORT).show()
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
