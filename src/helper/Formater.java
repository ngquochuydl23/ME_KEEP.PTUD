package helper;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;


public class Formater {

    public static String FormatVND(double vnd) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return currencyFormatter.format(vnd);
    }
    
    public static String FormatTime(Timestamp thoigian) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return formatDate.format(thoigian);
    }

    public static String FormatTime(LocalDateTime thoigian) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return formatDate.format(Timestamp.valueOf(thoigian));
    }
}
