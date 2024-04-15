package entity;

public class TestVe {
    public static void main(String[] args)
    {
        Tuyen tuyen = new Tuyen();
        tuyen.setGiaNiemYet(900000);

        ToaTau  toaTau = new ToaTau("D10H-toa4", "Toa tàu 4", new Tau("D10H"));
        Khoang khoang = new Khoang("D10H-toa4-khoang1-giuongnamkhoang4", "D10H Toa tàu 4 Khoang 1 (Giường nằm khoang 4)", new LoaiKhoang("giuong-nam-khoang-4"), toaTau);
        Slot slot = new Slot("D10H-toa4-khoang1-giuongnamkhoang4-slot1", 1, khoang, 1);
        Ve ve = new Ve("ve-123", slot, null, tuyen, null);

        System.out.println(ve.tinhGiaBanVe());
    }
}
