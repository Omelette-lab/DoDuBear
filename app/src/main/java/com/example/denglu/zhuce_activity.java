package com.example.denglu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class zhuce_activity extends AppCompatActivity {
    private Button back,reset,save;
    private ImageView icon;
    private EditText zhanghao,mima_one,mima_two,phone;
    private String content;
    private ArrayList<User> users=new ArrayList<User>();
    private Db_connect db_connect;
    private SQLiteDatabase db;
    private int current_icon=0;//当前图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        back=(Button)findViewById(R.id.back);
        reset=(Button)findViewById(R.id.reset);
        zhanghao=(EditText)findViewById(R.id.zhanghao);
        mima_one=(EditText)findViewById(R.id.mima_one);
        mima_two=(EditText)findViewById(R.id.mima_two);
        phone=(EditText)findViewById(R.id.phone);
        save=(Button)findViewById(R.id.save);
        icon=(ImageView)findViewById(R.id.icon);
        create_db();

    }
    //设置图片到保存页面上
    public void saveimg_view(int data){
        switch (data){
            case R.mipmap.cat:
                icon.setImageResource(R.mipmap.cat);
                break;
            case R.mipmap.fawn:
                icon.setImageResource(R.mipmap.fawn);
                break;
            case R.mipmap.siberiankusky:
                icon.setImageResource(R.mipmap.siberiankusky);
                break;
            case R.mipmap.tiger:
                icon.setImageResource(R.mipmap.tiger);
                break;
            case R.mipmap.timg:
                icon.setImageResource(R.mipmap.timg);
                break;
            case R.mipmap.timg_one:
                icon.setImageResource(R.mipmap.timg_one);
                break;
            case R.mipmap.timg_two:
                icon.setImageResource(R.mipmap.timg_two);
                break;
            case R.mipmap.timg_three:
                icon.setImageResource(R.mipmap.timg_three);
                break;
            case R.mipmap.timg_four:
                icon.setImageResource(R.mipmap.timg_four);
                break;
            case R.mipmap.yellowduck:
                icon.setImageResource(R.mipmap.yellowduck);
                break;

        }
    }
    public void select_icon(View v){
        MyDialog dialog=new MyDialog(zhuce_activity.this,new MyDialog.DataBackListener(){
            @Override
            public void getData(int data) {
                current_icon=data;
                saveimg_view(data);
            }
        });
        dialog.show();
    }
    //数据库的初始化
    public void create_db(){
        db_connect=new Db_connect(zhuce_activity.this);
        db=db_connect.getWritableDatabase();//可写
    }
    public void back(View v){
        Intent intent=new Intent();
        intent.setClass(zhuce_activity.this,MainActivity.class);
        startActivity(intent);
    }
    public void reset(View v){
        zhanghao.setText("");
        mima_one.setText("");
        mima_two.setText("");
        phone.setText("");
    }
    public  void save(View v){
        this.content="";
        String zhang=zhanghao.getText().toString();
        String mima_o=mima_one.getText().toString();
        String mima_t=mima_two.getText().toString();
        String ph=phone.getText().toString();
        if(zhang.length()!=0||mima_o.length()!=0||ph.length()!=0){
            if(zhang.length()!=0&&ph.length()!=0){
                if(zhang.equals(ph)){
                    this.content=this.content+"手机和账号不能一致";
                    zhanghao.setText("");
                    phone.setText("");
                }
            }
            if(!mima_o.equals(mima_t)){
                this.content=this.content+"  两次密码不一致";
                mima_one.setText("");
                mima_two.setText("");
            }
        }
        if(zhang.length()==0){
            this.content=this.content+"  账号不能为空";
        }
        if(mima_o.length()==0){
            this.content=this.content+"  密码不能为空";
        }
        if(ph.length()==0){
            this.content=this.content+"  手机号不能为空";
        }
        if(!this.judge_account(zhang)){
            this.content=this.content+"   账号已经存在";
        }
        if(this.current_icon==0){
            this.content=this.content+"   头像必须选择";
        }
        if(this.content.length()==0){
            this.content="注册成功";
            //插入数据
            insert(new User(zhang,mima_o,ph,1,0));

            Intent intent =new Intent();
            intent.setClass(zhuce_activity.this,MainActivity.class);
        }
        Toast.makeText(zhuce_activity.this,this.content,Toast.LENGTH_LONG).show();

    }
    //判断账号是否存在
    public boolean judge_account(String account){
        select();
        int len=this.users.size();
        for (int i=0;i<len;i++){
            if(account.equals(this.users.get(i).getZhanghao())){
                return false;
            }
        }
        return true;
    }
    //从数据库中读取用户
    public void select(){
        Cursor c=db.query("user",null,null,null,null,null,null);
        if(c.getCount()!=0){
            while (c.moveToNext()){
                int id=(int)c.getInt(c.getColumnIndex("_id"));
                String account=c.getString(c.getColumnIndex("account"));
                String password=c.getString(c.getColumnIndex("password"));
                String phone=c.getString(c.getColumnIndex("phone"));
                int flag=c.getInt(c.getColumnIndex("flag"));
                this.users.add(new User(account,password,phone,id,flag));//保存
            }
        }
    }
   //插入数据
    public void insert(User user){
        ContentValues values=new ContentValues();
        values.put("account",user.getZhanghao());
        values.put("password",user.getMima());
        values.put("phone",user.getPhone());
        values.put("flag",0);//1表示记住密码，0表示不记住密码
        values.put("icon",this.current_icon);
        long result=db.insert("user",null,values);
        System.out.println("插入数据成功,当前第"+result+"条数据");
    }
}
