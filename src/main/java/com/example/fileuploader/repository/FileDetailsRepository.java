package com.example.fileuploader.repository;

import com.example.fileuploader.model.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails, Long> {
}
