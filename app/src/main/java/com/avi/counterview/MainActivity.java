package com.avi.counterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avi.numbercounterview.NumberCounterView;
import com.avi.numbercounterview.OnValueChangeListener;

public class MainActivity extends AppCompatActivity {

    private NumberCounterView numberCounterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberCounterView = findViewById(R.id.number_counter);
        numberCounterView.setValueChangeListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(View view, int previousValue, int currentValue) {

                if (view instanceof AppCompatImageView) {
                    Log.i("Image View", "onValueChange: Image view clicked");
                }
                Toast.makeText(MainActivity.this, "Previous Value:" + previousValue
                        + "CurrentValue:" + currentValue, Toast.LENGTH_SHORT).show();
                Log.i("Value: ", String.valueOf(numberCounterView.getValue()));
            }
        });
    }
}
