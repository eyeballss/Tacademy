package me.blog.eyeballss.gpstest;

/**
 * Created by Tacademy on 2017-07-21.
 * 거리 순으로 특정 브랜드 점포 정보를 가져올 때 쓰는 모델. 특정 거리 이내로 가져옴.
 */

public class DistModel {
    String type;
    double lat;
    double lng;



    public DistModel(String type, double lat, double lng, double dist) {
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.dist = dist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    double dist;
}
