package dao1;

import entity.NhanVien;

import java.util.List;

public class TaiKhoanDao implements IDao<NhanVien, Integer> {


    @Override
    public NhanVien layTheoMa(Integer id) {
        return null;
    }

    @Override
    public List<NhanVien> layHet() {
        return null;
    }

    @Override
    public boolean them(NhanVien entity) {
        return true;
    }

    @Override
    public boolean xoa(Integer id) {
        return true;
    }

    @Override
    public boolean sua(NhanVien entity) {
        return true;
    }

    public NhanVien layTheoSdt(String sdt) {
        return null;
    }

    @Override
    public int getAutoIncrement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAutoIncrement'");
    }

}
