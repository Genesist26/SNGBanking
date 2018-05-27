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
        public static final String COL_PASS = "pin";
        public static final String COL_FIRSTNAME = "firstName";
        public static final String COL_LASTNAME = "surname";
        public static final String COL_BALANCE = "balance";
        private SQLiteDatabase sqLiteDatabase;


        public MyDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) { ;
            db.execSQL("CREATE TABLE " + TABLE_NAME +" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ACCNUMBER + " TEXT, " + COL_EMAIL + " TEXT, "
                    + COL_PASS + " TEXT, " + COL_FIRSTNAME + " TEXT, "
                    + COL_LASTNAME + " TEXT, " + COL_BALANCE + " REAL);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void addAcc(BankAccount ba) {
            sqLiteDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_ACCNUMBER, ba.getAccoutNumber());
            values.put(COL_EMAIL, ba.getEmail());
            values.put(COL_PASS, ba.getPassword());
            values.put(COL_FIRSTNAME, ba.getfName());
            values.put(COL_LASTNAME, ba.getlName());
            values.put(COL_BALANCE, ba.getBalance());

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

        public void deleteAcc(String accNO) {

            sqLiteDatabase = this.getWritableDatabase();

            sqLiteDatabase.delete(TABLE_NAME, COL_ACCNUMBER + " = " + accNO, null);

            sqLiteDatabase.close();
        }
    }
}
