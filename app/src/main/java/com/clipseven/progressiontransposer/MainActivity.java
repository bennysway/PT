package com.clipseven.progressiontransposer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    String fromPrev;
    TextView chordFlipDisplay;
    TableRow rowOfNewChords;
    int found, maxSizeofChords, numOfChords=0;
    boolean transposeButtonAvail=false;
    TableRow transposeDisplay;
    Intent sendIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-2945410942325181~8105361959");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        Button abut = (Button) findViewById(R.id.abut);
        Button asbut = (Button) findViewById(R.id.asbut);
        Button bbut = (Button) findViewById(R.id.bbut);
        Button cbut = (Button) findViewById(R.id.cbut);
        Button csbut = (Button) findViewById(R.id.csbut);
        Button dbut = (Button) findViewById(R.id.dbut);
        Button dsbut = (Button) findViewById(R.id.dsbut);
        Button ebut = (Button) findViewById(R.id.ebut);
        Button fbut = (Button) findViewById(R.id.fbut);
        Button fsbut = (Button) findViewById(R.id.fsbut);
        Button gbut = (Button) findViewById(R.id.gbut);
        Button gsbut = (Button) findViewById(R.id.gsbut);
        TextView bigChordLetter = (TextView) findViewById(R.id.rootChordDisplay);
        rowOfNewChords = (TableRow) findViewById(R.id.choosenChordsRow);
        transposeDisplay = (TableRow) findViewById(R.id.transposeButtonDisplay);
        sendIntent = new Intent(this, Transposer.class);

        mainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        fromPrev = intent.getStringExtra("chord");
        chordFlipDisplay = (TextView) findViewById(R.id.chordPop);
        maxSizeofChords = 6;


        bigChordLetter.setText(fromPrev);
        setBgColor(fromPrev);

        abut.setOnClickListener(buttonPressed);
        asbut.setOnClickListener(buttonPressed);
        bbut.setOnClickListener(buttonPressed);
        cbut.setOnClickListener(buttonPressed);
        csbut.setOnClickListener(buttonPressed);
        dbut.setOnClickListener(buttonPressed);
        dsbut.setOnClickListener(buttonPressed);
        ebut.setOnClickListener(buttonPressed);
        fbut.setOnClickListener(buttonPressed);
        fsbut.setOnClickListener(buttonPressed);
        gbut.setOnClickListener(buttonPressed);
        gsbut.setOnClickListener(buttonPressed);
    }

    public void setBgColor(String s) {
        switch (s) {
            case "A":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lAbg));
                break;
            case "A#":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lAsbg));
                break;
            case "B":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lBbg));
                break;
            case "C":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lCbg));
                break;
            case "C#":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lCsbg));
                break;
            case "D":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lDbg));
                break;
            case "D#":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lDsbg));
                break;
            case "E":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lEbg));
                break;
            case "F":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lFbg));
                break;
            case "F#":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lFsbg));
                break;
            case "G":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lGbg));
                break;
            case "G#":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lGsbg));
                break;
            case "Ab":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lGsbg));
                break;
            case "Bb":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lAsbg));
                break;
            case "Db":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lCsbg));
                break;
            case "Eb":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lDsbg));
                break;
            case "Gb":
                mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lFsbg));
                break;


        }
    }

    public void popAnimateChordPop(View s) {
        String a = ((Button) s).getText().toString();
        chordFlipDisplay.setText(a);
        chordFlipDisplay.setTextColor((ContextCompat.getColor(this, R.color.circleTopFontColor)));
        chordFlipDisplay.setBackground((ContextCompat.getDrawable(this, R.drawable.chord_pop)));
        chordFlipDisplay.animate().scaleY(1f).
                withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        chordFlipDisplay.animate().alpha(0f).scaleX(1.15f).scaleY(1.15f).
                                withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        chordFlipDisplay.setScaleY(0f);
                                        chordFlipDisplay.setScaleX(1f);
                                        chordFlipDisplay.setAlpha(1f);
                                    }
                                });
                    }
                }).start();
    }

    public String getStringFromButton(View v){
        Button b = (Button)v;
        return b.getText().toString();
    }

    public void popAnimateCorrectChord(String a) {
        chordFlipDisplay.clearAnimation();
        chordFlipDisplay.setText(a);
        chordFlipDisplay.setTextColor((ContextCompat.getColor(this, R.color.chordpopRimCorrectFont)));
        chordFlipDisplay.setBackground((ContextCompat.getDrawable(this, R.drawable.chord_pop_correct)));
        chordFlipDisplay.setScaleY(0f);
        chordFlipDisplay.setScaleX(0f);
        chordFlipDisplay.animate().scaleY(1f).scaleX(1f).
                withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        chordFlipDisplay.animate().alpha(0f).scaleX(1.1f).scaleY(1.1f).
                                withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        chordFlipDisplay.setScaleY(0f);
                                        chordFlipDisplay.setScaleX(1f);
                                        chordFlipDisplay.setAlpha(1f);
                                        chordFlipDisplay.setTextColor((ContextCompat.getColor(MainActivity.this, R.color.circleTopFontColor)));
                                    }
                                });
                    }
                }).start();

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

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
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
    
    public boolean isNoteAccepted(int x){
        int test = getChordIndex(fromPrev);
        int [] defaultScaleIndex =  new int[] {0,2,4,5,7,9,11};
        for(int i=0;i<7;i++)
            defaultScaleIndex[i]+=test;
        for(int i=0;i<7;i++)
            defaultScaleIndex[i]%=12;
        for(int i=0;i<7;i++){
            if(x==defaultScaleIndex[i]){
                found = i;
                return true;

            }
        }

            return false;
    }

    public void addChordButton(String s){
        final Button btn = new Button(this);
        btn.setText(s);
        btn.setTextColor(ContextCompat.getColor(this, R.color.circleTopFontColor));
        btn.setBackground(getDrawable(R.drawable.chord_add_ripple));
        rowOfNewChords.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowOfNewChords.removeView(btn);
                numOfChords--;
                showTransposeButton();
            }
        });

    }

    public boolean isThereRoomForChords(){
        if(numOfChords>=maxSizeofChords){
            QuickToast(getResources().getString(R.string.MaxChordsQuote));
            return false;
        }
        else{
            numOfChords++;
            showTransposeButton();
            return true;

        }
    }

    public void showTransposeButton(){
        final Button t = new Button(this);
        t.setText("Transpose");
        t.setBackground(getDrawable(R.drawable.trans_ripple));
        t.setTextColor(ContextCompat.getColor(this, R.color.Fbg));
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent.putExtra("numOfChords",numOfChords);
                String [] chordStore = new String[numOfChords];
                for (int i = 0; i < numOfChords; i++) {
                    View v = rowOfNewChords.getChildAt(i);
                    Button x = (Button)v;
                    chordStore[i] = x.getText().toString();
                }
                sendIntent.putExtra("chordStore",chordStore);
                sendIntent.putExtra("rootChord",getChordIndex(fromPrev));
                startActivity(sendIntent);
            }
        });


        if(numOfChords>0&& !transposeButtonAvail){
            transposeDisplay.addView(t);
            t.animate().scaleX(.2f).scaleY(.2f).
                    withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            t.animate().scaleX(1f).scaleY(1f);
                        }
                    }).start();
            transposeButtonAvail = true;

        }
        else if(numOfChords<1 && transposeButtonAvail){
            transposeDisplay.removeAllViews();
            transposeButtonAvail = false;
        }
    }

    public View.OnClickListener buttonPressed = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            popAnimateChordPop(view);
            if(isNoteAccepted(getChordIndex(getStringFromButton(view)))){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int x;
                        String z;
                        x=getChordIndex(fromPrev);
                        z=chordNotation(x,found);
                        popAnimateCorrectChord(z);
                        if(isThereRoomForChords())
                            addChordButton(z);

                    }
                },500);
            }

        }
    };
}

