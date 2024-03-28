package dao1;

import config.DatabaseUtil;
import entity.Khoang;
import entity.KhuyenMai;
import entity.ToaTau;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhuyenMaiDao implements IDao<KhuyenMai, String> {
    private Connection con;

    public KhuyenMaiDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public KhuyenMai layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM KhuyenMai WHERE maKhuyenMai=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maKhuyenMai = rs.getString("maKhuyenMai");
                double phanTramGiamGia = rs.getDouble("phanTramGiamGia");
                String ghiChu = rs.getString("ghiChu");
                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();

                return new KhuyenMai(maKhuyenMai, phanTramGiamGia, ghiChu, thoiGianBatDau, thoiGianKetThuc);
            }
        } catch (Exception e) {
            Logger.getLogger(KhuyenMaiDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<KhuyenMai> layHet() {
        List<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try {
            String sql = "SELECT * FROM KhuyenMai";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maKhuyenMai = rs.getString("maKhuyenMai");
                double phanTramGiamGia = rs.getDouble("phanTramGiamGia");
                String ghiChu = rs.getString("ghiChu");
                LocalDateTime thoiGianBatDau = rs.getTimestamp("thoiGianBatDau").toLocalDateTime();
                LocalDateTime thoiGianKetThuc = rs.getTimestamp("thoiGianKetThuc").toLocalDateTime();

                dsKhuyenMai.add(new KhuyenMai(maKhuyenMai, phanTramGiamGia, ghiChu, thoiGianBatDau, thoiGianKetThuc));
            }
        } catch (Exception e) {
            Logger.getLogger(KhuyenMaiDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsKhuyenMai;
    }

    @Override
    public boolean them(KhuyenMai entity) {
        try {
            String sql = "INSERT INTO `KhuyenMai`(`maKhuyenMai`, `phanTramGiamGia`, `ghiChu`, `thoiGianBatDau`, `thoiGianKetThuc`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, entity.getMaKhuyenMai());
            statement.setDouble(2, entity.getPhanTramGiamGia());
            statement.setString(3, entity.getGhiChu());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getThoiGianBatDau()));
            statement.setTimestamp(5, Timestamp.valueOf(entity.getThoiGianKetThuc()));

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `KhuyenMai` WHERE `maKhuyenMai` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(KhuyenMai entity) {
        try {
            String sql = "UPDATE `KhuyenMai` SET `phanTramGiamGia`=?,`ghiChu`=?,`thoiGianBatDau`=?,`thoiGianKetThuc` WHERE maKhuyenMai=?";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setDouble(1, entity.getPhanTramGiamGia());
            pst.setString(2, entity.getGhiChu());
            pst.setTimestamp(3, Timestamp.valueOf(entity.getThoiGianBatDau()));
            pst.setTimestamp(4, Timestamp.valueOf(entity.getThoiGianKetThuc()));
            // set where
            pst.setString(5, entity.getMaKhuyenMai());

            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public int getAutoIncrement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAutoIncrement'");
    }
}
