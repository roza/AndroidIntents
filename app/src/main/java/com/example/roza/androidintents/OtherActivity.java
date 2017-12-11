package com.example.roza.androidintents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class OtherActivity extends Activity {
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Intent intent = getIntent();
        String message = intent.getStringExtra("text");
        edt = findViewById(R.id.edt);
        edt.setText(message);
        Log.i("OtherActivity", message);
    }
    @Override
    public void finish(){
        Intent intent = new Intent();
        intent.putExtra("text", edt.getText().toString());
        setResult(Activity.RESULT_OK,intent);
        super.finish();
    }
}
