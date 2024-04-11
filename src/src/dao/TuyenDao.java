package dao;

import config.DatabaseUtil;
import entity.Ga;
import entity.Tuyen;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TuyenDao implements IDao<Tuyen, String> {
    private Connection con;

    public TuyenDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Tuyen layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Tuyen WHERE MaTuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maTuyen = rs.getString("MaTuyen");
                String diemDi = rs.getString("MaGaDi");
                String diemDen = rs.getString("MaGaDen");
                LocalDateTime NgayTaoTuyen = rs.getTimestamp("NgayTaoTuyen").toLocalDateTime();

                return new Tuyen(maTuyen, new Ga(diemDi), new Ga(diemDen), NgayTaoTuyen);
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
                String diemDi = rs.getString("MaGaDi");
                String diemDen = rs.getString("MaGaDen");
                LocalDateTime NgayTaoTuyen = rs.getTimestamp("NgayTaoTuyen").toLocalDateTime();

                dsTuyen.add(new Tuyen(maTuyen, new Ga(diemDi), new Ga(diemDen), NgayTaoTuyen));
            }
        } catch (Exception e) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsTuyen;
    }

    @Override
    public boolean them(Tuyen entity) {
        try {
            String sql = "INSERT INTO `Tuyen`(`MaTuyen`, `MaGaDi`, `MaGaDen`, `NgayTaoTuyen`) VALUES (?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, entity.getMaTuyen());
            statement.setString(2, entity.getGaDi().getMaGa());
            statement.setString(3, entity.getGaDen().getMaGa());
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
            String sql = "UPDATE `Tuyen` SET `MaGaDi`=?,`MaGaDen`=?,`NgayTaoTuyen`=? WHERE MaTuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, entity.getGaDi().getMaGa());
            pst.setString(2, entity.getGaDen().getMaGa());
            pst.setTimestamp(3, Timestamp.valueOf(entity.getThoiGianTaoTuyen()));

            // set where
            pst.setString(4, entity.getMaTuyen());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Tuyen timTuyen(String maGaDi, String maGaDen) {
        try {
            String sql = "SELECT * FROM Tuyen WHERE MaGaDi=? AND MaGaDen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maGaDi);
            pst.setString(2, maGaDen);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maTuyen = rs.getString("MaTuyen");
                String diemDi = rs.getString("MaGaDi");
                String diemDen = rs.getString("MaGaDen");
                LocalDateTime NgayTaoTuyen = rs.getTimestamp("NgayTaoTuyen").toLocalDateTime();

                return new Tuyen(maTuyen, new Ga(diemDi), new Ga(diemDen), NgayTaoTuyen);
            }
        } catch (Exception e) {
            Logger.getLogger(TuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
