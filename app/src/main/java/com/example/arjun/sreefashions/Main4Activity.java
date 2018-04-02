package com.example.arjun.sreefashions;

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

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Typeface tf=Typeface.createFromAsset(getAssets(),"Great Day Personal Use.ttf");
        TextView n= (TextView) findViewById(R.id.n);
        n.setTypeface(tf);
        n.setPaintFlags(n.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        MainActivity m1=new MainActivity();
        EditText e= (EditText) findViewById(R.id.editText);
        String m=m1.num;
        e.setText(m);
        EditText enumb= (EditText) findViewById(R.id.editText);
        SharedPreferences sp=Main4Activity.this.getSharedPreferences(getString(R.string.PRE_FILE),MODE_PRIVATE);
        enumb.setText(sp.getString(getString(R.string.num),""));
    }
    public void up(View view){
        SQLiteDatabase db=openOrCreateDatabase("MYDB",MODE_PRIVATE,null);
        EditText enumb,ename,edob,eamount;
        String num,name,dob,amount;
        Double point;
        enumb= (EditText) findViewById(R.id.editText);
        ename= (EditText) findViewById(R.id.editText1);
        edob= (EditText) findViewById(R.id.editText2);
        eamount= (EditText) findViewById(R.id.editText3);
        if(enumb.length()!=0&&ename.length()!=0&&edob.length()!=0&&eamount.length()!=0) {
            num = enumb.getText().toString();
            name = ename.getText().toString();
            dob = edob.getText().toString();
            amount = eamount.getText().toString();
            point = (Double.parseDouble(amount)) * 0.02;
            db.execSQL("INSERT INTO data VALUES (" + num + ",'" + name + "','" + dob + "'," + amount + "," + point + ");");
            Toast.makeText(getBaseContext(), "data saved......", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    public void home(View view){
        finish();
    }
}
