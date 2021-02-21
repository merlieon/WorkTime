package com.example.worktime.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.worktime.dao.SettingsDao;
import com.example.worktime.dao.TimeReportDao;
import com.example.worktime.dao.WorkplaceDao;
import com.example.worktime.models.SettingsModel;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;

@Database(entities = {TimeReportModel.class, WorkplaceModel.class, SettingsModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract TimeReportDao timeReportDao();

    public abstract WorkplaceDao workplaceDao();

    public abstract SettingsDao settingsDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "WORKTIME_DB")
                    .addCallback(roomCallBack)
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE timereport_table_new (" +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "company_name TEXT, " +
                    "salary REAL NOT NULL, " +
                    "hour_salary REAL NOT NULL, " +
                    "hours REAL NOT NULL, " +
                    "ob_hours REAL NOT NULL, " +
                    "ob_hour_salary TEXT," +
                    "unpaid_brake REAL NOT NULL," +
                    "have_worked INTEGER," +
                    "worked_date TEXT )");

            database.execSQL("INSERT INTO timereport_table_new (" +
                    "id, " +
                    "company_name, " +
                    "salary, " +
                    "hour_salary," +
                    "hours," +
                    "ob_hours," +
                    "ob_hour_salary," +
                    "unpaid_brake," +
                    "have_worked," +
                    "worked_date) SELECT id, company_name, salary, hour_salary, hours, ob_hours, ob_salary, unpaid_brake, have_worked, worked_Date FROM timereport_table");
            database.execSQL("DROP TABLE timereport_table");
            database.execSQL("ALTER TABLE timereport_table_new RENAME TO timereport_table");

            database.execSQL("CREATE TABLE workplace_table_new (" +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "company_name TEXT, " +
                    "company_address TEXT , " +
                    "company_orgnum TEXT , " +
                    "salary REAL NOT NULL, " +
                    "ob_hour_salary TEXT)");

            database.execSQL("INSERT INTO workplace_table_new (" +
                    "id, " +
                    "company_name, " +
                    "company_address, " +
                    "company_orgnum," +
                    "salary," +
                    "ob_hour_salary) SELECT id, company_name, company_address, company_orgnum, salary, ob_salary FROM workplace_table");
            database.execSQL("DROP TABLE workplace_table");
            database.execSQL("ALTER TABLE workplace_table_new RENAME TO workplace_table");

            database.execSQL("CREATE TABLE IF NOT EXISTS settings_table (" +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "birth_day TEXT, " +
                    "religious_community TEXT , " +
                    "municipal_tax TEXT , " +
                    "language TEXT , " +
                    "swedish_church TEXT)");
        }
    };


    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private TimeReportDao timeReportDao;
        private WorkplaceDao workplaceDao;
        private SettingsDao settingsDao;
        private PopulateDBAsyncTask(AppDatabase db) {
            timeReportDao = db.timeReportDao();
            workplaceDao = db.workplaceDao();
            settingsDao = db.settingsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
