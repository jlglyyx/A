package com.yang.lib_common.upload

import android.util.Log
import com.yang.lib_common.constant.AppConstant
import okhttp3.*
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Okio
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @Author Administrator
 * @ClassName UploadManage
 * @Description
 * @Date 2021/11/19 9:31
 */
class UploadManage {

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(AppConstant.ClientInfo.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(AppConstant.ClientInfo.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(AppConstant.ClientInfo.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectionPool(ConnectionPool())
        .build()

    var uploadListener: UploadListener? = null


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
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            builder.addPart(
                MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    UploadRequestBody(requestBody, index)
                )
            )
        }
        return builder.build()
    }

    fun startUpload(filePath: List<String>): Int {
        val createRequestBody = createRequestBody(filePath)
        createRequestBody.let {
            val build = Request.Builder()
                .post(it)
                .url(AppConstant.ClientInfo.BASE_URL + "uploadFile")
                .build()
            okHttpClient.newCall(build).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    Log.i(TAG, "onFailure: ")
                }

                override fun onResponse(call: Call, response: Response) {

                    Log.i(TAG, "onResponse: ")
                }
            })
        }
        return 0
    }


    inner class UploadRequestBody(var requestBody: RequestBody, var noticeId: Int) : RequestBody() {

        override fun contentType(): MediaType? {
            return requestBody.contentType()
        }

        override fun writeTo(sink: BufferedSink) {
            var countByte = 0L
            val buffer = Okio.buffer(object : ForwardingSink(sink) {
                override fun write(source: Buffer, byteCount: Long) {
                    super.write(source, byteCount)
                    countByte += byteCount
                    val process = (countByte / requestBody.contentLength().toFloat() * 100).toInt()
                    uploadListener?.onProgress(noticeId, process)
                    if (process >= 100) {
                        uploadListener?.onSuccess(noticeId)
                    }
                    Log.i(
                        TAG,
                        "write: $byteCount  $countByte   ${
                            countByte / requestBody.contentLength().toFloat() * 100
                        }%  $process"
                    )
                }
            })
            Log.i(TAG, "aaaa: sss")
            requestBody.writeTo(buffer)
            buffer.flush()
        }

        override fun contentLength(): Long {
            return requestBody.contentLength()
        }
    }

}