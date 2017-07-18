package me.blog.eyeballss.gpstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // 이렇게해서 view를 찾는다. 지도를 소유하고 있는 fragment를 획득함!
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //지도를 동기화시켜줌.
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //28번 라인 끝내고 map이 준비가 되면 호출되는 콜백 메소드. OnMapReadyCallback을 implements 하여 사용.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //지도 자체를 가리키는 객체
        mMap = googleMap;

        //지도에 진입할 때는 일반적으로 사용자의 GPS 값을 획득한 후에 들어감.
        mMap.setMyLocationEnabled(true); //이 메소드를 두면 내 위치로 가기 버튼이 활성화됨.

        // Add a marker in Sydney and move the camera
        //특정 위치 마킹하여 화면 중앙에 보여줌
//        LatLng sydney = new LatLng(-34, 151); //LatLng : latitude, longitude
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
