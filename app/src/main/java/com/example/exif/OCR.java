package com.example.exif;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OCR {

    private static final String LANG = "eng";
    private static final String TESS_DATA_DIR = "tessdata" + File.separator;
    private static final String TESS_TRAINED_DATA = LANG + ".traineddata";

    public OCR(Context context){
        checkTrainedData(context);
    }

    private void checkTrainedData(Context context) {
        String dataPath = context.getFilesDir() + File.separator + TESS_DATA_DIR;
        File dir = new File(dataPath);
        if (!dir.exists() && dir.mkdirs()){
            copyFiles(context);
        }
        if(dir.exists()) {
            String dataFilePath = dataPath + TESS_TRAINED_DATA;
            File datafile = new File(dataFilePath);
            if (!datafile.exists()) {
                copyFiles(context);
            }
        }
    }

    //１つ目はbaseApi.initの中では、言語データをFileでopenしているためFileで読み込める場所に言語データをコピーしてあげる必要があります。
    private void copyFiles(Context context) {
        try {
            String filePath = context.getFilesDir() + File.separator + TESS_DATA_DIR + TESS_TRAINED_DATA;

            InputStream inputStream = context.getAssets().open(TESS_DATA_DIR + TESS_TRAINED_DATA);
            OutputStream outStream = new FileOutputStream(filePath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, read);
            }
            outStream.flush();
            outStream.close();
            inputStream.close();

            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(Context context, Bitmap bitmap){
        final String DATA_PATH = context.getFilesDir().toString();

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(DATA_PATH, LANG);
        baseApi.setImage(bitmap);
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();

        return recognizedText;
    }

}

