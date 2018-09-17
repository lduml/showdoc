/**
  * Copyright 2018 bejson.com 
  */
package com.lduml.oc.androidokhttpwithcookie.model;

/**
 * Auto-generated: 2018-09-16 8:48:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Pages {

    private String page_id;
    private String author_uid;
    private String cat_id;
    private String page_title;
    private String addtime;
    public void setPage_id(String page_id) {
         this.page_id = page_id;
     }
     public String getPage_id() {
         return page_id;
     }

    public void setAuthor_uid(String author_uid) {
         this.author_uid = author_uid;
     }
     public String getAuthor_uid() {
         return author_uid;
     }

    public void setCat_id(String cat_id) {
         this.cat_id = cat_id;
     }
     public String getCat_id() {
         return cat_id;
     }

    public void setPage_title(String page_title) {
         this.page_title = page_title;
     }
     public String getPage_title() {
         return page_title;
     }

    public void setAddtime(String addtime) {
         this.addtime = addtime;
     }
     public String getAddtime() {
         return addtime;
     }

    @Override
    public String toString() {
        return "Pages{" +
                "page_id='" + page_id + '\'' +
                ", author_uid='" + author_uid + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", page_title='" + page_title + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}