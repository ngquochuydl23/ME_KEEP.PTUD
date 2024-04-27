package utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class TaoMaQR {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    public static void main(String[] args) {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}"; // Example JSON

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