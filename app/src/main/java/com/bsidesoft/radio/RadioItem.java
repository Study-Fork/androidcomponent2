package com.bsidesoft.radio;

import android.support.annotation.NonNull;

import java.util.UUID;

public class RadioItem {
    public final String title, value;
    public final UUID uuid = UUID.randomUUID();
    private boolean isChecked = false;
    public RadioItem(@NonNull String t, @NonNull String v){
        title = t;
        value = v;
    }
    public void isChecked(boolean v){
        isChecked = v;
    }
    public boolean isChecked(){
        return isChecked;
    }
}
