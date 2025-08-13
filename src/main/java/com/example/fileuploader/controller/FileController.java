package com.example.fileuploader.controller;

import com.example.fileuploader.model.FileDetails;
import com.example.fileuploader.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/files")
@Tag(name = "File Operations", description = "Endpoints for uploading files and retrieving file metadata")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Upload a file", description = "Uploads a file from local machine, stores it locally, and saves metadata in H2 DB")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public FileDetails uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        return fileService.uploadFile(file);
    }

    @Operation(summary = "Get all stored file metadata", description = "Retrieves metadata for all uploaded files stored in H2 DB")
    @GetMapping
    public List<FileDetails> getAllFiles() {
        return fileService.getAllFiles();
    }
}
