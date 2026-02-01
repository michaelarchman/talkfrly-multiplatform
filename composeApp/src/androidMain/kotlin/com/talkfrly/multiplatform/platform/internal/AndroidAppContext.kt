package com.talkfrly.multiplatform.platform.internal

import android.content.Context

object AndroidAppContext {
    lateinit var context: Context
        private set

    fun init(context: Context) {
        this.context = context.applicationContext
    }
}
