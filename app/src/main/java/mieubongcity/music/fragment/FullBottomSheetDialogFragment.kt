package mieubongcity.music.fragment

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mieubongcity.music.R


class FullBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var mBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog : BottomSheetDialog = super.onCreateDialog(savedInstanceState) as (BottomSheetDialog)
        var view : View = View.inflate(activity, R.layout.bottom_sheet_full_scren, null)
        var linearLayout : LinearLayout = view.findViewById(R.id.liearlayout_bottom_sheet_full_scren)
        var  params : LinearLayout.LayoutParams =  linearLayout.layoutParams as (LinearLayout.LayoutParams)
        params.height = getScreenHeight()
        linearLayout.setLayoutParams(params)
        dialog.setContentView(view)
        mBehavior = BottomSheetBehavior.from(view.parent as View)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        mBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}