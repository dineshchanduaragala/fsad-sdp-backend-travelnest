package com.klef.fsad.sdp.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil 
{
    private static final String BASE_DIR = System.getProperty("user.dir") + "/uploads/";

    public static String saveFile(MultipartFile file, String folder) throws IOException 
    {
        // ✅ Create folder if not exists
        String uploadDir = BASE_DIR + folder + "/";
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();   // 🔥 IMPORTANT FIX
        }

        // ✅ Generate unique file name
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // ✅ Save file
        File destination = new File(uploadDir + fileName);
        file.transferTo(destination);

        return "/uploads/" + folder + "/" + fileName;
    }
}