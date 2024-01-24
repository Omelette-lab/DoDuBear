package com.example.denglu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class add extends AppCompatActivity {
    private EditText item_name,deadline;
    private Button select_icon,save,back;
    private ImageView icon;
    private ToDoList todolist;
    private TextView head;
    private String flag;
    private int current_icon;//当前图片
    private String old_item;//老名字
    private Db_connect db_connect;
    private SQLiteDatabase db;
    private String account;//账号
    private ArrayList<ToDoList>todolists=new ArrayList<ToDoList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        item_name=(EditText)findViewById(R.id.item_name);
        deadline=(EditText)findViewById(R.id.deadline);
        select_icon=(Button)findViewById(R.id.select_icon);
        save=(Button)findViewById(R.id.save);
        back=(Button)findViewById(R.id.back);
        icon=(ImageView)findViewById(R.id.icon);
        head=(TextView)findViewById(R.id.head);

        create_db();//初始化数据库

        Intent intent=new Intent();
        this.todolist=(ToDoList) getIntent().getSerializableExtra("todolist");
        this.flag=(String)getIntent().getStringExtra("flag");
        this.account=getIntent().getStringExtra("name");
        Log.i("account",this.account);
        if(this.flag.equals("1")){
            item_name.setText(this.todolist.getItem_name());
            this.old_item=this.todolist.getItem_name();
            this.current_icon=this.todolist.getIcon();
            deadline.setText(this.todolist.getDeadline());
            icon.setImageResource(this.todolist.getIcon());
            head.setText("编辑任务项");
        }
    }
    public void select_icon(View v){
        MyDialog dialog=new MyDialog(add.this,new MyDialog.DataBackListener(){
            @Override
            public void getData(int data) {
                 current_icon=data;
                 saveimg_view(data);
            }
        });
        dialog.show();
    }
    //数据库初始化
    public void create_db(){
        db_connect=new Db_connect(add.this);
        db=db_connect.getWritableDatabase();
    }
    //增加用户
    public void insert(ToDoList toDoList){
        ContentValues values=new ContentValues();
        values.put("item_name",toDoList.getItem_name());
        values.put("deadline",toDoList.getDeadline());
        values.put("icon",toDoList.getIcon());
        long result=db.insert("todolist",null,values);
        System.out.println("插入数据成功,当前第"+result+"条数据");
    }
    //更新数据
    public void update(ToDoList todolist){
          ContentValues values=new ContentValues();
          values.put("item_name",todolist.getItem_name());
          values.put("deadline",todolist.getDeadline());
          values.put("icon",todolist.getIcon());
          int result=db.update("todolist",values,"item_name=?",new String[]{old_item});
          System.out.print("成功更新数据"+result+"条");
    }
    //查找所有任务项
    public void select(){
        Cursor c=db.query("todolist",null,null,null,null,null,null);
        if(c.getCount()!=0){
            while (c.moveToNext()){
               String item_name=c.getString(c.getColumnIndex("item_name"));
               String deadline=c.getString(c.getColumnIndex("deadline"));
               int icon=c.getInt(c.getColumnIndex("icon"));
                todolists.add(new ToDoList(item_name,deadline,icon));
            }
        }
    }
    //判断任务表是否存在
    public boolean judge_user(String name){
        boolean flag=true;
        select();
        int len=this.todolists.size();
        for(int i=0;i<len;i++){
            if(this.todolists.get(i).getItem_name().equals(name)){
                return false;
            }
        }
       return flag;
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
    //返回
    public void back(View v){
        Intent intent=new Intent();
        intent.setClass(add.this,RecyclerViewActivity.class);
        intent.putExtra("name",this.account);
        startActivity(intent);
    }
    public void save(View v){
        String new_name=item_name.getText().toString();
        String new_deadline=deadline.getText().toString();
        int new_icon=this.current_icon;
        if(new_name.length()!=0||new_deadline.length()!=0){
            if(this.flag.equals("1")){//编辑
                if (new_deadline.equals(this.todolist.getDeadline())){
                    Toast.makeText(add.this,"新编辑的任务截止时间不能一致",Toast.LENGTH_LONG).show();
                }else{//保存数据返回
                    update(new ToDoList(new_name,new_deadline,new_icon));
                    Toast.makeText(add.this,"保存成功",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent();
                    intent.setClass(add.this,RecyclerViewActivity.class);
                    intent.putExtra("name",this.account);
                    startActivity(intent);
                }
            }else{//增加
                insert(new ToDoList(new_name,new_deadline,new_icon));
                Toast.makeText(add.this,"保存成功",Toast.LENGTH_LONG).show();
                Intent intent =new Intent();
                intent.putExtra("name",this.account);
                intent.setClass(add.this,RecyclerViewActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(add.this,"任务或截止时间不能为空",Toast.LENGTH_LONG).show();
        }

    }


}

