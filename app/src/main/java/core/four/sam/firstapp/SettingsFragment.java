package core.four.sam.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by smyers on 9/15/2016.
 */
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_settings, container, false);

        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        // These should be declared as class attributes since they're constant.
        final Integer[] settings = {
                R.id.background_red,
                R.id.background_blue,
                R.id.background_green,
                R.id.background_reset
        };
        final Integer[] colors = {
                Color.argb(255, 255, 0, 0),
                Color.argb(255, 0, 0, 255),
                Color.argb(255, 0, 255, 0),
                Color.argb(255, 255, 255, 255)
        };

        int defaultValue = colors[colors.length - 1];
        int background = sharedPref.getInt(getString(R.string.saved_background), defaultValue);
        myView.setBackgroundColor(background);

        // Here you're saving the color every time it is changed in the settings fragment. It is
        // much more efficient to only save when the user exits the app, i.e. in the lifecycle
        // onDestroy() method.
        for (int i = 0; i < settings.length; i++) {
            final int index = i;
            myView.findViewById(settings[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int thisColor = colors[index];
                    myView.setBackgroundColor(thisColor);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getString(R.string.saved_background), thisColor);
                    editor.commit(); // Use editor.apply() instead
                }
            });
        }

        return myView;
    }
}
