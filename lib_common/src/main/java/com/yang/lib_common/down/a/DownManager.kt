package com.yang.lib_common.down.a

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

/**
 * @Author Administrator
 * @ClassName DownManager
 * @Description
 * @Date 2021/11/30 16:28
 */
class DownManager : DownListener {


    private var jobs: MutableList<Job> = mutableListOf()

    private var fileLength = 0L

    private var contentLength = 0L

    private val file = File("${Environment.getExternalStorageDirectory()}/MFiles/video/bbab.jpg")

    var byte = byteArrayOf(1024.toByte())

    var hasDownSize = 0L

    var jobSize = 10

    private fun initJob(start: Long, end: Long) {
        try {
            val job = GlobalScope.launch(Dispatchers.IO) {
                var currentPosition = start
                val openConnection =
                    URL("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg").openConnection() as HttpURLConnection
                openConnection.setRequestProperty("Range", "bytes=$start-$end")
                if (openConnection.responseCode == 200 || openConnection.responseCode == 206) {
                    val randomAccessFile = RandomAccessFile(file, "rw")
                    randomAccessFile.seek(start)
                    val bufferedInputStream = BufferedInputStream(openConnection.inputStream, 1)
                    while (currentPosition < end) {
                        val read = bufferedInputStream.read(byte)
                        if (read == -1) {
                            break
                        }
                        randomAccessFile.write(byte, 0, read)
                        currentPosition += read
                        hasDownSize += read
                        Log.i("TAG", "initJob: ${hasDownSize/contentLength.toFloat() * 100}%")
                    }
                    bufferedInputStream.close()
                    randomAccessFile.close()
                    openConnection.disconnect()
                    successDown()
                }
            }
            jobs.add(job)
        } catch (e: Exception) {
            Log.i("TAG", "initJob: ${e.message}")
        }

    }

    fun getContentLength() {
        GlobalScope.launch(Dispatchers.IO) {
            val openConnection =
                URL("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg").openConnection() as HttpURLConnection
            if (openConnection.responseCode == 200 || openConnection.responseCode == 206) {
                contentLength = openConnection.contentLength.toLong()
                if (file.exists()) {
                    fileLength = file.length()
                }
                if (contentLength == fileLength) {
                    successDown()
                    return@launch
                }
                val randomAccessFile = RandomAccessFile(file, "rwd")
                randomAccessFile.setLength(contentLength)
                randomAccessFile.close()
                val block = contentLength / jobSize
                for (i in 0 until jobSize) {
                    initJob(fileLength + block * i, fileLength + block * (i + 1))
                }
            } else {
                errorDown()
            }
        }
    }

    override fun startDown() {

    }

    override fun stopDown() {

    }

    override fun cancelDown() {

    }

    override fun finishDown() {

    }

    override fun successDown() {

        Log.i("TAG", "successDown: ")
    }

    override fun errorDown() {

    }
}