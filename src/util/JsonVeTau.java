package util;

import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import config.DatabaseUtil;
import dao.HoaDonDao;
import dao.VeDao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Ve;

public class JsonVeTau {
    public static String convertChiTietHoaDonToJson(ChiTietHoaDon chiTietHoaDon) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(chiTietHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseUtil.connect();
        VeDao veDao = new VeDao();
        Ve ve = veDao.layTheoMa("di-an-thanh-hoa-D8E-toa6-khoang1-giuongnamkhoang6-slot1-2024-04-25 11:21:15.492");
        System.out.println(ve.toString());
        HoaDonDao hoaDonDao = new HoaDonDao();
        HoaDon hoaDon = hoaDonDao.layTheoMa("HD-KH83-2024-04-25 11:21:48.935");
        System.out.println(hoaDon.toString());

        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, ve);
        System.out.println(JsonVeTau.convertChiTietHoaDonToJson(chiTietHoaDon));
    }
}
