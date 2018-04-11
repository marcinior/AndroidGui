package com.example.marcin.gui1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ListOfMarks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_marks);

        Bundle container = getIntent().getExtras();
        int count = container.getInt("countOfMarks");
        ArrayList<MarkModel> listOfMarks = new ArrayList<MarkModel>();
        ListView listView = (ListView) findViewById(R.id.list);

        for (int i = 0; i < count; i++) {
            listOfMarks.add(new MarkModel(SubjectName.values()[i].toString()));
        }

        InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(this, listOfMarks);
        listView.setAdapter(adapter);

        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfMarks.this, UserData.class);
                double sum = listOfMarks.stream().mapToInt(i -> i.getMark()).sum();
                double average = Math.round((sum * 100.0) / listOfMarks.size()) / 100.0;
                intent.putExtra("average", average);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    enum SubjectName {
        Matematyka,
        Angielski,
        Chemia,
        Fizyka,
        Polski,
        WF,
        Biologia,
        Plastyka,
        Technika,
        WOS,
        Francuski,
        Niemiecki,
        Muzyka,
        Informatyka,
        Religia,
    }
}

