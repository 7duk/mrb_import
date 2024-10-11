package org.example.mrb_import.utils;

import com.luciad.imageio.webp.WebPWriteParam;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.example.mrb_import.utils.StringUtils.generateUniqueRandomString;

public class FileUtils {

    private static String[] CSV_HEADERS = {"no", "partner_id", "website_property_name", "building_name", "status_building_name", "cho_aza_code", "house_number", "latitude", "longitude", "surrounding_environment", "other_transportation", "number_of_floors", "total_number_of_units", "building_area", "completion_date", "other_facilities", "contract_special_conditions", "number_of_parking_spots", "parking_notes", "other_notes"
            , "room_id", "url", "status_room_number", "room_type", "license_number", "floor_description", "floor", "room_size", "catchphrase", "video_url", "panorama_url", "bed_detail", "facility_detail", "mailbox_access", "owner_information", "business_contact_memo", "status"
            , "room_image_managements", "railway_line_stations", "base_price_of_rooms", "building_catalogs", "room_catalogs"};

    private static String path = "output";

    public static Iterable<CSVRecord> extractRecordsFromFile(String fileName) {
        Path file = Paths.get(path + File.separator + fileName);
        Iterable<CSVRecord> records = null;
        try {
            if (!Files.exists(file)) {
                throw new Exception("Path is invalid!");
            } else {
                Reader reader = new InputStreamReader(Files.newInputStream(file), StandardCharsets.UTF_8);
                CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                        .setHeader(FileUtils.CSV_HEADERS)
                        .setSkipHeaderRecord(true)
                        .build();
                records = csvFormat.parse(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public static String saveImageToFile(String imageUrl, String destinationFolder) {
        try {
            // Đọc ảnh từ URL
            URL url = new URL(imageUrl);
            BufferedImage originalImage = ImageIO.read(url);

            // Thay đổi kích thước ảnh
            int newWidth = 400;
            int newHeight = 400;
            BufferedImage resizedImage = resizeImage(originalImage, newWidth, newHeight);

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            File output = new File(destinationFolder + File.separator + generateUniqueRandomString() + ".webp");
            while (output.exists()) {
                output = new File(destinationFolder + File.separator + generateUniqueRandomString() + ".webp");
            }
            // Tạo thư mục nếu chưa tồn tại
            File parentDir = output.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Tạo các thư mục nếu chưa tồn tại
            }

            saveAsWebP(resizedImage, output);
            System.out.println("Ảnh đã được lưu thành công tại: " + output.getAbsolutePath());


            // Lưu ảnh dưới dạng WebP
            saveAsWebP(resizedImage, output);

            System.out.println("Ảnh đã được resize và lưu thành công tại: " + destinationFolder);
            return output.getName();
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra: " + e.getMessage());
        }
        return null;
    }

    // Hàm thay đổi kích thước ảnh
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedResizedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();
        return bufferedResizedImage;
    }

    // Hàm lưu ảnh dưới dạng WebP
    private static void saveAsWebP(BufferedImage image, File output) throws IOException {
        // Lấy ImageWriter cho định dạng WebP
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("webp");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No WebP writers found");
        }

        ImageWriter writer = writers.next();

        // Tạo ImageOutputStream từ file đích
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            writer.setOutput(ios);

            // Tạo WebPWriteParam để cấu hình nén
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(WebPWriteParam.MODE_DEFAULT);

            // Ghi ảnh với các tham số nén đã thiết lập
            writer.write(null, new javax.imageio.IIOImage(image, null, null), writeParam);
        } finally {
            writer.dispose();
        }
    }

    public static void pushImagesToFtp(String server, int port, String user, String pass, String localFolderPath, String remoteFolderPath) throws Exception {
        FTPSClient ftpClient = new FTPSClient("TLSv1.2");
        try {


            // Kết nối đến FTP server
            ftpClient.connect(server, port);
            showServerReply(ftpClient);

            // Kiểm tra nếu kết nối thành công
            if (!ftpClient.login(user, pass)) {
                throw new Exception("Login failed!");
            }
            showServerReply(ftpClient);
            ftpClient.setConnectTimeout(60000);  // Set connect timeout to 30 seconds
            ftpClient.setDataTimeout(60000);     // Set data timeout to 30 seconds
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftpClient.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());

            // Thiết lập chế độ chuyển file sang BINARY để tránh hỏng file
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            showServerReply(ftpClient);

            // Gọi phương thức tải lên thư mục
            uploadDirectory(ftpClient, remoteFolderPath, localFolderPath);

            // Đăng xuất và đóng kết nối
            ftpClient.logout();
        } catch (IOException ex) {
            showServerReply(ftpClient);
            throw new Exception("Error occurred: " + ex.getMessage(), ex);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void uploadDirectory(FTPClient ftpClient, String remoteDirPath, String localDirPath) throws IOException {
        File localDir = new File(localDirPath);
        File[] files = localDir.listFiles();  // Lấy tất cả các file và thư mục con

        // Tạo thư mục trên server, nếu nó chưa tồn tại
        if (!ftpClient.changeWorkingDirectory(remoteDirPath)) {
            if (!ftpClient.makeDirectory(remoteDirPath)) {
                showServerReply(ftpClient);
                System.out.println("Could not create directory: " + remoteDirPath);
            }
            // Chuyển vào thư mục vừa tạo
            ftpClient.changeWorkingDirectory(remoteDirPath.startsWith("/")?remoteDirPath:"/" + remoteDirPath);
        }

        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    // Nếu là file, tải lên FTP server
                    try (FileInputStream inputStream = new FileInputStream(file)) {
                        if (ftpClient.storeFile(file.getName(), inputStream)) {
                            System.out.println("File uploaded: " + file.getName());
                        } else {
                            System.out.println("Failed to upload file: " + file.getName());
                            showServerReply(ftpClient);
                        }
                    }
                } else if (file.isDirectory()) {
                    // Nếu là thư mục, gọi đệ quy để tải lên tất cả nội dung của thư mục con
                    uploadDirectory(ftpClient, remoteDirPath + "/" + file.getName(), file.getAbsolutePath());
                }
            }
        }
    }

    // Phương thức hiển thị phản hồi từ FTP server
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String reply : replies) {
                System.out.println("SERVER: " + reply);
            }
        }
    }

}
