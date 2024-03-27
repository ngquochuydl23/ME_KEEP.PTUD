import GUI.DangNhapForm;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import config.DatabaseUtil;

import javax.swing.*;
import java.sql.SQLException;

public class StartingPoint {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FlatRobotoFont.install();
                FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
                FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
                FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
                FlatIntelliJLaf.registerCustomDefaultsSource("style");
                FlatIntelliJLaf.setup();
                UIManager.put("PasswordField.showRevealButton", true);
                DangNhapForm login = new DangNhapForm();
                login.setVisible(true);


                try {
                    DatabaseUtil.connect();
                    System.out.println("Kết nối thành công");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
