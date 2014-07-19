package com.keqiaokeji.module.httpsdk.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端返回信息类型
 * @param <T>
 */
public class JsonResultTest<T> {


    private List<T> dataRows = new ArrayList<T>();

    public List<T> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<T> dataRows) {
        this.dataRows = dataRows;
        System.out.print("123");
    }
}
