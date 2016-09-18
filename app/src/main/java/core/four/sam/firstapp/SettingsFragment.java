package core.four.sam.firstapp;

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

        final Integer[] settings = {R.id.background_red, R.id.background_blue, R.id.background_green};
        final Integer[] colors = {Color.argb(255, 255, 0, 0), Color.argb(255, 0, 0, 255), Color.argb(255, 0, 255, 0)};

        for (int i = 0; i < settings.length; i++) {
            final int index = i;
            myView.findViewById(settings[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myView.setBackgroundColor(colors[index]);
                }
            });
        }

        return myView;
    }
}
