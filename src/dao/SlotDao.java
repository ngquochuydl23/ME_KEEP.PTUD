package dao;

import com.mysql.cj.log.Log;
import config.DatabaseUtil;
import entity.Khoang;
import entity.LoaiKhoang;
import entity.Slot;
import entity.ToaTau;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SlotDao implements IDao<SlotDao, String>{

    private Connection con;

    public SlotDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public SlotDao layTheoMa(String id) {
        return null;
    }

    @Override
    public List<SlotDao> layHet() {
        return null;
    }

    @Override
    public boolean them(SlotDao entity) {
        return false;
    }

    @Override
    public boolean xoa(String id) {
        return false;
    }

    @Override
    public boolean sua(SlotDao entity) {
        return false;
    }

    public List<Slot> laySlotTheoMaToaTauVaDsChoNgoi(String maToaTau, List<Integer> dsCho) {
        List<Slot> dsSlot = new ArrayList<>();

        if (dsSlot.isEmpty())
            return null;

        StringBuilder dsChoParam = new StringBuilder(dsCho.toString());
        dsChoParam.setCharAt(0,'(');
        dsChoParam.setCharAt(dsCho.toString().length() - 1,')');
        try {
            String sql = "SELECT * FROM quanlibanve.Slot slot\n" +
                    "LEFT JOIN Khoang khoang ON khoang.MaKhoang  = slot.MaKhoang \n" +
                    "WHERE khoang.MaToa = ? AND slot.SoSlot IN "+ dsChoParam +"\n" +
                    "ORDER BY slot.SoSlot ASC ";

            Logger.getLogger(SlotDao.class.getName()).log(Level.INFO, sql);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maToaTau);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSlot = rs.getString("MaSlot");
                String maKhoang = rs.getString("MaKhoang");
                int tinhTrang = rs.getInt("TinhTrang");
                int soSlot = rs.getInt("SoSlot");

                dsSlot.add(new Slot(maSlot, soSlot, new Khoang(maKhoang), tinhTrang));
            }
        } catch (Exception e) {
            Logger.getLogger(SlotDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsSlot;
    }

    public boolean capNhatHetSlot(Connection conn,  List<Slot> dsChoDaChon) throws SQLException {
        for (Slot slot : dsChoDaChon) {
            String sql = "UPDATE quanlibanve.Slot slot\n" +
                    "SET slot.TinhTrang = 0\n" +
                    "WHERE slot.MaSlot = ?";
            PreparedStatement pst =  conn.prepareStatement(sql);
            pst.setString(1, slot.getMaSlot());

            if (pst.executeUpdate() > 0)
                continue;
            else return false;
        }
        return true;
    }

    public boolean capNhatConSlot(Connection conn,  List<Slot> dsChoDaChon) throws SQLException {
        for (Slot slot : dsChoDaChon) {
            String sql = "UPDATE quanlibanve.Slot slot\n" +
                    "SET slot.TinhTrang = 1\n" +
                    "WHERE slot.MaSlot = ?";
            PreparedStatement pst =  conn.prepareStatement(sql);
            pst.setString(1, slot.getMaSlot());

            if (pst.executeUpdate() > 0)
                continue;
            else return false;
        }
        return true;
    }

    public List<Slot> layTinhTrangChoNgoiTheoToaTau(String maToaTau) {
        List<Slot> dsSlot = new ArrayList<>();
        try {
            String sql = "select slot.MaSlot, slot.SoSlot, slot.TinhTrang, slot.MaKhoang from quanlibanve.Slot slot\n" +
                    "LEFT JOIN Khoang khoang ON khoang.MaKhoang = slot.MaKhoang\n" +
                    "WHERE khoang.MaToa = ?\n" +
                    "ORDER BY slot.SoSlot ASC";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maToaTau);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maSlot = rs.getString("slot.MaSlot");
                int soSlot = rs.getInt("slot.SoSlot");
                int tinhTrang = rs.getInt("slot.TinhTrang");
                String maKhoang = rs.getString("slot.MaKhoang");
                dsSlot.add(new Slot( maSlot,  soSlot,  new Khoang(maKhoang),  tinhTrang));
            }
        } catch (Exception e) {
            Logger.getLogger(SlotDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return dsSlot;
    }
}
