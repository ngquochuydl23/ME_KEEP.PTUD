package ui.component;

import java.util.List;

public class KhoangBtn {
    private List<Seat> seats;

    // Constructor nhận một danh sách ghế khi khởi tạo khoang
    public KhoangBtn(List<Seat> seats) {
        this.seats = seats;
    }
    
    // Tạo một khoang với 4 chỗ ngồi
    public static KhoangBtn createFourSeaterCabin() {
        return new KhoangBtn(Seat.createSeats(4));
    }

    // Tạo một khoang với 6 chỗ ngồi
    public static KhoangBtn createSixSeaterCabin() {
        return new KhoangBtn(Seat.createSeats(6));
    }

    // Tạo một khoang với 50 chỗ ngồi
    public static KhoangBtn createFiftySeaterCabin() {
        return new KhoangBtn(Seat.createSeats(50));
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