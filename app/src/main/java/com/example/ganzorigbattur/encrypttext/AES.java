package com.example.ganzorigbattur.encrypttext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by battu010 on 1/20/18.
 */
public class AES {
    String plaintext;
    String key;



    public static final int[] rcon = {0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
            0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
            0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
            0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
            0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
            0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
            0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
            0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
            0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
            0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
            0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
            0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
            0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb};


    public int[][] keySchedule(int[] key){
        int[][] allkeys = new int[44][4];
        if (key.length == 16){
            int keyIndex =0;

            // placing key to first 4 element of the allkeys array
            for (int i =0; i <4; i++){
                    for (int j=0; j <4; j++){
                        allkeys[i][j] = key[keyIndex];
                        keyIndex++;
                    }
            }

            int counter = 4;

            for (int i =1; i < 11; i++){
                allkeys[counter] = xorArrays(allkeys[counter-4], gFunction(allkeys[counter-1],i));
                counter++;
                allkeys[counter] = xorArrays(allkeys[counter-1], allkeys[counter-4]);
                counter++;
                allkeys[counter] = xorArrays(allkeys[counter-1], allkeys[counter-4]);
                counter++;
                allkeys[counter] = xorArrays(allkeys[counter-1], allkeys[counter-4]);
                counter++;
             }
        }

        int[][] finalkeys = new int[11][16];
        for (int i =0; i <11; i++){
            for(int k =0; k <16; k++){
                int position = 16*i+k;
                finalkeys[i][k] = allkeys[position/4][position%4];
            }
        }
        return finalkeys;
    }


    public int[] gFunction (int[] fourBytes, int round){
        int[] result = new int[4];
        if (fourBytes.length == 4){

            int holder = fourBytes[0];
            result[0] = data.sBox[fourBytes[1]] ^ rcon[round];
            result[1] = data.sBox[fourBytes[2]];
            result[2] = data.sBox[fourBytes[3]];
            result[3] = data.sBox[holder];

        }
        return result;
    }

    public static int[] xorArrays(int[] one, int[] two){

        int size = one.length;

        int[] result = new int[size];
        if(size == two.length){
            for (int i =0; i < size; i++) {
                result[i] = one[i] ^ two[i];
            }
        }
        return result;
    }

    public int[] byteSub(int[] input){
        int[] result = new int[16];

        for (int i = 0; i <16; i++){

            result[i] = data.sBox[input[i]];
        }

        return result;
    }

    public void invByteSub(int[] input){

        for (int i =0; i < 16; i++){
            input[i]= data.invSBox[input[i]];
        }
       // return input;
    }


    public void shiftRow(int[] input){
        int holder =0;
        for (int i =1 ; i <4; i++){
            if(i != 2){
                holder = input[i];
                input[i]= input[4*i+i];
                input[4*i+i]= input[(8*i+i)%16];
                input[(8*i+i)%16]= input[(12*i+i)%16];
                input[(12*i+i)%16] = holder;
            }else{
                holder = input[2];
                input[2]= input[10];
                input[10]= holder;
                holder = input[6];
                input[6]= input[14];
                input[14]= holder;
            }
        }
    }
    public void invShiftRow(int[] input){
        int[] holder = new int[16];

        for (int i =0 ; i <4; i++){
            for (int k = 0; k< 4; k++){
                holder[(i+4*k+4*i)%16] = input[i+4*k];
            }
        }
        for (int i =0; i < 16; i++){
            input[i] = holder[i];
        }
    }

    public void mixColumn(int[] input){
        // for each column
        for (int i = 0; i < 4; i++){
            // proceed matrix multiplication
            int initialIndx = 4*i;
            int one =   data.gfby2[input[initialIndx]] ^ data.gfby3[input[initialIndx+1]] ^ input[initialIndx+2] ^ input[initialIndx+3] ;
            int two =   data.gfby2[input[initialIndx+1]] ^ data.gfby3[input[initialIndx+2]] ^ input[initialIndx] ^ input[initialIndx+3];
            int three = data.gfby2[input[initialIndx+2]] ^ data.gfby3[input[initialIndx+3]] ^ input[initialIndx] ^ input[initialIndx+1];
            int four =  data.gfby2[input[initialIndx+3]] ^ data.gfby3[input[initialIndx]] ^ input[initialIndx+2] ^ input[initialIndx+1];

            input[initialIndx] = one;
            input[initialIndx+1] = two;
            input[initialIndx+2] = three;
            input[initialIndx+3] = four;

        }
    }

    public void invMixColumn(int[] input){
        for (int i = 0; i < 4; i++){
            // proceed matrix multiplication
            int initialIndx = 4*i;
            int one =   data.gfby14[input[initialIndx]] ^ data.gfby11[input[initialIndx+1]] ^ data.gfby13[input[initialIndx+2]] ^ data.gfby9[input[initialIndx+3]] ;
            int two =   data.gfby9[input[initialIndx]] ^ data.gfby14[input[initialIndx+1]] ^ data.gfby11[input[initialIndx+2]] ^ data.gfby13[input[initialIndx+3]] ;
            int three = data.gfby13[input[initialIndx]] ^ data.gfby9[input[initialIndx+1]] ^ data.gfby14[input[initialIndx+2]] ^ data.gfby11[input[initialIndx+3]] ;
            int four =  data.gfby11[input[initialIndx]] ^ data.gfby13[input[initialIndx+1]] ^ data.gfby9[input[initialIndx+2]] ^ data.gfby14[input[initialIndx+3]] ;

            input[initialIndx] = one;
            input[initialIndx+1] = two;
            input[initialIndx+2] = three;
            input[initialIndx+3] = four;

        }
    }
    public int[] encrypt(int[] input, int[] key){

        int [][] keys = keySchedule(key);

        if(input.length == 16){
            input = xorArrays(input, keys[0]);
            for (int round = 1; round <11; round++){

                //byte Substitution
                input = byteSub(input);
                //shiftRows
                shiftRow(input);
                //Mix Columnn
                mixColumn(input);
                //Xor with key
                input = xorArrays(input, keys[round]);
            }
            return input;

        }else{
            System.out.println("Input is not 16 byte block");
        }

        return null;
    }

    public int[] decrypt(int[] input, int[] key){

        int [][] keys = keySchedule(key);

        if(input.length ==16){

            for (int round =10; round > 0; round--){

                //key addition
                input = xorArrays(input,keys[round]);
                //InvMixColumn
                invMixColumn(input);
                //InvShiftRow
                invShiftRow(input);
                //InvByteSub
                invByteSub(input);
            }
            int[] result = xorArrays( input , keys[0]);
            return result;
            //return Arrays.toString(result);
        }

        return null;
    }
    AES(String input, String k){
        plaintext = input;
        key = k;
    }
}
