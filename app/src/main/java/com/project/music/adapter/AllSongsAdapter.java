
package com.project.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.music.R;
import com.project.music.activity.SongPlayActivity;

import java.io.File;
import java.util.ArrayList;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.HomeViewHolder> {
    ArrayList<File> listSongs;
    String[] songs;
    Context context;
    public AllSongsAdapter(Context context, ArrayList<File> listSongs,String[] songs) {
        this.listSongs=listSongs;this.context =context;this.songs=songs;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_single_row,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
           holder.txtTrackTitle.setText(songs[position]);
           holder.txtTrackTitle.setOnClickListener(v -> {
               Intent intent=new Intent(context, SongPlayActivity.class);
               intent.putExtra("items",songs);
               intent.putExtra("allSongs",listSongs);
               intent.putExtra("currSong",songs[position]);
               intent.putExtra("currPos",position);
               context.startActivity(intent);
           });
    }

    @Override
    public int getItemCount() {
        return listSongs.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        ScrollView scrollView;
        TextView txtTrackTitle;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtTrackTitle=itemView.findViewById(R.id.txtTrackTitle);
            this.scrollView=itemView.findViewById(R.id.scrollview);
        }
    }
}
