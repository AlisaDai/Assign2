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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditChildActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    private Child child;
    private String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);

        idString = getIntent().getExtras().get("id").toString();
        child = getChild(idString);

        loadData();

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = findViewById(R.id.firstName);
                String firstName = edit.getText().toString();
                edit = findViewById(R.id.lastName);
                String lastName = edit.getText().toString();
                edit = findViewById(R.id.birthDate);
                String birthDate = edit.getText().toString();
                edit = findViewById(R.id.street);
                String street = edit.getText().toString();
                edit = findViewById(R.id.city);
                String city = edit.getText().toString();
                edit = findViewById(R.id.province);
                String province = edit.getText().toString();
                edit = findViewById(R.id.country);
                String country = edit.getText().toString();
                edit = findViewById(R.id.postalCode);
                String postalCode = edit.getText().toString();
                edit = findViewById(R.id.latitude);
                double latitude = Double.parseDouble(edit.getText().toString());
                edit = findViewById(R.id.longitude);
                double longitude = Double.parseDouble(edit.getText().toString());
                CheckBox checkBox = findViewById(R.id.isNaughty);
                Boolean isNaughty = checkBox.isChecked();
                Child newChild = new Child(
                        firstName,
                        lastName,
                        birthDate,
                        street,
                        city,
                        province,
                        country,
                        postalCode,
                        latitude,
                        longitude,
                        isNaughty
                );
                newChild.setDateCreated(child.getDateCreated());
                //Log.d("MyMessage", "isNaughty for insert ============================= " + newChild.getNaughty());
                SantaDbHelper helper = new SantaDbHelper(EditChildActivity.this);
                Log.d("MyMessage",newChild.getLastName() + " (" + newChild.getLatitude() + ", " + newChild.getLongitude() + ")");
                db = helper.getReadableDatabase();
                helper.updateChild(db, newChild, child.getID());
                startActivity(new Intent(EditChildActivity.this, MainActivity.class));
            }
        });
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Child getChild(String idString) {
        Child child = null;
        SQLiteOpenHelper helper = new SantaDbHelper(this);
        try {
            db = helper.getReadableDatabase();
            cursor = db.query("CHILD",
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

    private void loadData(){
        EditText edit = findViewById(R.id.firstName);
        edit.setText(child.getFirstName());
        edit = findViewById(R.id.lastName);
        edit.setText(child.getLastName());
        edit = findViewById(R.id.birthDate);
        edit.setText(child.getBirthDate());
        edit = findViewById(R.id.street);
        edit.setText(child.getStreet());
        edit = findViewById(R.id.city);
        edit.setText(child.getCity());
        edit = findViewById(R.id.province);
        edit.setText(child.getProvince());
        edit = findViewById(R.id.country);
        edit.setText(child.getCountry());
        edit = findViewById(R.id.postalCode);
        edit.setText(child.getPostalCode());
        edit = findViewById(R.id.latitude);
        edit.setText(String.valueOf(child.getLatitude()));
        edit = findViewById(R.id.longitude);
        edit.setText(String.valueOf(child.getLongitude()));
        CheckBox checkBox = findViewById(R.id.isNaughty);
        checkBox.setChecked(child.getNaughty());
    }
}
