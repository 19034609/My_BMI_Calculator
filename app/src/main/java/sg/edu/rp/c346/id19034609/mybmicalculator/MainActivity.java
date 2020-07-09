package sg.edu.rp.c346.id19034609.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tvLastDate, tvLastBMI, tvBMIresut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvLastDate = findViewById(R.id.textViewLastDate);
        tvLastBMI = findViewById(R.id.textViewLastBMI);
        tvBMIresut = findViewById(R.id.textViewBMIresult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" + (now.get(Calendar.MONTH)+1) + "/" + now.get(Calendar.YEAR)
                        + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);

                float floatWeight = Float.parseFloat(etWeight.getText().toString());
                float floatHeight = Float.parseFloat(etHeight.getText().toString());
                float BMI = floatWeight / (floatHeight * floatHeight);

                String msg = "";
                if (BMI < 18.5) {
                    msg = "You are underweight";
                }
                else if (BMI <= 24.9) {
                    msg = "Your BMI is normal";
                }
                else  if (BMI <= 29.9) {
                    msg = "You are overweight";
                }
                else if (BMI >= 30) {
                    msg = "You are obese";
                }
                else {
                    msg = "Error";
                }

                tvLastDate.setText(getString(R.string.last_date) + datetime);
                tvLastBMI.setText(getString(R.string.last_bmi) + String.format("%.3f", BMI));
                tvBMIresut.setText(msg);
                etWeight.setText(null);
                etHeight.setText(null);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(null);
                etHeight.setText(null);
                tvLastDate.setText(getString(R.string.last_date));
                tvLastBMI.setText(getString(R.string.last_bmi));
                tvBMIresut.setText(null);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        String lastDate = tvLastDate.getText().toString();
        String lastBMI = tvLastBMI.getText().toString();
        String lastResult = tvBMIresut.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("lastDate", lastDate);
        prefEdit.putString("lastBMI", lastBMI);
        prefEdit.putString("lastResult", lastResult);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String msgLastDate = prefs.getString("lastDate", getString(R.string.last_date));
        String msgLastBMI = prefs.getString("lastBMI", getString(R.string.last_bmi));
        String msgLastResult = prefs.getString("lastResult", null);
        tvLastDate.setText(msgLastDate);
        tvLastBMI.setText(msgLastBMI);
        tvBMIresut.setText(msgLastResult);
    }
}
