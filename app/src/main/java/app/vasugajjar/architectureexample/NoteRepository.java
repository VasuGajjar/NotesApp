package app.vasugajjar.architectureexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new insetAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new updateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new deleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public static class insetAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao dao;

        public insetAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.addNote(notes[0]);
            return null;
        }
    }

    public static class updateAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao dao;

        public updateAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.updateNote(notes[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao dao;

        public deleteAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.deleteNote(notes[0]);
            return null;
        }
    }

    public static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        NoteDao dao;

        public deleteAllAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllNotes();
            return null;
        }
    }

}
