package com.example.denglu;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter <VideoAdapter.ViewHolder>{
    private List<Video> videolist;
    private String account;
    public VideoAdapter(List<Video>videolist,String account){
        this.videolist=videolist;
        this.account=account;
    }
    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);
       final ViewHolder holder=new ViewHolder(view);
       holder.videoview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int position=holder.getAdapterPosition();
               Video video=videolist.get(position);
               String video_name=video.getVideName();
               String video_Url=video.getVideoUrl();
               Intent intent=new Intent(view.getContext(),Play.class);
               intent.putExtra("video_name",video_name);
               intent.putExtra("video_Url",video_Url);
               intent.putExtra("name",account);
               view.getContext().startActivity(intent);
           }
       });
       return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder,int position){
        Video video=videolist.get(position);
        holder.vi.setImageResource(video.getIcon());
        holder.video_name.setText(video.getVideName());

    }
    @Override
    public int getItemCount(){
        return videolist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView video_name;
        ImageView vi;
        View videoview;
        public ViewHolder(@NonNull View view){
            super(view);
            video_name=view.findViewById(R.id.video_name);
            vi=view.findViewById(R.id.video_icon);
            videoview=view;

        }
    }
}

