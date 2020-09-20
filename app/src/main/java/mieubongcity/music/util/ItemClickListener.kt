package mieubongcity.music.util

import mieubongcity.music.model.Model_Album
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.model.Model_TheLoai

interface ItemClickPlayListListener {
    fun onClick(playList: Model_PlayList)
}

interface ItemClickAlbumListener {
    fun onClick(album: Model_Album)
}

interface ItemClickTheLoaiListener{
     fun onClick(theloai : Model_TheLoai)
}