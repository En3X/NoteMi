package com.enex.notemi;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TodoDialog extends AppCompatDialogFragment {
    Context context;

    public TodoDialog(Context context) {
        this.context = context;
    }
    TodoDataListener listener;
    EditText todoEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.customDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.todo_input,null);
        todoEditText = view.findViewById(R.id.todoInput);
        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get value from user
                String todoData = todoEditText.getText().toString();
                listener.getTodo(todoData);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TodoDataListener) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public interface TodoDataListener{
        String getTodo(String todoText);
    }

}