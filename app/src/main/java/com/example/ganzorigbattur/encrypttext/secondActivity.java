package com.example.ganzorigbattur.encrypttext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class secondActivity extends AppCompatActivity {
    /* 128bits AES encryption will take input and key. It split input into 16 bytes of blocks
    and encrypt separately. The key is expected to by 128 bits. I treated a character as one byte.
    * */



    // when encrypt button clicked
    public void encryptClicked (View view){


        EditText myinput = findViewById(R.id.input);
        EditText mykey = findViewById(R.id.key);
        // get the key and input as string
        String inputStr = myinput.getText().toString();
        String keyStr = mykey.getText().toString();

        // if length is cannot be split by 16, adding black space in the end of string
        if (inputStr.length() % 16 != 0){
            int needToAdd = 16 - inputStr.length() % 16;
            for (int i = 0; i< needToAdd; i++){
                inputStr = inputStr + " ";
            }
        }

        AES mission = new AES(inputStr, keyStr);
        char[] inputChars = inputStr.toCharArray();
        char[] keyChars = keyStr.toCharArray();
        // getting the key that will be used for encryption
        int[] keyss = new int[16];
        for (int i =0 ; i <16; i++){
            keyss[i]= keyChars[i];
        }
        //setting up for encryption by split input by size 16 blocks
        int indexInput = 0;
        int indexResult = 0;
        int blocks = inputChars.length/16;

        // encrypting and replacing original by encrypted version.
        for (int k = 1; k <= blocks; k++){
            int[] in = new int[16];
            for (int i =0 ; i <16; i++){
                in[i]= inputChars[indexInput];
                indexInput++;
            }
            int [] result = mission.encrypt(in, keyss);
            for (int i =0 ; i <16; i++){
                inputChars[indexResult]= (char)result[i];
                indexResult++;
            }
        }
        String s = String.valueOf(inputChars);

        myinput.setText(s);
    }

    public void decryptClicked (View view){
        EditText myinput = findViewById(R.id.input);
        EditText mykey = findViewById(R.id.key);
        // get the key and input as string
        String inputStr = myinput.getText().toString();
        String keyStr = mykey.getText().toString();


        AES mission = new AES(inputStr, keyStr);
        char[] inputChars = inputStr.toCharArray();

        char[] keyChars = keyStr.toCharArray();
        // getting the key that will be used for encryption
        int[] keyss = new int[16];
        for (int i =0 ; i <16; i++){
            keyss[i]= keyChars[i];
        }
        //setting up for decryption by split input by size 16 blocks

        int indexInput = 0;
        int indexResult = 0;

        int blocks = inputChars.length/16;

        // decrypting and replacing original by encrypted version.
        for (int k = 1; k <= blocks; k++){
            int[] in = new int[16];
            for (int i =0 ; i <16; i++){
                in[i]= inputChars[indexInput];
                indexInput++;
            }
            int [] result = mission.decrypt(in, keyss);
            for (int i =0 ; i <16; i++){
                inputChars[indexResult]= (char)result[i];
                indexResult++;
            }
        }
        String s = String.valueOf(inputChars);
        myinput.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
