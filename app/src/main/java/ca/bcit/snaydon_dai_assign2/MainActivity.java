package ca.bcit.snaydon_dai_assign2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] ids;

    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list_children = findViewById(R.id.childList);
        list_children.setTextFilterEnabled(true);
        final String[] childrenName = getChildren();

        arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, childrenName
        );

        list_children.setAdapter(arrayAdapter);

        list_children.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ChildActivity.class);
                String selectedFromList = (String) ((TextView)view).getText();
                //Log.d("MyMessage", selectedFromList);
                for(int j = 0; j < childrenName.length; j++){
                    if(childrenName[j].equalsIgnoreCase(selectedFromList)){
                        //Log.d("MyMessage", j + ". " + childrenName[j]);
                        //Log.d("MyMessage", ids[j] + " not " + ids[i]);
                        intent.putExtra("id", ids[j]);
                        break;
                    }
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        arrayAdapter.getFilter().filter(newText);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus:
                startActivity(new Intent(this, InsertChildActivity.class));
                return true;
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String[] getChildren() {
        SQLiteOpenHelper helper = new SantaDbHelper(this);
        // Log.d("Running Message: ", "getChildren()");
        String[] children = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.query("CHILD",
                    new String[] {"ID", "FIRSTNAME", "LASTNAME"},
                    null,
                    null,
                    null, null, null);

            int count = cursor.getCount();
            //Log.d("Running Message: ", "getCount = " + count);
            children = new String[count];
            ids = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    ids[ndx] = cursor.getString(0);
                    children[ndx++] = cursor.getString(1) + " " + cursor.getString(2);
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
