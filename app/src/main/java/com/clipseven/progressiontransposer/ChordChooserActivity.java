package com.clipseven.progressiontransposer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChordChooserActivity extends AppCompatActivity {

    int clickPresscounter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chord_chooser);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Button abut = (Button) findViewById(R.id.sabut);
        Button asbut = (Button) findViewById(R.id.sasbut);
        Button bbut = (Button) findViewById(R.id.sbbut);
        Button cbut = (Button) findViewById(R.id.scbut);
        Button csbut = (Button) findViewById(R.id.scsbut);
        Button dbut = (Button) findViewById(R.id.sdbut);
        Button dsbut = (Button) findViewById(R.id.sdsbut);
        Button ebut = (Button) findViewById(R.id.sebut);
        Button fbut = (Button) findViewById(R.id.sfbut);
        Button fsbut = (Button) findViewById(R.id.sfsbut);
        Button gbut = (Button) findViewById(R.id.sgbut);
        Button gsbut = (Button) findViewById(R.id.sgsbut);
        final TextView promptText = (TextView) findViewById(R.id.promptText);

        final Intent intent = new Intent(this, MainActivity.class);

        //Setting fonts//
        Typeface buttonFont = Typeface.createFromAsset(getAssets(),  "fonts/Mael.ttf");
        abut.setTypeface(buttonFont);
        asbut.setTypeface(buttonFont);
        bbut.setTypeface(buttonFont);
        cbut.setTypeface(buttonFont);
        csbut.setTypeface(buttonFont);
        dbut.setTypeface(buttonFont);
        dsbut.setTypeface(buttonFont);
        ebut.setTypeface(buttonFont);
        fbut.setTypeface(buttonFont);
        fsbut.setTypeface(buttonFont);
        gbut.setTypeface(buttonFont);
        gsbut.setTypeface(buttonFont);

        //Definining Long click Listeners//
        View.OnLongClickListener LongClicklistener = new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Button clickedButton = (Button) v;
                String buttonText = clickedButton.getText().toString();
                intent.putExtra("chord", buttonText);
                startActivity(intent);
                QuickToast("Using "+buttonText+" as the root Scale...");
                return true;
            }
        };
        View.OnClickListener shortClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickPresscounter%3==0){
                    promptText.animate().scaleX(2f).scaleY(2f).translationY(-20f).alpha(.3f).setDuration(300)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            promptText.animate().setStartDelay(1000).scaleX(1f).scaleY(1f).alpha(1f).setDuration(1000);
                        }
                    });
                }
                clickPresscounter++;

            }
        };

        //Implying Long Clicks//
        abut.setOnLongClickListener(LongClicklistener);
        asbut.setOnLongClickListener(LongClicklistener);
        bbut.setOnLongClickListener(LongClicklistener);
        cbut.setOnLongClickListener(LongClicklistener);
        csbut.setOnLongClickListener(LongClicklistener);
        dbut.setOnLongClickListener(LongClicklistener);
        dsbut.setOnLongClickListener(LongClicklistener);
        ebut.setOnLongClickListener(LongClicklistener);
        fbut.setOnLongClickListener(LongClicklistener);
        fsbut.setOnLongClickListener(LongClicklistener);
        gbut.setOnLongClickListener(LongClicklistener);
        gsbut.setOnLongClickListener(LongClicklistener);
        //Implying Short Clicks//
        abut.setOnClickListener(shortClickListener);
        asbut.setOnClickListener(shortClickListener);
        bbut.setOnClickListener(shortClickListener);
        cbut.setOnClickListener(shortClickListener);
        csbut.setOnClickListener(shortClickListener);
        dbut.setOnClickListener(shortClickListener);
        dsbut.setOnClickListener(shortClickListener);
        ebut.setOnClickListener(shortClickListener);
        fbut.setOnClickListener(shortClickListener);
        fsbut.setOnClickListener(shortClickListener);
        gbut.setOnClickListener(shortClickListener);
        gsbut.setOnClickListener(shortClickListener);



    }


    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }



}
