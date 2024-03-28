package dao1;

import config.DatabaseUtil;
import entity.Tuyen;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TuyenDao implements IDao<Tuyen, String>{
    private Connection con;

    public TuyenDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Tuyen layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Tuyen WHERE maTuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maTuyen = rs.getString("maTuyen");
                String diemDi = rs.getString("diemDi");
                String diemDen = rs.getString("diemDen");
                LocalDateTime thoiGianTaoTuyen = rs.getTimestamp("thoiGianTaoTuyen").toLocalDateTime();

                return new Tuyen(maTuyen, diemDi, diemDen, thoiGianTaoTuyen);
            }
        } catch (Exception e) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Tuyen> layHet() {
        List<Tuyen> dsTuyen = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Tuyen";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maTuyen = rs.getString("maTuyen");
                String diemDi = rs.getString("diemDi");
                String diemDen = rs.getString("diemDen");
                LocalDateTime thoiGianTaoTuyen = rs.getTimestamp("thoiGianTaoTuyen").toLocalDateTime();

                dsTuyen.add(new Tuyen(maTuyen, diemDi, diemDen, thoiGianTaoTuyen));
            }
        } catch (Exception e) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsTuyen;
    }

    @Override
    public boolean them(Tuyen entity) {
        try {
            String sql = "INSERT INTO `Tuyen`(`maTuyen`, `diemDi`, `diemDen`, `thoiGianTaoTuyen`) VALUES (?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, entity.getMaTuyen());
            statement.setString(2, entity.getDiemDi());
            statement.setString(3, entity.getDiemDen());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getThoiGianTaoTuyen()));

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Tuyen` WHERE `maTuyen` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Tuyen entity) {
        try {
            String sql = "UPDATE `Tuyen` SET `diemDi`=?,`diemDen`=?,`thoiGianTaoTuyen`=? WHERE maTuyen=?";
            PreparedStatement pst =  con.prepareStatement(sql);

            pst.setString(1, entity.getDiemDi());
            pst.setString(2, entity.getDiemDen());
            pst.setTimestamp(3, Timestamp.valueOf(entity.getThoiGianTaoTuyen()));

            // set where
            pst.setString(4, entity.getMaTuyen());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public int getAutoIncrement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAutoIncrement'");
    }
}
