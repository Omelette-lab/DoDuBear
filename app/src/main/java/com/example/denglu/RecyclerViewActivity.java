package com.example.denglu;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private ArrayList<ToDoList>todolists=new ArrayList<ToDoList>();
    private Button add;
    private ImageView my ,v;
    private Db_connect db_connect;
    private SQLiteDatabase db;
    private String username;
    private int current_position;//当前编辑的对象下标

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        create_db(); //初始化数据库
        select(); //获取数据
        mRecyclerView=findViewById(R.id.rec);
        add=(Button) findViewById(R.id.add);
        my=(ImageView) findViewById(R.id.my_message);
        v=(ImageView)findViewById(R.id.vi);
        shuaxing();
        Intent intent=getIntent();
        this.username=intent.getStringExtra("name");
        Log.i("RecyclerViewActivity",this.username);
    }
    //刷新
    public void shuaxing(){
        //设置布局方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        mHomeAdapter=new HomeAdapter();
        mRecyclerView.setAdapter(mHomeAdapter);
    }
    //数据库初始化
    public void create_db(){
        db_connect=new Db_connect(RecyclerViewActivity.this);
        db=db_connect.getWritableDatabase();
    }
    //读取数据
    public void select(){
        Cursor c=db.query("todolist",null,null,null,null,null,null);
        if(c.getCount()!=0){
            while (c.moveToNext()){
                String item_name=c.getString(c.getColumnIndex("item_name"));
                String deadline=c.getString(c.getColumnIndex("deadline"));
                int icon=c.getInt(c.getColumnIndex("icon"));
                this.todolists.add(new ToDoList(item_name,deadline,icon));
            }
        }
    }

    //    增加计划项目
    public void add(View v){
        Intent intent=new Intent();
        intent.setClass(RecyclerViewActivity.this,add.class);
        intent.putExtra("flag","2");
        intent.putExtra("name",this.username);
        startActivityForResult(intent,100);
    }
    //我的个人信息
    public void my_message(View v){
        Intent intent=new Intent();
        intent.setClass(RecyclerViewActivity.this,My_message.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }
    //休息区
    public void vi(View v){
        Intent intent=new Intent();
        intent.setClass(RecyclerViewActivity.this, RestArea.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }
    //倒计时
    public void clock(View v){
        Intent intent=new Intent();
        intent.setClass(RecyclerViewActivity.this, TomaClock.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyViewHolder holder=new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_recycler_view,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.tvItem_name.setText(todolists.get(position).getItem_name());
            holder.tvDeadline.setText(todolists.get(position).getDeadline());
            holder.iv_icon.setImageResource(todolists.get(position).getIcon());
            holder.edit.getTag();
            holder.del.getTag();
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    current_position=position;//保存当前编辑下标
                    //获取数据
                    String click_itemName=todolists.get(position).getItem_name();
                    String click_deadline=todolists.get(position).getDeadline();
                    int click_icon=todolists.get(position).getIcon();
                    ToDoList todolist=new ToDoList(click_itemName,click_deadline,click_icon);
                    //跳转
                    Intent intent=new Intent();
                    intent.setClass(RecyclerViewActivity.this,add.class);
                    intent.putExtra("todolist",(Serializable) todolist);
                    intent.putExtra("name",username);
                    intent.putExtra("flag","1");//是否为编辑功能
                    startActivity(intent);
                }
            });
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String click_itemName=todolists.get(position).getItem_name();
                    String deadline=todolists.get(position).getDeadline();
                    int result= db.delete("todolist","item_name=? and deadline=?",new String[]{click_itemName,deadline});
                    Toast.makeText(RecyclerViewActivity.this,"恭喜您完成任务",Toast.LENGTH_LONG).show();
                    todolists.clear();//清空
                    select();
                    shuaxing();
                }
            });
        }

        @Override
        public int getItemCount() {
            return todolists.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tvItem_name,tvDeadline;
            ImageView iv_icon;
            Button edit,del;
            public MyViewHolder(@NonNull View itemView){
                super(itemView);
                tvItem_name=itemView.findViewById(R.id.tvItem_name);
                tvDeadline=itemView.findViewById(R.id.tvDeadline);
                iv_icon=itemView.findViewById(R.id.iv_icon);
                edit=itemView.findViewById(R.id.edit);
                del=itemView.findViewById(R.id.del);
            }
        }
    }

}
