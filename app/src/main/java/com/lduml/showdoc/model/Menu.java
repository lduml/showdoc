/**
  * Copyright 2018 bejson.com 
  */
package com.lduml.showdoc.model;
import java.util.List;

/**
 * Auto-generated: 2018-09-16 8:48:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Menu {

    private List<Pages> pages;
    private List<Catalogs> catalogs;
    public void setPages(List<Pages> pages) {
         this.pages = pages;
     }
     public List<Pages> getPages() {
         return pages;
     }

    public void setCatalogs(List<Catalogs> catalogs) {
         this.catalogs = catalogs;
     }
     public List<Catalogs> getCatalogs() {
         return catalogs;
     }

    @Override
    public String toString() {
        return "Menu{" +
                "pages=" + pages +
                ", catalogs=" + catalogs +
                '}';
    }
}