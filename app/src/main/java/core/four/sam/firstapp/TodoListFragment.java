package core.four.sam.firstapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodoListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> items = new ArrayList<String>();
        items.add("Get");
        items.add("Good"); // there should be five todo items

        ListView listView = (ListView) myView.findViewById(R.id.todoitems);
        listView.setAdapter(new TodoAdapter(getContext(), items));

        return myView;
    }
}
