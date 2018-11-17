package ca.bcit.snaydon_dai_assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SantaDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Santa's_List.sqlite";
    private static final int DB_VERSION = 2;
    private Context context;

    public SantaDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        //Log.d("SQL Message: ", "SQL onCreate()");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private String getCreateChildTableSql() {
        String sql = "";
        sql += "CREATE TABLE CHILD (";
        sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "FIRSTNAME TEXT, ";
        sql += "LASTNAME TEXT, ";
        sql += "BIRTHDATE DATE, ";
        sql += "STREET TEXT, ";
        sql += "CITY TEXT, ";
        sql += "PROVINCE TEXT, ";
        sql += "POSTALCODE TEXT, ";
        sql += "COUNTRY TEXT, ";
        sql += "LATITUDE FLOAT, ";
        sql += "LONGITUDE DOUBLE, ";
        sql += "ISNAUGHTY BOOLEAN, ";
        sql += "DATECREATED DATE); ";

        return sql;
    }

    public void insertChild(SQLiteDatabase db, Child child) {
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME", child.getFirstName());
        values.put("LASTNAME", child.getLastName());
        values.put("BIRTHDATE", child.getBirthDate());
        values.put("STREET", child.getStreet());
        values.put("CITY", child.getCity());
        values.put("PROVINCE", child.getProvince());
        values.put("POSTALCODE", child.getPostalCode());
        values.put("COUNTRY", child.getCountry());
        values.put("LATITUDE", child.getLatitude());
        values.put("LONGITUDE", child.getLongitude());
        if(child.getNaughty() == true){
            values.put("ISNAUGHTY", "TRUE");
        }else{
            values.put("ISNAUGHTY", "FALSE");
        }
        Log.d("MyMessage","getNaughty for SQL" + child.getNaughty());
        values.put("DATECREATED", child.getDateCreated());
        db.insert("CHILD", null, values);
    }

    public void updateChild(SQLiteDatabase db, Child child, int id){
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME", child.getFirstName());
        values.put("LASTNAME", child.getLastName());
        values.put("BIRTHDATE", child.getBirthDate());
        values.put("STREET", child.getStreet());
        values.put("CITY", child.getCity());
        values.put("PROVINCE", child.getProvince());
        values.put("POSTALCODE", child.getPostalCode());
        values.put("COUNTRY", child.getCountry());
        values.put("LATITUDE", child.getLatitude());
        values.put("LONGITUDE", child.getLongitude());
        if(child.getNaughty() == true){
            values.put("ISNAUGHTY", "TRUE");
        }else{
            values.put("ISNAUGHTY", "FALSE");
        }
        values.put("DATECREATED", child.getDateCreated());
        Log.d("MyMessage for values", values.toString());
        db.update("CHILD", values, "ID = ?", new String[]{String.valueOf(id)});
    }

    public void deleteChild(SQLiteDatabase db, int id){
        db.delete("CHILD", "ID = ?", new String[]{String.valueOf(id)});
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(getCreateChildTableSql());
                for (Child c : Child.CHILDREN) {
                    insertChild(db, c);
                }
            }
        } catch (SQLException sqle) {
            String msg = "[SantaDbHelper/updateChild/insertChild] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
