package mieubongcity.music.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import mieubongcity.music.R
import mieubongcity.music.fragment.*

class FragmentAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {
    private var mListFragment: MutableList<Fragment> = mutableListOf()

    private var mListTitle = arrayOf(
        "Bài Hát", "Danh sách phát", "Thể Loại", "Album"
    )

    init {
        mListFragment.add(Fragment_BaiHat())
        mListFragment.add(Fragment_PlayList())
        mListFragment.add(Fragment_TheLoai())
        mListFragment.add(Fragment_Album())
        try {
            mListTitle[4] = "${Resources.getSystem().getText(R.string.page_title)}"
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun getCount(): Int {
        return mListFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return mListFragment.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mListTitle.get(position)
    }

}