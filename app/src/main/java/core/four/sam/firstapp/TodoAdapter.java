package core.four.sam.firstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import core.four.sam.firstapp.database.Todo;
import core.four.sam.firstapp.database.TodoDatabaseHelper;

/**
 * Created by Sam on 9/17/2016.
 */
public class TodoAdapter extends ArrayAdapter<Todo> {

    private Context context;
    private final TodoDatabaseHelper dbHelper;

    public TodoAdapter(Context context, ArrayList<Todo> todos, TodoDatabaseHelper dbHelper) {
        super(context, 0, todos);
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Todo todo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.item_text);
        itemText.setText(todo.getText());

        LinearLayout todoContainer = (LinearLayout) convertView.findViewById(R.id.todo_text_container);
        CheckBox todoComplete = (CheckBox) convertView.findViewById(R.id.complete_checkbox);

        todoContainer.setOnClickListener(getEditListener(position));
        todoComplete.setOnClickListener(getCompleteListener(position, convertView));

        return convertView;
    }

    // Return the onclick listener as a closure for an item at a given index
    private View.OnClickListener getEditListener(int position) {
        final int index = position;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTodo(index);
            }
        };
    }

    // Same as above, but for the checkbox
    private View.OnClickListener getCompleteListener(int position, View convertView) {
        final int index = position;
        final View view = convertView;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markComplete(index, view);
            }
        };
    }

    // The actual callback function for editing an item
    public void editTodo(int position) {
        final Todo todo = getItem(position);
        final EditText todoInput = new EditText(context);
        todoInput.setText(todo.getText());
        todoInput.setSelection(todo.getText().length());

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(todoInput)
                .setCancelable(true)
                .setTitle(R.string.todo_edit_dialog_title)
                .setPositiveButton(R.string.todo_ok_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todo.setText(todoInput.getText().toString());
                        dbHelper.updateTodo(todo);
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.todo_cancel_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        );
        dialog.show();
    }

    public void markComplete(int position, View convertView) {
        final TodoAdapter adapter = this;
        final View view = convertView;
        final Todo todo = getItem(position);
        // Update the item and persist the change to the database
        todo.setCompleted(true);
        dbHelper.updateTodo(todo);
        // Fade out and remove the item
        view.animate().alpha(0f).setDuration(500).withEndAction(new Runnable() {
            // Callback for when the transition completes so it's async
            @Override
            public void run() {
                view.setVisibility(View.GONE);
                remove(todo);
                dbHelper.deleteTodo(todo);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
