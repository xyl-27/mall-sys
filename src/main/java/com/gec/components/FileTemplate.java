package com.gec.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileTemplate {
    //1.看情况打开下面这行注释
    //  将被赋值为 ： D:\GDOU
    @Value("${web.uploadDir}")
    private String DIR;

    private MultipartFile mFile;

    public FileTemplate(){}
    public FileTemplate(MultipartFile mfile){
        this.mFile = mfile;
    }
    public FileTemplate(String DIR){
        this.DIR = DIR;
    }

    /*          情况1        情况2
    * DIR      d:\space    d:\space\
    * 最终输出  d:\space    d:\space\
    */

    private String getPrefixPath(String DIR, String SUB){
        //1.先判断 DIR 是否以 \ 结尾, 没有就加上。
        if( !DIR.endsWith("\\") ){
            DIR = DIR +"\\";
        }
        if( SUB==null ) SUB = "";
        if( SUB.length()>0 ){
            //2.再判断 SUB 是否以 \ 结尾, 没有就加上。
            if( !SUB.endsWith("\\") ){
                SUB = SUB +"\\";
            }
        }
        return DIR + SUB;
    }

    //1.此方法用于获取文件的数据。
    // 注: 仅处理小文件。
    public byte[] getFile(String subDir, String fileName) throws IOException {
        //{1}获取前置路径 如: d:\GDOU
        String preFix = getPrefixPath(DIR, subDir);
        //{2}拼接完整路径 如: d:\GDOU\d01.png
        String path = preFix + fileName;
        File _file = new File(path);
        //{3}创建文件输入流。
        InputStream fis = new FileInputStream(_file);
        int len = fis.available();
        byte[] buff = new byte[len];
        //{4}读取文件。
        fis.read(buff);
        fis.close();
        //{5}返回字节数据。
        return buff;
    }

    public void setMultipartFile(MultipartFile mFile){
        this.mFile = mFile;
    }

    public boolean saveFile(String subDir, String fileName) throws IOException {
        //{1}获取前置路径 如: d:\GDOU
        String preFix = getPrefixPath(DIR, subDir);
        //{2}拼接完整路径 如: d:\GDOU\d01.png
        String path = preFix + fileName;
        //{3}创建 file 对象。
        File _target = new File(path);
        //{4}此方法可将 mFile 中数据写到本地磁盘。
        //   mFile 中包含有文件上传的数据。
        this.mFile.transferTo( _target );
        return true;
    }

    public void printDir(){
        System.out.println("DIR:"+ DIR);
    }

}

