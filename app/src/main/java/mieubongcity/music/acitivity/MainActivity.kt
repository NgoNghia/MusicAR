package mieubongcity.music.acitivity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import mieubongcity.music.R
import mieubongcity.music.adapter.FragmentAdapter
import mieubongcity.music.fragment.Fragment_Settings
import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.util.SendDataListSong
import java.lang.Exception
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, SendDataListSong {
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var mList: MutableList<Model_BaiHat> = mutableListOf()
    private var viTri: Int = 0
    private var CHANNEL_ID = "play_song"

    companion object {
        @JvmStatic
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        eventBottomSheet()
        eventClick()
//        navigationView.setCheckedItem(R.id.home_navi)
    }

    private fun eventBottomSheet() {
        bottomSheetBehavior.addBottomSheetCallback(object :
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
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                findViewById<ImageView>(R.id.image_arrow_down_bottomshet_morong).setOnClickListener {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                bottomSheetBehavior.isHideable = false
            }
        })
    }

    private fun changeBottomSheet() {
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

    private fun changeBottomSheetMoRong() {
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

        var ic_play_bottom_morong = findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)

        if (mediaPlayer != null && mediaPlayer?.isPlaying!!) {
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

    private fun initView() {
        var mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        mToolbar.setBackgroundColor(Color.TRANSPARENT)
        mDrawerLayout = findViewById(R.id.drawerlayout)
        var mNavigationView = findViewById<NavigationView>(R.id.navi_view)
        var mViewPager = findViewById<ViewPager>(R.id.viewpager)
        findViewById<TabLayout>(R.id.tablayout).setupWithViewPager(mViewPager)
        var mFragmentAdater = FragmentAdapter(supportFragmentManager, 8)
        mViewPager.adapter = mFragmentAdater
        var mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mDrawerLayout!!.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        mNavigationView.setNavigationItemSelectedListener(this)
        AppBarConfiguration.Builder(
            R.id.home_navi,
            R.id.infor_navi, R.id.settings_navi
        )
            .setDrawerLayout(mDrawerLayout)
            .build()
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet)
        bottomSheetBehavior.setHideable(true)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
//        var policy: StrictMode.ThreadPolicy =
//            StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//        var navi  =findNavController(this, R.id.nav_host_fragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_navi -> mDrawerLayout.closeDrawer(GravityCompat.START)
            R.id.infor_navi -> Toast.makeText(this, "thong tin", Toast.LENGTH_SHORT).show()
            else ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.framlayout,
                    Fragment_Settings()
                ).commit()

        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchItem = menu?.findItem(R.id.search_bar)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search here!"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                supportFragmentManager.beginTransaction().replace(
                    R.id.liearlayout,
                    Fragment_Settings()
                ).commit()
                return false
            }
        })
        return true
    }

    override fun sendData(i: Int, mutableList: MutableList<Model_BaiHat>) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        if (i != null && mutableList.size > 0) {
            mList = mutableList
            when (i) {
                -1 -> {
                    viTri = (0 until mList.size).random()
                    changeBottomSheet()
                    playSong()
                }
                else -> {
                    viTri = i
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    changeBottomSheet()
                    playSong()
                }
            }
        }
    }

    private fun playSong() {
        if (mediaPlayer == null) {
            var url = mList[viTri!!].linkBaiHat
            mediaPlayer = MediaPlayer().apply {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
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
            initNotification()
            updateTimeSong()
        } else {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
            playSong()
        }
    }


    private fun initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification"
            val descriptionText = "description"
            val importance: Int = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setSound(null,null)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val notificationLayout = RemoteViews(packageName, R.layout.notification_playsong)
            var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_24dp)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCustomContentView(notificationLayout)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setDefaults(Notification.DEFAULT_ALL)
            notificationLayout.setTextViewText(
                R.id.txt_notification_tenbaihat,
                mList.get(viTri).tenBaiHat
            )
            notificationLayout.setTextViewText(
                R.id.txt_notification_tencasy,
                mList.get(viTri).caSy
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

            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
        }
    }

    private fun eventClick() {
        var ic_play_bottom = findViewById<ImageView>(R.id.image_play_bottom_sheet)
        ic_play_bottom.setOnClickListener { checkPlaySong() }

        findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)
            .setOnClickListener { checkPlaySong() }

        var ic_skip_bottom = findViewById<ImageView>(R.id.image_skipnext_bottom_sheet)
        ic_skip_bottom.setOnClickListener {
            viTri = viTri?.plus(1)
            if (viTri == mList.size) viTri = 0
            changeBottomSheet()
            playSong()
        }

        var ic_previos_bottom = findViewById<ImageView>(R.id.image_previous_bottom_sheet)
        ic_previos_bottom.setOnClickListener {
            viTri = viTri?.minus(1)
            if (viTri == -1) viTri = mList.size - 1
            changeBottomSheet()
            playSong()
        }

        findViewById<ImageView>(R.id.image_previous_bottom_sheet_morong)
            .setOnClickListener {
                if (mediaPlayer != null && !mediaPlayer!!.isPlaying
                    && mediaPlayer!!.getCurrentPosition() > 1
                ) {
                    findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)
                        .setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
                }
                viTri = viTri?.minus(1)
                if (viTri == -1)
                    viTri = mList.size - 1
                changeBottomSheetMoRong()
                playSong()
            }

        findViewById<ImageView>(R.id.image_skipnext_bottom_sheet_morong)
            .setOnClickListener {
                if (mediaPlayer != null && !mediaPlayer!!.isPlaying
                    && mediaPlayer!!.getCurrentPosition() > 1
                ) {
                    findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)
                        .setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
                }
                viTri = viTri?.plus(1)
                if (viTri == mList.size) viTri = 0
                changeBottomSheetMoRong()
                playSong()
            }
    }

    private fun checkPlaySong() {
        var ic_play_bottom = findViewById<ImageView>(R.id.image_play_bottom_sheet)
        var ic_play_bottom_morong = findViewById<ImageView>(R.id.image_playsong_bottomsheet_morong)

        if (mediaPlayer != null && mediaPlayer?.isPlaying!!) {
            mediaPlayer!!.pause()
            ic_play_bottom.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            ic_play_bottom_morong.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            return
        }

        if (mediaPlayer != null && mediaPlayer!!.getCurrentPosition() > 1
            && !mediaPlayer!!.isPlaying
        ) {
            mediaPlayer!!.start()
            ic_play_bottom.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
            ic_play_bottom_morong.setColorFilter(Color.WHITE)
            ic_play_bottom_morong.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp)
        }
    }

    override fun onBackPressed() {
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }

    open inner class PlayMp3 : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg string: String?): String? {
            return string[0].toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            mediaPlayer = MediaPlayer().apply {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                }
            }
//            mediaPlayer!!.setOnCompletionListener {
//                it.stop()
//                it.reset()
//            }
            mediaPlayer!!.setDataSource(result)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            updateTimeSong()
        }
    }

    private fun updateTimeSong() {
        var simpleDateFormat = SimpleDateFormat("mm:ss")
        var timesong = findViewById<TextView>(R.id.txt_timesong)
        findViewById<TextView>(R.id.txt_totaltimesong)
            .setText(simpleDateFormat.format(mediaPlayer?.duration))
        var seekBar = findViewById<SeekBar>(R.id.seebar_playsong_bottomshet_morong)
        seekBar.max = mediaPlayer!!.duration
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(seebar: SeekBar?) {
                mediaPlayer?.let {
                    mediaPlayer!!.seekTo(seebar!!.progress)
                }
            }
        })
        var handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                    seekBar.setProgress(mediaPlayer!!.currentPosition)
                    timesong.setText(simpleDateFormat.format(mediaPlayer!!.currentPosition))
                }
                handler.postDelayed(this, 500)
                mediaPlayer!!.setOnCompletionListener {
                    Thread.sleep(1000)
                }
            }
        }, 500)
    }
}
