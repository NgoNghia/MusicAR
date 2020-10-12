package mieubongcity.music.acitivity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_danhsachphatbaihat.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import mieubongcity.music.R
import mieubongcity.music.adapter.AdapterDanhSachPhatBaiHat
import mieubongcity.music.model.Model_Album
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.model.Model_TheLoai
import mieubongcity.music.util.APIService
import mieubongcity.music.util.OnClick
import mieubongcity.music.util.SendDataListSong
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DanhSachPhatBaiHatActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemReselectedListener, OnClick {
    var mList = mutableListOf<Model_BaiHat>()
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterDanhSachPhatBaiHat
    private var passData: SendDataListSong? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danhsachphatbaihat)
        initView()
        getDataIntent()
        addEventsButton()
        if(MainActivity.mediaPlayer== null){

        }
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
        data.enqueue(object : Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mList.clear()
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
        var data = api.getDataBaiHatTheoAblum(id)
        data.enqueue(object : Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mList.clear()
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
                mList.clear()
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
//        collapsingToolbarLayout.title
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
            if (mediaPlayer == null) {
                finish()
            } else {
                passData?.let {
                    it.sendData(1, mList)
                }
            }
        }
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        val llBottomSheet = findViewById<RelativeLayout>(R.id.bottomsheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet)
        bottomSheetBehavior.setHideable(true)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        when (item.itemId) {
            R.id.infor_navi -> ""
        }
    }

    override fun clickItem(i: Int) {
        Toast.makeText(this, mList.get(i).tenBaiHat, Toast.LENGTH_SHORT).show()
    }

    override fun clickImageViewMore(i: Int) {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Danh sách yêu thích")
            .setMessage("Thêm ${mList.get(i).tenBaiHat} vào danh sách yêu thích")
            .setPositiveButton("Xác Nhận", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    updateLike(mList.get(i).idBaiHat!!)
                }
            })
            .setNegativeButton("Hủy", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    if (dialog != null) {
                        dialog.cancel()
                    }
                }
            })
        dialog.create().show()
    }

    private fun updateLike(id: String) {
        var api = APIService.getDataService()
        var data = api.updateLuotThich(id, "1")
        data.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var kq: String? = response.body()
                if (kq.equals("ok", true))
                    Toast.makeText(
                        this@DanhSachPhatBaiHatActivity,
                        "Đã thêm thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                else if (kq.equals("loi", true))
                    Log.d("loi", kq.toString() + response.errorBody())
                else
                    Log.d("loi1", kq.toString() + response.errorBody())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("fail", t.message.toString())
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(mediaPlayer==null){
            finish()
        }
    }
}
