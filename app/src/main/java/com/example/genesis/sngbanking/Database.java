package com.example.genesis.sngbanking;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {


    static class MyDbHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "bank";
        private static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "acc";
        public static final String COL_ACCNUMBER = "accNumber";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASS = "pass";
        public static final String COL_FIRSTNAME = "firstName";
        public static final String COL_LASTNAME = "lastName";
        public static final String COL_BALANCE = "balance";
        private SQLiteDatabase sqLiteDatabase;

        private static long lastAccNumber = 4823521350L;


        public MyDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) { ;
            db.execSQL("CREATE TABLE " + TABLE_NAME +" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ACCNUMBER + " TEXT, " + COL_EMAIL + " TEXT, "
                    + COL_PASS + " TEXT, " + COL_FIRSTNAME + " TEXT, "
                    + COL_LASTNAME + " TEXT, " + COL_BALANCE + " REAL);");

            // test account
            addAcc("Somsak","Binarwaeloh","somsakwp8@gmail.com","1234");
            addAcc("Chantapat","Sopontanasiri","sunsun@gmail.com","1234");
            addAcc("Nalina","Witee","nalina@gmail.com","1234");
            addAcc("test","test","test@test.com","test");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void addAcc(String fname, String lname, String email, String pass) {
            sqLiteDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_ACCNUMBER, Long.toString(++lastAccNumber));
            values.put(COL_EMAIL, email);
            values.put(COL_PASS, pass);
            values.put(COL_FIRSTNAME, fname);
            values.put(COL_LASTNAME, lname);
            values.put(COL_BALANCE, 0);

            sqLiteDatabase.insert(TABLE_NAME, null, values);
            sqLiteDatabase.close();
        }

        public void updateAcc(BankAccount ba) {

            sqLiteDatabase  = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_ACCNUMBER, ba.getAccoutNumber());
            values.put(COL_EMAIL, ba.getEmail());
            values.put(COL_PASS, ba.getPassword());
            values.put(COL_FIRSTNAME, ba.getfName());
            values.put(COL_LASTNAME, ba.getlName());
            values.put(COL_BALANCE, ba.getBalance());

            int row = sqLiteDatabase.update(TABLE_NAME,
                    values,
                    COL_ACCNUMBER + " = ? ",
                    new String[] { String.valueOf(ba.getAccoutNumber()) });

            sqLiteDatabase.close();
        }

        public void deleteAvv(String accNO) {

            sqLiteDatabase = this.getWritableDatabase();

            sqLiteDatabase.delete(TABLE_NAME, COL_ACCNUMBER + " = " + accNO, null);

            sqLiteDatabase.close();
        }
    }
}
