
package com.aurriola.daggerlogin.http.twitch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("cursor")
    @Expose
    private String cursor;

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public Pagination withCursor(String cursor) {
        this.cursor = cursor;
        return this;
    }

}
