package ca.bcit.snaydon_dai_assign2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChildActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    private Child child;
    private String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        idString = getIntent().getExtras().get("id").toString();
        Log.d("SQL Message: ", "idString is " + idString);
        child = getChild(idString);

        // Populate the country name
        TextView name = findViewById(R.id.name);
        name.setText(child.getID() + ". " + child.getFirstName() + " " + child.getLastName());

        // populate the country description
        TextView birthDate = findViewById(R.id.birthDate);
        birthDate.setText(child.getBirthDate());

        TextView address = findViewById(R.id.address);
        address.setText(child.getStreet() + ", "
                + child.getCity() + ", "
                + child.getProvince() + ", "
                + child.getCountry() + ", "
                + child.getPostalCode());

        TextView location = findViewById(R.id.location);
        location.setText("At: (" + child.getLatitude() + ", "
                + child.getLongitude() + ")");

        TextView naughty = findViewById(R.id.naughty);
        //Log.d("MyMessage", "isNaughty for result ============================= " + child.getNaughty());
        naughty.setText(child.getNaughty() ? "This child is naughty." : "This child is not naughty.");

        Button edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SantaDbHelper helper = new SantaDbHelper(ChildActivity.this);
                db = helper.getReadableDatabase();
                Intent intent = new Intent(ChildActivity.this, EditChildActivity.class);
                intent.putExtra("id", idString);
                startActivity(intent);
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SantaDbHelper helper = new SantaDbHelper(ChildActivity.this);
                db = helper.getReadableDatabase();
                helper.deleteChild(db, child.getID());
                startActivity(new Intent(ChildActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
    }

    private Child getChild(String idString) {
        Child child = null;
        SQLiteOpenHelper helper = new SantaDbHelper(this);
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("CHILD",
                    new String[] {"FIRSTNAME", "LASTNAME", "BIRTHDATE", "STREET", "CITY", "PROVINCE", "POSTALCODE", "COUNTRY", "LATITUDE", "LONGITUDE", "ISNAUGHTY", "DATECREATED"},
                    "ID = ?",
                    new String[] {idString},
                    null, null, null);

            // move to the first record
            if (cursor.moveToFirst()) {
                // get the country details from the cursor
                child = new Child(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9),
                        Boolean.parseBoolean(cursor.getString(10))
                );
                child.setID(Integer.parseInt(idString));
                //Log.d("SQL Message: ", child.toString());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[ChildActivity/getChild] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return  child;
    }
}
