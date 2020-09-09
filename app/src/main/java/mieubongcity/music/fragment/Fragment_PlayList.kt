package mieubongcity.music.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import mieubongcity.music.R
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.util.APIService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Fragment_PlayList : Fragment() {
    private lateinit var mView: View
    private lateinit var image_playlist: ImageView
    private lateinit var txt_playlist: TextView
    private var mList = mutableListOf<Model_PlayList>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_playlist, container, false)
        image_playlist = mView.findViewById(R.id.image_playlist);
        txt_playlist = mView.findViewById(R.id.txt_playlist);
        getDataPlayList();
        return mView

    }

    private fun getDataPlayList() {
        var getJson  = APIService.getDataService()
        var iDataService = getJson.getDataPlayList()
        iDataService.enqueue(object :  retrofit2.Callback<List<Model_PlayList>> {
            override fun onResponse(
                call: Call<List<Model_PlayList>>,
                response: Response<List<Model_PlayList>>
            ) {
                mList = response.body() as MutableList<Model_PlayList>
                if(mList.isEmpty() || mList==null)
                Toast.makeText(activity, "Rỗng", Toast.LENGTH_SHORT).show()
                Toast.makeText(activity, mList.get(0).tenPlayList, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Model_PlayList>>, t: Throwable) {
                Log.e("aaa", t.message.toString())
                Toast.makeText(activity, t.message.toString(), Toast.LENGTH_SHORT).show()

            }

        } )

    }

}