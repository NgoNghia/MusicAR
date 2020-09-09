package mieubongcity.music.ui

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import mieubongcity.music.R
import mieubongcity.music.adapter.FragmentAdapter
import mieubongcity.music.fragment.Fragment_Settings


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private var connected: Boolean = false
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
//        navigationView.setCheckedItem(R.id.home_navi)
    }

    private fun checkInternet() : Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun initView() {
        var mToolbar: Toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawerlayout)
        var mNavigationView: NavigationView = findViewById(R.id.navi_view)
        var mViewPager: ViewPager = findViewById(R.id.viewpager)
        var mTabLayout: TabLayout = findViewById(R.id.tablayout)
        mTabLayout.setupWithViewPager(mViewPager)
        var mFragmentAdater: FragmentAdapter = FragmentAdapter(supportFragmentManager, 0)
        mViewPager.adapter = mFragmentAdater
        setSupportActionBar(findViewById(R.id.toolbar))
        var mDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        );
        mDrawerLayout!!.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        mNavigationView.setNavigationItemSelectedListener(this)
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.home_navi, R.id.infor_navi, R.id.settings_navi
        )
            .setDrawerLayout(mDrawerLayout)
            .build()
//        var navi  =findNavController(this, R.id.nav_host_fragment)
    }

    override fun onBackPressed() {
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_navi -> mDrawerLayout.closeDrawer(GravityCompat.START)
            R.id.infor_navi -> Toast.makeText(this, "thong tin", Toast.LENGTH_SHORT).show()
            else ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.liearlayout,
                    Fragment_Settings()
                ).commit()
        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchItem = menu?.findItem(R.id.search_bar)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Search here!"
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        var ab = mutableListOf("2")

        ab.add("hà nội")
        ab.add("Đà nẵng")
        ab.add("hội an")
        ab.add("hải phòng")
        ab.add("bến tre")
        ab.add("cần thơ")
        ab.add("tháp trang")
        ab.filter { it.equals(newText, true) }
        return true
    }

}
