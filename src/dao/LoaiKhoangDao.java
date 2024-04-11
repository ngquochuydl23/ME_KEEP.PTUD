package dao;

import config.DatabaseUtil;
import entity.LoaiKhoang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoaiKhoangDao implements IDao<LoaiKhoang, String> {
    private Connection con;

    public LoaiKhoangDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public LoaiKhoang layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM LoaiVe WHERE maLoaiVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maLoaiVe = rs.getString("maLoaiVe");
                String tenLoaiVe = rs.getString("tenLoaiVe");

                return new LoaiKhoang(maLoaiVe, tenLoaiVe);
            }
        } catch (Exception e) {
            Logger.getLogger(LoaiKhoangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<LoaiKhoang> layHet() {
        List<LoaiKhoang> dsLoaiVe = new ArrayList<>();
        try {
            String sql = "SELECT * FROM LoaiVe";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maLoaiVe = rs.getString("maLoaiVe");
                String tenLoaiVe = rs.getString("tenLoaiVe");

                dsLoaiVe.add(new LoaiKhoang(maLoaiVe, tenLoaiVe));
            }
        } catch (Exception e) {
            Logger.getLogger(LoaiKhoangDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsLoaiVe;
    }

    @Override
    public boolean them(LoaiKhoang entity) {
        try {
            String sql = "INSERT INTO `LoaiKhoang`(`maLoaiKhoang`, `tenLoaiKhoang`) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, entity.getMaLoaiKhoang());
            statement.setString(2, entity.getTenLoaiKhoang());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiKhoangDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `LoaiVe` WHERE `maLoaiVe` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiKhoangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(LoaiKhoang entity) {
        try {
            String sql = "UPDATE `LoaiKhoang` SET `tenLoaiKhoang`=?,`loaiKhoang`=? WHERE maLoaiKhoang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, entity.getTenLoaiKhoang());

            // set where
            pst.setString(2, entity.getMaLoaiKhoang());

            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiKhoangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public LoaiKhoang layLoaiKhoangTheoMaToa(String ma) {
        try {
            String sql = "SELECT * FROM LoaiKhoang lk\n" +
                    "LEFT JOIN Khoang k ON k.MaLoaiKhoang = lk.MaLoaiKhoang\n" +
                    "LEFT  JOIN  ToaTau tt ON tt.MaToa = k.MaToa WHERE tt.MaToa = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ma);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maLoaiVe = rs.getString("MaLoaiKhoang");
                String tenLoaiVe = rs.getString("TenLoaiKhoang");

                return new LoaiKhoang(maLoaiVe, tenLoaiVe);
            }
        } catch (Exception e) {
            Logger.getLogger(LoaiKhoangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
