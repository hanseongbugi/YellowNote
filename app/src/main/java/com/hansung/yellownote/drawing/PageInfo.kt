package com.hansung.yellownote.drawing

import android.graphics.Color
import androidx.room.Embedded
import com.google.gson.Gson
import java.io.Serializable
import java.nio.file.Paths


class PageInfo(pageNo:Int) {
    var pageNo = pageNo
    var customPaths = ArrayList<CustomPath>()
    var penColor:Int? = Color.BLACK

    fun changePathColor(penColor:Int){
        this.penColor = penColor
    }

    fun removeSelectedPaths(selectedPaths: ArrayList<CustomPath>){
        for(i in 0..selectedPaths.size-1){
            customPaths.remove(selectedPaths[i])
        }
        System.out.println(customPaths.size)
    }
    @JvmName("setCustomPaths1")
    fun setCustomPaths(paths: ArrayList<CustomPath>){
        var gson = Gson()
        customPaths=paths
    }

    fun pathsToBytes(){
        for(i in 0..customPaths.size-1){
            customPaths[i].changeToByte()
        }
    }



}