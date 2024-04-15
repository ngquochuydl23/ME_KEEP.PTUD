package dao;

import config.DatabaseUtil;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoaDonDao implements IDao<HoaDon, String> {

    private Connection con;

    public HoaDonDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public HoaDon layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM HoaDon WHERE maHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                LocalDateTime thoiGianTaoHoaDon = rs.getTimestamp("thoiGianTaoHoaDon").toLocalDateTime();
                String ghiChu = rs.getString("ghiChu");
                double thueVat = rs.getDouble("thueVat");
                double tienKhachDua = rs.getDouble("tienKhachDua");

                int maKhachHang = rs.getInt("maKhachHang");
                int maNhanVien = rs.getInt("maNhanVien");

                String maKhuyenMai = rs.getString("maKhuyenMai");

                return new HoaDon(
                        maHoaDon,
                        thoiGianTaoHoaDon,
                        ghiChu,
                        thueVat,
                        tienKhachDua,
                        new KhachHang(maKhachHang),
                        new NhanVien(maNhanVien),
                        new KhuyenMai(maKhuyenMai)
                );
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<HoaDon> layHet() {
        return null;
    }

    @Override
    public boolean them(HoaDon entity) throws SQLException {
        try {
            con.setAutoCommit(false);


        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        } catch (RuntimeException runtimeEx) {
            runtimeEx.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean xoa(String id) {
        return false;
    }

    @Override
    public boolean sua(HoaDon entity) {
        return false;
    }
}
