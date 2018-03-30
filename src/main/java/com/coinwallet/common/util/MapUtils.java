package com.coinwallet.common.util;


public class MapUtils {

    public static double calculateDistance(double longitudeA, double latitudeA,double longitudeB, double latitudeB) {
        double radLat1 = getRadian(latitudeA);
        double radLat2 = getRadian(latitudeB);
        double a = radLat1 - radLat2;
        double b = getRadian(longitudeA) - getRadian(longitudeB);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        return s * 1000;
    }


    public static double getRadian(double degree) {
        return degree * Math.PI / 180.0;
    }

}
