package mieubongcity.music.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import mieubongcity.music.common.Constant
import java.util.*

open class MediaPlayerBroadcast : BroadcastReceiver() {
    var onclickNotifyBroadcast: OnclickNotifyBroadcast? = null
    fun setMyBroadcastCall(myBroadcastCall: OnclickNotifyBroadcast?) {
        onclickNotifyBroadcast = myBroadcastCall
    }

    //    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            when (Objects.requireNonNull(intent.action)) {
                Constant.BUTTON_CANCEL -> {
                    onclickNotifyBroadcast!!.onClickCanel()
                }

                Constant.BUTTON_PREVIOUS -> {
                    Toast.makeText(context, "đi lùi", Toast.LENGTH_SHORT).show()
                    onclickNotifyBroadcast?.onClickPrevious()
                }

                Constant.BUTTON_PLAY -> {
                    onclickNotifyBroadcast?.onClickPlay()
                    Toast.makeText(context, "play", Toast.LENGTH_SHORT).show()
                }
                Constant.BUTTON_NEXT -> {
                    onclickNotifyBroadcast?.onClickNext()
                    Toast.makeText(context, "đi tới", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    interface OnclickNotifyBroadcast {
        fun onClickPrevious()
        fun onClickPlay()
        fun onClickNext()
        fun onClickCanel()
    }
}
