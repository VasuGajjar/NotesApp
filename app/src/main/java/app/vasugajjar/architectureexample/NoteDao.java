package app.vasugajjar.architectureexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    public void addNote(Note note);

    @Delete
    public void deleteNote(Note note);

    @Query("DELETE FROM note_table")
    public void deleteAllNotes();

    @Update
    public void updateNote(Note note);

    @Query("SELECT * FROM note_table WHERE id=:id")
    public Note getNote(int id);

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    public LiveData<List<Note>> getAllNotes();

}
