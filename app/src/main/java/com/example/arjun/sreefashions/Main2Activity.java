package com.example.arjun.sreefashions;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    double point=0,amount=0,a;
    String n,msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Typeface tf=Typeface.createFromAsset(getAssets(),"Great Day Personal Use.ttf");
        TextView n= (TextView) findViewById(R.id.n);
        n.setTypeface(tf);
        n.setPaintFlags(n.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        EditText en= (EditText) findViewById(R.id.editText);
        SharedPreferences sp=Main2Activity.this.getSharedPreferences(getString(R.string.PRE_FILE),MODE_PRIVATE);
        en.setText(sp.getString(getString(R.string.num),""));
    }
    public void done(View view){
        EditText en,ea;
        en= (EditText) findViewById(R.id.editText);
        ea= (EditText) findViewById(R.id.editText1);
        n=en.getText().toString();
        a= Double.parseDouble(ea.getText().toString());
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM data WHERE NUMBER LIKE '"+n+"'",null);
        c.moveToFirst();
        amount= Double.parseDouble(c.getString(3));
        point= Double.parseDouble(c.getString(4));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Points:"+point+",\nAre you want to use it?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                update(false);
                Toast.makeText(Main2Activity.this, "Points used......!", Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 update(true);
                Toast.makeText(Main2Activity.this, "Points not used.....!", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();}
        public void update(Boolean f){
            if(f) {
                point=point + (a* 0.02);
                amount=amount+a;
                ContentValues data=new ContentValues();
                data.put("AMOUNT",amount);
                data.put("POINTS",point);
                SQLiteDatabase db=openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
                db.update("data", data, "NUMBER=" + n, null);
                Cursor c=db.rawQuery("SELECT * FROM data WHERE NUMBER LIKE '"+n+"'",null);
                c.moveToFirst();
                if(c.getCount()>0)do{
                    msg=msg+"PHONE NO: "+c.getString(0)+"\n"+
                            "NAME    : "+c.getString(1)+"\n"+
                            "DoB     : "+c.getString(2)+"\n"+
                            "AMOUNT  : "+c.getString(3)+"\n"+
                            "POINTS  : "+c.getString(4)+"\n";
                }while (c.moveToNext());
                alert(msg);
                msg="";
            }
            else {
                if(a>point){
                    a= a-point;
                    point=(a)*0.02;
                }
                else {
                    point=point-a;
                    a= 0;
                }
                amount=amount+a;
                ContentValues data=new ContentValues();
                data.put("AMOUNT",amount);
                data.put("POINTS",point);
                SQLiteDatabase db=openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
                db.update("data", data, "NUMBER=" + n,null);
                Cursor c=db.rawQuery("SELECT * FROM data WHERE NUMBER LIKE '"+n+"'",null);
                c.moveToFirst();
                if(c.getCount()>0)do{
                    msg=msg+"PHONE NO: "+c.getString(0)+"\n"+
                            "NAME    : "+c.getString(1)+"\n"+
                            "DoB     : "+c.getString(2)+"\n"+
                            "AMOUNT  : "+c.getString(3)+"\n"+
                            "POINTS  : "+c.getString(4)+"\n";
                }while (c.moveToNext());
                alert(msg);
                msg="";
            }
        }

    public void home(View view){

        change();
    }
    public void alert(String s){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog.Builder ok = alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                change();
            }
        });
        alertDialogBuilder.setMessage(s);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public void change(){
        finish();
        //super.onBackPressed();
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
    }
}
