package com.example.lz.tally.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by liz on 16-12-19.
 */
@DatabaseTable(tableName = "tb_bill")
public class Bill extends Base {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "title")
    private String title;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "platform")
    private String platform;
    @DatabaseField(columnName = "price")
    private String price;

    public Bill(int id, String title, String type, String platform, String price) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.platform = platform;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
