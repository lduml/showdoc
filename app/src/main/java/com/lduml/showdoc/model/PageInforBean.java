package com.lduml.showdoc.model;

import java.util.Date;

public class PageInforBean {

    private int error_code;
    private PageInforBeanData data;
    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    public int getError_code() {
        return error_code;
    }

    public void setData(PageInforBeanData data) {
        this.data = data;
    }
    public PageInforBeanData getData() {
        return data;
    }

    @Override
    public String toString() {
        return "PageInforBean{" +
                "error_code=" + error_code +
                ", data=" + data +
                '}';
    }
}