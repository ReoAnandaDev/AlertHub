package com.psik22a.uas.appssensorraysamaharani22a;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private TextView textViewStatus;

    private static final float FALL_THRESHOLD = 1.0f;
    private static final float GYRO_THRESHOLD = 1.5f;
    private static final String PHONE_NUMBER = "085134133968";
    private boolean hasSentSMS = false;
    private boolean hasPermissionSms = false;
    private boolean hasPermissionLocation = false;
    private boolean hasPermissionCall = false;

    private float[] gravity = new float[3];
    private float[] gyroValues = new float[3];

    private LocationManager locationManager;
    private double latitude;
    private double longitude;

    private static final int RESET_DELAY_MS = 30000;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = findViewById(R.id.textViewStatus);

        checkPermissions();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        requestLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensorListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensorListeners();
    }

    private void registerSensorListeners() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void unregisterSensorListeners() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroValues = event.values.clone();
        }

        float accelerationMagnitude = (float) Math.sqrt(gravity[0] * gravity[0] + gravity[1] * gravity[1] + gravity[2] * gravity[2]);

        String locationStatus = "Latitude: " + latitude + "\nLongitude: " + longitude;

        if (accelerationMagnitude < FALL_THRESHOLD && !hasSentSMS &&
                (Math.abs(gyroValues[0]) > GYRO_THRESHOLD || Math.abs(gyroValues[1]) > GYRO_THRESHOLD || Math.abs(gyroValues[2]) > GYRO_THRESHOLD)) {

            textViewStatus.setText("Status: Jatuh Terdeteksi!\n" + locationStatus);
            sendSMS();
            makePhoneCall();
            hasSentSMS = true;

            handler.postDelayed(() -> hasSentSMS = false, RESET_DELAY_MS);
        } else {
            textViewStatus.setText("Status: Normal\n" + locationStatus);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    private void sendSMS() {
        if (hasPermissionSms) {
            String message = "Handphone Anda Terjatuh! Lokasi Jatuh: https://maps.google.com/?q=" + latitude + "," + longitude;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(PHONE_NUMBER, null, message, null, null);
            Toast.makeText(this, "SMS terkirim!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Izin SMS tidak diberikan!", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePhoneCall() {
        if (hasPermissionCall) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin panggilan belum diberikan!", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Izin panggilan tidak diberikan!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermissions() {
        checkSmsPermission();
        checkLocationPermission();
        checkCallPermission();
    }

    private void checkSmsPermission() {
        hasPermissionSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermissionSms) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            saveSmsPermission();
        }
    }

    private void checkLocationPermission() {
        hasPermissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            saveLocationPermission();
        }
    }

    private void checkCallPermission() {
        hasPermissionCall = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermissionCall) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
        } else {
            saveCallPermission();
        }
    }

    private void saveSmsPermission() {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit().putBoolean("sms_permission", true).apply();
    }

    private void saveLocationPermission() {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit().putBoolean("location_permission", true).apply();
    }

    private void saveCallPermission() {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit().putBoolean("call_permission", true).apply();
    }

    private void requestLocationUpdates() {
        if (hasPermissionLocation) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }

                @Override
                public void onProviderEnabled(@NonNull String provider) { }

                @Override
                public void onProviderDisabled(@NonNull String provider) { }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                hasPermissionSms = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (hasPermissionSms) saveSmsPermission();
                break;
            case 2:
                hasPermissionLocation = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (hasPermissionLocation) saveLocationPermission();
                break;
            case 3:
                hasPermissionCall = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (hasPermissionCall) saveCallPermission();
                break;
        }
    }
}
