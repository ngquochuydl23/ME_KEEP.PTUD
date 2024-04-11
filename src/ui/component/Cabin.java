package ui.component;

import java.util.List;

public class Cabin {
    private List<Seat> seats;

    // Constructor nhận một danh sách ghế khi khởi tạo khoang
    public Cabin(List<Seat> seats) {
        this.seats = seats;
    }

    // Tạo một khoang với 4 chỗ ngồi
    public static Cabin createFourSeaterCabin() {
        return new Cabin(Seat.createSeats(4));
    }

    // Tạo một khoang với 6 chỗ ngồi
    public static Cabin createSixSeaterCabin() {
        return new Cabin(Seat.createSeats(6));
    }

    // Tạo một khoang với 50 chỗ ngồi
    public static Cabin createFiftySeaterCabin() {
        return new Cabin(Seat.createSeats(50));
    }

    // Trả về danh sách các chỗ ngồi trong khoang
    public List<Seat> getSeats() {
        return seats;
    }

    // Trả về số lượng chỗ ngồi trong khoang
    public int getNumberOfSeats() {
        return seats.size();
    }

    // Trả về chỗ ngồi tại một vị trí cụ thể trong khoang
    public Seat getSeat(int seatNumber) {
        if (seatNumber > 0 && seatNumber <= seats.size()) {
            return seats.get(seatNumber - 1);
        }
        return null;
    }
}