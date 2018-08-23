package com.aviator.elearning.aviator.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.aviator.elearning.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class General {

    public static void ReadImg(View target, Context ctx) {
        if (target instanceof CircleImageView || target instanceof ImageView) {
            String x = Environment.getExternalStorageState();
            if (x.equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {

                File file = new File(Environment.getExternalStorageDirectory() + "/Elearning/profile.jpg");
                if (!file.exists()) {
                    if (target instanceof CircleImageView) {
                        ((CircleImageView) target).setImageResource(R.mipmap.picholder);
                        return;
                    }
                    ((ImageView) target).setImageResource(R.mipmap.picholder);
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                profile.setImageBitmap(bitmap);
                if (target instanceof CircleImageView) {
//                    Glide.with(ctx).load(file).placeholder(R.mipmap.no_image).into((CircleImageView) target);
                    ((CircleImageView) target).setImageBitmap(bitmap);
                    return;
                }
//                Glide.with(ctx).load(file).placeholder(R.mipmap.no_image).into((ImageView) target);
                ((ImageView) target).setImageBitmap(bitmap);


            } else {
                try {
                    FileInputStream fileInputStream = ctx.openFileInput("profile.jpg");
//                Glide.with(getContext()).load
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    if (bitmap != null) {
                        if (target instanceof CircleImageView) {
                            ((CircleImageView) target).setImageBitmap(bitmap);
                            return;
                        }
                        ((ImageView) target).setImageBitmap(bitmap);
                    }
                } catch (FileNotFoundException e) {
                    if (target instanceof CircleImageView) {
                        ((CircleImageView) target).setImageResource(R.mipmap.picholder);
                        return;
                    }
                    ((ImageView) target).setImageResource(R.mipmap.picholder);
                }
            }
        }
    }

    public static void LoadWriteImgFromServer(Uri uri, Context context) {

        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(uri.getPath()));
            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            WriteImg(bitmap, context);
        } catch (FileNotFoundException ignored) {
        }

    }

    public static void WriteImg(Bitmap bitmap, Context context) {

        if (bitmap == null) {
            return;
        }

        String x = Environment.getExternalStorageState();
        if (x.equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory() + "/Elearning");

            if (!file.exists()) {
                file.mkdirs();
            }

            try {
//                InputStreamReader inputStreamReader = new InputStreamReade;
                FileOutputStream fileOutputStream = new FileOutputStream(file + "/profile.jpg");
                try {
                    fileOutputStream.write(ConvertBitmap(bitmap));
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException ignored) {

                }
            } catch (FileNotFoundException ignored) {
            }
        } else {
            try {
                FileOutputStream fileOutputStream = context.openFileOutput("profile.jpg", Context.MODE_PRIVATE);

                fileOutputStream.write(ConvertBitmap(bitmap));
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException ignored) {

            } catch (IOException ignored) {

            }
        }

    }

    private static byte[] ConvertBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
//        final String image_data= Base64.encodeToString(bitData,Base64.DEFAULT);
    }
}
