package com.gec.controller;
  
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

public class R extends HashMap {
  final static int STATUS_OK = 200;
  final static int STATUS_ERR = 500;
  final static int STATUS_NOT_FOUND = 404;
  
  // 静态ObjectMapper实例，避免重复创建
  private static final ObjectMapper objectMapper = new ObjectMapper();
  
  static {
    // 配置ObjectMapper
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  public static R ok(){
    R r = new R();
    r.put("result","success");
    r.put("status",STATUS_OK);
    return r;
  }
  public static R ok(Object obj){
    R r = new R();
    r.put("result","success");
    r.put("status",STATUS_OK);
    r.put("data",obj);
    return r;
  }
  public static R ok(Page page){
    R r = new R();
    r.put("result","success");
    r.put("status",STATUS_OK);
    r.put("data",page.getRecords());
    r.put("total",page.getTotal());
    return r;
  }

  public R put(String key, Object val){
    super.put( key , val);
    return this;
  }

  public static R err(Exception e) {
    R r = new R();
    r.put("result","failed");
    r.put("status",STATUS_ERR);
    r.put("cause",e.getMessage());
    return r;
  }

  public static R convertPage(IPage page) {
    R r = new R();
    r.put("result","success");
    r.put("status",STATUS_OK);
    r.put("data",page.getRecords());
    r.put("total",page.getTotal());
    return r;
  }

  public void write(HttpServletResponse resp) {
    Object status = get("status");
    if (status != null) {
      resp.setStatus((int)status);
    }
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json;charset=UTF-8");
    try (
      PrintWriter out = resp.getWriter();
    ) {
      out.write(toJson());
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private String toJson(){
    try {
      return objectMapper.writeValueAsString(this);
    } catch (Exception e) {
      e.printStackTrace();
      return "{\"result\":\"failed\",\"status\":500,\"cause\":\"JSON序列化错误\"}";
    }
  }
}
