package app.vasugajjar.architectureexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.setAction("add");
                startActivityForResult(intent, 101);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = noteAdapter.getNodeAt(viewHolder.getAdapterPosition());
                noteViewModel.delete(note);
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                 Intent intent =new Intent(MainActivity.this, AddNoteActivity.class);
                 intent.setAction("edit");
                 intent.putExtra("note_id",note.getId());
                 intent.putExtra("note_name",note.getName());
                 intent.putExtra("note_desc",note.getDescription());
                 intent.putExtra("note_prior",note.getPriority());
                 startActivityForResult(intent, 202);
            }
        });

    }

    public void addNote(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("note_name");
            String description = data.getStringExtra("note_desc");
            int prior = data.getIntExtra("note_prior",1);

            Note note = new Note(title, description, prior);
            noteViewModel.insert(note);

            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 202 && resultCode == RESULT_OK) {
            int id = data.getIntExtra("note_id",0);
            String title = data.getStringExtra("note_name");
            String description = data.getStringExtra("note_desc");
            int prior = data.getIntExtra("note_prior",1);

            Note note = new Note(title, description, prior);
            note.setId(id);
            noteViewModel.update(note);
        } else {
            Toast.makeText(this, "Action Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAll();
                Toast.makeText(this, "All Tasks Deleted", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}