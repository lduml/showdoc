/**
  * Copyright 2018 bejson.com 
  */
package com.lduml.oc.androidokhttpwithcookie.model;
import java.util.List;

/**
 * Auto-generated: 2018-09-16 8:48:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Catalogs {

    private String cat_id;
    private String cat_name;
    private String item_id;
    private String s_number;
    private String addtime;
    private String parent_cat_id;
    private String level;
    private List<Pages> pages;
    private List<String> catalogs;
    public void setCat_id(String cat_id) {
         this.cat_id = cat_id;
     }
     public String getCat_id() {
         return cat_id;
     }

    public void setCat_name(String cat_name) {
         this.cat_name = cat_name;
     }
     public String getCat_name() {
         return cat_name;
     }

    public void setItem_id(String item_id) {
         this.item_id = item_id;
     }
     public String getItem_id() {
         return item_id;
     }

    public void setS_number(String s_number) {
         this.s_number = s_number;
     }
     public String getS_number() {
         return s_number;
     }

    public void setAddtime(String addtime) {
         this.addtime = addtime;
     }
     public String getAddtime() {
         return addtime;
     }

    public void setParent_cat_id(String parent_cat_id) {
         this.parent_cat_id = parent_cat_id;
     }
     public String getParent_cat_id() {
         return parent_cat_id;
     }

    public void setLevel(String level) {
         this.level = level;
     }
     public String getLevel() {
         return level;
     }

    public void setPages(List<Pages> pages) {
         this.pages = pages;
     }
     public List<Pages> getPages() {
         return pages;
     }

    public void setCatalogs(List<String> catalogs) {
         this.catalogs = catalogs;
     }
     public List<String> getCatalogs() {
         return catalogs;
     }

    @Override
    public String toString() {
        return "Catalogs{" +
                "cat_id='" + cat_id + '\'' +
                ", cat_name='" + cat_name + '\'' +
                ", item_id='" + item_id + '\'' +
                ", s_number='" + s_number + '\'' +
                ", addtime='" + addtime + '\'' +
                ", parent_cat_id='" + parent_cat_id + '\'' +
                ", level='" + level + '\'' +
                ", pages=" + pages +
                ", catalogs=" + catalogs +
                '}';
    }
}