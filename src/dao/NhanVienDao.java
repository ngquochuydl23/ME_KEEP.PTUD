package dao;

import config.DatabaseUtil;
import entity.NhanVien;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVienDao implements IDao<NhanVien, Integer> {

    private Connection con;

    public NhanVienDao() {
        con = DatabaseUtil.getConnection();
    }


    @Override
    public NhanVien layTheoMa(Integer id) {
        return null;
    }

    @Override
    public List<NhanVien> layHet() {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        try {
            String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String hoTen = rs.getString("HoTen");

                int gioiTinh = rs.getInt("GioiTinh");
                String soDienThoai = rs.getString("SoDienThoai");
                LocalDate ngayDangKy = rs.getDate("NgayDangKy").toLocalDate();
                LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
                int trangThai = rs.getInt("TrangThai");
                String email = rs.getString("Email");
                String vaiTro = rs.getString("VaiTro");
                String matKhau = rs.getString("MatKhau");

                dsNhanVien.add(new NhanVien(maNhanVien, hoTen, gioiTinh, soDienThoai, ngayDangKy, ngaySinh, trangThai, matKhau, email, vaiTro));
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsNhanVien;
    }

    @Override
    public boolean them(NhanVien entity) {
        try {
            String sql = "INSERT INTO `NhanVien`(`HoTen`, `Gioitinh`, `SoDienThoai`, `NgaySinh`,`Trangthai`, `MatKhau`, `Email`, `VaiTro`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, entity.getHoTen());
            statement.setInt(2, entity.getGioitinh());
            statement.setString(3, entity.getSoDienThoai());
            statement.setDate(4, Date.valueOf(entity.getNgaysinh()));
            statement.setInt(5, entity.getTrangthai());
            statement.setString(6, entity.getMatKhau());
            statement.setString(7, entity.getEmail());
            statement.setString(8, entity.getVaiTro());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public boolean themVoiDanhSach(List<NhanVien> entities) {
        try {
            String sql = "INSERT INTO `NhanVien`(`HoTen`, `Gioitinh`, `SoDienThoai`, `NgaySinh`,`Trangthai`, `MatKhau`, `Email`, `VaiTro`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);

            for (NhanVien entity : entities) {
                statement.setString(1, entity.getHoTen());
                statement.setInt(2, entity.getGioitinh());
                statement.setString(3, entity.getSoDienThoai());
                statement.setDate(4, Date.valueOf(entity.getNgaysinh()));
                statement.setInt(5, entity.getTrangthai());
                statement.setString(6, entity.getMatKhau());
                statement.setString(7, entity.getEmail());
                statement.setString(8, entity.getVaiTro());

                statement.addBatch();
            }


            return statement.executeBatch().length > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public boolean xoa(Integer id) {
        try {
            String sql = "DELETE FROM `NhanVien` WHERE `MaNhanVien` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sua(NhanVien entity) {
        try {
            Connection connect = DatabaseUtil.getConnection();
            String sql = "UPDATE `NhanVien` SET `HoTen`=?, `SoDienThoai`=?, `Email`=?, `MatKhau`=?, `GioiTinh`=?, `NgaySinh`=?, `VaiTro`=? WHERE MaNhanVien=?";
            PreparedStatement pst = connect.prepareStatement(sql);

            pst.setString(1, entity.getHoTen());
            pst.setString(2, entity.getSoDienThoai());
            pst.setString(3, entity.getEmail());
            pst.setString(4, entity.getMatKhau());
            pst.setInt(5, entity.getGioitinh());
            pst.setDate(6, Date.valueOf(entity.getNgaysinh()));
            pst.setString(7, entity.getVaiTro());

            // set where
            pst.setInt(8, entity.getMaNhanVien());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public NhanVien layDsNhanVienTheoTen(String ten) {
        try {
            String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1 AND HoTen = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ten);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String hoTen = rs.getString("HoTen");

                int gioiTinh = rs.getInt("GioiTinh");
                String soDienThoai = rs.getString("SoDienThoai");
                LocalDate ngayDangKy = rs.getDate("NgayDangKy").toLocalDate();
                LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
                int trangThai = rs.getInt("TrangThai");
                String email = rs.getString("Email");

                return new NhanVien(maNhanVien, hoTen, gioiTinh, soDienThoai, ngayDangKy, ngaySinh, trangThai, email);
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }

    public NhanVien timNhanVienTheoSdt(String sdt) {
        try {
            String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1 AND SoDienThoai = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, sdt);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String hoTen = rs.getString("HoTen");

                int gioiTinh = rs.getInt("GioiTinh");
                String soDienThoai = rs.getString("SoDienThoai");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                int trangThai = rs.getInt("TrangThai");
                String matKhau = rs.getString("MatKhau");
                String email = rs.getString("Email");
                String vaiTro = rs.getString("VaiTro");

                return new NhanVien(maNhanVien, hoTen, gioiTinh, soDienThoai, ngaySinh, trangThai, matKhau, email, vaiTro);
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }

    public NhanVien timNhanVienTheoEmail(String emailParam) {
        try {
            String sql = "SELECT * FROM NhanVien WHERE TrangThai = 1 AND Email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, emailParam);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String hoTen = rs.getString("HoTen");

                int gioiTinh = rs.getInt("GioiTinh");
                String soDienThoai = rs.getString("SoDienThoai");
                LocalDate ngayDangKy = rs.getDate("NgayDangKy").toLocalDate();
                LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
                int trangThai = rs.getInt("TrangThai");
                String email = rs.getString("Email");

                return new NhanVien(maNhanVien, hoTen, gioiTinh, soDienThoai, ngayDangKy, ngaySinh, trangThai, email);
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }

    public Map<String, Long> thongKeBanHangTheoNhanVien(LocalDate from, LocalDate to) {
        Map<String, Long> mapDataset = new HashMap<>();

        try {
            String sql = "SELECT nv.MaNhanVien, nv.HoTen, SUM(hd.TongTien) as TongDoanhSo FROM NhanVien nv \n" +
                    "LEFT JOIN HoaDon hd ON hd.MaNhanVien = nv.MaNhanVien\n" +
                    "WHERE CAST(hd.ThoiGianTaoHoaDon AS DATE) BETWEEN ? AND ?\n" +
                    "GROUP BY nv.MaNhanVien, nv.HoTen ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(from));
            pst.setDate(2, Date.valueOf(to));
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int maNhanVien = rs.getInt("MaNhanVien");
                String hoTen = rs.getString("HoTen");
                long tongDoanhSo = rs.getLong("TongDoanhSo");
                mapDataset.put(hoTen, tongDoanhSo);
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapDataset;
    }
}
