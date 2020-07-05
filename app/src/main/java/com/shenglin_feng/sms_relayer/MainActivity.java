package com.shenglin_feng.sms_relayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SharedPreferences sp;
    private Switch switch1;
    private EditText editTextTextPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("sms_relayer", Activity.MODE_PRIVATE);
        init();
    }

    private void init() {
        // switch1
        switch1 = findViewById(R.id.switch1);
        switch1.setChecked(sp.getBoolean("switch1", false));
        switch1.setOnCheckedChangeListener(this);

        // editTextTextPersonName
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName.setText(sp.getString("editTextTextPersonName", ""));
        editTextTextPersonName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("editTextTextPersonName", editTextTextPersonName.getText().toString());
                    editor.apply();
                    editTextTextPersonName.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    sendToast("转发地址已更新 √");
                }
                return false;
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton btn, boolean status) {
        switch (btn.getId()) {
            case R.id.switch1:
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("switch1", status);
                editor.apply();
                if(status) {
                    sendToast("总开关已打开 √");
                } else {
                    sendToast("总开关已关闭 ×");
                }
                break;
        }
    }

    private void sendToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}