package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] stanowiska = new String[]{"Kierownik","Starszy programista", "Młodszy programista","Tester"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stanowiska);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        Button generateBtn = findViewById(R.id.generatePassword);
        Button accept = findViewById(R.id.accept);
        EditText nameText = findViewById(R.id.imieInput);
        EditText surnameText = findViewById(R.id.nazwiskoInput);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText lengthOfPassword = findViewById(R.id.iloscZnakow);
                String lengthText = lengthOfPassword.getText().toString().trim();
                if(lengthText.isEmpty()){
                    return;
                }
                int length = Integer.parseInt(lengthText);

                CheckBox digit = findViewById(R.id.digit);
                CheckBox bigLetter = findViewById(R.id.bigLetters);
                CheckBox specialCharacters = findViewById(R.id.specialCharacters);

                password = generatePassword(length, digit.isChecked(), bigLetter.isChecked(), specialCharacters.isChecked());
                Toast.makeText(MainActivity.this, password,Toast.LENGTH_LONG).show();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString().trim();
                String surname = surnameText.getText().toString().trim();
                String occupation = spinner.getSelectedItem().toString();
                Toast.makeText(MainActivity.this,"Imię:"+name+", Nazwisko:"+surname+", Zawód:"+occupation+", Hasło:"+password,Toast.LENGTH_LONG).show();
            }
        });
    }

    public String generatePassword(int lengthOfPassword,Boolean isDigit, Boolean isBigLetter,Boolean isSpecialCharacters){
        char[] letters = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        byte[] digits = {0,1,2,3,4,5,6,7,8,9};
        char[] specialCharacters = {'!','@','#','$','%','^','&','(',')','_','+','-','='};
        int placeOfDigit = -1;
        int placeOfBigLetter = -1;
        int placeOfSpecialCharacters = -1;
        String generatePassword = "";

        if(isDigit){
            placeOfDigit = getRandomNumber(lengthOfPassword);
        }
        if(isBigLetter){
            do{
                placeOfBigLetter = getRandomNumber(lengthOfPassword);
            } while(placeOfBigLetter == placeOfDigit);
        }
        if(isSpecialCharacters){
            do{
                placeOfSpecialCharacters = getRandomNumber(lengthOfPassword);
            } while((placeOfSpecialCharacters == placeOfBigLetter) || (placeOfSpecialCharacters == placeOfDigit));
        }

        System.out.println(isDigit);
        for(int i=0;i<lengthOfPassword;i++){
            if(i==placeOfDigit){
                generatePassword+=digits[getRandomNumber(digits.length)];
            } else if(i==placeOfBigLetter){
                generatePassword+=String.valueOf(letters[getRandomNumber(letters.length)]).toUpperCase();
            } else if(i==placeOfSpecialCharacters){
                generatePassword+=specialCharacters[getRandomNumber(specialCharacters.length)];
            } else{
                generatePassword+=letters[getRandomNumber(letters.length)];
            }
        }
        return generatePassword;
    }

    public int getRandomNumber(int length){
        return (int) (Math.random() * length);
    }
}