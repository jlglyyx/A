package com.yang.lib_common.transform

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * @Author Administrator
 * @ClassName BlurTransformation
 * @Description
 * @Date 2021/8/30 16:42
 */
class BlurTransformation constructor(private val mContext:Context,private val radius:Float = 15f):BitmapTransformation() {

    companion object{
        private const val TAG = "BlurTransformation"
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val mRenderScript = RenderScript.create(mContext)
        val mScriptIntrinsicBlur = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript))
        val input = Allocation.createFromBitmap(mRenderScript, toTransform)
        val output = Allocation.createTyped(mRenderScript, input.type)
        mScriptIntrinsicBlur.setInput(input)
        mScriptIntrinsicBlur.setRadius(radius)
        mScriptIntrinsicBlur.forEach(output)
        output.copyTo(toTransform)
        mRenderScript.destroy()
        return toTransform
    }
}