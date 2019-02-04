package com.bts.lucasoskorep.project5androiddatabases;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bts.lucasoskorep.project5androiddatabases.Database.AppDatabase;
import com.bts.lucasoskorep.project5androiddatabases.Entities.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY_TAG";
    private static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Boilerplate code here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateWithTestData(appDatabase);
                for(User user: appDatabase.userDao().getAll()){
                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + user.getAge() + " : " +user.getUid());
                    user.setAge(102);
                    updateUser(appDatabase, user);
                }

                Log.i(TAG, "Done updating the users, printing out the update results.");
                for(User user: appDatabase.userDao().getAll()){
                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + user.getAge() + " : " +user.getUid());
                    deleteUser(appDatabase, user);
                }
                Log.i(TAG, "Removing all users from the database. ");
                Log.i(TAG, "Attempting to print all users from teh database, there should be no more log messages after this. ");
                for(User user: appDatabase.userDao().getAll()){
                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + user.getAge() + " : " +user.getUid());

                }
            }
        };
        runnable.run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static User updateUser(AppDatabase db, User user){
        db.userDao().updateUsers(user);
        return user;
    }

    private static void deleteUser(AppDatabase db, User user){
        db.userDao().delete(user);
    }

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setFirstName("Lucas");
        user.setLastName("Oskorep");
        user.setAge(22);
        addUser(db, user);
        user = new User();
        user.setFirstName("Carmen");
        user.setLastName("Bertucci");
        user.setAge(25);
        addUser(db, user);
        user = new User();
        user.setFirstName("Student");
        user.setLastName("McStudentFace");
        user.setAge(21);
        addUser(db, user);
    }
}
