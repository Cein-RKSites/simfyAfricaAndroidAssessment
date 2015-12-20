package africa.simfy.com.androidassessment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        TextView welcomeH = (TextView) findViewById(R.id.welcomeText);
        TextView subMsg = (TextView) findViewById(R.id.subMsg);
        String displayType = getIntent().getExtras().getString("DisplayType");

        Button closeBtn = (Button) findViewById(R.id.closeAppBtn);
        closeBtn.setOnClickListener(this);
        Button backUserAdd = (Button) findViewById(R.id.AddUserBackBtn);
        backUserAdd.setOnClickListener(this);

        if(displayType.equals("Short")){
            //get name from db
            SQLiteDatabase db = SQLiteDatabase.openDatabase("data/data/africa.simfy.com.androidassessment/databases/UsersDB",null,MODE_PRIVATE);
            Cursor results = db.rawQuery("SELECT Name FROM Users WHERE id=1",null);
            results.moveToFirst();
            welcomeH.setText("Welcome "+ results.getString(results.getColumnIndex("Name")));
            subMsg.setVisibility(View.INVISIBLE);
            db.close();

        }else if(displayType.equals("Long")){
            //get Name from db
            SQLiteDatabase db = SQLiteDatabase.openDatabase("data/data/africa.simfy.com.androidassessment/databases/UsersDB",null,MODE_PRIVATE);
            Cursor results = db.rawQuery("SELECT Name FROM Users WHERE id=1", null);
            results.moveToFirst();
            welcomeH.setText("Welcome " + results.getString(results.getColumnIndex("Name")));
            subMsg.setVisibility(View.VISIBLE);
            db.close();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.closeAppBtn){
            Intent passClose = new Intent(getApplicationContext(),Main.class);
            passClose.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            passClose.putExtra("closeApp", true);
            startActivity(passClose);

        }else if(v.getId() == R.id.AddUserBackBtn){
            Intent backUserAdd = new Intent(Welcome.this,AddUser.class);
            startActivity(backUserAdd);
        }
    }
}
