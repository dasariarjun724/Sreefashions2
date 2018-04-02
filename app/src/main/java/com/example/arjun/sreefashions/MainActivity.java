package com.example.arjun.sreefashions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String num,msg="",d,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Great Day Personal Use.ttf");
        TextView n = (TextView) findViewById(R.id.n);
        n.setTypeface(tf);
        n.setPaintFlags(n.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data (NUMBER INT(10),NAME VARCHAR,DOB VARCHAR,AMOUNT INT,POINTS REAL);");

    }

    public void check(View view) {
        EditText ed = (EditText) findViewById(R.id.editText);
        num = ed.getText().toString();
        if(num.length()==10) {
            SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("SELECT * FROM data WHERE NUMBER LIKE '" + num + "'", null);
            SharedPreferences sp=MainActivity.this.getSharedPreferences(getString(R.string.PRE_FILE),MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(getString(R.string.num),num);
            editor.commit();
            if (c.getCount() == 0) {
                Intent intent = new Intent(this, Main4Activity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(MainActivity.this, "Enter valid info....!", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View view) {
        EditText ed = (EditText) findViewById(R.id.editText);
        num = ed.getText().toString();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to delete "+num+" data?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
                db.execSQL("DELETE FROM data WHERE number like'"+num+"';");
                Toast.makeText(MainActivity.this, "Deleted......!", Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Not Deleted.....!", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void home(View view){
        EditText ed = (EditText) findViewById(R.id.editText);
        ed.setText("");
    }
public void query(View view) {
    EditText ed = (EditText) findViewById(R.id.editText);
    num = ed.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM data WHERE NUMBER LIKE '" + num + "'", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                msg = msg + "PHONE NO: " + c.getString(0) + "\n" +
                        "NAME    : " + c.getString(1) + "\n" +
                        "DoB     : " + c.getString(2) + "\n" +
                        "AMOUNT  : " + c.getString(3) + "\n" +
                        "POINTS  : " + c.getString(4) + "\n";
            } while (c.moveToNext());
        } else
            msg = "Not Found!";
        c = db.rawQuery("SELECT * FROM data WHERE NUMBER LIKE '" + num + "'", null);
        c.moveToFirst();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alertDialogBuilder.setMessage(msg);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    msg="";
    }
    public void wish(View view){
        Calendar cal=Calendar.getInstance();
        m= String.valueOf(cal.get(cal.MONTH)+1);
        d= String.valueOf(cal.get(cal.DATE));
        if(m.length()==1){
            m="0"+m;
        }
        if(d.length()==1){
            d="0"+d;
        }
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT NUMBER,NAME FROM data WHERE DOB LIKE '"+d+"/"+m+"/%'", null);
        c.moveToFirst();
        String ms= String.valueOf(c.getCount());
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(c.getCount()>0) {
            alertDialogBuilder.setMessage("No.of birthdays :" + ms + "\n Are you sure?");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
                    Cursor c = db.rawQuery("SELECT NUMBER,NAME FROM data WHERE DOB LIKE '"+d+"/"+m+"/%'", null);
                    c.moveToFirst();
                    Cursor c1= db.rawQuery("SELECT * FROM app WHERE key LIKE '"+1+"'", null);
                    c1.moveToFirst();
                    SmsManager manager = SmsManager.getDefault();
                    do {
                        msg=c1.getString(2)+" " + c.getString(1) + " \nFrom \nSREE FASHIONS,\nTIRUCHANUR,\n9441276025,8886316299";
                        manager.sendTextMessage(c.getString(0), null, msg, null, null);
                    } while (c.moveToNext());
                    Toast.makeText(getBaseContext(), "Message sent...", Toast.LENGTH_LONG).show();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getBaseContext(), "Message Not sent!", Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            Toast.makeText(getBaseContext(),"No Birthdays found!",Toast.LENGTH_LONG).show();
        }
    }
    public void msg(View view){
        Intent intent=new Intent(this,Main5Activity.class);
        startActivity(intent);
    }
    public void save(View view){
        String state;
        state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File root=Environment.getExternalStorageDirectory();
            File dir=new File(root.getAbsolutePath()+"/sree fashions");
            if(!dir.exists()){
                dir.mkdir();
            }
            File file=new File(dir,"Customer_Data.txt");
            Calendar cal=Calendar.getInstance();
            String month= String.valueOf(cal.get(cal.MONTH)+1);
            String day= String.valueOf(cal.get(cal.DATE));
            String year=String.valueOf(cal.get(cal.YEAR));
            String hour=String.valueOf(cal.get(cal.HOUR));
            String min=String.valueOf(cal.get(cal.MINUTE));
            SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("SELECT * FROM data", null);
            c.moveToFirst();
            String data="----DATE:"+day+"/"+month+"/"+year+"  TIME:"+hour+":"+min+"----\n";
            if (c.getCount() > 0) {
                do {
                    data = data + "PHONE NO: " + c.getString(0) + "\n" +
                            "NAME    : " + c.getString(1) + "\n" +
                            "DoB     : " + c.getString(2) + "\n" +
                            "AMOUNT  : " + c.getString(3) + "\n" +
                            "POINTS  : " + c.getString(4) + "\n"+
                        "-------------------------------------------------\n";
                } while (c.moveToNext());
            }
            else {
                data=data+"no data found!";
            }
            try {
                FileOutputStream fos=new FileOutputStream(file);
                fos.write(data.getBytes());
                fos.close();
                Toast.makeText(getBaseContext(),"Data saved to sdcard.....",Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getBaseContext(),"SD Card not found!",Toast.LENGTH_LONG).show();
        }
    }

}