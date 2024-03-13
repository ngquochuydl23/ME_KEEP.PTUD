package dao1;

import config.DatabaseUtil;
import entity.Quyen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuyenDao implements IDao<Quyen, String>{

    private Connection con;

    public QuyenDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Quyen layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Quyen WHERE maQuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maQuyen = rs.getString("maQuyen");
                String tenQuyen = rs.getString("tenQuyen");

                return new Quyen(maQuyen, tenQuyen);
            }
        } catch (Exception e) {
            Logger.getLogger(QuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Quyen> layHet() {
        List<Quyen> dsQuyen = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Quyen";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maQuyen = rs.getString("maQuyen");
                String tenQuyen = rs.getString("tenQuyen");

                dsQuyen.add(new Quyen(maQuyen, tenQuyen));
            }
        } catch (Exception e) {
            Logger.getLogger(QuyenDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsQuyen;
    }

    @Override
    public boolean them(Quyen entity) {
        try {
            String sql = "INSERT INTO `Quyen`(`maQuyen`, `tenQuyen`) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, entity.getMaQuyen());
            statement.setString(2, entity.getTenQuyen());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(QuyenDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Quyen` WHERE `maQuyen` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(QuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Quyen entity) {
        try {
            String sql = "UPDATE `Quyen` SET `tenQuyen`=? WHERE maQuyen=?";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1, entity.getTenQuyen());
            // set where
            pst.setString(2, entity.getMaQuyen());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(QuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
