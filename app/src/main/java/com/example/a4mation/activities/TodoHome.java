package com.example.a4mation.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a4mation.R;

import java.util.ArrayList;
import java.util.List;

public class TodoHome extends AppCompatActivity {

    private Button add;
    private ListView listView;
    private TextView count;
    Context context;
    private DbHandler dbHandler;
    private List<ToDo> toDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_home);

        dbHandler = new DbHandler(this);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.todolist);
        count = findViewById(R.id.todocount);
        toDos = new ArrayList<>();
        context = this;

        toDos = dbHandler.getAllToDos();

        ToDoAdapter adapter = new ToDoAdapter(context,R.layout.single_todo,toDos);
        listView.setAdapter(adapter);

        // Get todo counts from the table
        int countTodo = dbHandler.countToDo();
        count.setText("You Have "+countTodo+" todos");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), AddToDo.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ToDo todo =  toDos.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(todo.getTitle());
                builder.setMessage(todo.getDescription());

                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todo.setFinished(System.currentTimeMillis());
                        dbHandler.updateSingelToDo(todo);
                        startActivity(new Intent(context,TodoHome.class));
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.deleteToDo(todo.getId());
                        startActivity(new Intent(context,TodoHome.class));
                    }
                });
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, EditTodo.class);
                        intent.putExtra("id",String.valueOf(todo.getId()));
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}