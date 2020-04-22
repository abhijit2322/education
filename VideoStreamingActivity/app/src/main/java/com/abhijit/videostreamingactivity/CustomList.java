package com.abhijit.videostreamingactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ImageView thumView = (ImageView) rowView.findViewById(R.id.img_thumnail);

        if(web[position]!=null) {
            txtTitle.setText(web[position]);
            imageView.setImageResource(imageId[position]);
            loadVideoThumbnail(rowView,web[position]);
        }
        return rowView;
    }

    public void loadVideoThumbnail(View v, String videoFileUri)
    {
       // videoFileUri= "https://firebasestorage.googleapis.com/v0/b/fir-video-867f4.appspot.com/o/video%2F7896.mp4?alt=media&token=c82c6995-81a6-48ed-ad39-e7cbb7e3616d";
        //videoFileUri= "https://firebasestorage.googleapis.com/v0/b/fir-video-867f4.appspot.com/o/video%2F7896.mp4";
        videoFileUri="https://www.youtube.com/watch?v=QuR969uMICM";

        if (videoFileUri != null) {

            Bitmap bitmapThumb = null;
            try {
                bitmapThumb = ThumbnailUtils.createVideoThumbnail(videoFileUri,
                        MediaStore.Video.Thumbnails.MICRO_KIND);// MINI_KIND, size: 512 x 384 thumbnail | MICRO_KIND, size: 96 x 96 thumbnail
                if((bitmapThumb==null))
                    System.out.println("THIS IS NULL bitmapThumb>>>>>>>>>>>>>>>>>>>>>");
                ImageView imageView = (ImageView) v.findViewById(R.id.img_thumnail);
//                            imageView.setImageBitmap(bitmapThumb);

               // Uri uri = getImageUri(context, bitmapThumb);
/*
                Picasso.with(context)
                        .load(uri)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .resize(350, 200)//as per need.
                        .centerCrop()
                        .into(imageView);*/
                if(v.getContext()==null)
                    System.out.println("THIS IS NULL v.getContext()>>>>>>>>>>>>>>>>>>>>>");

/*
                Uri uri = getImageUri(v.getContext(), bitmapThumb);
                Glide.with(v.getContext()).
                        load(uri).
                        thumbnail(0.1f).
                        into(imageView);
*/
                Glide.with(v.getContext())
                        .asBitmap()
                        .load(videoFileUri)
                        .thumbnail(0.1f)
                        .into(imageView);



            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bitmapThumb = null;
            }
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.parse("");
        }

    }

}
