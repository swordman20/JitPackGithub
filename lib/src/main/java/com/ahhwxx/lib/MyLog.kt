package com.ahhwxx.lib

import android.util.Log

/**
 *    author : hades
 *    date   : 3/18/22
 *    desc   :
 */
object MyLog {
    fun i(msg:String){Log.i(this.javaClass.simpleName,msg)}
}