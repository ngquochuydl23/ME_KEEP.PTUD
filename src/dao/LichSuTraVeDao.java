package dao;

import config.DatabaseUtil;
import entity.*;
import models.TraVeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LichSuTraVeDao implements IDao<LichSuTraVe, Integer> {
    private Connection con;

    public LichSuTraVeDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public LichSuTraVe layTheoMa(Integer id) {
        return null;
    }

    @Override
    public List<LichSuTraVe> layHet() {
        List<LichSuTraVe> dsLichSuTraVe = new ArrayList<>();
        try {
            String sql ="SELECT * FROM quanlibanve.LichSuTraVe ls\n" +
                    "LEFT JOIN KhachHang kh ON kh.MaKhachHang = ls.MaKhachHang \n" +
                    "LEFT JOIN Ve ve ON ve.MaVe = ls.MaVe \n" +
                    "LEFT JOIN Slot slot ON slot.MaSlot = ve.MaSlot \n" +
                    "ORDER BY ThoiGianTraVe DESC\n";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int maLichSuTraVe = rs.getInt("MaLichSuTraVe");
                LocalDateTime thoiGianTraVe = rs.getTimestamp("ThoiGianTraVe").toLocalDateTime();
                String ghiChu = rs.getString("GhiChu");

                int maNhanVien = rs.getInt("MaNhanVienThucHien");


                // khachhang
                int maKhachHang = rs.getInt("kh.MaKhachHang");
                String hoTen = rs.getString("kh.HoTen");
                String soDienThoai = rs.getString("kh.SoDienThoai");
                String cmnd = rs.getString("kh.CMND");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoai, cmnd);

                // Slot
                String maSlot = rs.getString("slot.MaSlot");
                int soSlot = rs.getInt("slot.SoSlot");
                int tinhTrang = rs.getInt("slot.TinhTrang");
                String maKhoang = rs.getString("slot.MaKhoang");
                Slot slot = new Slot( maSlot,  soSlot,  new Khoang(maKhoang),  tinhTrang);

                // Ve
                String maTuyen = rs.getString("ve.MaTuyen");
                String maTau = rs.getString("ve.MaTau");
                String maVe = rs.getString("ve.MaVe");
                String hoTenNguoiDi = rs.getString("ve.HoTenNguoiDi");
                String cccdNguoiDi = rs.getString("ve.CCCDNguoiDi");
                int namSinhNguoiDi = rs.getInt("ve.NamSinhNguoiDi");
                int tinhTrangVe = rs.getInt("ve.TinhTrangVe");
                Ve ve = new Ve(maVe, slot, khachHang, new Tuyen(maTuyen), new Tau(maTau), hoTenNguoiDi, cccdNguoiDi,  namSinhNguoiDi,  tinhTrangVe);

                double phiTraVe = rs.getDouble("ls.PhiTraVe");
                String loaiTraVe = rs.getString("ls.LoaiTraVe");

                dsLichSuTraVe.add(new LichSuTraVe(maLichSuTraVe, thoiGianTraVe, ghiChu,  phiTraVe, loaiTraVe ,khachHang, new NhanVien(maNhanVien), ve));
            }
        } catch (Exception e) {
            Logger.getLogger(LichSuTraVeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsLichSuTraVe;
    }

    public List<LichSuTraVe> layTheoSoDienThoai(String soDienThoai, LocalDate thoiGianTV) {
        List<LichSuTraVe> dsLichSuTheoSDT = new ArrayList<>();
        try {
        	LocalDateTime startOfDay = thoiGianTV.atStartOfDay();
            LocalDateTime endOfDay = thoiGianTV.atStartOfDay().plusDays(1).minusSeconds(1);

            String sql = "SELECT * FROM quanlibanve.LichSuTraVe ls " +
                         "LEFT JOIN KhachHang kh ON kh.MaKhachHang = ls.MaKhachHang " +
                         "LEFT JOIN Ve ve ON ve.MaVe = ls.MaVe " +
                         "LEFT JOIN Slot slot ON slot.MaSlot = ve.MaSlot " +
                         "WHERE kh.SoDienThoai = ? OR ls.ThoiGianTraVe BETWEEN ? AND ? " +
                         "ORDER BY ls.ThoiGianTraVe DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, soDienThoai);
            pst.setTimestamp(2, Timestamp.valueOf(startOfDay));
            pst.setTimestamp(3, Timestamp.valueOf(endOfDay));

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maLichSuTraVe = rs.getInt("MaLichSuTraVe");
                LocalDateTime thoiGianTraVe = rs.getTimestamp("ThoiGianTraVe").toLocalDateTime();
                String ghiChu = rs.getString("GhiChu");
                int maNhanVien = rs.getInt("MaNhanVienThucHien");

                int maKhachHang = rs.getInt("kh.MaKhachHang");
                String hoTen = rs.getString("kh.HoTen");
                String soDienThoaiKH = rs.getString("kh.SoDienThoai");
                String cmnd = rs.getString("kh.CMND");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoaiKH, cmnd);

                String maSlot = rs.getString("slot.MaSlot");
                int soSlot = rs.getInt("slot.SoSlot");
                int tinhTrang = rs.getInt("slot.TinhTrang");
                String maKhoang = rs.getString("slot.MaKhoang");
                Slot slot = new Slot(maSlot, soSlot, new Khoang(maKhoang), tinhTrang);

                String maVe = rs.getString("ve.MaVe");
                String hoTenNguoiDi = rs.getString("ve.HoTenNguoiDi");
                String cccdNguoiDi = rs.getString("ve.CCCDNguoiDi");
                int namSinhNguoiDi = rs.getInt("ve.NamSinhNguoiDi");
                int tinhTrangVe = rs.getInt("ve.TinhTrangVe");
                Ve ve = new Ve(maVe, slot, khachHang, null, null, hoTenNguoiDi, cccdNguoiDi, namSinhNguoiDi, tinhTrangVe);

                double phiTraVe = rs.getDouble("ls.PhiTraVe");
                String loaiTraVe = rs.getString("ls.LoaiTraVe");

                dsLichSuTheoSDT.add(new LichSuTraVe(maLichSuTraVe, thoiGianTraVe, ghiChu, phiTraVe, loaiTraVe, khachHang, new NhanVien(maNhanVien), ve));
            }
        } catch (Exception e) {
            Logger.getLogger(LichSuTraVeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsLichSuTheoSDT;
    }



    @Override
    public boolean them(LichSuTraVe entity) throws SQLException {
        return false;
    }
    @Override
    public boolean xoa(Integer id) {
        return false;
    }

    @Override
    public boolean sua(LichSuTraVe entity) {
        return false;
    }

    public boolean huyVe(List<TraVeModel> dsTraVe, KhachHang khachHang, NhanVien nhanVienThucHien) throws SQLException {
        SlotDao slotDao = new SlotDao();
        VeDao veDao = new VeDao();
        try {

            List<Slot> dsCho = dsTraVe
                    .stream()
                    .map(item -> item.getVe().getSlot())
                    .toList();

            List<Ve> dsVeCanHuy = dsTraVe
                    .stream()
                    .map(item -> item.getVe())
                    .toList();

            con.setAutoCommit(false);

            if (veDao.capNhatThanhVeDaHuy(con, dsVeCanHuy)) {
                Logger.getLogger(LichSuTraVeDao.class.getName()).log(Level.INFO, "Bước 1. Vé đã thành trạng thái hủy");

            }

            if (slotDao.capNhatConSlot(con, dsCho)) {
                Logger.getLogger(LichSuTraVeDao.class.getName()).log(Level.INFO, "Bước 2. Chuyển trạng thái thành còn chỗ");
            }

            PreparedStatement lichSuTraVeStmt = con.prepareStatement("INSERT INTO LichSuTraVe (MaKhachHang, MaVe, PhiTraVe, LoaiTraVe, MaNhanVienThucHien) VALUES(?, ?, ?, ?, ?)");
            for (TraVeModel duDinhTraVe : dsTraVe) {
                lichSuTraVeStmt.setInt(1, khachHang.getMaKhachHang());
                lichSuTraVeStmt.setString(2, duDinhTraVe.getVe().getMaVe());
                lichSuTraVeStmt.setDouble(3, duDinhTraVe.tinhPhiTraVe());
                lichSuTraVeStmt.setString(4, duDinhTraVe.tinhLoaiTraVe());
                lichSuTraVeStmt.setInt(5, nhanVienThucHien.getMaNhanVien());
                lichSuTraVeStmt.addBatch();
            }
            lichSuTraVeStmt.executeBatch();
            Logger.getLogger(LichSuTraVeDao.class.getName()).log(Level.INFO, "Đã ghi lại lịch sử trả vé.");
            con.commit();
            return true;
        }
        catch (RuntimeException runtimeEx) {
            runtimeEx.printStackTrace();

            return false;
        } catch (SQLException e) {

            con.rollback();
            e.printStackTrace();

            return false;
        }
    }
}
