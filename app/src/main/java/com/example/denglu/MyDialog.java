package com.example.denglu;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.content.Context;

import java.sql.DatabaseMetaData;

public class MyDialog extends Dialog {
    public interface DataBackListener{
        public void getData(int Data);
    }
    private ImageView img0,img1,img2,img3,img4,img5,img6,img7,img8,img9;
    DataBackListener listener;
    public MyDialog(Context context,final DataBackListener listener){
         super(context);
         this.listener=listener;
         setContentView(R.layout.icon_item);

         img0=(ImageView)findViewById(R.id.cat);
         img1=(ImageView)findViewById(R.id.fawn);
         img2=(ImageView)findViewById(R.id.siberiankusky);
         img3=(ImageView)findViewById(R.id.tiger);
         img4=(ImageView)findViewById(R.id.timg);
         img5=(ImageView)findViewById(R.id.timg_one);
         img6=(ImageView)findViewById(R.id.timg_two);
         img7=(ImageView)findViewById(R.id.timg_three);
         img8=(ImageView)findViewById(R.id.timg_four);
         img9=(ImageView)findViewById(R.id.yellowduck);

         this.setListener();
    }
    public void setListener(){
        img0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.cat);
                dismiss();
            }
        });
        img1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.fawn);
                dismiss();
            }
        });
        img2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.siberiankusky);
                dismiss();
            }
        });
        img3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.tiger);
                dismiss();
            }
        });
        img4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.timg);
                dismiss();
            }
        });
        img5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.timg_one);
                dismiss();
            }
        });
        img6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.timg_two);
                dismiss();
            }
        });
        img7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.timg_three);
                dismiss();
            }
        });
        img8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.timg_four);
                dismiss();
            }
        });
        img9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.getData(R.mipmap.yellowduck);
                dismiss();
            }
        });

    }
}
