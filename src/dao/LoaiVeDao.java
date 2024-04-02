package dao;


import config.DatabaseUtil;
import entity.LoaiVe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoaiVeDao implements IDao<LoaiVe, String> {
    private Connection con;

    public LoaiVeDao() {
        con =  DatabaseUtil.getConnection();
    }

    @Override
    public LoaiVe layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM LoaiVe WHERE maLoaiVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maLoaiVe = rs.getString("maLoaiVe");
                String tenLoaiVe = rs.getString("tenLoaiVe");
                String loaiKhoang = rs.getString("loaiKhoang");

                return new LoaiVe(maLoaiVe, tenLoaiVe, loaiKhoang);
            }
        } catch (Exception e) {
            Logger.getLogger(LoaiVeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<LoaiVe> layHet() {
        List<LoaiVe> dsLoaiVe = new ArrayList<>();
        try {
            String sql = "SELECT * FROM LoaiVe";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maLoaiVe = rs.getString("maLoaiVe");
                String tenLoaiVe = rs.getString("tenLoaiVe");
                String loaiKhoang = rs.getString("loaiKhoang");

                dsLoaiVe.add(new LoaiVe(maLoaiVe, tenLoaiVe, loaiKhoang));
            }
        } catch (Exception e) {
            Logger.getLogger(LoaiVeDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsLoaiVe;
    }

    @Override
    public boolean them(LoaiVe entity) {
        try {
            String sql = "INSERT INTO `LoaiVe`(`maLoaiVe`, `tenLoaiVe`, `loaiKhoang`) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, entity.getMaLoaiVe());
            statement.setString(2, entity.getTenLoaiVe());
            statement.setString(3, entity.getLoaiKhoang());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiVeDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
            Logger.getLogger(LoaiVeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(LoaiVe entity) {
        try {
            String sql = "UPDATE `LoaiVe` SET `tenLoaiVe`=?,`loaiKhoang`=? WHERE maLoaiVe=?";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1, entity.getTenLoaiVe());
            pst.setString(2, entity.getLoaiKhoang());

            // set where
            pst.setString(3, entity.getMaLoaiVe());

            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiVeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
