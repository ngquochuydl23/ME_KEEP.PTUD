package dao;

import config.DatabaseUtil;
import entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        try {
            String sql = "SELECT *, kh.HoTen AS HoTenKhachHang, \r\n" + //
                    "nv.HoTen AS HoTenNhanVien, \r\n" + //
                    "nv.SoDienThoai AS SDTNhanVien,\r\n" + //
                    "kh.SoDienThoai AS SDTKhachHang, \r\n" + //
                    "km.GhiChu AS GhiChuKhuyenMai \r\n" + // "
                    "FROM HoaDon hd  LEFT JOIN KhachHang kh ON kh.MaKhachHang = hd.MaKhachHang \r\n" + //
                    "LEFT JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien \r\n" + //
                    "LEFT JOIN  KhuyenMai km ON hd.MaKhuyenMai = km.MaKhuyenMai WHERE maHoaDon = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                LocalDateTime thoiGianTaoHoaDon = rs.getTimestamp("thoiGianTaoHoaDon").toLocalDateTime();
                String ghiChu = rs.getString("ghiChu");
                double vat = rs.getDouble("VAT");
                double tongTien = rs.getDouble("tongTien");
                double tamTinh = rs.getDouble("tamTinh");
                double tongTienGiam = rs.getDouble("tongTienGiam");

                // KhachHang
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("hoTenKhachHang");
                String soDienThoai = rs.getString("SDTKhachHang");
                String cmnd = rs.getString("CMND");
                LocalDateTime thoigianDKi = LocalDateTime.ofInstant(
                        new Date(rs.getDate("thoiGianDangKy").getTime()).toInstant(), ZoneId.systemDefault());
                boolean laKhachHangThanThiet = rs.getBoolean("LaKhachHangThanThiet");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoai, thoigianDKi, laKhachHangThanThiet,
                        cmnd);

                // Nhan Vien
                int maNhanVien = rs.getInt("maNhanVien");
                String hoTenNV = rs.getString("hoTenNhanVien");
                String soDienThoaiNV = rs.getString("SDTNhanVien");
                String email = rs.getString("email");
                LocalDate ngayDangKy = LocalDate.ofInstant(new Date(rs.getDate("ngayDangKy").getTime()).toInstant(),
                        ZoneId.systemDefault());
                LocalDate ngaySinh = LocalDate.ofInstant(new Date(rs.getDate("ngaySinh").getTime()).toInstant(),
                        ZoneId.systemDefault());
                int trangThai = rs.getInt("trangThai");
                int gioiTinh = rs.getInt("gioiTinh");
                String vaiTro = rs.getString("VaiTro");
                NhanVien nhanVien = new NhanVien(maNhanVien, hoTenNV, gioiTinh, soDienThoaiNV, ngayDangKy, ngaySinh,
                        trangThai, soDienThoaiNV, email, vaiTro);

                // KhuyenMai
                String maKhuyenMai = rs.getString("maKhuyenMai");
                double phanTramGiamGia = rs.getDouble("phanTramGiamGia");
                String ghiChuKM = rs.getString("GhiChuKhuyenMai");
                LocalDateTime thoiGianBatDau = LocalDateTime.ofInstant(
                        new Date(rs.getDate("ThoiGianBatDau").getTime()).toInstant(), ZoneId.systemDefault());
                LocalDateTime thoiGianKetThuc = LocalDateTime.ofInstant(
                        new Date(rs.getDate("ThoiGianKetThuc").getTime()).toInstant(), ZoneId.systemDefault());
                KhuyenMai khuyenMai = new KhuyenMai(maKhuyenMai, phanTramGiamGia, ghiChuKM, thoiGianBatDau,
                        thoiGianKetThuc);

                return new HoaDon(maHoaDon, thoiGianTaoHoaDon, ghiChu, vat, tongTien, tamTinh, tongTienGiam, khachHang,
                        nhanVien, khuyenMai);
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<HoaDon> layHet() {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            String sql = "SELECT hd.*, kh.HoTen, kh.SoDienThoai, kh.ThoiGianDangKy, kh.LaKhachHangThanThiet FROM HoaDon hd JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang";

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

                // Lấy thông tin của khách hàng từ ResultSet
                String hoTenKhachHang = rs.getString("HoTen");
                String soDienThoaiKhachHang = rs.getString("SoDienThoai");
                LocalDateTime thoiGianDangKy = rs.getTimestamp("ThoiGianDangKy").toLocalDateTime();
                boolean laKhachHangThanThiet = rs.getBoolean("LaKhachHangThanThiet");

                KhachHang khachHang = new KhachHang(maKhachHang, hoTenKhachHang, soDienThoaiKhachHang, thoiGianDangKy,
                        laKhachHangThanThiet);
                dsHoaDon.add(new HoaDon(maHoaDon, thoiGianTaoHoaDon, ghiChu, vat, tongTien, tamTinh, tongTienGiam,
                        khachHang, new NhanVien(maNhanVien), new KhuyenMai(maKhuyenMai)));
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

    public boolean taoHoaDon(
            List<Slot> dsChoDaChon,
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
                System.out.println("Bước 1. Tạo hóa đơn thành công");
            }

            if (slotDao.capNhatHetSlot(con, dsChoDaChon)) {
                System.out.println("Bước 2. Chuyển trạng thái thành hết chỗ");
            }

            PreparedStatement veStmt = con.prepareStatement(
                    "INSERT INTO quanlibanve.Ve (MaVe, MaKhachHang, MaTuyen, MaTau, MaSlot, TinhTrangVe, HoTenNguoiDi, CCCDNguoiDi, NguoiLon, NamSinhNguoiDi) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (Ve ve : dsVe) {
                veStmt.setString(1, ve.getMaVe());
                veStmt.setInt(2, ve.getKhachHang().getMaKhachHang());
                veStmt.setString(3, ve.getTuyen().getMaTuyen());
                veStmt.setString(4, ve.getTau().getMaTau());
                veStmt.setString(5, ve.getSlot().getMaSlot());
                veStmt.setInt(6, 1);
                veStmt.setString(7, ve.getHoTenNguoiDi());
                veStmt.setString(8, ve.getCccdNguoiDi());
                veStmt.setInt(9, ve.laNguoiLon() ? 1 : 0);
                veStmt.setInt(10, ve.getNamSinhNguoiDi());
                veStmt.addBatch();
            }
            veStmt.executeBatch();
            System.out.println("Inserted Vé.");

            PreparedStatement cthdStmt = con
                    .prepareStatement("INSERT INTO quanlibanve.ChiTietHoaDon (MaHoaDon, MaVe, DonGia) VALUES(?, ?, ?)");
            for (ChiTietHoaDon chiTietHoaDon : dsChiTietHoaDon) {
                cthdStmt.setString(1, entity.getMaHoaDon());
                cthdStmt.setString(2, chiTietHoaDon.getVe().getMaVe());
                // cthdStmt.setDouble(3, chiTietHoaDon.getDonGia());
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
