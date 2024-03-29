package dao1;

import config.DatabaseUtil;
import entity.KhachHang;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDao implements IDao<KhachHang, Integer> {

    private Connection con;

    public KhachHangDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public KhachHang layTheoMa(Integer id) {
        try {
            String sql = "SELECT * FROM KhachHang WHERE maKhachHang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String soDienThoai = rs.getString("soDienThoai");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("thoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("laKhachHangThanThiet");

                return new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy, laKhachHangThanThiet);
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<KhachHang> layHet() {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            String sql = "SELECT * FROM KhachHang";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String soDienThoai = rs.getString("soDienThoai");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("thoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("laKhachHangThanThiet");

                dsKhachHang.add(new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy, laKhachHangThanThiet));
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsKhachHang;
    }

    @Override
    public boolean them(KhachHang entity) {
        try {
            String sql = "INSERT INTO `KhachHang`(`maKhachHang`, `hoTen`, `soDienThoai`, `thoiGianDangKy`, `laKhachHangThanThiet`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, entity.getMaKhachHang());
            statement.setString(2, entity.getHoTen());
            statement.setString(3, entity.getSoDienThoai());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getThoiGianDangKy()));
            statement.setBoolean(5, false);

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(Integer id) {
        try {
            String sql = "DELETE FROM `KhachHang` WHERE `maKhachHang` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(KhachHang entity) {
        try {
            String sql = "UPDATE `KhachHang` SET `hoTen`=?,`soDienThoai`=?,`laKhachHangThanThiet`=? WHERE maKhachHang=?";
            PreparedStatement pst =  con.prepareStatement(sql);

            pst.setString(1, entity.getHoTen());
            pst.setString(2, entity.getSoDienThoai());
            pst.setBoolean(3, entity.laKhachHangThanThiet());

            // set where
            pst.setInt(4, entity.getMaKhachHang());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
