package com.bsidesoft.radio;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bsidesoft.radio.databinding.RadioItemBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Radio extends LinearLayout implements Runnable, View.OnClickListener {
    private RadioVM vm;
    private List<UUID> itemsOld;
    private final Map<View, Holder> list = new LinkedHashMap<>();
    public Radio(Context context) {
        super(context);
    }
    public Radio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Radio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setV(@NonNull RadioVM v){
        vm = v;
        vm.listener(this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemsOld = v.items();

        removeAllViews();

        for(UUID i:itemsOld){
            RadioItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.radio_item, this, false);
            binding.setClick(this);

            Holder holder = new Holder(binding, i);
            View view = binding.getRoot();
            addView(view);

            list.put(view, holder);
        }
        run();
    }

    @Override
    public void onClick(View v) {
        if(!list.containsKey(v)) return;
        vm.select(list.get(v).item);
    }

    @BindingAdapter(value = {"app:v"})
    public static void setV(Radio v, RadioVM vm){
        v.setV(vm);
    }

    @Override
    public void run() {
        if(vm.isChanged(itemsOld)){
            setV(vm);
            return;
        }
        for(Holder h:list.values())h.update(vm.item(h.item));
    }

    static private class Holder{
        private final RadioItemBinding binding;
        private final UUID item;
        private Holder(RadioItemBinding b, UUID i){
            binding = b;
            item = i;
        }
        private void update(RadioItem item){
            binding.setV(item);
            binding.executePendingBindings();
        }
    }
}
