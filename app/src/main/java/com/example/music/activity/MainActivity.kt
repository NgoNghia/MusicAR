package com.example.music.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager.widget.ViewPager
import com.example.music.R
import com.example.music.adapter.FragmentAdapter
import com.example.music.fragment.Fragment_Settings
import com.example.music.fragment.Fragment_TheLoai
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var mNavigationView: NavigationView
    private lateinit var mViewPager: ViewPager
    private lateinit var mTabLayout: TabLayout
    private lateinit var mFragmentAdater: FragmentAdapter
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
//        navigationView.setCheckedItem(R.id.home_navi)
    }

    private fun initView() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawerlayout)
        mNavigationView = findViewById(R.id.navi_view)
        mViewPager = findViewById(R.id.viewpager)
        mTabLayout = findViewById(R.id.tablayout)
        mTabLayout.setupWithViewPager(mViewPager)
        mFragmentAdater = FragmentAdapter(supportFragmentManager, 0)
        mViewPager.adapter = mFragmentAdater
        setSupportActionBar(findViewById(R.id.toolbar))
        mDrawerToggle = ActionBarDrawerToggle(
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
        var fragment: Fragment
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
        // fragment_container
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_bar)
            Toast.makeText(this, "Search View", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}
