package com.example.jobbook.upload;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.example.jobbook.util.Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadManager {

    public static Uri saveBitmap(Context context, Bitmap bm, String userAccount) {
        File file;
        if (Util.hasSDCard()) {
            File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.example.jobbook");
            if (!tmpDir.exists()) {
                tmpDir.mkdir();
            }
            file = new File(tmpDir.getAbsolutePath(), userAccount + "_userhead.jpeg");
        } else {
            file = new File(context.getFilesDir(), userAccount + "_userhead.jpeg");
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri convertUri(Context context, Uri uri, String userAccount) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(context, bitmap, userAccount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] compressBitmap(byte[] data, float size) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap == null) {
            return null;
        }
        if (getSizeOfBitmap(bitmap) <= size) {
            return data;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int quality = 100;
        while ((baos.toByteArray().length / 1024f) > size) {
            quality -= 5;// 每次都减少5(如果这里-=10，有时候循环次数会提前结束)
            baos.reset();// 重置baos即清空baos
            if (quality <= 0) {
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        byte[] byteData = baos.toByteArray();
        return byteData;
    }

    private static long getSizeOfBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100的话表示不压缩质量
        long length = baos.toByteArray().length / 1024;//读出图片的kb大小
        return length;
    }

}
