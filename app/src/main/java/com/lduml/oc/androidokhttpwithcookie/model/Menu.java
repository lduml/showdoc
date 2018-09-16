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
public class Menu {

    private List<String> pages;
    private List<Catalogs> catalogs;
    public void setPages(List<String> pages) {
         this.pages = pages;
     }
     public List<String> getPages() {
         return pages;
     }

    public void setCatalogs(List<Catalogs> catalogs) {
         this.catalogs = catalogs;
     }
     public List<Catalogs> getCatalogs() {
         return catalogs;
     }

}