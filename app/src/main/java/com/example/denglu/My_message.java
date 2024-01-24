package com.example.denglu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class My_message extends AppCompatActivity {
    private String username;
    private Db_connect db_connect;
    private SQLiteDatabase db;
    private TextView t_account,t_password,t_phone;
    private ImageView t_icon,i_todolist,vi;
    private Button udpate,back,save;
    private String account,password,phone;
    private int current_icon=0;//当前图片
    private int icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        t_account=findViewById(R.id.account);
        t_password=findViewById(R.id.password);
        t_phone=findViewById(R.id.phone);
        t_icon=findViewById(R.id.icon);
        udpate=findViewById(R.id.update);
        back=findViewById(R.id.back);
        i_todolist=findViewById(R.id.i_todolist);
        save=findViewById(R.id.save);
        vi=findViewById(R.id.vi);
        Intent intent=getIntent();
        this.username=intent.getStringExtra("name");
        createdb();
        select();
    }
   //初始化数据库
    public void createdb(){
        this.db_connect=new Db_connect(My_message.this);
       this.db=db_connect.getWritableDatabase();
    }
    //查找数据
    public void select(){
        Cursor c=db.query("user",null,"account=?",new String[]{this.username},null,null,null);
        if(c.getCount()!=0){
            while (c.moveToNext()){
                account=c.getString(c.getColumnIndex("account"));
                password=c.getString(c.getColumnIndex("password"));
                phone=c.getString(c.getColumnIndex("phone"));
                icon=c.getInt(c.getColumnIndex("icon"));
            }
        }
        show(account,password,phone,icon);
    }
    //更新数据
    public void save(View v){
        ContentValues values=new ContentValues();
        values.put("account",this.account);
        values.put("password",this.password);
        values.put("phone",this.phone);
        values.put("icon",this.current_icon);
        int result=db.update("user",values,"account=?",new String[]{account});
        System.out.print("成功更新数据"+result+"条");
        Toast.makeText(My_message.this,"修改成功",Toast.LENGTH_LONG).show();
    }
    //显示数据
    public void show(String account,String password,String phone,int icon){
         t_icon.setImageResource(icon);
         t_account.setText(account);
         t_password.setText(password);
         t_phone.setText(phone);
    }
    //设置图片到保存页面上
    public void saveimg_view(int data){
        switch (data){
            case R.mipmap.cat:
                t_icon.setImageResource(R.mipmap.cat);
                break;
            case R.mipmap.fawn:
                t_icon.setImageResource(R.mipmap.fawn);
                break;
            case R.mipmap.siberiankusky:
                t_icon.setImageResource(R.mipmap.siberiankusky);
                break;
            case R.mipmap.tiger:
                t_icon.setImageResource(R.mipmap.tiger);
                break;
            case R.mipmap.timg:
                t_icon.setImageResource(R.mipmap.timg);
                break;
            case R.mipmap.timg_one:
                t_icon.setImageResource(R.mipmap.timg_one);
                break;
            case R.mipmap.timg_two:
                t_icon.setImageResource(R.mipmap.timg_two);
                break;
            case R.mipmap.timg_three:
                t_icon.setImageResource(R.mipmap.timg_three);
                break;
            case R.mipmap.timg_four:
                t_icon.setImageResource(R.mipmap.timg_four);
                break;
            case R.mipmap.yellowduck:
                t_icon.setImageResource(R.mipmap.yellowduck);
                break;

        }
    }
    //修改头像
    public void update(View v){
        MyDialog dialog=new MyDialog(My_message.this,new MyDialog.DataBackListener(){
            @Override
            public void getData(int data) {
                current_icon=data;
                saveimg_view(data);
            }
        });
        dialog.show();
    }
    //休息区
    public void vi(View v){
        Intent intent=new Intent();
        intent.setClass(My_message.this, RestArea.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }

    //返回
    public void back(View v){
        Intent intent=new Intent();
        intent.putExtra("name",this.account);
        intent.setClass(My_message.this,RecyclerViewActivity.class);
        startActivity(intent);
    }
}
