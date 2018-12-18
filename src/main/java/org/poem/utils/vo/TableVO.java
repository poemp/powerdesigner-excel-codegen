package org.poem.utils.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author poem
 */
public class TableVO {

    private String tableName;

    private String chineseName;

    private List<JSONObject> filed;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }


    public List<JSONObject> getFiled() {
        return filed;
    }

    public void setFiled(List<JSONObject> filed) {
        this.filed = filed;
    }
}
