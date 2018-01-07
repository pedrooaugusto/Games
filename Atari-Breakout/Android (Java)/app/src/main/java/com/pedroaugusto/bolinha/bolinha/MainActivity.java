package com.pedroaugusto.bolinha.bolinha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
    public static int PADRAO_BARRA = 0;
    public static boolean ENABLED_SOM = true;
    public static int TYPE_SOUND = 0;
    private CheckBox ligado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ligado = (CheckBox) findViewById(R.id.checkBox1);
        ligado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ((RadioGroup) MainActivity.this.findViewById(R.id.radioGroup1)).check(R.id.radioButton3);
                else
                    ((RadioGroup) MainActivity.this.findViewById(R.id.radioGroup1)).clearCheck();
            }
        });
    }
    public void start(View v)
    {
        ENABLED_SOM = ligado.isChecked();
        PADRAO_BARRA = ((RadioButton) findViewById(R.id.radioButton1)).isChecked() ? 0 : 1;
        TYPE_SOUND = ((RadioButton) findViewById(R.id.radioButton3)).isChecked() ? 0 : 1;
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }
}
