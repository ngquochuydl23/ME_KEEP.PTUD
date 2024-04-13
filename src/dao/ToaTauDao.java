package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.DatabaseUtil;
import entity.Tau;
import entity.ToaTau;

public class ToaTauDao implements IDao<ToaTau, String> {
    private Connection con;

    public ToaTauDao() {
        con = DatabaseUtil.getConnection();
    }

    @Override
    public ToaTau layTheoMa(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'layTheoMa'");
    }

    @Override
    public List<ToaTau> layHet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'layHet'");
    }

    @Override
    public boolean them(ToaTau entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'them'");
    }

    @Override
    public boolean xoa(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'xoa'");
    }

    @Override
    public boolean sua(ToaTau entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sua'");
    }

    public List<ToaTau> layToaTheoMaTau(String ma) {
        List<ToaTau> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ToaTau tt LEFT JOIN Tau t ON tt.MaTau  = t.MaTau WHERE t.MaTau = ? ORDER BY MaToa";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, ma);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maToa = rs.getString("maToa");
                String tenToa = rs.getString("tenToa");
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau");


                list.add(new ToaTau(maToa, tenToa, new Tau(maTau, tenTau)));
            }
        } catch (Exception e) {
            Logger.getLogger(TauDao.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return list;
    }
}
