package dao1;

import config.DatabaseUtil;
import entity.KhachHang;
import entity.NhanVien;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVienDao implements IDao<NhanVien, Integer> {

    private Connection con;

    public NhanVienDao() {
        con =  DatabaseUtil.getConnection();
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

                dsNhanVien.add(new NhanVien( maNhanVien,  hoTen,  gioiTinh,  soDienThoai, ngayDangKy, ngaySinh,  trangThai, email));
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsNhanVien;
    }

    @Override
    public boolean them(NhanVien entity) {
        try {
            String sql = "INSERT INTO `NhanVien`(`hoTen`, `gioitinh`, `soDienThoai`, `ngaySinh`,`trangthai`, `matKhau`, `email`, `vaiTro`) VALUES (?,?,?,?,?,?,?)";
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

    @Override
    public boolean xoa(Integer id) {
        return false;
    }

    @Override
    public boolean sua(NhanVien entity) {
        return false;
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

                return new NhanVien( maNhanVien,  hoTen,  gioiTinh,  soDienThoai, ngayDangKy, ngaySinh,  trangThai, email);
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
                LocalDate ngayDangKy = rs.getDate("NgayDangKy").toLocalDate();
                LocalDate ngaySinh = rs.getDate("NgaySinh").toLocalDate();
                int trangThai = rs.getInt("TrangThai");
                String email = rs.getString("Email");

                return new NhanVien( maNhanVien,  hoTen,  gioiTinh,  soDienThoai, ngayDangKy, ngaySinh,  trangThai, email);
            }
        } catch (Exception e) {
            Logger.getLogger(NhanVienDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }
}
