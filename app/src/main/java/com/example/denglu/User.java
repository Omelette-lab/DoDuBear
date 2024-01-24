package com.example.denglu;
import java.io.Serializable;
public class User implements Serializable{
    private int id;
    private String zhanghao;
    private String mima;
    private String phone;
    private int flag;
    public User(String zhanghao,String mima,String phone,int id,int flag){
        this.zhanghao=zhanghao;
        this.mima=mima;
        this.phone=phone;
        this.id=id;
        this.flag=flag;
    }
    public String getZhanghao(){
        return this.zhanghao;
    }
    public String getMima(){
        return  this.mima;
    }
    public String getPhone(){
        return this.phone;
    }
    public int getId(){
        return this.id;
    }
    public int getFlag(){return this.flag;}

    //    覆写equals方法
    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(!(obj instanceof User)){
            return false;
        }
        User per=(User)obj;
        return this.zhanghao.equals(per.zhanghao)&&this.phone.equals(per.phone);//返回的数据就是要判断的属性
    }
}
