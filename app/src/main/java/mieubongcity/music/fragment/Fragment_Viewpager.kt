package mieubongcity.music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import mieubongcity.music.R
import mieubongcity.music.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout

class Fragment_Viewpager : Fragment() {
    private lateinit var mView: View
    private lateinit var mDrawerLayout: DrawerLayout
//    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var mViewPager: ViewPager
    private lateinit var mTabLayout: TabLayout
    private lateinit var mFragmentAdater: FragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_viewpager, container, false)
        mDrawerLayout = mView.findViewById(R.id.drawerlayout)
        mViewPager = mView.findViewById(R.id.viewpager)
        mTabLayout = mView.findViewById(R.id.tablayout)
        mTabLayout.setupWithViewPager(mViewPager)
        resources.getString(R.string.app_name)
        mViewPager.adapter = mFragmentAdater
        return mView
    }
}