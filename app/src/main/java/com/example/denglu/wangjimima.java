package com.example.denglu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class wangjimima extends AppCompatActivity {
    private Button back_two,zhaohui;
    private EditText shouji,zhanghao;

    private ArrayList<User> users=new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangjimima);
        back_two=(Button)findViewById(R.id.back_two);
        zhaohui=(Button)findViewById(R.id.zhaohui);
        shouji=(EditText)findViewById(R.id.shouji);
        zhanghao=(EditText)findViewById(R.id.zhanghao);


        Intent intent=new Intent();
        this.users=(ArrayList<User>) getIntent().getSerializableExtra("users");
}
    public void back_two(View v){
        Intent intent=new Intent();
        intent.setClass(wangjimima.this,MainActivity.class);
        startActivity(intent);
    }
    public void zhaohui(View v){
        String zhang=zhanghao.getText().toString();
        String shou=shouji.getText().toString();
        String message="";
        if(zhang.length()!=0&&shou.length()!=0){
            User user=new User(zhang,"",shou,0,0);
            //判断是否存在
            Boolean flag=this.users.contains(user);
            if(flag==true){
                String mima=this.getmima(zhang,shou);
                Log.i("wangji", "Zhaohui"+mima);
                message="用户密码为"+mima;
            }else{
                Log.i("wangji","不存在");
                message="您输入的信息有误";
            }
            Toast.makeText(wangjimima.this,message,Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(wangjimima.this,"账号和密码不能为空",Toast.LENGTH_LONG).show();
        }
    }
    public String getmima(String zhang,String phone){
        String mima="";
        for (int i=0;i<this.users.size();i++){
            if(zhang.equals(this.users.get(i).getZhanghao())&&phone.equals(this.users.get(i).getPhone())){
                mima=this.users.get(i).getMima();
            }
        }
        return mima;
    }
}
