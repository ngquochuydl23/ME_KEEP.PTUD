package dao;

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
                String CMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("thoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("laKhachHangThanThiet");

                return new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy, laKhachHangThanThiet, CMND);
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
                String CMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("thoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("laKhachHangThanThiet");

                dsKhachHang.add(new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy, laKhachHangThanThiet, CMND));
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsKhachHang;
    }

    @Override
    public boolean them(KhachHang entity) {
        try {
            String sql = "INSERT INTO `KhachHang`(`hoTen`, `soDienThoai`, `thoiGianDangKy`, `laKhachHangThanThiet`, `CMND`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, entity.getHoTen());
            statement.setString(2, entity.getSoDienThoai());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getThoiGianDangKy()));
            statement.setBoolean(4, false);
            statement.setString(5, entity.getCMND());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public KhachHang themVoiKieuTraVe(KhachHang entity) {
        try {
            String sql = "INSERT INTO `KhachHang`(`hoTen`, `soDienThoai`, `thoiGianDangKy`, `laKhachHangThanThiet`, `CMND`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, entity.getHoTen());
            statement.setString(2, entity.getSoDienThoai());
            
            // Set thoiGianDangKy to current timestamp
            LocalDateTime currentTime = LocalDateTime.now();
            statement.setTimestamp(3, Timestamp.valueOf(currentTime));
            
            statement.setBoolean(4, false);
            statement.setString(5, entity.getCMND());

            if (statement.executeUpdate() > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setMaKhachHang(generatedKeys.getInt(1));
                        return entity;
                    }
                }
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
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
            String sql = "UPDATE `KhachHang` SET `hoTen`=?, `soDienThoai`=?, `laKhachHangThanThiet`=?, `CMND`=? WHERE maKhachHang=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, entity.getHoTen());
            pst.setString(2, entity.getSoDienThoai());
            pst.setBoolean(3, entity.laKhachHangThanThiet());
            pst.setString(4, entity.getCMND());

            // set where
            pst.setInt(5, entity.getMaKhachHang());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public KhachHang timTheoSDT(String sdt) {
        try {
            String sql = "SELECT * FROM KhachHang WHERE soDienThoai=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, sdt);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String soCMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("thoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("laKhachHangThanThiet");

                return new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy, laKhachHangThanThiet, soCMND);
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public KhachHang timTheoCmnd(String cmnd) {
        try {
            String sql = "SELECT * FROM KhachHang WHERE CMND=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, cmnd);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("hoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String soCMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("thoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("laKhachHangThanThiet");

                return new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy, laKhachHangThanThiet, soCMND);
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
