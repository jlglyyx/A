package com.yang.lib_common.upload

import android.util.Log
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.interceptor.LogInterceptor
import com.yang.lib_common.interceptor.UrlInterceptor
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.UploadTaskData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Okio
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @Author Administrator
 * @ClassName UploadManage
 * @Description
 * @Date 2021/11/19 9:31
 */
class UploadManage {

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(UrlInterceptor())
        .addInterceptor(LogInterceptor(AppConstant.Constant.NUM_ONE))
        .connectTimeout(AppConstant.ClientInfo.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(AppConstant.ClientInfo.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(AppConstant.ClientInfo.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectionPool(ConnectionPool())
        .build()
    private var uploadListeners: MutableList<UploadListener?> = mutableListOf()

    private var uploadCalls: MutableList<Call> = mutableListOf()

    companion object {
        private const val TAG = "UploadManage"

        val instance: UploadManage by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UploadManage()
        }
    }

    private fun createRequestBody(filePath: List<String>): RequestBody {
        val builder = MultipartBody.Builder()
        filePath.forEachIndexed { index, s ->
            val file = File(s)
            val id = UUID.randomUUID().toString().replace("-", "")
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            builder.addPart(
                MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    UploadRequestBody(requestBody, id)
                )
            )
            CoroutineScope(Dispatchers.IO).launch {
                BaseAppDatabase.instance.uploadTaskDao().insertData(UploadTaskData(id, 0, s, 0))
            }
        }
        return builder.build()
    }

    fun startUpload(filePath: List<String>): Call {
        val createRequestBody = createRequestBody(filePath)
        var newCall: Call
        createRequestBody.let {
            val build = Request.Builder()
                .post(it)
                .url(AppConstant.ClientInfo.BASE_URL + "uploadFile")
                .build()
            newCall = okHttpClient.newCall(build)
            uploadCalls.add(newCall)
            newCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i(TAG, "onFailure: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i(TAG, "onResponse: ")
                }
            })
        }
        return newCall
    }

    fun cancelUpload(uploadCall: Call) {
        uploadCall.cancel()
    }

    inner class UploadRequestBody(var requestBody: RequestBody, var id: String) :
        RequestBody() {

        override fun contentType(): MediaType? {
            return requestBody.contentType()
        }

        override fun writeTo(sink: BufferedSink) {
            var countByte = 0L
            val buffer = Okio.buffer(object : ForwardingSink(sink) {
                override fun write(source: Buffer, byteCount: Long) {
                    super.write(source, byteCount)
                    countByte += byteCount
                    val process =
                        (countByte / requestBody.contentLength().toFloat() * 100).toInt()
                    uploadListeners.forEach {
                        it?.onProgress(id, process)
                        val queryData =
                            BaseAppDatabase.instance.uploadTaskDao().queryData(id)
                        queryData.progress = process
                        BaseAppDatabase.instance.uploadTaskDao().updateData(queryData)
                        if (process >= 100) {
                            it?.onSuccess(id)
                            queryData.status = 1
                            BaseAppDatabase.instance.uploadTaskDao().updateData(queryData)
                        }
                    }
                }
            }
            )
            requestBody.writeTo(buffer)
            buffer.flush()
        }

        override fun contentLength(): Long {
            return requestBody.contentLength()
        }
    }

    fun addUploadListener(uploadListener: UploadListener) {
        uploadListeners.add(uploadListener)
    }

    fun removeUploadListener(uploadListener: UploadListener) {
        uploadListeners.remove(uploadListener)
    }

    fun clearUploadListener() {
        uploadListeners.clear()
    }


}