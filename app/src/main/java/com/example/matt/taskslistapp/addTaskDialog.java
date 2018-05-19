package com.example.matt.taskslistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class addTaskDialog extends android.support.v4.app.DialogFragment {

    public interface addTaskListener{
        void onDialogPositiveClick(android.support.v4.app.DialogFragment Dialog, DialogInterface d);
        void onDialogNegativeClick(android.support.v4.app.DialogFragment Dialog, DialogInterface d);
    }

    addTaskListener taskListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //Create the builder and get the layout inflater
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //EditText etName = (EditText) inflater.findViewById(R.id.task_name_input);


        //Set the custom layout to the builder
        builder.setView(inflater.inflate(R.layout.add_task_layout, null))
                //Add buttons to add task or cancel
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Clicked the add button ");
                        taskListener.onDialogPositiveClick(addTaskDialog.this, dialog);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskListener.onDialogNegativeClick(addTaskDialog.this, dialog);

                    }
                });
        // Use the Builder class for convenient dialog construction


        return builder.create();
    }


    @Override
    public void onAttach(Context context){
        super.onAttach( context );

        try{
            taskListener = (addTaskListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement addTaskListener");
        }
    }
}
