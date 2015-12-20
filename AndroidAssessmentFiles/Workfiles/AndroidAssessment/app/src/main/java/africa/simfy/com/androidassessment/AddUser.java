package africa.simfy.com.androidassessment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        Button userSub = (Button) findViewById(R.id.submitUserBtn);
        final EditText nameInput = (EditText) findViewById(R.id.usersName);
        //check for Name in db and display it
        SQLiteDatabase db = SQLiteDatabase.openDatabase("data/data/africa.simfy.com.androidassessment/databases/UsersDB", null, MODE_PRIVATE);
        Cursor checkName = db.rawQuery("SELECT * FROM Users WHERE id=1", null);
        if(checkName.getCount() !=0){
            checkName.moveToFirst();
            String userNameGrab = checkName.getString(checkName.getColumnIndex("Name"));
            nameInput.setText(userNameGrab);
        }

        userSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameInput.getText().toString();
                if (userName.equals("")) {
                    AlertDialog.Builder errorName = new AlertDialog.Builder(AddUser.this);
                    errorName.setMessage("Please Enter Your Name").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
                    errorName.show();
                } else {
                    //add to the database.
                    SQLiteDatabase db = SQLiteDatabase.openDatabase("data/data/africa.simfy.com.androidassessment/databases/UsersDB", null, MODE_PRIVATE);
                    Cursor results = db.rawQuery("SELECT Name FROM Users WHERE id = 1", null);
                    if (results.getCount() != 0) {
                        //update entry
                        db.execSQL("UPDATE Users SET Name = '" + userName + "' WHERE id = 1");
                    } else {
                        //insert entry
                        db.execSQL("INSERT INTO Users (id,Name) VALUES(1,'" + userName + "')");
                    }
                    db.close();
                    //got to final offline page
                    Intent intent = new Intent(AddUser.this, Welcome.class);
                    intent.putExtra("DisplayType", "Short");
                    startActivity(intent);
                }
            }
        });
    }
}
