package dao;

import config.DatabaseUtil;
import entity.*;
import models.TraVeModel;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeDao implements IDao<Ve, String> {

    private Connection con;

    public VeDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Ve layTheoMa(String id) {
//        try {
//            String sql = "SELECT * FROM Ve WHERE maVe=?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setString(1, id);
//            ResultSet rs = pst.executeQuery();
//
//            while (rs.next()) {
//                String maVe = rs.getString("maVe");
//                int choNgoi = rs.getInt("choNgoi");
//                double giaVe = rs.getDouble("giaVe");
//                String moTa = rs.getString("moTa");
//                int tinhTrangVe = rs.getInt("tinhTrangVe");
//                String maKhoang = rs.getString("maKhoang");
//
//                return new Ve(maVe, choNgoi, giaVe, moTa, tinhTrangVe,null);
//            }
//        } catch (Exception e) {
//            Logger.getLogger(VeDao.class.getName()).log(Level.SEVERE, null, e);
//        }
        return null;
    }

    @Override
    public List<Ve> layHet() {
        List<Ve> dsVe = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Ve";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maVe = rs.getString("maVe");
                int choNgoi = rs.getInt("choNgoi");
                double giaVe = rs.getDouble("giaVe");
                String moTa = rs.getString("moTa");
                int tinhTrangVe = rs.getInt("tinhTrangVe");
                String maLoaiVe = rs.getString("maLoaiVe");
                String maKhoang = rs.getString("maKhoang");

                // dsVe.add(new Ve(maVe, choNgoi, giaVe, moTa, tinhTrangVe, null));
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

//            statement.setString(1, entity.getMaVe());
//            statement.setInt(2, entity.getChoNgoi());
//            statement.setDouble(3, entity.getGiaVe());
//            statement.setString(4, entity.getMoTa());
//            statement.setInt(5, entity.getTinhTrangVe());
//            statement.setString(6,"");

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
            String sql = "UPDATE `Ve` SET `choNgoi`=?,`giaVe`=?,`moTa`=?, `tinhTrangVe`=?, `maKhoang`=? WHERE maVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
//
//            pst.setInt(1, entity.getChoNgoi());
//            pst.setDouble(2, entity.getGiaVe());
//            pst.setString(3, entity.getMoTa());
//            pst.setInt(4, entity.getTinhTrangVe());
//            pst.setString(5, "");

            pst.setString(7, entity.getMaVe());
            return pst.executeUpdate() > 0;
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
                Ve ve = new Ve(maVe, slot, new KhachHang(maKhachHang), new Tuyen(maTuyen), new Tau(maTau), hoTenNguoiDi, cccdNguoiDi, namSinhNguoiDi, tinhTrangVe);
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
            else return false;
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
}
