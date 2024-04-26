package util;

import com.google.gson.Gson;

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Ve;

public class JsonVeTau {
    public static String convertChiTietHoaDonToJson(ChiTietHoaDon chiTietHoaDon) {
        Gson gson = new Gson();
        return gson.toJson(chiTietHoaDon);
    }

    public static String convertVeToJson(Ve ve) {
        Gson gson = new Gson();
        return gson.toJson(ve);
    }

    public static String convertHoaDonToJson(HoaDon hoaDon) {
        Gson gson = new Gson();
        return gson.toJson(hoaDon);
    }
}
