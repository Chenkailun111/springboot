package com.itqf.dtboot.anno;

@Deprecated //标记类方法已经过时
public class MyAnno {

    public  void sayHi(){
        System.out.println("hi");
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        String name="尼古拉斯.找死";
        MyAnno myAnno = new MyAnno();
        myAnno.sayHi();
        System.out.println();
    }



}
