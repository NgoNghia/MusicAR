package com.example.music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.music.R
import me.relex.circleindicator.CircleIndicator

class Fragment_BaiHat : Fragment() {
    private lateinit var mView: View
    private lateinit var mIndicator: CircleIndicator
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRelativeLayout: RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_baihat, container, false)
        mIndicator = mView.findViewById(R.id.indicator);
        mRecyclerView = mView.findViewById(R.id.rc_baihat);
        mRelativeLayout = mView.findViewById(R.id.mRelativeLayout);
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRelativeLayout.setOnClickListener { Toast.makeText(activity," abc", Toast.LENGTH_SHORT).show() }
    }
}