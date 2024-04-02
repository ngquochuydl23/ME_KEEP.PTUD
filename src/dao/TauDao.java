package dao;

import config.DatabaseUtil;
import entity.Tau;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TauDao implements IDao<Tau, String>{

    private Connection con;

    public TauDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Tau layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Tau WHERE maTau=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");

                return new Tau(maTau, tenTau);
            }
        } catch (Exception e) {
            Logger.getLogger(TauDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Tau> layHet() {
        List<Tau> dsTau = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Tau";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");

                dsTau.add(new Tau(maTau, tenTau));
            }
        } catch (Exception e) {
            Logger.getLogger(TauDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsTau;
    }

    @Override
    public boolean them(Tau entity) {
        try {
            String sql = "INSERT INTO `Tau`(`maTau`, `tenTau`) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, entity.getMaTau());
            statement.setString(2, entity.getTenTau());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TauDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Tau` WHERE `maTau` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TauDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Tau entity) {
        try {
            String sql = "UPDATE `Tau` SET `tenTau`=? WHERE maTau=?";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1, entity.getTenTau());
            // set where
            pst.setString(2, entity.getMaTau());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TauDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
