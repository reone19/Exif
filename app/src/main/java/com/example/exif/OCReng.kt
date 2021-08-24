package com.example.exif

import android.content.Context
import android.graphics.Bitmap
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.*

class OCReng(context: Context) {
    private fun checkTrainedData(context: Context) {
        val dataPath = context.filesDir.toString() + File.separator + TESS_DATA_DIR
        val dir = File(dataPath)

        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(context)
        }

        if (dir.exists()) {
            val dataFilePath = dataPath + TESS_TRAINED_DATA
            val datafile = File(dataFilePath)

            if (!datafile.exists()) {
                copyFiles(context)
            }
        }
    }


    //１つ目はbaseApi.initの中では、言語データをFileでopenしているためFileで読み込める場所に言語データをコピーしてあげる必要があります。
    private fun copyFiles(context: Context) {
        try {
            val filePath =
                context.filesDir.toString() + File.separator + TESS_DATA_DIR + TESS_TRAINED_DATA
            val inputStream = context.assets.open(TESS_DATA_DIR + TESS_TRAINED_DATA)
            val outStream: OutputStream = FileOutputStream(filePath)
            val buffer = ByteArray(1024)
            var read: Int

            while (inputStream.read(buffer).also { read = it } != -1) {
                outStream.write(buffer, 0, read)
            }

            outStream.flush()
            outStream.close()
            inputStream.close()

            val file = File(filePath)

            if (!file.exists()) {
                throw FileNotFoundException()
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    fun getString(context: Context, bitmap: Bitmap?): String {
        val dataPath = context.filesDir.toString()
        val baseApi = TessBaseAPI()

        baseApi.init(dataPath, LANG)
        baseApi.setImage(bitmap)

        val recognizedText = baseApi.utF8Text

        baseApi.end()

        return recognizedText
    }


    companion object {
        private const val LANG = "eng"
        private val TESS_DATA_DIR = "tessdata" + File.separator
        private const val TESS_TRAINED_DATA = "$LANG.traineddata"
    }


    init {
        checkTrainedData(context)
    }
}