package com.bsidesoft.radio;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RadioVM {
    private final List<RadioItem> items = new ArrayList<>();
    private final Set<Runnable> listeners = new HashSet<>();
    public void listener(Runnable l){
        listeners.add(l);
    }
    private void noti(){
        for(Runnable r:listeners) r.run();
    }
    public void add(@NonNull RadioItem... is){
        for(RadioItem i:is) items.add(i);
        noti();
    }
    public void select(UUID u){
        for(RadioItem i:items) i.isChecked(i.uuid.equals(u));
        noti();
    }
    public RadioItem item(UUID u){
        for(RadioItem i:items){
            if(i.uuid.equals(u)) return i;
        }
        noti();
        return null;
    }

    public List<UUID> items(){
        List<UUID> r = new ArrayList<>();
        for(RadioItem i:items) r.add(i.uuid);
        return r;
    }
    public boolean isChanged(List<UUID> old){
        List<UUID> items = items();
        if(items.size() != old.size()) return true;
        for(int i = 0; i < old.size(); i++){
            if(!old.get(i).equals(items.get(i))) return true;
        }
        return false;
    }
}
