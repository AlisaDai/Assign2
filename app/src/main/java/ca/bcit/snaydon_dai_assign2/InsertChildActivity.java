package ca.bcit.snaydon_dai_assign2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class InsertChildActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_child);

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
                //Log.d("MyMessage", "isNaughty for insert ============================= " + newChild.getNaughty());
                SantaDbHelper helper = new SantaDbHelper(InsertChildActivity.this);
                db = helper.getReadableDatabase();
                helper.insertChild(db, newChild);
                startActivity(new Intent(InsertChildActivity.this, MainActivity.class));
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
}
