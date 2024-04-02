package dao;

import config.DatabaseUtil;
import entity.Khoang;
import entity.ToaTau;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhoangDao implements IDao<Khoang, String> {

    private Connection con;

    public KhoangDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Khoang layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Khoang WHERE maKhoang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maKhoang = rs.getString("maKhoang");
                String tenKhoang = rs.getString("tenKhoang");
                String maToaTau = rs.getString("maToaTau");

                return new Khoang(maKhoang, tenKhoang, new ToaTau(maToaTau));
            }
        } catch (Exception e) {
            Logger.getLogger(KhoangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Khoang> layHet() {
        List<Khoang> dsKhoang = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Khoang";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maKhoang = rs.getString("maKhoang");
                String tenKhoang = rs.getString("tenKhoang");
                String maToaTau = rs.getString("maToaTau");

                dsKhoang.add(new Khoang(maKhoang, tenKhoang, new ToaTau(maToaTau)));
            }
        } catch (Exception e) {
            Logger.getLogger(KhoangDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsKhoang;
    }

    @Override
    public boolean them(Khoang entity) {
        try {
            String sql = "INSERT INTO `Khoang`(`maKhoang`, `tenKhoang`, `maToaTau`) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, entity.getMaKhoang());
            statement.setString(2, entity.getTenKhoang());
            statement.setString(3, entity.getToaTau().getMaToa());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhoangDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Khoang` WHERE `maKhoang` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhoangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Khoang entity) {
        try {
            String sql = "UPDATE `Khoang` SET `tenKhoang`=?,`maToaTau`=? WHERE maKhoang=?";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1, entity.getTenKhoang());
            pst.setString(2, entity.getToaTau().getMaToa());

            // set where
            pst.setString(3, entity.getMaKhoang());

            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(KhoangDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
