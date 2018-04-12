package com.example.marcin.gui1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.marcin.gui1.R;


import java.util.List;

/**
 * Created by marcin on 22.03.18.
 */

public class InteractiveArrayAdapter extends ArrayAdapter<MarkModel> {
    private List<MarkModel> listOfMarks;
    private Activity context;

    public InteractiveArrayAdapter(Activity activity, List<MarkModel> list) {
        super(activity, R.layout.mark, list);
        listOfMarks = list;
        context = activity;
    }

    @Override
    public View getView(int rawNumber,View viewToEdit, ViewGroup parent) {
        
        View view = null;
        
        if (viewToEdit == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.mark, null);
            RadioGroup markGroup = (RadioGroup) view.findViewById(R.id.markRadioGroup);
            markGroup.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkButtonId) {
                            updateMarkModel(group, checkButtonId);
                        }
                    });

            markGroup.setTag(listOfMarks.get(rawNumber));
        } else {
            view = viewToEdit;
            RadioGroup markGroup = (RadioGroup) view.findViewById(R.id.markRadioGroup);
            markGroup.setTag(listOfMarks.get(rawNumber));
        }
        TextView label = (TextView) view.findViewById(R.id.markLabel);
        label.setText(listOfMarks.get(rawNumber).getName());
        RadioGroup markGroup = (RadioGroup) view.findViewById(R.id.markRadioGroup);
        setMark(markGroup, rawNumber);
        return view;
    }

    private void setMark(RadioGroup group, int rowNumber) {
        switch (listOfMarks.get(rowNumber).getMark()) {
            case 2:
                group.check(R.id.mark2);
                break;
            case 3:
                group.check(R.id.mark3);
                break;
            case 4:
                group.check(R.id.mark4);
                break;
            case 5:
                group.check(R.id.mark5);
                break;
        }
    }

    private void updateMarkModel(RadioGroup group, int id) {
        MarkModel element =(MarkModel) group.getTag();
        switch (id) {
            case R.id.mark2:
                element.setMark(2);
                break;
            case R.id.mark3:
                element.setMark(3);
                break;
            case R.id.mark4:
                element.setMark(4);
                break;
            case R.id.mark5:
                element.setMark(5);
                break;
        }
    }
    }
