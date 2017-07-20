package me.blog.eyeballss.gpstest;

/**
 * Created by Tacademy on 2017-07-20.
 */

public class CoffeeStoreModel {
    String NM;
    String ADRESS;
    int X_AXIS;
    int Y_AXIS;
    String type; //커피브랜드

    public String getNM() {
        return NM;
    }

    public void setNM(String NM) {
        this.NM = NM;
    }

    public String getADRESS() {
        return ADRESS;
    }

    public void setADRESS(String ADRESS) {
        this.ADRESS = ADRESS;
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
