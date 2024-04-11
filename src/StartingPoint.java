import ui.DangNhapForm;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import config.DatabaseUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartingPoint {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    DatabaseUtil.connect();
                    Logger.getLogger(StartingPoint.class.getName()).log(Level.INFO, "Kết nối thành công");
                } catch (SQLException e) {
                    Logger.getLogger(StartingPoint.class.getName()).log(Level.SEVERE, null, e);
                }

                FlatRobotoFont.install();
                FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
                FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
                FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
                FlatIntelliJLaf.registerCustomDefaultsSource("style");
                FlatIntelliJLaf.setup();
                UIManager.put("PasswordField.showRevealButton", true);
                DangNhapForm login = new DangNhapForm();
                login.setVisible(true);
            }
        });
    }
}
