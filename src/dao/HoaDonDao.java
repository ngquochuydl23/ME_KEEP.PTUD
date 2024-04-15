package dao;

import config.DatabaseUtil;
import entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoaDonDao implements IDao<HoaDon, String> {

    private Connection con;

    public HoaDonDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public HoaDon layTheoMa(String id) {
//        try {
//            String sql = "SELECT * FROM HoaDon WHERE maHoaDon=?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setString(1, id);
//            ResultSet rs = pst.executeQuery();
//
//            while (rs.next()) {
//                String maHoaDon = rs.getString("maHoaDon");
//                LocalDateTime thoiGianTaoHoaDon = rs.getTimestamp("thoiGianTaoHoaDon").toLocalDateTime();
//                String ghiChu = rs.getString("ghiChu");
//                double thueVat = rs.getDouble("thueVat");
//                double tienKhachDua = rs.getDouble("tienKhachDua");
//
//                int maKhachHang = rs.getInt("maKhachHang");
//                int maNhanVien = rs.getInt("maNhanVien");
//
//                String maKhuyenMai = rs.getString("maKhuyenMai");
//
//                return new HoaDon(
//                        maHoaDon,
//                        thoiGianTaoHoaDon,
//                        ghiChu,
//                        thueVat,
//                        tienKhachDua,
//                        new KhachHang(maKhachHang),
//                        new NhanVien(maNhanVien),
//                        new KhuyenMai(maKhuyenMai)
//                );
//            }
//        } catch (Exception e) {
//            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
//        }
        return null;
    }

    @Override
    public List<HoaDon> layHet() {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            String sql = "SELECT * FROM HoaDon";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maHoaDon = rs.getString("MaHoaDon");
                LocalDateTime thoiGianTaoHoaDon = rs.getTimestamp("ThoiGianTaoHoaDon").toLocalDateTime();
                String ghiChu = rs.getString("GhiChu");
                double vat = rs.getDouble("VAT");

                int maKhachHang = rs.getInt("MaKhachHang");
                int maNhanVien = rs.getInt("MaNhanVien");
                String maKhuyenMai = rs.getString("MaKhuyenMai");
                double tongTien = rs.getDouble("TongTien");
                double tamTinh = rs.getDouble("TamTinh");
                double tongTienGiam = rs.getDouble("TongTienGiam");

                dsHoaDon.add(new HoaDon(maHoaDon, thoiGianTaoHoaDon, ghiChu, vat, tongTien, tamTinh, tongTienGiam, new KhachHang(maKhachHang), new NhanVien(maNhanVien), new KhuyenMai(maKhuyenMai)));
            }
        } catch (Exception e) {
            Logger.getLogger(HoaDonDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsHoaDon;
    }

    @Override
    public boolean them(HoaDon entity) {

        return false;
    }

    @Override
    public boolean xoa(String id) {
        return false;
    }

    @Override
    public boolean sua(HoaDon entity) {
        return false;
    }


    public boolean taoHoaDon(String maToaTau,
                             List<Integer> dsSoSlot,
                             HoaDon entity,
                             List<ChiTietHoaDon> dsChiTietHoaDon,
                             List<Ve> dsVe) throws SQLException {
        SlotDao slotDao = new SlotDao();
        try {
            con.setAutoCommit(false);
            String sql = "INSERT INTO quanlibanve.HoaDon (MaHoaDon, ThoiGianTaoHoaDon, GhiChu, VAT, MaKhachHang, MaNhanVien, MaKhuyenMai, TongTien, TamTinh, TongTienGiam) VALUES(?, curtime(), ?, ?, ?, ?, NULL, ?, ?, ?)";
            PreparedStatement hoaDonStmt = con.prepareStatement(sql);


            hoaDonStmt.setString(1, entity.getMaHoaDon());
            hoaDonStmt.setString(2, entity.getGhiChu());
            hoaDonStmt.setDouble(3, entity.getVat());
            hoaDonStmt.setInt(4, entity.getKhachHang().getMaKhachHang());
            hoaDonStmt.setInt(5, entity.getNhanVien().getMaNhanVien());
            hoaDonStmt.setDouble(6, entity.getTongTien());
            hoaDonStmt.setDouble(7, entity.getTamTinh());
            hoaDonStmt.setDouble(8, entity.getTongTienGiam());
            if (hoaDonStmt.executeUpdate() > 0) {
                System.out.println("capNhatHetSlot");
            }

            if (slotDao.capNhatHetSlot(con, maToaTau, dsSoSlot)) {
                System.out.println("capNhatHetSlot");
            }

            PreparedStatement veStmt = con.prepareStatement("INSERT INTO quanlibanve.Ve (MaVe, MaKhachHang, MaTuyen, MaTau, MaSlot) VALUES(?, ?, ?, ?, ?)");
            for (Ve ve : dsVe) {
                veStmt.setString(1, ve.getMaVe());
                veStmt.setInt(2, ve.getKhachHang().getMaKhachHang());
                veStmt.setString(3, ve.getTuyen().getMaTuyen());
                veStmt.setString(4, ve.getTau().getMaTau());
                veStmt.setString(5, ve.getSlot().getMaSlot());
                veStmt.addBatch();
            }
            veStmt.executeBatch();
            System.out.println("Inserted Vé.");

            PreparedStatement cthdStmt = con.prepareStatement("INSERT INTO quanlibanve.ChiTietHoaDon (MaHoaDon, MaVe) VALUES(?, ?)");
            for (ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon) {
                cthdStmt.setString(1, entity.getMaHoaDon());
                cthdStmt.setString(2, chiTietHoaDon.getVe().getMaVe());
                cthdStmt.addBatch();
            }

            cthdStmt.executeBatch();
            System.out.println("Inserted Chi tiết hóa đơn.");
            con.commit();
            return true;
        } catch (SQLException e) {

            con.rollback();
            e.printStackTrace();

            return false;
        } catch (RuntimeException runtimeEx) {
            runtimeEx.printStackTrace();

            return false;
        }
    }
}
