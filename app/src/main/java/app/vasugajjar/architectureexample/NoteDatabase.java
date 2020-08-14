package app.vasugajjar.architectureexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase Instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return Instance;
    }

    public static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Populate(Instance).execute();
        }
    };

    public static class Populate extends AsyncTask<Void, Void, Void> {
        private  NoteDao dao;

        public Populate(NoteDatabase database) {
            dao = database.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.addNote(new Note("Hello 1", "Description 1Description 1Description 1Description 1",8));
            dao.addNote(new Note("Hello 2", "Description 11",2));
            dao.addNote(new Note("Hello 3", "Description 121Description 1Description 1",7));
            return null;
        }
    }

}
