package com.example.sensortemperatura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView tvTemperatura, tvDiagnostico;
    private SensorManager sensorManager;
    private Sensor temperaturaSensor;
    private int temperatura = 0;
    private boolean sensorDisponible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDiagnostico = (TextView) findViewById(R.id.tvDiagnostico);
        tvTemperatura = (TextView) findViewById(R.id.tvTemperatura);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            temperaturaSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorDisponible = true;
        } else {
            Toast.makeText(this, "Su dispositivo no cuenta con un sensor de temperatura", Toast.LENGTH_SHORT).show();
            sensorDisponible = false;
        }

        if (sensorDisponible) {
            sensorManager.registerListener(this, temperaturaSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        temperatura = (int) event.values[0];
        if (temperatura < 20) {
            tvDiagnostico.setText(getString(R.string.text_view_temperatura_baja));
        } else if (temperatura < 36){
            tvDiagnostico.setText(getString(R.string.text_view_temperatura_normal));
        } else {
            tvDiagnostico.setText(getString(R.string.text_view_temperatura_alta));
        }
        tvTemperatura.setText(temperatura + " °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}