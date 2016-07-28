package iovi.pf3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Iovi on 05.09.2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/iovi.pf3/databases/";
    private static String DB_NAME = "dictPF.sqlite";
    final static String TABLE_WORDS_RU="words_ru";
    final static String WORD="word";

    private static final String TAG = "PF3";

    private SQLiteDatabase db;

    private final Context context;


    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream input = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }


    public void openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;

        File file = new File(path);
        if (!file.exists())
            Log.v(TAG,"not exists :(");
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Log.v(TAG,"opening done!");
    }

    public ArrayList<String> getDictionary(int wordlength) throws SQLException {
        ArrayList<String> dict=new ArrayList<>();
        String selection = "length("+WORD+") = "+Integer.toString(wordlength);
        Cursor cursor=db.query(TABLE_WORDS_RU, null, selection,null, null, null, null);
        //String outputArray[]=new String[cursor.getCount()];
        while(cursor.moveToNext()){
            dict.add( cursor.getString(cursor.getColumnIndex(WORD)));
        }
        return dict;
    }


    @Override
    public synchronized void close() {

        if(db != null) db.close();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
