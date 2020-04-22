package com.abhijit.videostreamingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoShow extends AppCompatActivity {
    VideoView myVideoView;
    Button signin;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
        myVideoView = this.findViewById(R.id.videofox1);
       // signin=this.findViewById(R.id.button2);

        Intent intent=getIntent();
        String video_url= intent.getStringExtra("Video_url");

        System.out.println("Abhijit Video URL comming here is   >>>>>>>>>>>>>>"+video_url);


        MediaController mc = new MediaController(this);
        myVideoView.setMediaController(mc);
        final String urlStream =video_url;// "https://firebasestorage.googleapis.com/v0/b/fir-video-867f4.appspot.com/o/video%2F7896.mp4?alt=media&token=c82c6995-81a6-48ed-ad39-e7cbb7e3616d";
        myVideoView.start();
        myVideoView.findFocus();
        runOnUiThread(new Runnable() { @Override public void run() { myVideoView.setVideoURI(Uri.parse(urlStream)); } });

    }
}
