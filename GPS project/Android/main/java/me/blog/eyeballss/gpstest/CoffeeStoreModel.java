package me.blog.eyeballss.gpstest;

/**
 * Created by Tacademy on 2017-07-20.
 */

public class CoffeeStoreModel {
    String NM;      //` VARCHAR(128) NULL DEFAULT NULL COMMENT '점포명',
    String ADDRESS; //` VARCHAR(512) NULL DEFAULT NULL COMMENT '정포주소',
    int X_AXIS;     //` INT(10) UNSIGNED NOT NULL COMMENT 'x',
    int Y_AXIS;     //` INT(10) UNSIGNED NOT NULL COMMENT 'y',
    String type;    //` VARCHAR(10) NULL DEFAULT NULL COMMENT '커피브랜드'

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
