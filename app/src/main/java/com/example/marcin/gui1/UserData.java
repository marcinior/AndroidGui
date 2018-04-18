package com.example.marcin.gui1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UserData extends AppCompatActivity {
    boolean nameFlag;
    boolean surnameFlag;
    boolean countFlag;
    final int REQUEST_CODE = 1;

    boolean checkTheCorrectness(EditText view, String message) {
        if (view.getText().toString().isEmpty()) {
            findViewById(R.id.buttonMarks).setVisibility(View.INVISIBLE);
            Toast.makeText(UserData.this, "Uzupełnij pole : " + message, Toast.LENGTH_LONG).show();
            return false;
        } else if (!view.getText().toString().matches("[A-Z]{1}[a-ząćżźśółęń]{2,}")) {
            findViewById(R.id.buttonMarks).setVisibility(View.INVISIBLE);
            Toast.makeText(UserData.this, message + " musi zaczynać się z dużej litery i nie może zawierać cyfr i znaków specjalnych", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Button button = (Button) findViewById(R.id.buttonMarks);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserData.this, ListOfMarks.class);
                int count = Integer.parseInt(((EditText) findViewById(R.id.countOfMarks)).getText().toString());
                intent.putExtra("countOfMarks", count);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final EditText name = (EditText) findViewById(R.id.name);
        final EditText surname = (EditText) findViewById(R.id.surname);
        final EditText countOfMarks = (EditText) findViewById(R.id.countOfMarks);

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!view.hasFocus()) {

                    if (view.getId() == name.getId()) {
                        nameFlag = checkTheCorrectness(name, "Imie");
                    } else {
                        if (view.getId() == surname.getId()) {
                            surnameFlag = checkTheCorrectness(surname, "Nazwisko");
                        } else {
                            countFlag = checkTheCorrectness(countOfMarks, "liczba ocen");
                        }
                    }

                    if (nameFlag && surnameFlag && countFlag) {
                        findViewById(R.id.buttonMarks).setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        name.setOnFocusChangeListener(onFocusChangeListener);
        surname.setOnFocusChangeListener(onFocusChangeListener);
        countOfMarks.setOnFocusChangeListener(onFocusChangeListener);

        countOfMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (countOfMarks.getText().toString().isEmpty()) {
                    countFlag = false;
                    Toast.makeText(UserData.this, "Uzupełnij pole liczba ocen", Toast.LENGTH_LONG).show();
                    findViewById(R.id.buttonMarks).setVisibility(View.INVISIBLE);
                } else {
                    if (!countOfMarks.getText().toString().matches("([5-9])|(1[0-5])")) {
                        countFlag = false;
                        Toast.makeText(UserData.this, "Liczba ocen musi być liczbą i zawierać się w przedziale <5,15>", Toast.LENGTH_LONG).show();
                        findViewById(R.id.buttonMarks).setVisibility(View.INVISIBLE);
                    } else {
                        countFlag = true;

                        if (nameFlag && surnameFlag) {
                            findViewById(R.id.buttonMarks).setVisibility(View.VISIBLE);
                        }
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int taskCode, int resultCode, Intent data) {
        super.onActivityResult(taskCode, resultCode, data);

        if (taskCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            double average = bundle.getDouble("average");
            Button successButton = (Button) findViewById(R.id.succesButton);
            Button failButton = (Button) findViewById(R.id.failButton);
            ((Button) findViewById(R.id.buttonMarks)).setVisibility(Button.INVISIBLE);
            ((EditText) findViewById(R.id.name)).setEnabled(false);
            ((EditText) findViewById(R.id.surname)).setEnabled(false);
            ((EditText) findViewById(R.id.countOfMarks)).setEnabled(false);
            ((TextView) findViewById(R.id.averageLabel)).setText("Twoja średnia to: " + average);

            if (average >= 3) {
                successButton.setVisibility(Button.VISIBLE);
            } else {
                failButton.setVisibility(Button.VISIBLE);
            }

            successButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UserData.this, "Gratulacje! Otrzymujesz zaliczenie", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            failButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UserData.this, "Wysyłam podanie o zaliczenie warunkowe", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String average = ((TextView) findViewById(R.id.averageLabel)).getText().toString();
        outState.putString("average", average);
        int visibleButtonId = 0;

        if (((Button) findViewById(R.id.buttonMarks)).getVisibility() == Button.VISIBLE) {
            visibleButtonId = R.id.buttonMarks;
        } else if (((Button) findViewById(R.id.succesButton)).getVisibility() == Button.VISIBLE) {
            visibleButtonId = R.id.succesButton;
        } else if (((Button) findViewById(R.id.failButton)).getVisibility() == Button.VISIBLE) {
            visibleButtonId = R.id.failButton;
        }
        outState.putInt("buttonId", visibleButtonId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        ((TextView) findViewById(R.id.averageLabel)).setText(saveInstanceState.getString("average"));

        if (saveInstanceState.getInt("buttonId") != 0) {
            Button visibleButton = (Button) findViewById(saveInstanceState.getInt("buttonId"));
            visibleButton.setVisibility(Button.VISIBLE);
        }
    }
}
