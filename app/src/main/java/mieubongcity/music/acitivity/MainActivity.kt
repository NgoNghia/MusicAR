package mieubongcity.music.acitivity

import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import mieubongcity.music.R
import mieubongcity.music.adapter.FragmentAdapter
import mieubongcity.music.fragment.Fragment_Settings


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mDrawerLayout: DrawerLayout

    companion object {
        @JvmStatic
        var mediaPlayer: MediaPlayer? = null
        lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

//        navigationView.setCheckedItem(R.id.home_navi)
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

    override fun onBackPressed() {
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }

//    open inner class PlayMp3 : AsyncTask<String, Void, String>() {
//        override fun doInBackground(vararg string: String?): String? {
//            return string[0].toString()
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            mediaPlayer = MediaPlayer().apply {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    setAudioAttributes(
//                        AudioAttributes.Builder()
//                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                            .setUsage(AudioAttributes.USAGE_MEDIA)
//                            .build()
//                    )
//                }
//            }
////            mediaPlayer!!.setOnCompletionListener {
////                it.stop()
////                it.reset()
////            }
//            mediaPlayer!!.setDataSource(result)
//            mediaPlayer!!.prepare()
//            mediaPlayer!!.start()
////            updateTimeSong()
//        }
//    }

}
