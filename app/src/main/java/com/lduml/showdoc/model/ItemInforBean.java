/**
  * Copyright 2018 bejson.com 
  */
package com.lduml.showdoc.model;

/**
 * Auto-generated: 2018-09-16 8:48:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ItemInforBean {

    private int error_code;
    private Data data;
    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    @Override
    public String toString() {
        return "ItemInforBean{" +
                "error_code=" + error_code +
                ", data=" + data +
                '}';
    }
}