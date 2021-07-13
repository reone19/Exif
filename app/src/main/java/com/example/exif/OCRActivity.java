package com.example.exif;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class OCRActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_CODE_PICK_CONTENT = 0;

    private OCR _ocr;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        findViewById(R.id.button).setOnClickListener(this);

        _ocr = new OCR(getApplicationContext());
    }

    @Override
    public void onClick(View v){
        if(v == findViewById(R.id.button)){
            Intent intent;
            if(Build.VERSION.SDK_INT < 19){
                intent = new Intent(Intent.ACTION_PICK);
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }else{
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
            }
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_CONTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_PICK_CONTENT){
            String ocrString;
            if(resultCode == RESULT_OK && data != null){
                Bitmap bitmap = null;
                if(Build.VERSION.SDK_INT < 19){
                    try{
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(in);
                        try{
                            if(in != null){ in.close(); }
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }else{
                    Uri uri = data.getData();
                    try{
                        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                        if(parcelFileDescriptor != null){
                            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            parcelFileDescriptor.close();
                        }

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
                if(bitmap != null){
                    ImageView imageView = (ImageView)findViewById(R.id.image);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setImageBitmap(bitmap);
                    ocrString = _ocr.getString(getApplicationContext(), bitmap);
                }else{
                    ocrString = "bitmap is null";
                }
            }else{
                ocrString = "something wrong?";
            }
            ((TextView)findViewById(R.id.OCRString)).setText(ocrString);
        }
    }

}
