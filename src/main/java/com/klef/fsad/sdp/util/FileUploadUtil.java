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
        if (file == null || file.isEmpty()) 
        {
            throw new IOException("File is empty");
        }

        // ✅ Create folder
        String uploadDir = BASE_DIR + folder + "/";
        File dir = new File(uploadDir);

        if (!dir.exists()) 
        {
            dir.mkdirs();
        }

        // ✅ Clean filename
        String originalName = file.getOriginalFilename();
        String safeName = originalName.replaceAll("\\s+", "_");

        String fileName = UUID.randomUUID() + "_" + safeName;

        // ✅ Save file
        File destination = new File(uploadDir + fileName);
        file.transferTo(destination);

        return "/uploads/" + folder + "/" + fileName;
    }
}