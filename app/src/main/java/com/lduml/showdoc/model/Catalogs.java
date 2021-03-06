package com.lduml.showdoc.model;

import java.util.List;

/*目录下最后一级的catalog*/
public class Catalogs {
    private String cat_id;
    private String cat_name;
    private String item_id;
    private String s_number;
    private String addtime;
    private String parent_cat_id;
    private String level;
    private List<Pages> pages;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getS_number() {
        return s_number;
    }

    public void setS_number(String s_number) {
        this.s_number = s_number;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getParent_cat_id() {
        return parent_cat_id;
    }

    public void setParent_cat_id(String parent_cat_id) {
        this.parent_cat_id = parent_cat_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Pages> getPages() {
        return pages;
    }

    public void setPages(List<Pages> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PaCatalogs{" +
                "cat_id='" + cat_id + '\'' +
                ", cat_name='" + cat_name + '\'' +
                ", item_id='" + item_id + '\'' +
                ", s_number='" + s_number + '\'' +
                ", addtime='" + addtime + '\'' +
                ", parent_cat_id='" + parent_cat_id + '\'' +
                ", level='" + level + '\'' +
                ", pages=" + pages +
                '}';
    }
}
