package com.clipseven.progressiontransposer;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import java.io.IOException;
import java.util.Objects;

public class Transposer extends AppCompatActivity {


    Intent receivedIntent;
    NumberPicker chordPicker;
    int numOfChords,rootChordIndex;
    int [] indexArray;
    String [] chordStore;
    String letter;
    TableRow chordDisplay;
    TextView displayLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transposer);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2945410942325181~8105361959");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AdView mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .build();
        mAdView.loadAd(adRequest);



        receivedIntent = getIntent();
        displayLetter = (TextView) findViewById(R.id.letterDisplay);
        Typeface font = Typeface.createFromAsset(getAssets(),  "fonts/nemo.ttf");



        numOfChords = receivedIntent.getIntExtra("numOfChords",0);
        chordStore = receivedIntent.getStringArrayExtra("chordStore");
        rootChordIndex = receivedIntent.getIntExtra("rootChord",0);
        letter = getNoteString(rootChordIndex);
        displayLetter.setText(letter);
        displayLetter.setAlpha(.1f);
        displayLetter.setTypeface(font);
        chordDisplay = (TableRow) findViewById(R.id.chordCatcher);
        indexArray = new int[numOfChords];
        chordPicker = (NumberPicker) findViewById(R.id.chordPicker);



        chordPicker.setMinValue(0);
        chordPicker.setMaxValue(11);
        chordPicker.setDisplayedValues( new String[] {
                "A","A#","B","C","C#","D","D#","E","F","F#","G","G#" } );
        chordPicker.setValue(rootChordIndex);
        storeToArray();

        AddChords();
        chordPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                changeDisplay();
            }
        });






    }

    public void changeDisplay(){
        chordDisplay.removeAllViews();
        rootChordIndex = chordPicker.getValue();
        AddChords();
        changeLetter();
    }

    public void changeLetter(){
        displayLetter.animate().alpha(0f).translationXBy(-100f).setDuration(1500).
                withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        displayLetter.setText(getNoteString(rootChordIndex));
                        displayLetter.setX(0f);
                        displayLetter.animate().alpha(.2f);
                    }
                }).start();
    }

    public void AddChords(){
        for(int i=0;i<numOfChords;i++){
            final TextView txt = new TextView(this);
            txt.setTextColor(setBgColor("white"));
            txt.setTextSize(50f);
            txt.setGravity(Gravity.CENTER);
            txt.setText(chordNotation(rootChordIndex,indexArray[i]));
            txt.setBackgroundColor(setBgColor(chordNotation(rootChordIndex,indexArray[i])));
            chordDisplay.addView(txt);
        }
    }

    public int getIndex(int base,String test){
        int a=0;
        String [][] major = new String[12][7];
        major[0][0]="A";major[0][1]="Dm";major[0][2]="C#m";major[0][3]="D";major[0][4]="E";major[0][5]="F#m";major[0][6]="G#dim";
        major[1][0]="A#";major[1][1]="Cm";major[1][2]="Dm";major[1][3]="D#";major[1][4]="F";major[1][5]="Gm";major[1][6]="Adim";
        major[2][0]="B";major[2][1]="C#m";major[2][2]="D#m";major[2][3]="E";major[2][4]="F#";major[2][5]="G#m";major[2][6]="A#dim";
        major[3][0]="C";major[3][1]="Dm";major[3][2]="Em";major[3][3]="F";major[3][4]="G";major[3][5]="Am";major[3][6]="Bdim";
        major[4][0]="C#";major[4][1]="D#m";major[4][2]="Fm";major[4][3]="F#";major[4][4]="G#";major[4][5]="A#m";major[4][6]="Cdim";
        major[5][0]="D";major[5][1]="Em";major[5][2]="F#m";major[5][3]="G";major[5][4]="A";major[5][5]="Bm";major[5][6]="C#dim";
        major[6][0]="D#";major[6][1]="Fm";major[6][2]="Gm";major[6][3]="G#";major[6][4]="A#";major[6][5]="Cm";major[6][6]="Ddim";
        major[7][0]="E";major[7][1]="F#m";major[7][2]="G#m";major[7][3]="A";major[7][4]="B";major[7][5]="C#m";major[7][6]="D#dim";
        major[8][0]="F";major[8][1]="Gm";major[8][2]="Am";major[8][3]="A#";major[8][4]="C";major[8][5]="Dm";major[8][6]="Edim";
        major[9][0]="F#";major[9][1]="G#m";major[9][2]="A#m";major[9][3]="B";major[9][4]="C#";major[9][5]="D#m";major[9][6]="Fdim";
        major[10][0]="G";major[10][1]="Am";major[10][2]="Bm";major[10][3]="C";major[10][4]="D";major[10][5]="Em";major[10][6]="F#dim";
        major[11][0]="G#";major[11][1]="A#m";major[11][2]="Cm";major[11][3]="C#";major[11][4]="D#";major[11][5]="Fm";major[11][6]="Gdim";

        for(int i=0; i<7; i++)
            if(Objects.equals(major[base][i], test)){
                a=i;
            }
        return a;
    }

    public String getNoteString(int x){
        switch (x){
            case 0:
                return "A";
            case 1:
                return "A#";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "C#";
            case 5:
                return "D";
            case 6:
                return "D#";
            case 7:
                return "E";
            case 8:
                return "F";
            case 9:
                return "F#";
            case 10:
                return "G";
            case 11:
                return "G#";

        }
        return "A";
    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public String InttoString(int x){
        return Integer.toString(x);
    }

    public int setBgColor(String s) {
        switch (s) {
            case "A":
                return ContextCompat.getColor(this, R.color.Abg);
            case "A#":
                return ContextCompat.getColor(this, R.color.Asbg);
            case "B":
                return ContextCompat.getColor(this, R.color.Bbg);
            case "C":
                return ContextCompat.getColor(this, R.color.Cbg);
            case "C#":
                return ContextCompat.getColor(this, R.color.Csbg);
            case "D":
                return ContextCompat.getColor(this, R.color.Dbg);
            case "D#":
                return ContextCompat.getColor(this, R.color.Dsbg);
            case "E":
                return ContextCompat.getColor(this, R.color.Ebg);
            case "F":
                return ContextCompat.getColor(this, R.color.Fbg);
            case "F#":
                return ContextCompat.getColor(this, R.color.Fsbg);
            case "G":
                return ContextCompat.getColor(this, R.color.Gbg);
            case "G#":
                return ContextCompat.getColor(this, R.color.Gsbg);
            case "Ab":
                return ContextCompat.getColor(this, R.color.Gsbg);
            case "Bb":
                return ContextCompat.getColor(this, R.color.Asbg);
            case "Db":
                return ContextCompat.getColor(this, R.color.Csbg);
            case "Eb":
                return ContextCompat.getColor(this, R.color.Dsbg);
            case "Gb":
                return ContextCompat.getColor(this, R.color.Fsbg);

            case "Am":
                return ContextCompat.getColor(this, R.color.Abg);
            case "A#m":
                return ContextCompat.getColor(this, R.color.Asbg);
            case "Bm":
                return ContextCompat.getColor(this, R.color.Bbg);
            case "Cm":
                return ContextCompat.getColor(this, R.color.Cbg);
            case "C#m":
                return ContextCompat.getColor(this, R.color.Csbg);
            case "Dm":
                return ContextCompat.getColor(this, R.color.Dbg);
            case "D#m":
                return ContextCompat.getColor(this, R.color.Dsbg);
            case "Em":
                return ContextCompat.getColor(this, R.color.Ebg);
            case "Fm":
                return ContextCompat.getColor(this, R.color.Fbg);
            case "F#m":
                return ContextCompat.getColor(this, R.color.Fsbg);
            case "Gm":
                return ContextCompat.getColor(this, R.color.Gbg);
            case "G#m":
                return ContextCompat.getColor(this, R.color.Gsbg);
            case "Abm":
                return ContextCompat.getColor(this, R.color.Gsbg);
            case "Bbm":
                return ContextCompat.getColor(this, R.color.Asbg);
            case "Dbm":
                return ContextCompat.getColor(this, R.color.Csbg);
            case "Ebm":
                return ContextCompat.getColor(this, R.color.Dsbg);
            case "Gbm":
                return ContextCompat.getColor(this, R.color.Fsbg);

            case "Adim":
                return ContextCompat.getColor(this, R.color.Abg);
            case "A#dim":
                return ContextCompat.getColor(this, R.color.Asbg);
            case "Bdim":
                return ContextCompat.getColor(this, R.color.Bbg);
            case "Cdim":
                return ContextCompat.getColor(this, R.color.Cbg);
            case "C#dim":
                return ContextCompat.getColor(this, R.color.Csbg);
            case "Ddim":
                return ContextCompat.getColor(this, R.color.Dbg);
            case "D#dim":
                return ContextCompat.getColor(this, R.color.Dsbg);
            case "Edim":
                return ContextCompat.getColor(this, R.color.Ebg);
            case "Fdim":
                return ContextCompat.getColor(this, R.color.Fbg);
            case "F#dim":
                return ContextCompat.getColor(this, R.color.Fsbg);
            case "Gdim":
                return ContextCompat.getColor(this, R.color.Gbg);
            case "G#dim":
                return ContextCompat.getColor(this, R.color.Gsbg);
            case "Abdim":
                return ContextCompat.getColor(this, R.color.Gsbg);
            case "Bbdim":
                return ContextCompat.getColor(this, R.color.Asbg);
            case "Dbdim":
                return ContextCompat.getColor(this, R.color.Csbg);
            case "Ebdim":
                return ContextCompat.getColor(this, R.color.Dsbg);
            case "Gbdim":
                return ContextCompat.getColor(this, R.color.Fsbg);


        }
        return ContextCompat.getColor(this, R.color.white);
    }

    public String chordNotation(int x,int y){
        String [][] major = new String[12][7];
        major[0][0]="A";major[0][1]="Dm";major[0][2]="C#m";major[0][3]="D";major[0][4]="E";major[0][5]="F#m";major[0][6]="G#dim";
        major[1][0]="A#";major[1][1]="Cm";major[1][2]="Dm";major[1][3]="D#";major[1][4]="F";major[1][5]="Gm";major[1][6]="Adim";
        major[2][0]="B";major[2][1]="C#m";major[2][2]="D#m";major[2][3]="E";major[2][4]="F#";major[2][5]="G#m";major[2][6]="A#dim";
        major[3][0]="C";major[3][1]="Dm";major[3][2]="Em";major[3][3]="F";major[3][4]="G";major[3][5]="Am";major[3][6]="Bdim";
        major[4][0]="C#";major[4][1]="D#m";major[4][2]="Fm";major[4][3]="F#";major[4][4]="G#";major[4][5]="A#m";major[4][6]="Cdim";
        major[5][0]="D";major[5][1]="Em";major[5][2]="F#m";major[5][3]="G";major[5][4]="A";major[5][5]="Bm";major[5][6]="C#dim";
        major[6][0]="D#";major[6][1]="Fm";major[6][2]="Gm";major[6][3]="G#";major[6][4]="A#";major[6][5]="Cm";major[6][6]="Ddim";
        major[7][0]="E";major[7][1]="F#m";major[7][2]="G#m";major[7][3]="A";major[7][4]="B";major[7][5]="C#m";major[7][6]="D#dim";
        major[8][0]="F";major[8][1]="Gm";major[8][2]="Am";major[8][3]="A#";major[8][4]="C";major[8][5]="Dm";major[8][6]="Edim";
        major[9][0]="F#";major[9][1]="G#m";major[9][2]="A#m";major[9][3]="B";major[9][4]="C#";major[9][5]="D#m";major[9][6]="Fdim";
        major[10][0]="G";major[10][1]="Am";major[10][2]="Bm";major[10][3]="C";major[10][4]="D";major[10][5]="Em";major[10][6]="F#dim";
        major[11][0]="G#";major[11][1]="A#m";major[11][2]="Cm";major[11][3]="C#";major[11][4]="D#";major[11][5]="Fm";major[11][6]="Gdim";
        return major[x][y];
    }

    public int getChordIndex(String s){
        switch (s){
            case "A":
                return 0;
            case "A#":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "C#":
                return 4;
            case "D":
                return 5;
            case "D#":
                return 6;
            case "E":
                return 7;
            case "F":
                return 8;
            case "F#":
                return 9;
            case "G":
                return 10;
            case "G#":
                return 11;
            case "Ab":
                return 11;
            case "Bb":
                return 1;
            case "Db":
                return 4;
            case "Eb":
                return 6;
            case "Gb":
                return 9;
        }
        return 1;

    }

    public void storeToArray(){
        for(int i=0;i<numOfChords;i++){
            indexArray[i] = getIndex(rootChordIndex,chordStore[i]);
        }
    }


    ///////////////



}
