package ca.bcit.ass2.snaydon_dai;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Running Message: ", "onCreate()");

        ListView list_children = findViewById(R.id.childList);
        String[] childrenName = getChildren();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, childrenName
        );

        list_children.setAdapter(arrayAdapter);

        list_children.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ChildActivity.class);
                Log.d("Running Message: ", "int " + i + ", long " + l);
                intent.putExtra("id", i + 1);

                startActivity(intent);
            }
        });
    }

    private String[] getChildren() {
        SQLiteOpenHelper helper = new SantaDbHelper(this);
       // Log.d("Running Message: ", "getChildren()");
        String[] children = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.query("CHILD",
                    new String[] {"FIRSTNAME", "LASTNAME"},
                    null,
                    null,
                    null, null, null);
            //cursor= db.rawQuery("select DISTINCT FIRSTNAME from CHILD", null);

            int count = cursor.getCount();
            Log.d("Running Message: ", "getCount = " + count);
            children = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    children[ndx++] = cursor.getString(0) + " " + cursor.getString(1);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getChildren] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return children;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
    }
}
