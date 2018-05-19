package com.example.matt.taskslistapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Matt on 12/18/2017.
 */

public class todoAdapter extends ArrayAdapter<todos> {

    protected todos [] arr;

    public todoAdapter(Context context , ArrayList<todos> items){
        super( context, 0, items);
        arr = items.toArray(new todos[items.size()]);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final todos todo = getItem(position);
        if( convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo , parent, false);
        }

        TextView tvTask = (TextView) convertView.findViewById(R.id.tvTask);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        final ImageView tvComp = (ImageView) convertView.findViewById(R.id.tvComp);

        tvTask.setText(todo.title);
        tvDesc.setText(todo.desc);
        if( todo.complete ) {
            tvComp.setImageResource(R.drawable.check);
        }else{
            tvComp.setImageResource(R.drawable.xmark);
        }

        tvComp.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if( todo.complete ){
                                              todo.complete = false;
                                              tvComp.setImageResource(R.drawable.xmark);
                                          }
                                          else{
                                              todo.complete = true;
                                              tvComp.setImageResource(R.drawable.check);
                                          }
                                      }
                                  }
        );


        //return super.getView(position, convertView, parent);
        return convertView;
    }

    public void swap( int position ){

        todos todo = getItem(position);
        if( todo.complete ){
            todo.complete = false;

        }else{
            todo.complete = true;
        }

    }
}
