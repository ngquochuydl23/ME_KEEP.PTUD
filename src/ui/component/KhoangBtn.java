package ui.component;

import entity.Slot;
import entity.ToaTau;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class KhoangBtn {
    private String maKhoang;
    private List<SlotBtn> dsSlotBtn;
    private ToaTau toaTau;

    public KhoangBtn(String maKhoang, ToaTau toaTau, List<SlotBtn> dsBtnSlot) {
        this.maKhoang = maKhoang;
        this.toaTau = toaTau;
        this.dsSlotBtn = dsBtnSlot;
    }

    public List<SlotBtn> getDsSlotBtn() {
        return dsSlotBtn;
    }


    public String getMaKhoang() {
        return maKhoang;
    }
}