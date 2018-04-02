package com.example.arjun.sreefashions;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Great Day Personal Use.ttf");
        TextView n = (TextView) findViewById(R.id.n);
        n.setTypeface(tf);
        n.setPaintFlags(n.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    public void cpass(View view){
        EditText eop= (EditText) findViewById(R.id.editText);
        EditText enp= (EditText) findViewById(R.id.editText1);
        EditText enp1= (EditText) findViewById(R.id.editText2);
        String op,np,np1,p1;
        op=eop.getText().toString();
        np=enp.getText().toString();
        np1=enp1.getText().toString();
        SharedPreferences sp=Main3Activity.this.getSharedPreferences(getString(R.string.PRE_FILE),MODE_PRIVATE);
        p1="sree2016*";
        if(op.equals(p1)){
            if(np.equals(np1)){
                ContentValues data=new ContentValues();
                data.put("pass",np1);
                SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
                db.update("app",data,"key=" + 1,null);
                Toast.makeText(getBaseContext(),"Password changed!",Toast.LENGTH_LONG).show();
                //super.onBackPressed();
                finish();
            }
            else {
                Toast.makeText(getBaseContext(),"New passwords doesn't match!",Toast.LENGTH_LONG).show();
                enp.setText("");
                enp1.setText("");
            }
        }
        else {
            Toast.makeText(getBaseContext(),"Incorrect password!",Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }
    }
}
