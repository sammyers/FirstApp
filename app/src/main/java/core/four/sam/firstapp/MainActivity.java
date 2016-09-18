package core.four.sam.firstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMainFragment();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.settings_button);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // maybe do something eventually
            }
        });
    }

    public void createMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.placeholder, new TodoListFragment());
        transaction.commit();
    }

    public void switchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuItem menuItem = menu.findItem(R.id.action_settings);

        if (findViewById(R.id.todolist) != null) {
            transaction.replace(R.id.placeholder, new SettingsFragment());
            menuItem.setTitle(R.string.todo_menu_item);
        } else {
            transaction.replace(R.id.placeholder, new TodoListFragment());
            menuItem.setTitle(R.string.action_settings);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            switchFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
