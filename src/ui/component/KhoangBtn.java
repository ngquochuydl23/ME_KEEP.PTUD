package ui.component;

import java.util.List;
import java.util.Map;

public class KhoangBtn {
    private List<Seat> seats;

    // Constructor nhận một danh sách ghế khi khởi tạo khoang
    public KhoangBtn(List<Seat> seats) {
        this.seats = seats;
    }


    public static KhoangBtn createFourSeaterCabin(Map<Integer, Integer> dsChoNgoi) {
        return new KhoangBtn(Seat.createSeats(dsChoNgoi));
    }


    public static KhoangBtn createSixSeaterCabin(Map<Integer, Integer> dsChoNgoi) {
        return new KhoangBtn(Seat.createSeats(dsChoNgoi));
    }


    public static KhoangBtn createFiftySeaterCabin(Map<Integer, Integer> dsChoNgoi) {
        return new KhoangBtn(Seat.createSeats(dsChoNgoi));
    }

    // Trả về danh sách các chỗ ngồi trong khoang
    public List<Seat> getSeats() {
        return seats;
    }

    // Trả về số lượng chỗ ngồi trong khoang
    public int getNumberOfSeats() {
        return seats.size();
    }
    
    public void updateSeatNumbers(int startSeatNumber) {
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            seat.setSeatNumber(startSeatNumber + i);
        }
    }
    
    // Trả về chỗ ngồi tại một vị trí cụ thể trong khoang
    public Seat getSeat(int seatNumber) {
        if (seatNumber > 0 && seatNumber <= seats.size()) {
            return seats.get(seatNumber - 1);
        }
        return null;
    }
}