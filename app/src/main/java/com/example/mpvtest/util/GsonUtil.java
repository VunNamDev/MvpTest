package com.example.mpvtest.util;

import com.google.gson.Gson;

public class GsonUtil {
    Gson gson = new Gson();

    public String parseBool(boolean b) {
        return gson.toJson(b);

    }

    public boolean getBool(String str) {
        Boolean bool = gson.fromJson(str, Boolean.class);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public String parseInt(int num) {
        return gson.toJson(num);
    }

    public int getInt(String str) {
        Integer num = gson.fromJson(str, Integer.class);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public String parseFloat(float num) {
        return gson.toJson(num);
    }

    public float getFloat(String str) {
        Float num = gson.fromJson(str, Float.class);
        if (num == null) {
            return 0f;
        }
        return num.floatValue();
    }

    public String parseString(String string) {
        return gson.toJson(string);
    }

    public String getString(String str) {
        String data = gson.fromJson(str, String.class);
        if (data == null) {
            return "";
        }
        return data;
    }

}
