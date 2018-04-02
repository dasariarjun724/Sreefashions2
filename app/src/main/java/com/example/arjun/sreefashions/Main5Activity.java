package com.example.arjun.sreefashions;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Great Day Personal Use.ttf");
        TextView n = (TextView) findViewById(R.id.n);
        n.setTypeface(tf);
        n.setPaintFlags(n.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    public void msgset(View view){
        EditText emsg= (EditText) findViewById(R.id.msg);
        String msg=emsg.getText().toString();
        ContentValues data=new ContentValues();
        data.put("msg",msg);
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        db.update("app",data,"key=" + 1,null);
        change();
    }
    public void home(View view){

        change();
    }
    public void change(){
        //super.onBackPressed();
        finish();
    }
}
