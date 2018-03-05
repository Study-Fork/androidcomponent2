package com.bsidesoft.radio;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsidesoft.radio.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        RadioVM vm = new RadioVM();
        vm.add(
                new RadioItem("item0", "0"),
                new RadioItem("item1", "1"),
                new RadioItem("item2", "2"),
                new RadioItem("item3", "3")
        );

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setRadio(vm);
        binding.setClick(v->{
            int cnt = vm.items().size();
            vm.add(new RadioItem("item" + cnt, cnt + ""));
        });
        binding.executePendingBindings();
    }
}
