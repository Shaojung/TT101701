package com.test.tt101701;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    final String DB_NAME = "address.db";
    final String DB_PATH = "/data/data/com.test.tt101701/databases/";
    final String PACKAGE_NAME = "com.test.tt101701";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File dbFile = new File(DB_PATH + DB_NAME);
        try {
            if (!dbFile.exists())
            {
                copyDataBase();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        Cursor c = db.rawQuery("Select * from phonebook", null);
        c.moveToFirst();
        Log.d("DATA", c.getString(1));
        c.close();
        db.close();

    }

    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        File f = new File(DB_PATH);
        if (!f.exists())
        {
            f.mkdir();
        }

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
}
