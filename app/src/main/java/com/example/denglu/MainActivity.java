package com.example.denglu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.denglu.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     private Button zhuce,denglv,wangji;
     private EditText zhanghao,mima;
     private CheckBox check;
     private Db_connect db_connect;
     private SQLiteDatabase db;
     private ArrayList<User> users=new ArrayList<User>();
     private int check_flag=0;//判断checkbox是否点击 1点击
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zhuce=(Button)findViewById(R.id.zhuce);
        denglv=(Button)findViewById(R.id.denglv);
        zhanghao=(EditText) findViewById(R.id.zhanghao);
        mima=(EditText) findViewById(R.id.mima);
        wangji=(Button)findViewById(R.id.wangji);
        check=(CheckBox)findViewById(R.id.check);
        createdb();

        zhanghao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   mima.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("check","afterTextChanged");
                edmi();
            }
        });
    }
    //初始化数据库
    public void createdb(){
        db_connect=new Db_connect(MainActivity.this);
        db=db_connect.getWritableDatabase();
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
        for(int i=0;i<this.users.size();i++){
            Log.i("user","第"+i+"条数据"+this.users.get(i).getZhanghao()+" "+this.users.get(i).getMima()+" "+this.users.get(i).getFlag());
        }

    }
    //记住密码
    public void check(View v){
        if(this.check_flag==1){
            this.check_flag=0;
        }
        if(this.check_flag==0){
            this.check_flag=1;
        }
    }
    //更新flag
    public void update_flag(){
        if(this.check_flag==1){
            String account=zhanghao.getText().toString();
            String password=mima.getText().toString();
            ContentValues values=new ContentValues();
            values.put("account",account);
            values.put("password",password);
            values.put("flag",1);//1为记住密码，0为不记住密码
            String where="account="+account+"and password="+password;
            int result=db.update("user",values,"account=? and password=?",new String[]{account,password});
            System.out.print("成功更新数据"+result+"条");
        }
    }
    //显示密码
    public void edmi(){
        String account=zhanghao.getText().toString();
        Cursor c=db.query("user",null,"account=?",new String[]{account},null,null,null,null);
        if(c.getCount()!=0){
           while (c.moveToNext()){
             if(c.getInt(c.getColumnIndex("flag"))==1){
                 mima.setText(c.getString(c.getColumnIndex("password")));
             }
           }
        }
    }
    public void denglv(View v){
        //取出所有用户信息
        select();
        String zhang=zhanghao.getText().toString();
        String mi=mima.getText().toString();
        if(zhang.length()!=0&&mima.length()!=0){
            for(int i=0;i<this.users.size();i++){
                if(zhang.equals(this.users.get(i).getZhanghao())&& mi.equals(this.users.get(i).getMima())){
                     update_flag();
                     Toast.makeText(MainActivity.this,"欢迎账号为"+zhang+"登录成功",Toast.LENGTH_LONG).show();
                     Intent intent=new Intent();
                     intent.setClass(MainActivity.this,RecyclerViewActivity.class);
                     intent.putExtra("name",zhang);
                     startActivity(intent);
                     return;
                }


            }
            Toast.makeText(MainActivity.this, "账号或密码错误",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"账号或密码不能为空",Toast.LENGTH_LONG).show();
        }
    }
    public void zhuce(View v){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,zhuce_activity.class);
        startActivity(intent);
    }
    public void wangji(View v){
        //取出所有用户信息
        select();
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,wangjimima.class);
        intent.putExtra("users",(Serializable) this.users);
        startActivityForResult(intent,300);
    }
}
