package core.four.sam.firstapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

import core.four.sam.firstapp.database.Todo;
import core.four.sam.firstapp.database.TodoDatabaseHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodoListFragment extends Fragment {
    private TodoDatabaseHelper dbHelper;
    private TodoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.settings_button);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTodo();
            }
        });

        ListView listView = (ListView) myView.findViewById(R.id.todoitems);

        this.dbHelper = new TodoDatabaseHelper(getContext());
        final ArrayList<Todo> allTodos = dbHelper.getTodos();

        this.adapter = new TodoAdapter(getContext(), allTodos, dbHelper);
        listView.setAdapter(adapter);

        return myView;
    }

    public void addTodo() {
        final EditText todoInput = new EditText(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(todoInput)
                .setCancelable(true)
                .setTitle(R.string.todo_edit_dialog_title)
                .setPositiveButton(R.string.todo_ok_button_text, new DialogInterface.OnClickListener() {
                    // callback for Ok button
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Todo todo = dbHelper.createTodo(todoInput.getText().toString());
                        adapter.add(todo);
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.todo_cancel_button_text, new DialogInterface.OnClickListener() {
                    // callback for Cancel button
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        // Pop up keyboard automatically
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        );
        dialog.show();
    }
}
