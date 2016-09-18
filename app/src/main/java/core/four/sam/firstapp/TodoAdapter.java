package core.four.sam.firstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sam on 9/17/2016.
 */
public class TodoAdapter extends ArrayAdapter<String> {
    public TodoAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.item_text);
        itemText.setText(item);
        itemText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final TextView thisTodo = (TextView) v;
                final EditText todoInput = new EditText(getContext());
                // Set initial value of dialog to existing text
                todoInput.setText(thisTodo.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setView(todoInput)
                        .setCancelable(true)
                        .setTitle(R.string.todo_edit_dialog_title)
                        .setPositiveButton(R.string.todo_ok_button_text, new DialogInterface.OnClickListener() {
                            // callback for Ok button
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                thisTodo.setText(todoInput.getText().toString());
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.todo_cancel_button_text, new DialogInterface.OnClickListener() {
                            // callback for Cancel button
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.show();
            }
        });

        return convertView;
    }
}
