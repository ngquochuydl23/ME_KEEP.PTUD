package dao1;

import config.DatabaseUtil;
import entity.KhachHang;
import entity.NhanVien;
import entity.TiepNhanYeuCauDoiVe;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiepNhanYeuCauDoiVeDao implements IDao<TiepNhanYeuCauDoiVe, Integer> {

    private Connection con;

    public TiepNhanYeuCauDoiVeDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public TiepNhanYeuCauDoiVe layTheoMa(Integer id) {
        try {
            String sql = "SELECT * FROM TiepNhanYeuCauDoiVe WHERE maYeuCau=?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maYeuCau = rs.getInt("maYeuCau");
                LocalDateTime thoiGianTiepNhan = rs.getTimestamp("thoiGianTiepNhan").toLocalDateTime();
                LocalDateTime thoiGianYeuCauDoi = rs.getTimestamp("thoiGianYeuCauDoi").toLocalDateTime();
                String ghiChu = rs.getString("ghiChu");
                int maNhanVien = rs.getInt("maNhanVien");
                int maKhachHang = rs.getInt("maKhachHang");

                return new TiepNhanYeuCauDoiVe(maYeuCau, thoiGianTiepNhan, thoiGianYeuCauDoi, ghiChu, new NhanVien(maNhanVien), new KhachHang(maKhachHang));
            }
        } catch (Exception e) {
            Logger.getLogger(TiepNhanYeuCauDoiVeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<TiepNhanYeuCauDoiVe> layHet() {
        List<TiepNhanYeuCauDoiVe> dsYeuCau = new ArrayList<>();
        try {
            String sql = "SELECT * FROM TiepNhanYeuCauDoiVe";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maYeuCau = rs.getInt("maYeuCau");
                LocalDateTime thoiGianTiepNhan = rs.getTimestamp("thoiGianTiepNhan").toLocalDateTime();
                LocalDateTime thoiGianYeuCauDoi = rs.getTimestamp("thoiGianYeuCauDoi").toLocalDateTime();
                String ghiChu = rs.getString("ghiChu");
                int maNhanVien = rs.getInt("maNhanVien");
                int maKhachHang = rs.getInt("maKhachHang");

                dsYeuCau.add(new TiepNhanYeuCauDoiVe(maYeuCau, thoiGianTiepNhan, thoiGianYeuCauDoi, ghiChu, new NhanVien(maNhanVien), new KhachHang(maKhachHang)));

            }
            return dsYeuCau;
        } catch (Exception e) {
            Logger.getLogger(TiepNhanYeuCauDoiVeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public boolean them(TiepNhanYeuCauDoiVe entity) {
        try {
            String sql = "INSERT INTO `TiepNhanYeuCauDoiVe`(`maYeuCau`, `thoiGianTiepNhan`, `thoiGianYeuCauDoi`, `ghiChu`, `maNhanVien`, `maKhachHang`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, entity.getMaYeuCau());
            statement.setTimestamp(2,Timestamp.valueOf( entity.getThoiGianTiepNhan()));
            statement.setTimestamp(3, Timestamp.valueOf( entity.getThoiGianYeuCauDoi()));
            statement.setString(4, entity.getGhiChu());
            statement.setInt(5, entity.getNhanVien().getMaNhanVien());
            statement.setInt(6, entity.getKhachHang().getMaKhachHang());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TiepNhanYeuCauDoiVeDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(Integer id) {
        try {
            String sql = "DELETE FROM `TiepNhanYeuCauDoiVe` WHERE `maYeuCau` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TiepNhanYeuCauDoiVeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(TiepNhanYeuCauDoiVe entity) {
        try {
            String sql = "UPDATE `TiepNhanYeuCauDoiVe` SET `thoiGianTiepNhan`=?,`thoiGianYeuCauDoi`=?,`ghiChu`=?, `maNhanVien`=?,`maKhachHang`=?  WHERE maYeuCau=?";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setTimestamp(1,Timestamp.valueOf( entity.getThoiGianTiepNhan()));
            pst.setTimestamp(2, Timestamp.valueOf( entity.getThoiGianYeuCauDoi()));
            pst.setString(3, entity.getGhiChu());
            pst.setInt(4, entity.getNhanVien().getMaNhanVien());
            pst.setInt(5, entity.getKhachHang().getMaKhachHang());
            // set where
            pst.setInt(6, entity.getMaYeuCau());

            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TiepNhanYeuCauDoiVeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public int getAutoIncrement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAutoIncrement'");
    }
}
