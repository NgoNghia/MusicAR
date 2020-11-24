package mieubongcity.music.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import mieubongcity.music.R
import mieubongcity.music.acitivity.MainActivity
import mieubongcity.music.adapter.AdapterBaiHat
import mieubongcity.music.broadcast.MediaPlayerBroadcast
import mieubongcity.music.common.Constant
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.service.MyMusicSevice
import mieubongcity.music.util.APIService
import mieubongcity.music.util.SendDataListSong
import mieubongcity.music.util.OnClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_BaiHat : Fragment(), OnClick {
    private lateinit var adapterBaiHat: AdapterBaiHat
    private lateinit var mView: View
    private lateinit var mRecyclerView: RecyclerView
    private var mListSong: MutableList<Model_BaiHat> = mutableListOf()
    private var passData: SendDataListSong? = null
    private var viTri = 0
    var intentService: Intent? = null
    var mediaPlayerBroadcast: MediaPlayerBroadcast? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_baihat, container, false)
        initView()
        getDataSong()
        randomListSong()
        eventClick()
        eventClickNotication()
        return mView
    }

    private fun initView() {
        mRecyclerView = mView.findViewById(R.id.rc_baihat)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initIntentFilter() {
        mediaPlayerBroadcast = MediaPlayerBroadcast()
        var intent = IntentFilter()
        intent.addAction(Constant.BUTTON_CANCEL)
        intent.addAction(Constant.BUTTON_NEXT)
        intent.addAction(Constant.BUTTON_PLAY)
        intent.addAction(Constant.BUTTON_PREVIOUS)
        activity?.registerReceiver(mediaPlayerBroadcast, intent)
    }

    private fun eventClickNotication() {
        initIntentFilter()
        mediaPlayerBroadcast?.let {
            it.setMyBroadcastCall(object : MediaPlayerBroadcast.OnclickNotifyBroadcast {
                override fun onClickPrevious() {

                }

                override fun onClickPlay() {

                }

                override fun onClickNext() {
//                    Toast.makeText(activity, "đi tới", Toast.LENGTH_SHORT).show()
                }

                override fun onClickCanel() {
                    if (MainActivity.mediaPlayer != null) {
                        if(MainActivity.mediaPlayer!!.isPlaying)
                            MainActivity!!.mediaPlayer!!.stop()
                        MainActivity?.mediaPlayer?.release()
                        MainActivity.mediaPlayer = null
                        MainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        activity?.stopService(intentService)
                    }
                }
            })
        }
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
            if (!mListSong.isEmpty()) {
                viTri = (0 until mListSong.size).random()
                clickItem(viTri)
            }
        }
    }

    override fun clickItem(i: Int) {
        if (i != null) {
            viTri = i
            setViewBottomSheet(i)
        }
        var bundle = Bundle()
        if (intentService == null) {
            intentService = Intent(activity, MyMusicSevice::class.java)
            bundle.putInt(position, i)
            bundle.putParcelableArrayList(list, mListSong as ArrayList<out Parcelable>)
            intentService?.putExtra(data, bundle)
        } else {
            bundle.putInt(position, i)
            bundle.putParcelableArrayList(list, mListSong as ArrayList<out Parcelable>)
            intentService?.putExtra(data, bundle)
        }
        activity!!.startService(intentService)
    }

    private fun setViewBottomSheet(i: Int) {
        var music = mListSong.get(i)
        var tenbaihat = activity?.findViewById<TextView>(R.id.txt_tenbaihat_bottom_sheet)
        var tencasy = activity?.findViewById<TextView>(R.id.txt_casy_bottom_sheet)
        var hinhbaihat = activity?.findViewById<ImageView>(R.id.image_hinh_bottom_sheet)

        tenbaihat?.text = music.tenBaiHat
        tencasy?.text = music.caSy
        Picasso.get()
            .load(music.hinhBaiHat)
            .error(R.drawable.error)
            .into(hinhbaihat)
    }

    fun checkPlaySong() {
        var ic_play_bottom = activity?.findViewById<ImageView>(R.id.image_play_bottom_sheet)
        var ic_play_bottom_morong =
            activity?.findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)

        if (MainActivity.mediaPlayer != null && MainActivity.mediaPlayer?.isPlaying!!) {
            MainActivity.mediaPlayer!!.pause()
            ic_play_bottom?.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            ic_play_bottom_morong?.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            return
        }

        if (MainActivity.mediaPlayer != null && MainActivity.mediaPlayer!!.getCurrentPosition() > 1
            && !MainActivity.mediaPlayer!!.isPlaying
        ) {
            MainActivity.mediaPlayer!!.start()
            ic_play_bottom?.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
            ic_play_bottom_morong?.setColorFilter(Color.WHITE)
            ic_play_bottom_morong?.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
        }
    }

    fun eventClick() {
        var ic_play_bottom = activity?.findViewById<ImageView>(R.id.image_play_bottom_sheet)
        ic_play_bottom?.setOnClickListener { checkPlaySong() }

        activity?.findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)
            ?.setOnClickListener { checkPlaySong() }

        var ic_skip_bottom = activity?.findViewById<ImageView>(R.id.image_skipnext_bottom_sheet)
        ic_skip_bottom?.setOnClickListener {
            viTri = viTri?.plus(1)
            if (viTri == mListSong.size) viTri = 0
//            changeBottomSheet()
            clickItem(viTri)
        }

        var ic_previos_bottom = activity?.findViewById<ImageView>(R.id.image_previous_bottom_sheet)
        ic_previos_bottom?.setOnClickListener {
            viTri = viTri?.minus(1)
            if (viTri == -1) viTri = mListSong.size - 1
//            changeBottomSheet()
            clickItem(viTri)
        }

        activity?.findViewById<ImageView>(R.id.image_previous_bottom_sheet_morong)
            ?.setOnClickListener {
                if (MainActivity.mediaPlayer != null && !MainActivity.mediaPlayer!!.isPlaying
                    && MainActivity.mediaPlayer!!.getCurrentPosition() > 1
                ) {
                    activity?.findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)!!
                        .setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
                }
                viTri = viTri?.minus(1)
                if (viTri == -1)
                    viTri = mListSong.size - 1
//                changeBottomSheetMoRong()
                clickItem(viTri)
            }

        activity?.findViewById<ImageView>(R.id.image_skipnext_bottom_sheet_morong)
            ?.setOnClickListener {
                if (MainActivity.mediaPlayer != null && !MainActivity.mediaPlayer!!.isPlaying
                    && MainActivity.mediaPlayer!!.getCurrentPosition() > 1
                ) {
                    activity?.findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)
                        ?.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
                }
                viTri = viTri?.plus(1)
                if (viTri == mListSong.size) viTri = 0
//                changeBottomSheetMoRong()
                clickItem(viTri)
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

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is SendDataListSong) {
//            passData = context as SendDataListSong
//        } else {
//            throw RuntimeException(context.toString())
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        passData = null
//        activity?.unregisterReceiver(mediaPlayerBroadcast)
    }

    companion object {
        var position: String = "position"
        var list: String = "list"
        var data: String = "data"
    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
}