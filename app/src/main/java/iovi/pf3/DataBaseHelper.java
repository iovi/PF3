package iovi.pf3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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




    public void openDataBase() throws SQLException {

        String path = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
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
