package dao;

import config.DatabaseUtil;
import java.sql.*;
import entity.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietHoaDonDao {
    private Connection con;

    public ChiTietHoaDonDao() {
        con = DatabaseUtil.getConnection();
    }

    public ChiTietHoaDon timCTHDTheoVeVaHD(Ve ve, HoaDon hd) {
        try {
            String sql = "SELECT * FROM ChiTietHoaDon WHERE MaVe=? AND MaHoaDon=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ve.getMaVe());
            pst.setString(2, hd.getMaHoaDon());
    
            ResultSet rs = pst.executeQuery();
    
            if (rs.next()) {
                double donGia = rs.getDouble("donGia");
                return new ChiTietHoaDon(hd, ve, donGia);
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public void capNhatDonGiaChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        try {
            String sql = "UPDATE ChiTietHoaDon SET DonGia = ? WHERE MaVe = ? AND MaHoaDon = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, chiTietHoaDon.getDonGia());
            pst.setString(2, chiTietHoaDon.getVe().getMaVe());
            pst.setString(3, chiTietHoaDon.getHoaDon().getMaHoaDon());
    
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Đã cập nhật đơn giá cho chi tiết hóa đơn thành công.");
            } else {
                System.out.println("Không có bản ghi nào được cập nhật.");
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
