package com.e.mybackgroundtasks2

import timber.log.Timber

class MyRunnable(var message: String): Runnable {
    var toRun = true
    override fun run() {
        while (toRun){
            Thread.sleep(1000)
            Timber.i(message)
        }
    }
}