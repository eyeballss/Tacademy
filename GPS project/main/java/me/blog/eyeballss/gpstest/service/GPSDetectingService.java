package me.blog.eyeballss.gpstest.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;

import com.squareup.otto.Bus;

import java.security.Provider;

import me.blog.eyeballss.gpstest.Util;

//서비스는 백그라운드에서 작동.
//어떤 동작을 취하고 결과를 보내는 등의 처리를 함
public class GPSDetectingService extends Service implements LocationListener {
    LocationManager locationManager;
    final long MIN_UPDATE_TIME = 1000 * 60 * 1; //1000ms = 1초, 즉 1분 단위로 갱신한다는 의미.
    final float MIN_UPDATE_DISTANCE = 10.0f; // 10m 단위로 갱신한다는 의미.

    public GPSDetectingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //서비스가 여기서부터 동작.

        initLocation();

        return super.onStartCommand(intent, flags, startId);


    }

    private void initLocation() {
        //위치 매니저를 획득
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //위치 정보를 제공하는 공급자(3개 : gps, wifi, 통신망)의 가용 여부를 획득!
        boolean isGPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);         //센서
        boolean isPassOn = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);    //기지국
        boolean isNetOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);    //wifi

        //각 공급자별로 처리를 함.
        if (!isGPSOn && !isNetOn && !isPassOn) {
            sendGPSBus(null);
            return;
        }

        String provider = null;
        if (isGPSOn) {
            provider = LocationManager.GPS_PROVIDER;
        }
        if (isPassOn) {
            provider = LocationManager.PASSIVE_PROVIDER;
        }
        if (isNetOn) {
            provider = LocationManager.NETWORK_PROVIDER;
        }

        if (provider != null) {

            locationManager.requestLocationUpdates(provider,
                    MIN_UPDATE_TIME,
                    MIN_UPDATE_DISTANCE,
                    this); //빨간 줄 뜨는 이유는, 퍼미션때문인 경고. 이미 앞서 퍼미션을 받았으므로 그냥 빨간 줄 무시하고 진행하면 됨
            Location location = locationManager.getLastKnownLocation(provider);
            sendGPSBus(location);
        }

        //어느 쪽이던 데이터가 오면 otto bus 로 전송! Main이 받을 수 있도록.


    }

    private void freeLocation() {
        //각종 설정된 리스너들을 해제! 및 detecting 중단!
        locationManager.removeUpdates(this);
    }

    private void sendGPSBus(Location location){
        if(location==null) return;

        //GPS 값을 bus 로 전송! Main 이 받을꺼야.
        Util.getInstance().getGpsBusFromService().post(location);
    }


    //서비스가 중단될 때 이벤트들이 모조리 해제.
    public void onDestroy() {
        super.onDestroy();
        freeLocation();
    }

    //아래는 LocationListener 의 methods.  ----------------------------------------------------------------
    @Override
    public void onLocationChanged(Location location) {
        sendGPSBus(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //위는 LocationListener 의 methods.  ----------------------------------------------------------------

}
