package com.gec.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
    static final int DEF_PAGE = 1;    //默认页码
    static final int DEF_LIMIT = 10;  //默认页大小。

    public <T> Page<T> newPage(Integer page, Integer limit){
        Page<T> _page = new Page<>(page, limit);
        return _page;
    }

    //{2}做一个数据校验, 不能为空, 不能 <= 0
    private Integer getInteger(Integer value, int defVal) {
        if( value==null || value.intValue()<=0 ){
            return defVal;
        }
        return value;
    }
    protected abstract FileTemplate getFileTemplate();

    Map<String,String> MIMES = new HashMap<>();
    protected String getMIME(String fileName){
        String extName = FileUtils.extName(fileName);
        String mime = MIMES.get(extName);
        return mime;
    }
    //mime 是设置在响应头部, 再来告之浏览器, 当前这个文件的格式。
    public BaseController(){
        MIMES.put(".jpg","image/jpeg");
        MIMES.put(".jpeg","image/jpeg");
        MIMES.put(".png","image/png");
        MIMES.put(".html","text/html");
		MIMES.put(".css","text/css");
		MIMES.put(".js","text/javascript");
    }

    /*
    *  MIME: 媒体内容类型 (告诉浏览器当前的文件内型)
    *  扩展名 MIME         浏览器行为
    *  jpg  image/jpeg    解析图片
    *  png  image/png     解析图片
    *  html text/html     解析网页
    *  mp4  mpeg/mp4      播放视频
    */
    //2.这个方法用来输出文件。
    protected void outFile(HttpServletResponse resp,
        String subDIR, String fileName)
        throws IOException {
        //1.获取文件操作模板类。
        FileTemplate temp = getFileTemplate();
        /*
         * 2.读取文件的数据。
         * {1}subDir: 子目录(如: brand)
         * {2}fileName: d01.png
         */
        byte[] data = temp.getFile(subDIR, fileName);
        //3.获取 MIME 类型名称
        String mime = getMIME(fileName);
        //4.把 MIME 设置到响应头。
        resp.setContentType(mime);
        //5.把文件数据输出响应对象缓冲区
        resp.getOutputStream().write( data );
    }

    /*
     * 操作步骤:
     * 1.在 D:\GDOU 放置一个文件:
     *   图片文件: 404_pic.png
     */
    protected void send404File(HttpServletResponse resp) {
        try {
            //1.设置 404 状态码。
            resp.setStatus(404);
            //2.调用 outFile() 输出文件。
            outFile(resp, "", "404_pic.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
