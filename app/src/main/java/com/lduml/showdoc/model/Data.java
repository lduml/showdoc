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
public class Data {

    private String item_id;
    private String item_domain;
    private String is_archived;
    private String item_name;
    private String default_page_id;
    private int default_cat_id2;
    private int default_cat_id3;
    private String unread_count;
    private int item_type;
    private Menu menu;
    private boolean is_login;
    private boolean ItemPermn;
    private boolean ItemCreator;
    public void setItem_id(String item_id) {
         this.item_id = item_id;
     }
     public String getItem_id() {
         return item_id;
     }

    public void setItem_domain(String item_domain) {
         this.item_domain = item_domain;
     }
     public String getItem_domain() {
         return item_domain;
     }

    public void setIs_archived(String is_archived) {
         this.is_archived = is_archived;
     }
     public String getIs_archived() {
         return is_archived;
     }

    public void setItem_name(String item_name) {
         this.item_name = item_name;
     }
     public String getItem_name() {
         return item_name;
     }

    public void setDefault_page_id(String default_page_id) {
         this.default_page_id = default_page_id;
     }
     public String getDefault_page_id() {
         return default_page_id;
     }

    public void setDefault_cat_id2(int default_cat_id2) {
         this.default_cat_id2 = default_cat_id2;
     }
     public int getDefault_cat_id2() {
         return default_cat_id2;
     }

    public void setDefault_cat_id3(int default_cat_id3) {
         this.default_cat_id3 = default_cat_id3;
     }
     public int getDefault_cat_id3() {
         return default_cat_id3;
     }

    public void setUnread_count(String unread_count) {
         this.unread_count = unread_count;
     }
     public String getUnread_count() {
         return unread_count;
     }

    public void setItem_type(int item_type) {
         this.item_type = item_type;
     }
     public int getItem_type() {
         return item_type;
     }

    public void setMenu(Menu menu) {
         this.menu = menu;
     }
     public Menu getMenu() {
         return menu;
     }

    public void setIs_login(boolean is_login) {
         this.is_login = is_login;
     }
     public boolean getIs_login() {
         return is_login;
     }

    public void setItemPermn(boolean ItemPermn) {
         this.ItemPermn = ItemPermn;
     }
     public boolean getItemPermn() {
         return ItemPermn;
     }

    public void setItemCreator(boolean ItemCreator) {
         this.ItemCreator = ItemCreator;
     }
     public boolean getItemCreator() {
         return ItemCreator;
     }

    @Override
    public String toString() {
        return "Data{" +
                "item_id='" + item_id + '\'' +
                ", item_domain='" + item_domain + '\'' +
                ", is_archived='" + is_archived + '\'' +
                ", item_name='" + item_name + '\'' +
                ", default_page_id='" + default_page_id + '\'' +
                ", default_cat_id2=" + default_cat_id2 +
                ", default_cat_id3=" + default_cat_id3 +
                ", unread_count='" + unread_count + '\'' +
                ", item_type=" + item_type +
                ", menu=" + menu +
                ", is_login=" + is_login +
                ", ItemPermn=" + ItemPermn +
                ", ItemCreator=" + ItemCreator +
                '}';
    }
}