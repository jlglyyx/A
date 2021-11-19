package com.yang.lib_common.upload

import android.util.Log
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.helper.getRemoteComponent
import okhttp3.*
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Okio
import java.io.File
import java.io.IOException
import javax.inject.Inject

/**
 * @Author Administrator
 * @ClassName UploadManage
 * @Description
 * @Date 2021/11/19 9:31
 */
class UploadManage : UploadListener{

    @Inject
    lateinit var okHttpClient: OkHttpClient


    companion object{
        private const val TAG = "UploadManage"

        val instance:UploadManage by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
            UploadManage()
        }
    }

    init {
        getRemoteComponent().inject(this)
    }


    private fun createRequestBody(filePath:String):RequestBody{
        val file = File(filePath)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        MultipartBody.Part.createFormData("file", file.name, object : RequestBody() {
            override fun contentType(): MediaType? {
                return requestBody.contentType()
            }
            override fun writeTo(sink: BufferedSink) {
                val buffer = Okio.buffer(object : ForwardingSink(sink) {
                    override fun write(source: Buffer, byteCount: Long) {
                        super.write(source, byteCount)
                        Log.i(TAG, "write: $byteCount")
                    }
                })
                requestBody.writeTo(buffer)
                buffer.flush()
            }
            override fun contentLength(): Long {
                return requestBody.contentLength()
            }
        })
        return requestBody
    }

    override fun startUpload(filePath:String): Int {
        val build = Request.Builder()
            .post(createRequestBody(filePath))
            .url(AppConstant.ClientInfo.BASE_URL+"uploadFile")
            .build()
        okHttpClient.newCall(build).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                Log.i(TAG, "onFailure: ")
            }

            override fun onResponse(call: Call, response: Response) {

                Log.i(TAG, "onResponse: ")
            }

        })
        return 0
    }

    override fun cancelUpload(i: Int) {

    }

}