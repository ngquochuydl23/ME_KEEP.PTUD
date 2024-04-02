package dao1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.DatabaseUtil;
import entity.Chuyen;
import entity.Ga;
import entity.Tau;
import entity.Tuyen;

public class GaDao implements IDao<Ga, String> {
    private Connection con;

    public GaDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public Ga layTheoMa(String id) {
        try {
            String sql = "SELECT * FROM Ga WHERE maGa=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maGa = rs.getString("MaGa");
                String tenGa = rs.getString("TenGa");
                String vungMien = rs.getString("VungMien");

                return new Ga(maGa, tenGa, vungMien);
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Ga> layHet() {
        List<Ga> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Ga ";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maGa = rs.getString("MaGa");
                String tenGa = rs.getString("TenGa");
                String vungMien = rs.getString("VungMien");

                list.add(new Ga(maGa, tenGa, vungMien));
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return list;
    }

    @Override
    public boolean them(Ga entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'them'");
    }

    @Override
    public boolean xoa(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'xoa'");
    }

    @Override
    public boolean sua(Ga entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sua'");
    }

    public Ga layTheoTen(String name) {
        try {
            String sql = "SELECT * FROM Ga WHERE TenGa=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String maGa = rs.getString("MaGa");
                String tenGa = rs.getString("TenGa");
                String vungMien = rs.getString("VungMien");

                return new Ga(maGa, tenGa, vungMien);
            }
        } catch (Exception e) {
            Logger.getLogger(ChuyenDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
