package com.example.lz.tally.Bean;

/**
 * Created by liz on 16-12-22.
 */

public class BillType extends Base {
    private String name;
    private String img;
    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
