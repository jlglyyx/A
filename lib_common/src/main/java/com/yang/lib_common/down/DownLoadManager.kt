package com.yang.lib_common.down

import android.os.Environment
import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

/**
 * @Author Administrator
 * @ClassName DownLoadManager
 * @Description
 * @Date 2021/12/1 14:26
 */
class DownLoadManager : Thread(),DownLoadManagerListener {

    companion object{
        private const val TAG = "DownLoadManager"
    }

    var file = File("${Environment.getExternalStorageDirectory()}/MFiles/picture/${System.currentTimeMillis()}_b.jpg")


    var fileLength = 0L

    var url = "https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"


    var executorService = Executors.newFixedThreadPool(5)

    var threadSize = 3000

    var threadList = mutableListOf<DownLoadTask>()

    var downloadPercent = 0f

    @Volatile
    var downloadSize = 0L


    private fun getContentLength() {
        val url = URL(url)
        val openConnection = url.openConnection() as HttpURLConnection
        if (openConnection.responseCode == 200 || openConnection.responseCode == 206) {
            val contentLength = openConnection.contentLength.toLong()
            if (file.exists()) {
                fileLength = file.length()
            }
            if (fileLength == contentLength) {
                return
            }
            val randomAccessFile = RandomAccessFile(file, "rwd")
            randomAccessFile.setLength(contentLength)
            randomAccessFile.close()

            val block = (contentLength - fileLength) / threadSize

            val lastBlock = (contentLength - fileLength) % threadSize

            for (i in 0 until threadSize) {
                if (i == threadSize - 1) {
                    executorService.submit(DownLoadTask(block * i, block * (i + 1) + lastBlock).apply {
                        threadList.add(this)
                    })
                } else {
                    executorService.submit(DownLoadTask(block * i, block * (i + 1)).apply {
                        threadList.add(this)
                    })
                }
            }
            val measureTimeMillis = measureTimeMillis {
                var finish = false
                while (!finish) {
                    downloadSize = 0
                    threadList.forEach {
                        downloadSize += it.downSize
                    }
                    val fl = downloadSize / contentLength.toFloat()
                    if (downloadPercent != fl) {
                        downloadPercent = fl
                        onProgress(downloadPercent)
                    }
                    if (downloadSize >= contentLength) {
                        finish = true
                    }
                }
            }
            onSuccess(file.absolutePath,measureTimeMillis)

        }
    }

    override fun run() {
        super.run()
        getContentLength()
    }

    inner class DownLoadTask(var startIndex: Long, var endIndex: Long) : Thread() {

        var downSize = 0L

        override fun run() {
            super.run()
            startDown(startIndex, endIndex)
        }
        private fun startDown(startIndex: Long, endIndex: Long) {
            val url = URL(url)
            val openConnection = url.openConnection() as HttpURLConnection
            Log.i("TAG", "startDown===: $startIndex  $endIndex")
            openConnection.setRequestProperty("Range", "bytes=$startIndex-$endIndex")
            if (openConnection.responseCode == 200 || openConnection.responseCode == 206) {
                val contentLength = openConnection.contentLength
                val inputStream = openConnection.inputStream
                var currentIndex = startIndex
                val randomAccessFile = RandomAccessFile(file, "rwd")
                var bytes = ByteArray((endIndex-startIndex).toInt())
                val bufferedInputStream = BufferedInputStream(inputStream,bytes.size)
                randomAccessFile.seek(startIndex)
                val measureTimeMillis = measureTimeMillis {
                    while (currentIndex < endIndex) {
                        val read = bufferedInputStream.read(bytes)
                        if (read == -1) {
                            break
                        }
                        randomAccessFile.write(bytes, 0, read)
                        currentIndex += read
                        downSize += read
                        onChildProgress(
                            this@DownLoadTask.name,
                            downSize / (endIndex - startIndex).toFloat()
                        )
                    }
                }
                Log.i(TAG, "${this@DownLoadTask.name}===measureTimeMillis: $measureTimeMillis")
                randomAccessFile.close()
                bufferedInputStream.close()
                inputStream.close()
                openConnection.disconnect()
            }
        }
    }

    override fun onSuccess(downloadPath: String,time:Long) {
        Log.i(TAG, "onSuccess: $downloadPath 用时:$time")
    }

    override fun onFailed(errorMessage: String) {
    }

    override fun onPaused() {
    }

    override fun onCanceled() {
    }

    override fun onProgress(progress: Float) {
        Log.i(TAG, "onProgress: ${progress*100}%")
    }

    override fun onChildProgress(name:String,progress: Float) {
        Log.i(TAG, "onChildProgress $name 下载: ${progress*100}%")
    }


}