package org.example.backend.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static void upload(MultipartFile file,String destPath, String fileName) {
        File dest = new File(destPath + File.separator + fileName);
        // Check whether parent file exists
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // Save file
            file.transferTo(dest);
        } catch (Exception e) {
            logger.error("Save file exception. {}", e.getMessage());
        }
    }

    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String generateRandomFileName(String fileName) {
        return UUID.randomUUID() + getSuffix(fileName);
    }
}
