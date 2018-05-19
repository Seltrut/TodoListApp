package com.example.matt.taskslistapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements addTaskDialog.addTaskListener{

    private ArrayList<todos> todoList;
    private todoAdapter adapter;
    private ListView taskList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instantiate elements of the activity
        taskList = findViewById(R.id.tasks);
        fab = findViewById(R.id.fab);

        //Load the data a populate the listView
        loadData();
        adapter = new todoAdapter(this, todoList);
        taskList.setAdapter(adapter);



        //Floating Action button onClick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment atDialog = new addTaskDialog();
                atDialog.show(getSupportFragmentManager(), "addTaskDialog");
            }
        });

        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                PopupMenu popupMenu = new PopupMenu( MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.deletepopup, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveData();
    }

    //Save the list of tasks to be repopulated later
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String tasksJson = gson.toJson( todoList );
        editor.putString( "task list" , tasksJson );
        editor.apply();
    }

    //load the list of tasks
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String tasksJson = sharedPreferences.getString( "task list", null );
        Type type = new TypeToken<ArrayList<todos>>() {}.getType();
        todoList = gson.fromJson(tasksJson, type);
        if( todoList == null ){
            todoList = new ArrayList<todos>();
        }
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

    //Override the interface methods to use data from the custom dialog
    //Positive will add a new task to the list, negative will cancel the dialog
    @Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment Dialog, DialogInterface d) {
        //declare the EditTexts and retrieve their data
        Dialog dialg = (Dialog) d;
        EditText etName = (EditText)dialg.findViewById(R.id.task_name_input);
        EditText etDesc = (EditText)dialg.findViewById(R.id.task_desc_input);

        String taskName = etName.getText().toString();
        String taskDesc = etDesc.getText().toString();
        todos addTodo = new todos(taskName, taskDesc, false);
        todoList.add(addTodo);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment Dialog, DialogInterface d) {
        Dialog.getDialog().cancel();
    }
}
