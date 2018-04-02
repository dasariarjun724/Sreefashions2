package com.example.arjun.sreefashions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String p1="",m1="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Great Day Personal Use.ttf");
        TextView n = (TextView) findViewById(R.id.n);
        n.setTypeface(tf);
        n.setPaintFlags(n.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Boolean isfirstrun=getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("isfirstrun",true);
        if(isfirstrun) {
            SQLiteDatabase db=openOrCreateDatabase("MYDB",MODE_PRIVATE,null);
            db.execSQL("DROP TABLE IF EXISTS app");
            db.execSQL("CREATE TABLE app (key VARCHAR,pass VARCHAR,msg TEXT);");
            db.execSQL("INSERT INTO app VALUES ('1', 'sree2016*','It''s time to get happy!\nMany more happy returns of the day.......');");
            getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putBoolean("isfirstrun",false).commit();
        }
    }
    public void signIn(View view){
        String p1;
        EditText u= (EditText) findViewById(R.id.editText);
        EditText p= (EditText) findViewById(R.id.editText1);
        TextView m= (TextView) findViewById(R.id.textView2);
        String user=u.getText().toString();
        String pass=p.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase("MYDB", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM app WHERE key LIKE '" + 1 + "'", null);
        c.moveToFirst();
        p1=c.getString(1);
        if(user.equals("")&&pass.equals("")){
            Toast.makeText(getBaseContext(),"Enter username & password!",Toast.LENGTH_LONG).show();
        }
        else if((user.equals("SREE")||user.equals("sree"))&&pass.equals(p1)){
            u.setText("");
            p.setText("");
            m.setText("");
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else{
            m.setText("Wrong password try again!");
        }
    }
    public void change(View view){
        Intent intent=new Intent(this,Main3Activity.class);
        startActivity(intent);
    }
}
