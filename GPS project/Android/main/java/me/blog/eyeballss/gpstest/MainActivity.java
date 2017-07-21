package me.blog.eyeballss.gpstest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.blog.eyeballss.gpstest.service.GPSDetectingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION_CODES.M;
import static me.blog.eyeballss.gpstest.R.id.map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    // 0 : GPS 설정 없이 그냥 들어갔을 때
    // 1 : GPS 설정을 on 하고 돌아온 후
    private int flagGPSStatus=0;
    EditText addr;
    private GoogleMap mMap;
    private boolean isFirstGPSLoad;
    RecyclerView recyclerView;
    CoffeeAdapter coffeeAdapter;
//    ResCoffeeStoresModel coffeeStores;
    ArrayList<CoffeeStoreModel> coffeeStores;
    Spinner cateSpinner;
    EditText dist;
    LatLng myLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstGPSLoad = true;

        //버스를 만들고 등록함. 서비스에서부터 오는 정보를 이 버스가 계속 받아옴.
        Util.getInstance().getGpsBusFromService().register(this);

        //여기서부터 지도 끌고오는 부분------------------------------------------------------------------------
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // 이렇게해서 view를 찾는다. 지도를 소유하고 있는 fragment를 획득함!
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);

        //지도를 동기화시켜줌.
        mapFragment.getMapAsync(this);
        //까지 지도.-------------------------------------------------------------------------------------------

        initUI();
        //네트워크 체크(정확한 내 위치 계산을 위하여)
        //GPS 체크. 디바이스 옵션의 '위치' 가 꺼져있는지 켜져있는지
        isAvailableGPS();
        //OS 버전 체크(6.0 이상인지) (동의 여부까지 확인)
        //GPS detecting 시작! (구/신 버전으로 나뉨)
        //geo coder (GPS <-> 주소 서로 변환해주는 코더)를 얻음
        //상기 작업이 2초 이내에!!
        //OTTO bus 를 이용하여 비동기적 상황의 이벤트를 전달하는 루틴으로 사용









    }

    private void initUI() {
        addr = (EditText) findViewById(R.id.EditText_SearchBar);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        coffeeAdapter = new CoffeeAdapter();
        //방향 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //기본형
        recyclerView.setAdapter(coffeeAdapter);

        cateSpinner= (Spinner) findViewById(R.id.Spinner_Category);
        dist= (EditText) findViewById(R.id.EditText_Distance);

        ArrayAdapter <CharSequence> spinnerAdapter= ArrayAdapter.createFromResource(this, R.array.cate, android.R.layout.simple_spinner_item);
        //자바 코드에서 리소스 가져오는 방법 : getResource().xxx....
        ArrayAdapter <String> spinnerAdapter2= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.cate));

        cateSpinner.setAdapter(spinnerAdapter2);
        cateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //아이템 선택 리스너
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //커피 전문점을 선택하면 호출이 됨. 어떤 샾을 선택했는지 획득 후 저장
                String selectedShop = cateSpinner.getItemAtPosition(i).toString();
                Log.d("Main","선택된 샵 : "+selectedShop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //커스텀 메소드들

    public void onSearch(View view){

    }

    @Override
    protected void onResume() {
        super.onResume();
        //gps 설정을 처리하고 돌아왔을 때(flagGPSStatus가 1일때) 다음 단계로 갈 수 있게 처리.
        if(flagGPSStatus==1){
            flagGPSStatus=0; // 다시 if문을 못 타도록 함.
            checkGPSUseOn();

        }

    }

    //OS 버전 체크(6.0 이상인지) (동의 여부까지 확인)
    public void checkGPSUseOn(){
        if(Build.VERSION.SDK_INT >= M){ //6.0 이상이라면
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    //동의 되었다
                    gpsDetect(1);


                }else{
                    //요청. 요청코드는 1000(임의로 정한 것임)
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
                    //onRequestPermissionsResult로 갑니다.

                }
            }else{
                //퍼미션 동의를 이미 함
                gpsDetect(2);



            }
        }else{ //6.0 미만이라면
            gpsDetect(3);

        }
    }

    //내가 코드 1000번으로 보낸 응답이 여기로 온다
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1000){
            if(grantResults.length>0){
                if(grantResults[0]<0){ //거부
                    gpsDetect(4);

                }else{ //동의
                    gpsDetect(5);

                }
            }//if

            for(int i=0; i<permissions.length; i++){
                Util.getInstance().Log("Main", permissions);
            }//for
        }//if

    }

    private void gpsDetect(int i) {
        switch(i){
            case 4:{ // 거부
                Util.getInstance().showPopup3(this, "알림", "gps를 사용하지 못합니다..뜨아",
                        "확인",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        },
                        null,null
                        );
                break;
            }//case 4
            case 5:
            case 1:
            case 2:{//동의. 6.0 이상 사용자라는 의미가 됨.
                startGPSDetectingService();
                break;
            }//case 5 , 1, 2
            case 3:{ //6.0 이하 단말기이므로 그냥 pass

            }//case 3
        }
    }


    //GPS를 획득하는 서비스
    public void startGPSDetectingService(){
        Intent intent = new Intent(this, GPSDetectingService.class);
        startService(intent);
    }


    //서비스로부터 gps 값을 받는 버스를 세팅!
    @Subscribe
    public void receiveGPS(Location location) { //위치정보는 Location 객체가 들고 있음. 'post를 하는 서비스'로부터 Location 객체를 받아 올 것임.
        // TODO: React to the event somehow!
        addr.setText(location.getLatitude()+","+location.getLongitude());
        getAddress(location);


        //첫번째로 받고 난 뒤에 retrofit 통신을 시작하자.
        if(isFirstGPSLoad) {
            showMyLocation(location);
            isFirstGPSLoad=false;
            startAllCoffeeStores();

        }

    }

    //모든 커피 전문점 가져옴
    private void startAllCoffeeStores() {
        Call<ResCoffeeStoresModel> res = Net.getInstance().getApiIm().all();
        res.enqueue(new Callback<ResCoffeeStoresModel>() {
            @Override
            public void onResponse(Call<ResCoffeeStoresModel> call, Response<ResCoffeeStoresModel> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        coffeeStores = response.body().getBody();
//                        coffeeAdapter.notifyDataSetChanged(); //가급적 전체 갱신은 쓰지 말자. 에너지를 너무 많이 먹음
                        drawStoresOnMap(coffeeStores);

                    }else{
                        //body가 없음
                    }
                }else{
                    //실패
                }
            }

            @Override
            public void onFailure(Call<ResCoffeeStoresModel> call, Throwable t) {
                //실패
            }
        });



    }

    private void drawStoresOnMap(ArrayList<CoffeeStoreModel> coffeeStores) {
        GeoPoint point;
        LatLng currentPosition;
        int index=0;
        for(CoffeeStoreModel coffee : coffeeStores){

            point= Util.getInstance().transFromKATEC2GEO(new GeoPoint(coffee.getX_AXIS(), coffee.getY_AXIS()));
            currentPosition = new LatLng(point.getY(), point.getX());
            int icon;
            if(coffee.getType().equals("STARBUCKS")){
                icon=R.drawable.small_starbucks;
            }else {
                icon = R.drawable.small_bean;
            }
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .icon(BitmapDescriptorFactory.fromResource(icon))
                    .title(coffee.getNM()));

            //마커 인스턴스를 만든 후에 데이터를 세팅!
            //해당 마커에 Tag가 붙에 된다.
            coffee.setIndex(index++);
            marker.setTag(coffee);

        }

    }

    //lat, lng 을 address로 바꾸는 메소드
    public void getAddress(Location location){
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        //lat과 lng을 받고 geocoder와 짬뽕시켜서 주소로 변환.
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        //기본 준비는 끝. 이제 변환
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(lat, lng, 2);
        } catch (IOException e) {
            e.printStackTrace();
            addr.setText("주소 변환 결과 없음");
        } catch(Exception e){
            e.printStackTrace();
            addr.setText("주소 변환 결과 없음");
        }


        if(addressList!= null && addressList.size()>0){
            for(Address ad : addressList){
                Log.d("Main", ad.toString()+" "+ad.getThoroughfare());
            }
            addr.setText(Util.getInstance().getTransferAddr(addressList.get(0)));
        }else{
            addr.setText("주소 변환 결과 없음");
        }
        //주소 결과는 아래처럼 나옴.
        //Address[
        // addressLines=[0:"대한민국 서울특별시 관악구 낙성대동"],
        // feature=낙성대동,
        // admin=서울특별시,
        // sub-admin=null,
        // locality=관악구,
        // thoroughfare=낙성대동,
        // postalCode=null,
        // countryCode=KR,
        // countryName=대한민국,
        // hasLatitude=true,
        // latitude=37.4762397,
        // hasLongitude=true,
        // longitude=126.9583907,
        // phone=null,
        // url=null,
        // extras=null] 낙성대동


    }


    public void isAvailableGPS(){
        //단말기에서 gps 사용을 on 했는지 체크
        //정확도를 높이고, gps 값을 획득하기 위한 조치

        //현재 단말기에 설정된 값
        String gps = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


        // gps 사용이 막혀 있음. gps를 켜라고 안내를 해주자.
        if(!(gps.matches(".*gps*.") || gps.matches(".*network*."))){
            Util.getInstance().showPopup3(this, "알림", "gps를 사용할 수 없습니다.\n설정 화면으로 이동하시겠습니까?",
                    "예",
                    new SweetAlertDialog.OnSweetClickListener(){
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            if(flagGPSStatus==0) flagGPSStatus =1 ;
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    },
                    "아니오",
                    new SweetAlertDialog.OnSweetClickListener(){
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismissWithAnimation();
                            checkGPSUseOn(); //??

                        }
                    });
        }//if
        // gps 사용이 허용된 상태임
        else{
            checkGPSUseOn();
        }//else
    }

    //OnMapReadyCallback 를 implements 하기 때문에 구현하는 오버라이드 메소드
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //지도 자체를 가리키는 객체
        mMap = googleMap;
        //마커를 클릭하는 발생하는 이벤트
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final CoffeeStoreModel coffee = (CoffeeStoreModel) marker.getTag();
                //리사이클뷰의 센터를, 선택한 내용으로 choice!

                recyclerView.smoothScrollToPosition(coffee.getIndex());

                //아래서 튀어나오는 스낵바
                Snackbar.make(recyclerView, coffee.getNM()+":"+coffee.getADDRESS(), Snackbar.LENGTH_LONG).setAction("상세보기",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Util.getInstance().showPopup(MainActivity.this, coffee.getNM(), coffee.getADDRESS(), SweetAlertDialog.NORMAL_TYPE);
                                Intent intent = new Intent(MainActivity.this, SubDetailActivity.class);
                                intent.putExtra("coffee", coffee);
                                startActivity(intent);
                            }
                        }).show();
                return false;
            }
        });


        //지도에서 클릭하면 위치 이동!!
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                myLoc = latLng;
                moveCamera(myLoc);
            }
        }); //맵을 눌렀을 때
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Util.getInstance().showPopup(MainActivity.this, "추가", "일정 입력", SweetAlertDialog.NORMAL_TYPE);

            }
        }); //맵을 길-게 눌렀을 때

        // Add a marker in Sydney and move the camera
        //특정 위치 마킹하여 화면 중앙에 보여줌
//        LatLng sydney = new LatLng(-34, 151); //LatLng : latitude, longitude
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void moveCamera(LatLng latLng) {
        CameraPosition cp = new CameraPosition.Builder()
                .target(latLng) //위치
                .zoom(16) //확대 정도
//                .bearing(60) //돌리는 각
//                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    public void showMyLocation(Location location) {
        showMyLocation(location, "here I am!");
    }
     public void showMyLocation(Location location, String title){
        //현재 위치를 지도에 표시!
        if(mMap == null) return;

        //화면에 표시함. 움직일 때 마다 마킹이 되니 clear 함.
        mMap.clear();
        myLoc = new LatLng(location.getLatitude(), location.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(myLoc).title(title));

         //줌 처리 . 해당 위치로 이동을 바로 합니다.
         CameraPosition cp = new CameraPosition.Builder()
                 .target(myLoc) //위치
                 .zoom(16) //확대 정도
//                .bearing(60) //돌리는 각
//                .tilt(30)
                 .build();
         mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }



    //'리사이클러뷰'의 뷰 홀더
    class CoffeeViewHolder extends RecyclerView.ViewHolder{

        ImageView brandImage;
        TextView name, address;

        //초기화
        public CoffeeViewHolder(View itemView) {
            super(itemView);
            brandImage = (ImageView) itemView.findViewById(R.id.cell_BrandImage);
            name = (TextView) itemView.findViewById(R.id.cell_Name);
            address = (TextView) itemView.findViewById(R.id.cell_Address);
        }

        //각자 맞게 세팅
        public void toBind(CoffeeStoreModel coffeeStoreModel){
            name.setText(coffeeStoreModel.getNM());
            address.setText(coffeeStoreModel.getADDRESS());

            int imageRef;
            if(coffeeStoreModel.getType().equals("COFFEEBEAN")){
                imageRef=R.drawable.bean;
            }else{
                imageRef=R.drawable.starbuck;
            }

            Picasso.with(brandImage.getContext()).load(imageRef).into(brandImage);
        }


    }

    class CoffeeAdapter extends RecyclerView.Adapter<CoffeeViewHolder>{

        @Override
        public CoffeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.cell_coffee_layout, parent, false); //왜 false인지는 모르겠음.
            return new CoffeeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CoffeeViewHolder holder, int position) {
            holder.toBind(coffeeStores.get(position));//내가 만든 toBind
        }

        @Override
        public int getItemCount() {
            return coffeeStores ==null? 0 : coffeeStores.size();
        }
    }//CoffeeAdapter



    public void onDistanceSelect(View view){
        //팝업을 띄워 검색 거리를 선택함

        AlertDialog.Builder ad = new AlertDialog.Builder(this)
            .setCancelable(true)
            .setSingleChoiceItems(R.array.distance, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String[] arr = getResources().getStringArray(R.array.distance);
                    Snackbar.make(recyclerView, "[ "+arr[i]+" ] 거리를 선택하였음.", Snackbar.LENGTH_LONG).show();
                    //요청된 반경을 지도에 표시해보자.
                    mMap.addCircle(new CircleOptions().center(myLoc).radius(1000*Double.parseDouble(arr[i].replace("km",""))));
                }
            })

                .setTitle("거리 선택");
        AlertDialog alertDialog = ad.create();
        alertDialog.show();
    }

    public void onSearch2(View view){
        //커피숍 브랜드 + 거리 => 통신해서 그 결과로 지도를 갱신.

    }
}
