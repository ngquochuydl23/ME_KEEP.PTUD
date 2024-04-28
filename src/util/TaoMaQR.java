package util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import config.DatabaseUtil;
import dao.HoaDonDao;
import dao.VeDao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Ve;

import java.util.HashMap;
import java.util.Map;

public class TaoMaQR {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    public static void main(String[] args) throws SQLException {
        DatabaseUtil.connect();
        VeDao veDao = new VeDao();
        Ve ve = veDao.layTheoMa("di-an-thanh-hoa-D8E-toa6-khoang1-giuongnamkhoang6-slot1-2024-04-25 11:21:15.492");
        HoaDonDao hoaDonDao = new HoaDonDao();
        HoaDon hoaDon = hoaDonDao.layTheoMa("HD-KH83-2024-04-25 11:21:48.935");

        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, ve);
        String json = JsonVeTau.convertChiTietHoaDonToJson(chiTietHoaDon); // Example JSON

        try {
            generateQRCode(json, QR_CODE_IMAGE_PATH);
        } catch (WriterException | IOException e) {
            System.out.println("Could not generate QR Code: " + e);
        }
    }

    private static void generateQRCode(String json, String filePath) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new QRCodeWriter().encode(json, BarcodeFormat.QR_CODE, 200, 200, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("QR Code generated successfully at: " + filePath);
    }
}