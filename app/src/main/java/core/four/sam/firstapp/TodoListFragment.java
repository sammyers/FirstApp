package core.four.sam.firstapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodoListFragment extends Fragment {

    public TodoListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_main, container, false);
        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.settings_button);
        return myView;
    }
}
