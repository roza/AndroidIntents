package com.example.roza.androidintents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.net.URI;



public class MainActivity extends AppCompatActivity {

    EditText edt;
    static final int LAUNCH_OTHER_ACTIVITY = 1;
    static final int REQUEST_SELECT_CONTACT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edt = findViewById(R.id.edit);
        FloatingActionButton fab0 = (FloatingActionButton) findViewById(R.id.fab0);
        fab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Launch Intent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               dialPhoneNumer("0123456789");
            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Launch Contact Intent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                selectContact();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Launch Intent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startOtherActivityForResult();
            }
        });
    }

    private void dialPhoneNumer(String s) {
        Intent intent= new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+s));
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
    }

    private void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }
    }

    public void startOtherActivity(){
        Intent intent = new Intent(this,OtherActivity.class);
        // Paramètres à passer à OtherActivity
        String message = edt.getText().toString();
        intent.putExtra("text", message);
        Log.i("MainActivity","Launching Other Activity");
        startActivity(intent);
    }

    public void startOtherActivityForResult(){
        Intent intent = new Intent(this,OtherActivity.class);
        // Paramètres à passer à OtherActivity
        String message = edt.getText().toString();
        intent.putExtra("text", message);
        Log.i("MainActivity","Launching Other Activity");
        startActivityForResult(intent, LAUNCH_OTHER_ACTIVITY);
    }
    /* Information de retour de OtherActivity */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check which request we're responding to
        if (requestCode == LAUNCH_OTHER_ACTIVITY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String message = intent.getStringExtra("text");
                edt.setText(message);
            }
        }
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = intent.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                if (number != null)
                    edt.setText(number);
                else
                    edt.setText("Tel Inconnu");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
