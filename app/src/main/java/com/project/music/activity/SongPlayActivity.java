package com.project.music.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.music.R;

import java.util.ArrayList;

public class SongPlayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    RelativeLayout rLay;
    ImageView imgShuffle,imgPlayPrev,imgPlayNext,imgPlay,imgLoop;
    TextView txtSongName,txtStTime,txtEndTime;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Thread updateSeek;
    int pos=0;
    Uri uri;
    Handler timeThread=new Handler();
    ArrayList songs;
    String[] items;
    String currSong;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);

        rLay = findViewById(R.id.rLay);
        imgLoop = findViewById(R.id.imgLoopWhite);
        imgPlay = findViewById(R.id.imgPlay);
        imgPlayNext = findViewById(R.id.imgPlayNext);
        imgPlayPrev = findViewById(R.id.imgPlayPrev);
        imgShuffle = findViewById(R.id.imgShuffle);
        seekBar = findViewById(R.id.seekBar);
        txtSongName = findViewById(R.id.txtSongName);
        txtEndTime = findViewById(R.id.txtEndTime);
        txtStTime = findViewById(R.id.txtStTime);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xff270d36, 0xff4e1741, 0xff74204d, 0xff9d2a58});

        rLay.setBackground(gradientDrawable);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pos = bundle.getInt("currPos");
        songs = (ArrayList) bundle.getParcelableArrayList("allSongs");
        items = bundle.getStringArray("items");
        currSong = bundle.getString("currSong");
        txtSongName.setText(currSong);
        uri = Uri.parse(songs.get(pos).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });
        seekBarAndTimeUpdate();

        imgPlay.setOnClickListener(l->{
            if(mediaPlayer.isPlaying()){
                imgPlay.setImageResource(R.drawable.ic_play);
                mediaPlayer.pause();
            }
            else{
                imgPlay.setImageResource(R.drawable.ic_pause);
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(this);
        imgPlayNext.setOnClickListener(l->{
            mediaPlayer.stop();
            mediaPlayer.release();
            pos=(pos==(songs.size()-1))?0:pos+1;
            uri=Uri.parse(songs.get(pos).toString());
            mediaPlayer=MediaPlayer.create(getApplication(),uri);
            txtSongName.setText(items[pos]);
            seekBar.setMax(mediaPlayer.getDuration());
            seekBarAndTimeUpdate();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        });
        imgPlayPrev.setOnClickListener(l->{
            mediaPlayer.stop();
            mediaPlayer.release();
            pos=(pos==0)?(songs.size()-1):pos-1;
            uri=Uri.parse(songs.get(pos).toString());
            mediaPlayer=MediaPlayer.create(getApplication(),uri);
            txtSongName.setText(items[pos]);
            seekBar.setMax(mediaPlayer.getDuration());
            seekBarAndTimeUpdate();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        });
        imgLoop.setOnClickListener(l->{
           if(!mediaPlayer.isLooping()){
               imgLoop.setImageResource(R.drawable.ic_loopon);
               mediaPlayer.setLooping(true);
           }
           else{
               imgLoop.setImageResource(R.drawable.ic_loop);
               mediaPlayer.setLooping(false);
           }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        imgPlayNext.performClick();
    }

    private void seekBarAndTimeUpdate(){
        String endTime1=showTime(mediaPlayer.getDuration());
        txtEndTime.setText(endTime1);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            int currpos=0;
            @Override
            public void run() {
                    currpos=mediaPlayer.getCurrentPosition();
                    String curr=showTime(currpos);
                    seekBar.setProgress(currpos);
                    txtStTime.setText(curr);
                    handler.postDelayed(this,1000);
            }
        },1000);

    }
    private String showTime(int dur){
        String time="";
        int min=dur/1000/60;
        int sec=dur/1000%60;
        time+=min+":";
        if(sec<10) time+="0";
        time+=sec;
        return time;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }



}