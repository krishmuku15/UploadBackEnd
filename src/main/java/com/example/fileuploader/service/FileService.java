package com.example.fileuploader.service;

import com.example.fileuploader.model.FileDetails;
import com.example.fileuploader.repository.FileDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FileService {
    private final FileDetailsRepository repository;
    private final String storagePath = "./uploaded_files";
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList(
            "jpg", "jpeg", "png", "gif", "bmp", "webp", // images
            "mp4", "mov", "avi", "mkv", "webm", "flv"   // videos
    ));

    public FileService(FileDetailsRepository repository) {
        this.repository = repository;
    }

    public FileDetails uploadFile(MultipartFile file) throws Exception {
        Path storageDir = Path.of(storagePath);
        Files.createDirectories(storageDir);

        String file_name = file.getOriginalFilename();
        File dest = new File(storagePath + file_name);
//        if(dest.exists()){
            Calendar calendar = Calendar
                    .getInstance(TimeZone.getTimeZone("GMT"));
            Date d = calendar.getTime();
            file_name = file_name.substring(0,file_name.lastIndexOf("."))
                    +"_"+d.getTime()+file_name.substring(file_name.lastIndexOf("."));
//        }
        Path localFilePath = storageDir.resolve(file_name);

        Files.copy(file.getInputStream(), localFilePath, StandardCopyOption.REPLACE_EXISTING);

        FileDetails details = new FileDetails(
                file_name,
                file.getSize(),
                LocalDateTime.now()
        );
        return repository.save(details);
    }

    public List<FileDetails> getAllFiles() {
        return repository.findAll();
    }

    public FileDetails updateFileName(Long id, String newFileName) throws Exception {
        System.out.println(id+":::"+newFileName);
        FileDetails fileDetails = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));

        Path storageDir = Path.of(storagePath);

        // Validate allowed extensions
        String oldExt = getFileExtension(fileDetails.getFileName());
        String newExt = getFileExtension(newFileName);

        if (!ALLOWED_EXTENSIONS.contains(newExt.toLowerCase())) {
            throw new RuntimeException("Invalid file type. Only image and video formats are allowed.");
        }

        // Prevent changing extension
        if (!oldExt.equalsIgnoreCase(newExt)) {
            throw new RuntimeException("Changing file extension is not allowed.");
        }

        Path oldPath = storageDir.resolve(fileDetails.getFileName());
        Path newPath = storageDir.resolve(newFileName);

        if (!Files.exists(oldPath)) {
            throw new RuntimeException("Original file not found: " + oldPath);
        }

        // Rename file in file system
        Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);

        // Update metadata
        fileDetails.setFileName(newFileName);
        return repository.save(fileDetails);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex >= 0) ? fileName.substring(dotIndex + 1) : "";
    }
}
