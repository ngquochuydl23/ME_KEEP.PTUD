package dao;

import config.DatabaseUtil;
import entity.Chuyen;
import entity.Ga;
import entity.Tau;
import entity.Tuyen;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChuyenDao implements IDao<Chuyen, String> {

    private Connection con;

    public ChuyenDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Chuyen layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Chuyen WHERE maChuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maChuyen = rs.getString("maChuyen");

                LocalDateTime thoiGianKhoiHanh = rs.getTimestamp("thoiGianKhoiHanh").toLocalDateTime();
                LocalDateTime thoiGianDen = rs.getTimestamp("thoiGianDen").toLocalDateTime();

                String maTuyen = rs.getString("maTuyen");
                String maTau = rs.getString("maTau");

                return new Chuyen(maChuyen, thoiGianKhoiHanh, thoiGianDen, new Tuyen(maTuyen), new Tau(maTau));
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Chuyen> layHet() {
        List<Chuyen> dsChuyen = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Chuyen";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maChuyen = rs.getString("maChuyen");

                LocalDateTime thoiGianKhoiHanh = rs.getTimestamp("thoiGianKhoiHanh").toLocalDateTime();
                LocalDateTime thoiGianDen = rs.getTimestamp("thoiGianDen").toLocalDateTime();

                String maTuyen = rs.getString("maTuyen");
                String maTau = rs.getString("maTau");

                dsChuyen.add(new Chuyen(maChuyen, thoiGianKhoiHanh, thoiGianDen, new Tuyen(maTuyen), new Tau(maTau)));
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsChuyen;
    }

    @Override
    public boolean them(Chuyen entity) {
        try {
            String sql = "INSERT INTO `Chuyen`(`maChuyen`, `thoiGianKhoiHanh`, `thoiGianDen`, `maTuyen`, `maTau`) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, entity.getMaChuyen());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getThoiGianKhoiHanh()));
            statement.setTimestamp(3, Timestamp.valueOf(entity.getThoiGianDen()));

            statement.setString(4, entity.getTuyen().getMaTuyen());
            statement.setString(4, entity.getTau().getMaTau());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Chuyen` WHERE `maChuyen` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Chuyen entity) {
        try {
            String sql = "UPDATE `Quyen` SET `thoiGianKhoiHanh`=?, `thoiGianDen`=?, `maTuyen`=?, `maTau`=?  WHERE maChuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setTimestamp(1, Timestamp.valueOf(entity.getThoiGianKhoiHanh()));
            pst.setTimestamp(2, Timestamp.valueOf(entity.getThoiGianDen()));

            pst.setString(3, entity.getTuyen().getMaTuyen());
            pst.setString(4, entity.getTau().getMaTau());
            // set where
            pst.setString(5, entity.getMaChuyen());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Chuyen> timChuyenTheoTuyen(String idTuyen, Date ngayDi) {
        List<Chuyen> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Chuyen WHERE MaTuyen=? AND DATE(ThoiGianKhoiHanh)=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, idTuyen);
            pst.setDate(2, new java.sql.Date(ngayDi.getTime()));

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maChuyen = rs.getString("MaChuyen");

                LocalDateTime thoiGianKhoiHanh = rs.getTimestamp("ThoiGianKhoiHanh").toLocalDateTime();
                LocalDateTime thoiGianDen = rs.getTimestamp("ThoiGianDen").toLocalDateTime();

                String maTuyen = rs.getString("MaTuyen");
                String maTau = rs.getString("MaTau");
                list.add(new Chuyen(maChuyen, thoiGianKhoiHanh, thoiGianDen, new Tuyen(maTuyen), new Tau(maTau)));
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Chuyen> timChuyenTheoGa(String maGaDiParam, String maGaDenParam, LocalDate ngayDiParam) {
        List<Chuyen> dsChuyen = new ArrayList<>();
        try {
            String sql =
                    "SELECT * FROM Chuyen\n" +
                            "LEFT JOIN Tuyen t ON t.MaTuyen = Chuyen.MaTuyen\n" +
                            "LEFT JOIN Ga gaDi ON gaDi.MaGa = t.MaGaDi \n" +
                            "LEFT JOIN Ga gaDen ON gaDen.MaGa = t.MaGaDen\n" +
                            "LEFT JOIN Tau tau ON tau.MaTau  = Chuyen.MaTau\n" +
                            "WHERE gaDi.TenGa =? AND gaDen.TenGa =? AND Chuyen.ThoiGianKhoiHanh = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maGaDiParam);
            pst.setString(2, maGaDenParam);
            pst.setDate(3, java.sql.Date.valueOf(ngayDiParam));
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Ga Đi
                String maGaDi = rs.getString("gaDi.MaGa");
                String tenGaDi = rs.getString("gaDi.TenGa");
                String vungMienGaDi = rs.getString("gaDi.VungMien");
                Ga gaDi = new Ga(maGaDi, tenGaDi, vungMienGaDi);

                // Ga Đến
                String maGaDen = rs.getString("gaDen.MaGa");
                String tenGaDen = rs.getString("gaDen.TenGa");
                String vungMienGaDen = rs.getString("gaDen.VungMien");
                Ga gaDen = new Ga(maGaDen, tenGaDen, vungMienGaDen);

                // Tuyến
                String maTuyen = rs.getString("t.MaTuyen");
                LocalDateTime ngayTaoTuyen = rs.getTimestamp("t.NgayTaoTuyen").toLocalDateTime();
                Tuyen tuyen = new Tuyen(maTuyen, gaDi, gaDen, ngayTaoTuyen);

                // Chuyến
                String maChuyen = rs.getString("Chuyen.MaChuyen");
                LocalDateTime thoiGianKhoiHanh = rs.getTimestamp("Chuyen.ThoiGianKhoiHanh").toLocalDateTime();
                LocalDateTime thoiGianDen = rs.getTimestamp("Chuyen.ThoiGianDen").toLocalDateTime();


                // Tàu
                String maTau = rs.getString("tau.MaTau");
                String tenTau = rs.getString("tau.TenTau");
                Tau tau = new Tau(maTau, tenTau);


                dsChuyen.add(new Chuyen(maChuyen, thoiGianKhoiHanh, thoiGianDen, tuyen, tau));
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsChuyen;
    }
}
