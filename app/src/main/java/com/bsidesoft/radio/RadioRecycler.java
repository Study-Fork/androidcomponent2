package com.bsidesoft.radio;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsidesoft.radio.databinding.RadioItemBinding;

import java.util.List;
import java.util.UUID;

public class RadioRecycler extends RecyclerView {
    public RadioRecycler(Context context) {
        super(context);
    }
    public RadioRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RadioRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setV(@NonNull RadioVM v){
        ListAdapter adapter = new ListAdapter();
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(adapter);
        adapter.setV(v);
    }

    @BindingAdapter(value = {"app:v"})
    public static void setV(Radio v, RadioVM vm){
        v.setV(vm);
    }

    static private class ListAdapter extends Adapter<Holder> implements Runnable{
        private RadioVM vm;
        private List<UUID> itemsOld;
        public ListAdapter(){
        }
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return new Holder(this, DataBindingUtil.inflate(inflater, R.layout.radio_item, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.update(vm.item(itemsOld.get(position)), position);
        }

        @Override
        public int getItemCount() {
            return itemsOld.size();
        }

        public void setV(@NonNull RadioVM v){
            vm = v;
            vm.listener(this);
            itemsOld = v.items();
            run();
        }

        @Override
        public void run() {
            if(vm.isChanged(itemsOld)){
                setV(vm);
                return;
            }
            notifyDataSetChanged();
        }

        public void select(int position) {
            vm.select(itemsOld.get(position));
        }
    }
    static private class Holder extends ViewHolder implements OnClickListener{
        private final RadioItemBinding binding;
        private final ListAdapter adapter;
        private int position;
        public Holder(ListAdapter a, RadioItemBinding b) {
            super(b.getRoot());
            adapter = a;
            binding = b;
            binding.setClick(this);
        }
        private void update(RadioItem item, int p){
            position = p;
            binding.setV(item);
            binding.executePendingBindings();
        }
        @Override
        public void onClick(View v) {
            adapter.select(position);
        }
    }


}
