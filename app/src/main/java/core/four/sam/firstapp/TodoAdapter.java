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

/**
 * Created by Sam on 9/17/2016.
 */
public class TodoAdapter extends ArrayAdapter<String> {

    private Context context;
    private final ArrayList<String> items;

    public TodoAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String todoText = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.item_text);
        itemText.setText(todoText);

        LinearLayout todoContainer = (LinearLayout) convertView.findViewById(R.id.todo_text_container);
        CheckBox todoComplete = (CheckBox) convertView.findViewById(R.id.complete_checkbox);

        todoContainer.setOnClickListener(getEditListener(position));
        todoComplete.setOnClickListener(getCompleteListener(position, convertView));

        return convertView;
    }

    private View.OnClickListener getEditListener(int position) {
        final int index = position;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTodo(index);
            }
        };
    }

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

    public void editTodo(int position) {
        final int index = position;
        String todoText = items.get(position);
        final EditText todoInput = new EditText(context);
        todoInput.setText(todoText);
        todoInput.setSelection(todoText.length());

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(todoInput)
                .setCancelable(true)
                .setTitle(R.string.todo_edit_dialog_title)
                .setPositiveButton(R.string.todo_ok_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        items.set(index, todoInput.getText().toString());
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
        final int index = position;
        view.animate().alpha(0f).setDuration(500).withEndAction(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
                items.remove(index);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
