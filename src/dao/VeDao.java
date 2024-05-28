package dao;

import config.DatabaseUtil;
import entity.*;
import models.TraVeModel;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeDao implements IDao<Ve, String> {

    private Connection con;

    public VeDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Ve layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Ve v " +
                    "LEFT JOIN KhachHang kh ON v.MaKhachHang = kh.MaKhachHang " +
                    "LEFT JOIN Tuyen t ON t.MaTuyen = v.MaTuyen " +
                    "LEFT JOIN Slot s ON s.MaSlot = v.MaSlot " +
                    "LEFT JOIN Tau t2 ON t2.MaTau = v.MaTau " +
                    "LEFT JOIN Ga gaDi ON gaDi.MaGa = t.MaGaDi " +
                    "LEFT JOIN Ga gaDen ON gaDen.MaGa = t.MaGaDen " +
                    "WHERE v.maVe= ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String maVe = rs.getString("maVe");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String hoTenNguoiDi = rs.getString("hoTenNguoiDi");
                String cccdNguoiDi = rs.getString("cccdNguoiDi");
                int nguoiLon = rs.getInt("nguoiLon");
                int namSinhNguoiDi = rs.getInt("namSinhNguoiDi");

                // Khach Hang
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("HoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String CMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = LocalDateTime.ofInstant(
                        new java.util.Date(rs.getDate("thoiGianDangKy").getTime()).toInstant(), ZoneId.systemDefault());
                boolean laKhachHangThanThiet = rs.getBoolean("LaKhachHangThanThiet");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy,
                        laKhachHangThanThiet,
                        CMND);

                // Slot
                String maSlot = rs.getString("maSlot");
                int soSlot = rs.getInt("soSlot");
                Slot slot = new Slot(maSlot, soSlot);

                // Tuyen
                String maTuyen = rs.getString("maTuyen");
                String maGaDi = rs.getString("MaGaDi");
                String tenGaDi = rs.getString("gaDi.TenGa");

                String maGaDen = rs.getString("MaGaDen");
                String tenGaDen = rs.getString("gaDen.TenGa");
                Tuyen tuyen = new Tuyen(maTuyen, new Ga(maGaDi, tenGaDi, ""), new Ga(maGaDen, tenGaDen, ""), 0);

                // Tau
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");
                Tau tau = new Tau(maTau, tenTau);

                return new Ve(maVe, slot, khachHang, tuyen, tau, hoTenNguoiDi, cccdNguoiDi, namSinhNguoiDi,
                        tinhTrangVe);
            }
        } catch (SQLException e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Ve layVeTheoMaVaSdt(String id, String sdt) {
        try {
            String sql = "SELECT * FROM Ve v " +
                    "LEFT JOIN KhachHang kh ON v.MaKhachHang = kh.MaKhachHang " +
                    "LEFT JOIN Tuyen t ON t.MaTuyen = v.MaTuyen " +
                    "LEFT JOIN Slot s ON s.MaSlot = v.MaSlot " +
                    "LEFT JOIN Tau t2 ON t2.MaTau = v.MaTau " +
                    "LEFT JOIN Ga gaDi ON gaDi.MaGa = t.MaGaDi " +
                    "LEFT JOIN Ga gaDen ON gaDen.MaGa = t.MaGaDen " +
                    "WHERE v.maVe = ? AND kh.SoDienThoai = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, sdt);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String maVe = rs.getString("maVe");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String hoTenNguoiDi = rs.getString("hoTenNguoiDi");
                String cccdNguoiDi = rs.getString("cccdNguoiDi");
                int nguoiLon = rs.getInt("nguoiLon");
                int namSinhNguoiDi = rs.getInt("namSinhNguoiDi");

                // Khach Hang
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("HoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String CMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = LocalDateTime.ofInstant(
                        new java.util.Date(rs.getDate("thoiGianDangKy").getTime()).toInstant(), ZoneId.systemDefault());
                boolean laKhachHangThanThiet = rs.getBoolean("LaKhachHangThanThiet");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy,
                        laKhachHangThanThiet,
                        CMND);

                // Slot
                String maSlot = rs.getString("maSlot");
                int soSlot = rs.getInt("soSlot");
                Slot slot = new Slot(maSlot, soSlot);

                // Tuyen
                String maTuyen = rs.getString("maTuyen");
                String maGaDi = rs.getString("MaGaDi");
                String tenGaDi = rs.getString("gaDi.TenGa");

                String maGaDen = rs.getString("MaGaDen");
                String tenGaDen = rs.getString("gaDen.TenGa");
                Tuyen tuyen = new Tuyen(maTuyen, new Ga(maGaDi, tenGaDi, ""), new Ga(maGaDen, tenGaDen, ""), 0);

                // Tau
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");
                Tau tau = new Tau(maTau, tenTau);

                return new Ve(maVe, slot, khachHang, tuyen, tau, hoTenNguoiDi, cccdNguoiDi, namSinhNguoiDi,
                        tinhTrangVe);
            }
        } catch (SQLException e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Ve> layHet() {
        List<Ve> dsVe = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Ve v \r\n" + //
                    "LEFT JOIN KhachHang kh ON v.MaKhachHang = kh.MaKhachHang \r\n" + //
                    "LEFT JOIN Tuyen t ON t.MaTuyen = v.MaTuyen \r\n" + //
                    "LEFT JOIN Slot s ON s.MaSlot = v.MaSlot\r\n" + //
                    "LEFT JOIN Khoang k ON s.MaKhoang = k.MaKhoang \r\n" + //
                    "LEFT JOIN ToaTau tt ON tt.MaToa = k.MaToa \r\n" + //
                    "LEFT JOIN Tau t2 ON t2.MaTau = v.MaTau ";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maVe = rs.getString("maVe");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String hoTenNguoiDi = rs.getString("hoTenNguoiDi");
                String cccdNguoiDi = rs.getString("cccdNguoiDi");
                int nguoiLon = rs.getInt("nguoiLon");
                int namSinhNguoiDi = rs.getInt("namSinhNguoiDi");

                // Khach Hang
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("HoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String CMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = LocalDateTime.ofInstant(
                        new java.util.Date(rs.getDate("thoiGianDangKy").getTime()).toInstant(), ZoneId.systemDefault());
                boolean laKhachHangThanThiet = rs.getBoolean("LaKhachHangThanThiet");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy,
                        laKhachHangThanThiet,
                        CMND);

                // Slot
                String maSlot = rs.getString("maSlot");
                int soSlot = rs.getInt("soSlot");
                Slot slot = new Slot(maSlot, soSlot);

                // Tuyen
                String maTuyen = rs.getString("maTuyen");
                Tuyen tuyen = new Tuyen(maTuyen);

                // Tau
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");
                Tau tau = new Tau(maTau, tenTau);

                dsVe.add(new Ve(maVe, slot, khachHang, tuyen, tau, hoTenNguoiDi, cccdNguoiDi, namSinhNguoiDi,
                        tinhTrangVe));
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return dsVe;
    }

    @Override
    public boolean them(Ve entity) {

        try {
            String sql = "INSERT INTO `Ve`(`maVe`, `choNgoi`, `giaVe`, `moTa`, `tinhTrangVe`,`maKhoang`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            // statement.setString(1, entity.getMaVe());
            // statement.setInt(2, entity.getChoNgoi());
            // statement.setDouble(3, entity.getGiaVe());
            // statement.setString(4, entity.getMoTa());
            // statement.setInt(5, entity.getTinhTrangVe());
            // statement.setString(6,"");

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(String id) {
        try {
            String sql = "DELETE FROM `Ve` WHERE `maVe` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(Ve entity) {
        try {
            String sql = "UPDATE Ve SET TinhTrangVe = ?, HoTenNguoiDi = ?, CCCDNguoiDi = ?, NamSinhNguoiDi = ? WHERE MaVe = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, entity.getTinhTrangVe());
            stmt.setString(2, entity.getHoTenNguoiDi());
            stmt.setString(3, entity.getCccdNguoiDi());
            stmt.setInt(4, entity.getNamSinhNguoiDi());
            stmt.setString(5, entity.getMaVe());

            // Thực thi câu lệnh SQL
            stmt.executeUpdate();

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<TraVeModel> layVeTheoSdtKhachHang(String soDienThoai) {
        List<TraVeModel> dsTraVeModel = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Ve ve\n" +
                    "LEFT JOIN ChiTietHoaDon cthd ON cthd.MaVe = ve.MaVe \n" +
                    "LEFT JOIN Slot slot ON slot.MaSlot = ve.MaSlot \n" +
                    "LEFT JOIN HoaDon hd ON hd.MaHoaDon = cthd.MaHoaDon  \n" +
                    "LEFT JOIN Khoang khoang ON khoang.MaKhoang = slot.MaKhoang \n" +
                    "LEFT JOIN KhachHang kh ON kh.MaKhachHang = ve.MaKhachHang\n" +
                    "LEFT JOIN Tuyen tuyen ON ve.MaTuyen = tuyen.MaTuyen\n" +
                    "LEFT JOIN Chuyen chuyen ON chuyen.MaTuyen = tuyen.MaTuyen AND ve.MaTau = chuyen.MaTau \n" +
                    "WHERE kh.SoDienThoai = ? AND ve.TinhTrangVe = 1 AND chuyen.ThoiGianKhoiHanh > CURRENT_TIME() \n" +
                    "ORDER BY chuyen.ThoiGianKhoiHanh";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, soDienThoai);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maVe = rs.getString("ve.MaVe");
                int tinhTrangVe = rs.getInt("ve.TinhTrangVe");
                String hoTenNguoiDi = rs.getString("ve.HoTenNguoiDi");
                String cccdNguoiDi = rs.getString("ve.CCCDNguoiDi");
                int namSinhNguoiDi = rs.getInt("ve.NamSinhNguoiDi");
                String maTau = rs.getString("ve.MaTau");
                String maTuyen = rs.getString("ve.MaTuyen");
                int maKhachHang = rs.getInt("ve.MaKhachHang");
                LocalDateTime thoiGianKhoiHanh = rs.getTimestamp("chuyen.ThoiGianKhoiHanh").toLocalDateTime();

                String maKhoang = rs.getString("khoang.maKhoang");
                String tenKhoang = rs.getString("khoang.TenKhoang");
                String maSlot = rs.getString("slot.MaSlot");
                int tinhTrang = rs.getInt("slot.TinhTrang");
                int soSlot = rs.getInt("slot.SoSlot");

                double donGia = rs.getDouble("cthd.DonGia");

                Slot slot = new Slot(maSlot, soSlot, new Khoang(maKhoang), tinhTrang);
                Ve ve = new Ve(maVe, slot, new KhachHang(maKhachHang), new Tuyen(maTuyen), new Tau(maTau), hoTenNguoiDi,
                        cccdNguoiDi, namSinhNguoiDi, tinhTrangVe);
                dsTraVeModel.add(new TraVeModel(ve, donGia, thoiGianKhoiHanh, tenKhoang));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsTraVeModel;
    }

    public boolean capNhatThanhVeDaHuy(Connection conn, List<Ve> dsVeCanHuy) throws SQLException {
        for (Ve ve : dsVeCanHuy) {
            String sql = "UPDATE quanlibanve.Ve ve\n" +
                    "SET ve.TinhTrangVe = 0\n" +
                    "WHERE ve.MaVe = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, ve.getMaVe());

            if (pst.executeUpdate() > 0)
                continue;
            else
                return false;
        }
        return true;
    }

    public Map<String, Long> thongKeDoTuoiDiTau(LocalDate from, LocalDate to) {
        Map<String, Long> mapDataset = new HashMap<>();

        try {
            String sql = "SELECT NamSinhNguoiDi, COUNT(*) AS SoLuongVe  FROM Ve ve\n" +
                    "LEFT JOIN ChiTietHoaDon cthd ON cthd.MaVe = ve.MaVe\n" +
                    "LEFT JOIN HoaDon hd ON hd.MaHoaDon = cthd.MaHoaDon \n" +
                    "WHERE  CAST(hd.ThoiGianTaoHoaDon AS DATE) BETWEEN ? AND ?\n" +
                    "GROUP BY NamSinhNguoiDi\n";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(from));
            pst.setDate(2, Date.valueOf(to));
            ResultSet rs = pst.executeQuery();

            Long tongDuoi18Tuoi = 0L;
            Long tongTu18DenDuoi60 = 0L;
            Long tongTren60 = 0L;

            while (rs.next()) {
                Long namSinhNguoiDi = rs.getLong("NamSinhNguoiDi");
                Long soLuongVe = rs.getLong("SoLuongVe");

                if (LocalDate.now().getYear() - namSinhNguoiDi < 18) {
                    tongDuoi18Tuoi += soLuongVe;
                } else if (LocalDate.now().getYear() - namSinhNguoiDi >= 18 && LocalDate.now().getYear() - namSinhNguoiDi < 60) {
                    tongTu18DenDuoi60 += soLuongVe;
                } else {
                    tongTren60 += soLuongVe;
                }
            }

            mapDataset.put("Dưới 18 tuổi", tongDuoi18Tuoi);
            mapDataset.put("Từ 18 đến dưới 60 tuổi", tongTu18DenDuoi60);
            mapDataset.put("Trên 60 tuổi", tongTren60);
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapDataset;
    }


    public Map<LocalDate, Long> laySoLuongVeTheoNgay(LocalDate from, LocalDate to, int tinhTrangVe) {
        Map<LocalDate, Long> mapDataset = new HashMap<>();

        try {
            String sql = "SELECT CAST(hd.ThoiGianTaoHoaDon AS DATE) As Ngay, COUNT(*) As SoLuongVe FROM quanlibanve.Ve ve\n" +
                    "LEFT JOIN ChiTietHoaDon cthd ON cthd.MaVe = ve.MaVe\n" +
                    "LEFT JOIN HoaDon hd ON hd.MaHoaDon = cthd.MaHoaDon \n" +
                    "WHERE ve.TinhTrangVe = ? AND (CAST(hd.ThoiGianTaoHoaDon AS DATE) BETWEEN ? AND ?)\n" +
                    "GROUP BY CAST(hd.ThoiGianTaoHoaDon AS DATE)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, tinhTrangVe);
            pst.setDate(2, Date.valueOf(from));
            pst.setDate(3, Date.valueOf(to));


            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                LocalDate ngay = rs.getDate("Ngay").toLocalDate();
                Long soLuongVe = rs.getLong("SoLuongVe");
                mapDataset.put(ngay, soLuongVe);
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapDataset;
    }

    public List<Map<String, Object>> layHetCoToaTau() {
        List<Map<String, Object>> maps = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Ve v \r\n" + //
                    "LEFT JOIN KhachHang kh ON v.MaKhachHang = kh.MaKhachHang \r\n" + //
                    "LEFT JOIN Tuyen t ON t.MaTuyen = v.MaTuyen \r\n" + //
                    "LEFT JOIN Slot s ON s.MaSlot = v.MaSlot\r\n" + //
                    "LEFT JOIN Khoang k ON s.MaKhoang = k.MaKhoang \r\n" + //
                    "LEFT JOIN ToaTau tt ON tt.MaToa = k.MaToa \r\n" + //
                    "LEFT JOIN Tau t2 ON t2.MaTau = v.MaTau ";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maVe = rs.getString("maVe");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String hoTenNguoiDi = rs.getString("hoTenNguoiDi");
                String cccdNguoiDi = rs.getString("cccdNguoiDi");
                int nguoiLon = rs.getInt("nguoiLon");
                int namSinhNguoiDi = rs.getInt("namSinhNguoiDi");

                // Khach Hang
                int maKhachHang = rs.getInt("maKhachHang");
                String hoTen = rs.getString("HoTen");
                String soDienThoai = rs.getString("soDienThoai");
                String CMND = rs.getString("CMND");
                LocalDateTime thoiGianDangKy = LocalDateTime.ofInstant(
                        new java.util.Date(rs.getDate("thoiGianDangKy").getTime()).toInstant(), ZoneId.systemDefault());
                boolean laKhachHangThanThiet = rs.getBoolean("LaKhachHangThanThiet");
                KhachHang khachHang = new KhachHang(maKhachHang, hoTen, soDienThoai, thoiGianDangKy,
                        laKhachHangThanThiet,
                        CMND);

                // Slot
                String maSlot = rs.getString("maSlot");
                int soSlot = rs.getInt("soSlot");
                Slot slot = new Slot(maSlot, soSlot);

                // Tuyen
                String maTuyen = rs.getString("maTuyen");
                Tuyen tuyen = new Tuyen(maTuyen);

                // Tau
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");
                Tau tau = new Tau(maTau, tenTau);

                // Toa
                String maToa = rs.getString("maToa");
                String tenToa = rs.getString("tenToa");
                ToaTau toaTau = new ToaTau(maToa, tenToa);

                Map<String, Object> map = new HashMap<>();
                Ve ve = new Ve(maVe, slot, khachHang, tuyen, tau, hoTenNguoiDi, cccdNguoiDi, namSinhNguoiDi,
                        tinhTrangVe);
                map.put("ve", ve);
                map.put("toa", toaTau);
                maps.add(map);
            }
        } catch (Exception e) {
            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return maps;
    }
}
