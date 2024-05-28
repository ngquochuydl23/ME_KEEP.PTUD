package dao;

import config.DatabaseUtil;
import entity.KhachHang;
import entity.NhanVien;
import entity.TiepNhanYeuCauDoiVe;
import entity.Ve;

import java.sql.*;
import java.time.LocalDate;
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
                LocalDate thoiGianYeuCauDoi = rs.getDate("thoiGianYeuCauDoi").toLocalDate();
                String ghiChu = rs.getString("ghiChu");
                int maNhanVien = rs.getInt("maNhanVien");
                int maKhachHang = rs.getInt("maKhachHang");
                String maVe = rs.getString("maVe");
                return new TiepNhanYeuCauDoiVe(maYeuCau, thoiGianTiepNhan, thoiGianYeuCauDoi, ghiChu, new NhanVien(maNhanVien), new KhachHang(maKhachHang), new Ve(maVe));
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
                LocalDate thoiGianYeuCauDoi = rs.getDate("thoiGianYeuCauDoi").toLocalDate();
                String ghiChu = rs.getString("ghiChu");
                int maNhanVien = rs.getInt("maNhanVien");
                int maKhachHang = rs.getInt("maKhachHang");
                String maVe = rs.getString("maVe");
                dsYeuCau.add(new TiepNhanYeuCauDoiVe(maYeuCau, thoiGianTiepNhan, thoiGianYeuCauDoi, ghiChu, new NhanVien(maNhanVien), new KhachHang(maKhachHang), new Ve(maVe)));

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
            String sql = "INSERT INTO `TiepNhanYeuCauDoiVe`(`thoiGianYeuCauDoi`, `ghiChu`, `maNhanVien`, `maKhachHang`, `maVe`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setDate(1, Date.valueOf(entity.getThoiGianYeuCauDoi()));
            statement.setString(2, entity.getGhiChu());
            statement.setInt(3, entity.getNhanVien().getMaNhanVien());
            statement.setInt(4, entity.getKhachHang().getMaKhachHang());
            statement.setString(5, entity.getVe().getMaVe());

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
            pst.setDate(2, Date.valueOf(entity.getThoiGianYeuCauDoi()));
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
}
