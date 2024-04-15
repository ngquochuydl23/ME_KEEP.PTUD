package dao;

import config.DatabaseUtil;
import entity.Khoang;
import entity.LoaiKhoang;
import entity.Slot;
import entity.ToaTau;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public List<Slot> laySlotTheoMaToaTauVaDsChoNgoi(String maToaTau) {
        List<Slot> dsSlot = new ArrayList<>();
        try {
            String sql = "SELECT * FROM quanlibanve.Slot slot\n" +
                    "LEFT JOIN Khoang khoang ON khoang.MaKhoang  = slot.MaKhoang \n" +
                    "WHERE khoang.MaToa = ?\n" +
                    "ORDER BY slot.SoSlot ASC";

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
        return null;
    }

    public boolean capNhatHetSlot(String maToaTau, List<Integer> dsSoSlot) {
        try {
            con.setAutoCommit(false);

            for (Integer soSlot: dsSoSlot) {
                String sql = "UPDATE quanlibanve.Slot slot\n" +
                        "LEFT JOIN Khoang khoang ON khoang.MaKhoang = slot.MaKhoang\n" +
                        "SET slot.TinhTrang = 0\n" +
                        "WHERE khoang.MaToa = ? AND slot.SoSlot = ?";
                PreparedStatement pst =  con.prepareStatement(sql);
                pst.setString(1, maToaTau);
                pst.setInt(2, soSlot);

                if (pst.executeUpdate() > 0)
                    continue;
                else return false;
            }

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception rollBackEx) {
                rollBackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
