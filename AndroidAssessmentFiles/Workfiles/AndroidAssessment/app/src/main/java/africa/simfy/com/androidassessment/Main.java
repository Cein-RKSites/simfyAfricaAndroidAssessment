package africa.simfy.com.androidassessment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends AppCompatActivity {
    boolean netConnected;
    String error_DataBaseFound = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        boolean netConnected = isInternetOn();

        if(getIntent().getBooleanExtra("closeApp",false)){
            System.exit(0);
        }else{
            usher(netConnected);
        }
    }

    public void usher(boolean netConnected){
        if(netConnected == false) {
            //check if user in a database
            SQLiteDatabase db = openOrCreateDatabase("UsersDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS Users (id INTEGER AUTO_INCREMENT PRIMARY KEY,Name VARCHAR);");
            Cursor results = db.rawQuery("SELECT Name FROM Users WHERE id = 1",null);

            if(results.getCount() != 0) {
                //entry found
                Intent intentWelcome = new Intent(Main.this,Welcome.class);
                intentWelcome.putExtra("DisplayType","Long");
                startActivity(intentWelcome);
            }else {
                //no entry found
                //Got to the Add User Activity
                Intent moveAddUser = new Intent(Main.this, AddUser.class);
                startActivity(moveAddUser);
            }
            db.close();
        }else{
            //Pass To online custom list
            Intent intentList = new Intent(Main.this,CustomList.class);
            startActivity(intentList);
        }
    }

    public boolean isInternetOn(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        this.finish();
    }
}
