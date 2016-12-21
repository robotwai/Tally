package com.example.lz.tally.Bean;

/**
 * Created by liz on 16-12-19.
 */

public class DataEntity extends Base {
    private  Long time;
    private  Float mFloat;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Float getFloat() {
        return mFloat;
    }

    public void setFloat(Float aFloat) {
        mFloat = aFloat;
    }
}
