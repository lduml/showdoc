package com.lduml.showdoc.model;

public class ItemDataList {

    /*
      "item_id": "152190162663851",
      "item_name": "\u817e\u8baf\u4e91\u6559\u7a0b",
      "item_domain": "",
      "item_type": "1",
      "last_update_time": "1536497496",
      "item_description": "",
      "is_del": "0",
      "top": 0
    * */
    String  item_id;
    String  item_name;
    String  item_domain;
    String  item_type;
    String  last_update_time;
    String  item_description;
    String  is_del;
    String  top;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_domain() {
        return item_domain;
    }

    public void setItem_domain(String item_domain) {
        this.item_domain = item_domain;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "ItemDataList{" +
                "item_id='" + item_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", item_domain='" + item_domain + '\'' +
                ", item_type='" + item_type + '\'' +
                ", last_update_time='" + last_update_time + '\'' +
                ", item_description='" + item_description + '\'' +
                ", is_del='" + is_del + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}
