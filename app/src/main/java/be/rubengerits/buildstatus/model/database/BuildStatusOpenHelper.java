package be.rubengerits.buildstatus.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuildStatusOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String TABLE_REPOSITORIES = "repositories";
    public static final String DATABASE_NAME = "BuildStatus.db";
    public static final int VERSION = 1;

    public BuildStatusOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountsTable = "CREATE TABLE " + TABLE_ACCOUNTS +
                "(" +
                "id INTEGER PRIMARY KEY," + // Define a primary key
                "username TEXT," +
                "token TEXT," +
                "type TEXT" +
                ")";

        String createRepositoryTable = "CREATE TABLE " + TABLE_REPOSITORIES +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT," +
                "lastBuildNumber LONG," +
                "lastBuildState TEXT," +
                "lastBuildDuration LONG," +
                "lastBuildFinished DATE" +
                ")";

        db.execSQL(createAccountsTable);
        db.execSQL(createRepositoryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
