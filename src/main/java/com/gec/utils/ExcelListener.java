package com.gec.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.gec.domain.excel.ExcelUser;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelListener implements ReadListener<ExcelUser> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<ExcelUser> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 所有读取到的数据
     */
    private List<ExcelUser> allData = new ArrayList<>();

    /**
     * 每读一行，会调用该方法
     */
    @Override
    public void invoke(ExcelUser data, AnalysisContext context) {
        log.info("解析到一条数据: {}", JSON.toJSONString(data));
        dataList.add(data);
        allData.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= BATCH_COUNT) {
            // 实际项目中这里应该调用service层进行数据处理
            // 存储完成清理 list
            dataList.clear();
        }
    }

    /**
     * 所有数据解析完成了，都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 确保最后遗留的数据也被处理
        if (!dataList.isEmpty()) {
            // 实际项目中这里应该调用service层进行数据处理
            dataList.clear();
        }
        log.info("所有数据解析完成！总条数：{}", allData.size());
    }

    /**
     * 获取所有解析到的数据
     */
    public List<ExcelUser> getAllData() {
        return allData;
    }
}