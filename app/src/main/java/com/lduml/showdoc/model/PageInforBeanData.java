package com.lduml.showdoc.model;

import java.util.Date;

public class PageInforBeanData {

    private String page_id;
    private String author_uid;
    private String author_username;
    private String item_id;
    private String cat_id;
    private String page_title;
    private String page_comments;
    private String page_content;
    private String s_number;
    private String is_del;
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

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }
    public String getAuthor_username() {
        return author_username;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
    public String getItem_id() {
        return item_id;
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

    public void setPage_comments(String page_comments) {
        this.page_comments = page_comments;
    }
    public String getPage_comments() {
        return page_comments;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }
    public String getPage_content() {
        return page_content;
    }

    public void setS_number(String s_number) {
        this.s_number = s_number;
    }
    public String getS_number() {
        return s_number;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }
    public String getIs_del() {
        return is_del;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
    public String getAddtime() {
        return addtime;
    }

    @Override
    public String toString() {
        return "PageInforBeanData{" +
                "page_id='" + page_id + '\'' +
                ", author_uid='" + author_uid + '\'' +
                ", author_username='" + author_username + '\'' +
                ", item_id='" + item_id + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", page_title='" + page_title + '\'' +
                ", page_comments='" + page_comments + '\'' +
                ", page_content='" + page_content + '\'' +
                ", s_number='" + s_number + '\'' +
                ", is_del='" + is_del + '\'' +
                ", addtime=" + addtime +
                '}';
    }
}