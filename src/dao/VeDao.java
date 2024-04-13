package dao;

import config.DatabaseUtil;
import entity.Chuyen;
import entity.Khoang;
import entity.LoaiKhoang;
import entity.Ve;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeDao implements IDao<Ve, String> {

    private Connection con;

    public VeDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Ve layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Ve WHERE maVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maVe = rs.getString("maVe");
                int choNgoi = rs.getInt("choNgoi");
                double giaVe = rs.getDouble("giaVe");
                String moTa = rs.getString("moTa");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String maKhoang = rs.getString("maKhoang");

                return new Ve(maVe, choNgoi, giaVe, moTa, tinhTrangVe,null);
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Ve> layHet() {
        List<Ve> dsVe = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Ve";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maVe = rs.getString("maVe");
                int choNgoi = rs.getInt("choNgoi");
                double giaVe = rs.getDouble("giaVe");
                String moTa = rs.getString("moTa");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String maLoaiVe = rs.getString("maLoaiVe");
                String maKhoang = rs.getString("maKhoang");

                dsVe.add(new Ve(maVe, choNgoi, giaVe, moTa, tinhTrangVe, null));
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsVe;
    }

    @Override
    public boolean them(Ve entity) {
        try {
            String sql = "INSERT INTO `Ve`(`maVe`, `choNgoi`, `giaVe`, `moTa`, `tinhTrangVe`,`maKhoang`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, entity.getMaVe());
            statement.setInt(2, entity.getChoNgoi());
            statement.setDouble(3, entity.getGiaVe());
            statement.setString(4, entity.getMoTa());
            statement.setInt(5, entity.getTinhTrangVe());
            statement.setString(6,"");

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Ve` WHERE `maVe` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Ve entity) {
        try {
            String sql = "UPDATE `Ve` SET `choNgoi`=?,`giaVe`=?,`moTa`=?, `tinhTrangVe`=?, `maKhoang`=? WHERE maVe=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, entity.getChoNgoi());
            pst.setDouble(2, entity.getGiaVe());
            pst.setString(3, entity.getMoTa());
            pst.setInt(4, entity.getTinhTrangVe());
            pst.setString(5, "");

            pst.setString(7, entity.getMaVe());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
