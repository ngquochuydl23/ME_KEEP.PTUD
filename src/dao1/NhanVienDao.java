package dao1;

import config.DatabaseUtil;
import entity.KhachHang;
import entity.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
                Date ngayDangKy = rs.getDate("NgayDangKy");
                Date ngaySinh = rs.getDate("NgaySinh");
                int trangThai = rs.getInt("TrangThai");
                String email = rs.getString("Email");

                dsNhanVien.add(new NhanVien( maNhanVien,  hoTen,  gioiTinh,  soDienThoai, ngayDangKy, ngaySinh,  trangThai, email));
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsNhanVien;
    }

    @Override
    public boolean them(NhanVien entity) {
        return false;
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
                Date ngayDangKy = rs.getDate("NgayDangKy");
                Date ngaySinh = rs.getDate("NgaySinh");
                int trangThai = rs.getInt("TrangThai");
                String email = rs.getString("Email");

                return new NhanVien( maNhanVien,  hoTen,  gioiTinh,  soDienThoai, ngayDangKy, ngaySinh,  trangThai, email);
            }
        } catch (Exception e) {

            Logger.getLogger(KhachHangDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }
}
