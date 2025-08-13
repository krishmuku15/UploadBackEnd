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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class FileService {
    private final FileDetailsRepository repository;
    private final String storagePath = "./uploaded_files";

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
}
