package ca.bcit.ass2.snaydon_dai;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChildActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        String idString = getIntent().getExtras().get("id").toString();
        Log.d("SQL Message: ", "idString is " + idString);
        Child child = getChild(idString);
        Log.d("SQL Message: ", child.toString());

        // Populate the country name
        TextView name = findViewById(R.id.name);
        name.setText(child.getFirstName() + " " + child.getLastName());

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
        naughty.setText(child.getNaughty() ? "This child is naughty." : "This child is not naughty.");
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
                Log.d("SQL Message: ", child.toString());
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
