package mieubongcity.music.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import mieubongcity.music.fragment.*

class FragmentAdapter(fm: FragmentManager, behavior: Int) :

    FragmentStatePagerAdapter(fm, behavior) {
    private var mListFragment: MutableList<Fragment> = mutableListOf()

    private var mListTitle: Array<String> = arrayOf(
        "Bài Hát", "Album",
        "Thể Loại", "Chủ Đề", "Danh sách phát"
    )

    init {
        mListFragment.add(Fragment_BaiHat())
        mListFragment.add(Fragment_Album())
        mListFragment.add(Fragment_TheLoai())
        mListFragment.add(Fragment_ChuDe())
        mListFragment.add(Fragment_PlayList())
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