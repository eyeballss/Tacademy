package me.blog.eyeballss.gpstest;

import java.io.Serializable;

/**
 * Created by Tacademy on 2017-07-20.
 */

public class CoffeeStoreModel implements Serializable{
    String NM;      //` VARCHAR(128) NULL DEFAULT NULL COMMENT '점포명',
    String ADDRESS; //` VARCHAR(512) NULL DEFAULT NULL COMMENT '정포주소',
    int X_AXIS;     //` INT(10) UNSIGNED NOT NULL COMMENT 'x',
    int Y_AXIS;     //` INT(10) UNSIGNED NOT NULL COMMENT 'y',
    String type;    //` VARCHAR(10) NULL DEFAULT NULL COMMENT '커피브랜드'

    int index; // 인데에에에엑쓰으
    double dist; //거리

    @Override
    public String toString() {
        return "CoffeeStoreModel{" +
                "NM='" + NM + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", X_AXIS=" + X_AXIS +
                ", Y_AXIS=" + Y_AXIS +
                ", type='" + type + '\'' +
                ", index=" + index +
                ", dist=" + dist +
                '}';
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNM() {
        return NM;
    }

    public void setNM(String NM) {
        this.NM = NM;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public int getX_AXIS() {
        return X_AXIS;
    }

    public void setX_AXIS(int x_AXIS) {
        X_AXIS = x_AXIS;
    }

    public int getY_AXIS() {
        return Y_AXIS;
    }

    public void setY_AXIS(int y_AXIS) {
        Y_AXIS = y_AXIS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
