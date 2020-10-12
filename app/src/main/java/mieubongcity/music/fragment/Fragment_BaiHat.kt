package mieubongcity.music.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.adapter.AdapterBaiHat
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.util.APIService
import mieubongcity.music.util.SendDataListSong
import mieubongcity.music.util.OnClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class Fragment_BaiHat : Fragment(), OnClick {
    private lateinit var adapterBaiHat: AdapterBaiHat
    private lateinit var mView: View
    private lateinit var mRecyclerView: RecyclerView
    private var mListSong: MutableList<Model_BaiHat> = mutableListOf()
    private var passData: SendDataListSong? = null
    var mediaPlayer: MediaPlayer? = null
//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_baihat, container, false)
        initView()
        getDataSong()
        randomListSong()

        return mView
    }

    private fun initView() {
        mRecyclerView = mView.findViewById(R.id.rc_baihat)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
    }


    private fun getDataSong() {
        var iData = APIService.getDataService()
        var call = iData.getDataListSong()
        call.enqueue(object : Callback<List<Model_BaiHat>> {
            override fun onResponse(
                call: Call<List<Model_BaiHat>>,
                response: Response<List<Model_BaiHat>>
            ) {
                mListSong = response.body() as MutableList<Model_BaiHat>
                if (mListSong.isEmpty())
                    Toast.makeText(activity, "rỗng", Toast.LENGTH_SHORT).show()
                adapterBaiHat = AdapterBaiHat(mListSong, this@Fragment_BaiHat)
                mRecyclerView.adapter = adapterBaiHat
            }

            override fun onFailure(call: Call<List<Model_BaiHat>>, t: Throwable) {
                Log.d("loi", " " + t.message.toString())
            }
        })
    }

    private fun updateLike(id: String, luotthich: String) {
        var api = APIService.getDataService()
        var data = api.updateLuotThich(id, "1")
        data.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var kq: String? = response.body()
                if (kq.equals("ok", true)) {
                    Toast.makeText(activity, "Đã thêm thành công", Toast.LENGTH_SHORT).show()
                    mView.findViewById<ImageView>(R.id.image_morebahat)
                        .setImageResource(R.drawable.ic_like)
                    mView.findViewById<ImageView>(R.id.image_morebahat).setEnabled(false)
                } else if (kq.equals("loi", true))
                    Log.d("loi", kq.toString() + response.errorBody())
                else
                    Log.d("loi1", kq.toString() + response.errorBody())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("fail", t.message.toString())
            }

        })
    }

    private fun randomListSong() {
        mView.findViewById<Button>(R.id.btn_random).setOnClickListener {
            clickItem(-1)
        }
    }

    private fun playSong(i: Int) {

        var url: String? = null
        if (i == 0) {
            var rd = (0 until mListSong.size).random()
            Log.d("rd", rd.toString())
            url = mListSong.get(rd).linkBaiHat
        } else
            url = mListSong.get(i).linkBaiHat
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.release()
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
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

    override fun clickItem(i: Int) {
//        playSong(i)
        passData?.let {
            it.sendData(i, mListSong)
        }
    }


    override fun clickImageViewMore(i: Int) {
        var dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Danh sách yêu thích")
            .setMessage("Thêm ${mListSong.get(i).tenBaiHat} vào danh sách yêu thích")
            .setPositiveButton("Xác Nhận", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    updateLike(mListSong.get(i).idBaiHat!!, "1")
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SendDataListSong) {
            passData = context as SendDataListSong
        } else {
            throw RuntimeException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        passData = null
    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
}