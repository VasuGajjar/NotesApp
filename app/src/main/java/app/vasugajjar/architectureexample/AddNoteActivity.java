package app.vasugajjar.architectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    int id, priority;
    String name, description, action;
    Intent get, set;
    EditText nameEditText, descriptionEditText;
    NumberPicker priorityNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        get = getIntent();
        set = new Intent();
        action = get.getAction();

        nameEditText = findViewById(R.id.et_note_name);
        descriptionEditText = findViewById(R.id.et_note_description);
        priorityNumberPicker = findViewById(R.id.priority_picker);
        priorityNumberPicker.setMinValue(1);
        priorityNumberPicker.setMaxValue(10);

        checkAction();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                setResult();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkAction() {
        if (action.equals("edit")) {
            id = get.getIntExtra("note_id", 1);
            name = get.getStringExtra("note_name");
            description = get.getStringExtra("note_desc");
            priority = get.getIntExtra("note_prior", 1);

            nameEditText.setText(name);
            descriptionEditText.setText(description);
            priorityNumberPicker.setValue(priority);

            setTitle("Edit Task");
        } else {
            setTitle("Add Task");
        }
    }

    public void setResult() {
        name = nameEditText.getText().toString();
        description = descriptionEditText.getText().toString();
        priority = priorityNumberPicker.getValue();

        set.putExtra("note_id",id);
        set.putExtra("note_name", name);
        set.putExtra("note_desc", description);
        set.putExtra("note_prior", priority);

        setResult(RESULT_OK, set);
        finish();
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_CANCELED);
        super.onDestroy();
    }
}