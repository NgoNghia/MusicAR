package mieubongcity.music.service

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import mieubongcity.music.R
import mieubongcity.music.acitivity.MainActivity
import mieubongcity.music.common.Constant
import mieubongcity.music.fragment.Fragment_BaiHat
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.util.SendDataListSong
import java.lang.IllegalStateException
import java.text.SimpleDateFormat


class MyMusicSevice : Service() {
    private var tag = "tag"
    private var changeView: ChangeView? = null
    private var mList: MutableList<Model_BaiHat> = mutableListOf()
    private var viTri: Int = 0
    private var CHANNEL_ID = "play_song"
    override fun onBind(p0: Intent?): IBinder? {
        Log.d(tag, "onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("tag", "Service started...")
//        Toast.makeText(applicationContext, "Service started...", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            var bundle = intent.getBundleExtra(Fragment_BaiHat.data)
            if (bundle != null) {
                viTri = bundle.getInt(Fragment_BaiHat.position)
                mList = bundle.getParcelableArrayList<Parcelable>(Fragment_BaiHat.list)!! as MutableList<Model_BaiHat>
                playSong()
                MainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                initNotification()
            }
        }

//        changeView?.getDataListMusic(object : SendDataListSong {
//            override fun sendData(i: Int, mutableList: MutableList<Model_BaiHat>) {
//                MainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                if (i != null && !mutableList.isEmpty()) {
//                    mList = mutableList
//
//                } else {
//                    Toast.makeText(applicationContext, "mutableList =null", Toast.LENGTH_SHORT)
//                        .show()
//                    Log.d("tag", "mutableList  =null")
//                }
//            }
//        })
//        changeView?.eventClick()
//        changeView?.eventBottomSheet()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (MainActivity.mediaPlayer != null) {
            if(MainActivity.mediaPlayer?.isPlaying!!)
                MainActivity.mediaPlayer?.stop()
        }
    }

    private fun initNotification() {
        createNotification()
        var notificationLayout = RemoteViews(packageName, R.layout.notification_playsong)
        notificationLayout.setTextViewText(R.id.txt_notification_tenbaihat, mList.get(viTri).tenBaiHat)
        notificationLayout.setTextViewText(R.id.txt_notification_tencasy, mList.get(viTri).caSy)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constant.SERVICE_POSITION_NOTIFICATION, viTri)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val pendingCancel = Intent(Constant.BUTTON_CANCEL)
        val pendingNext = Intent(Constant.BUTTON_NEXT)
        val pendingPrevious = Intent(Constant.BUTTON_PREVIOUS)
        val pendingPlay = Intent(Constant.BUTTON_PLAY)

        val pendingIntentCancel = PendingIntent.getBroadcast(this, 1, pendingCancel, PendingIntent.FLAG_CANCEL_CURRENT)
        val pendingIntentNext = PendingIntent.getBroadcast(this, 2, pendingNext, PendingIntent.FLAG_CANCEL_CURRENT)
        val pendingIntentPrevious = PendingIntent.getBroadcast(this, 3, pendingPrevious, PendingIntent.FLAG_CANCEL_CURRENT)
        val pendingIntentPlay = PendingIntent.getBroadcast(this, 4, pendingPlay, PendingIntent.FLAG_CANCEL_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_24dp)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCustomContentView(notificationLayout)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .build()

        notificationLayout.setOnClickPendingIntent(
            R.id.image_cancel_notification,
            pendingIntentCancel
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.image_playsong_notification,
            pendingIntentPlay
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.image_skipnext_notification,
            pendingIntentNext
        )
        notificationLayout.setOnClickPendingIntent(
            R.id.image_previous_notificaion,
            pendingIntentPrevious
        )
        Picasso.get()
            .load(mList.get(viTri).hinhBaiHat)
            .placeholder(R.drawable.ic_file_download_black_24dp)
            .error(R.drawable.error)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    bitmap?.let {
                        notificationLayout.setImageViewBitmap(R.id.image_notification, it)
                    }
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }
            })
        startForeground(1, builder)
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification"
            val descriptionText = "description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setSound(null, null)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun playSong() {
        if (MainActivity.mediaPlayer == null) {
            var url = mList[viTri!!].linkBaiHat
            MainActivity.mediaPlayer = MediaPlayer().apply {
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
//            initNotification()
//            changeView?.updateTimeSong()
        } else {
            if(MainActivity.mediaPlayer!!.isPlaying)
                MainActivity.mediaPlayer!!.stop()
            MainActivity.mediaPlayer!!.release()
            MainActivity.mediaPlayer = null
            playSong()
        }
    }

    inner class ChangeView : AppCompatActivity() {
        private var sendDataListSong: SendDataListSong? = null

        fun changeBottomSheet() {
            findViewById<LinearLayout>(R.id.bottomsheet_linearlayout)
                .visibility = View.GONE
            findViewById<RelativeLayout>(R.id.bottomsheet_relativelayout)
                .visibility = View.VISIBLE
            findViewById<TextView>(R.id.txt_tenbaihat_bottom_sheet)
                .setText(mList.get(viTri!!).tenBaiHat)
            findViewById<TextView>(R.id.txt_casy_bottom_sheet)
                .setText(mList.get(viTri!!).caSy)
            Picasso.get()
                .load(mList.get(viTri!!).hinhBaiHat)
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.error)
                .fit()
                .into(findViewById<ImageButton>(R.id.image_hinh_bottom_sheet))
            findViewById<ImageView>(R.id.image_play_bottom_sheet)
                .setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
        }

        fun updateTimeSong() {
            var simpleDateFormat = SimpleDateFormat("mm:ss")
            var timesong = findViewById<TextView>(R.id.txt_timesong)
            findViewById<TextView>(R.id.txt_totaltimesong)
                .setText(simpleDateFormat.format(MainActivity.mediaPlayer?.duration))
            var seekBar = findViewById<SeekBar>(R.id.seebar_playsong_bottomshet_morong)
            seekBar.max = MainActivity.mediaPlayer!!.duration
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(seebar: SeekBar?) {
                    MainActivity.mediaPlayer?.let {
                        MainActivity.mediaPlayer!!.seekTo(seebar!!.progress)
                    }
                }
            })
            var handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if (MainActivity.mediaPlayer != null && MainActivity.mediaPlayer!!.isPlaying) {
                        seekBar.setProgress(MainActivity.mediaPlayer!!.currentPosition)
                        timesong.setText(simpleDateFormat.format(MainActivity.mediaPlayer!!.currentPosition))
                    }
                    handler.postDelayed(this, 500)
                    MainActivity.mediaPlayer!!.setOnCompletionListener {
                        Thread.sleep(1000)
                    }
                }
            }, 500)
        }

        fun changeBottomSheetMoRong() {
            findViewById<RelativeLayout>(R.id.bottomsheet_relativelayout)
                .visibility = View.GONE
            findViewById<TextView>(R.id.txt_tenbaihat_bottom_sheet_morong)
                .setText(mList.get(viTri!!).tenBaiHat)
            findViewById<TextView>(R.id.txt_casy_bottom_sheet_morong)
                .setText(mList.get(viTri!!).caSy)
            findViewById<ImageView>(R.id.image_previous_bottom_sheet_morong)
                .setColorFilter(Color.WHITE)
            findViewById<ImageView>(R.id.image_skipnext_bottom_sheet_morong)
                .setColorFilter(Color.WHITE)

            var ic_play_bottom_morong =
                findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)

            if (MainActivity.mediaPlayer != null && MainActivity.mediaPlayer?.isPlaying!!) {
                ic_play_bottom_morong.setColorFilter(Color.WHITE)
                ic_play_bottom_morong.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
            } else {
                ic_play_bottom_morong.setColorFilter(Color.WHITE)
            }
            Picasso.get()
                .load(mList.get(viTri!!).hinhBaiHat)
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .error(R.drawable.error)
                .fit()
                .into(findViewById<ImageView>(R.id.image_hinh_bottom_sheet_morong))
            findViewById<LinearLayout>(R.id.bottomsheet_linearlayout)
                .visibility = View.VISIBLE
        }

        fun eventBottomSheet() {
            MainActivity.bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            changeBottomSheet()
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            changeBottomSheetMoRong()
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    bottomSheet.setOnClickListener {
                        MainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    findViewById<ImageView>(R.id.image_arrow_down_bottomshet_morong).setOnClickListener {
                        MainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    MainActivity.bottomSheetBehavior.isHideable = false
                }
            })
        }

        fun getDataListMusic(sendDataListSong: SendDataListSong) {
            this.sendDataListSong = sendDataListSong
        }
    }
}