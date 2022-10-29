package com.rmn.toolkit.mediastorage.query.util;

import com.rmn.toolkit.mediastorage.query.exception.FileReadException;
import com.rmn.toolkit.mediastorage.query.exception.notfound.FileNotFoundException;
import com.rmn.toolkit.mediastorage.query.model.MediaFileData;
import com.rmn.toolkit.mediastorage.query.repository.MediaStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaStorageQueryUtil {
    @Value("${upload.path}")
    private String uploadPath;
    private final MediaStorageRepository mediaStorageRepository;

    public byte[] getFile(String userId) {
        MediaFileData data = findByUserId(userId);
        File file = new File(uploadPath + File.separator + data.getUrl());
        byte[] result;
        try {
            result = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }
        return result;
    }

    public MediaFileData findByUserId(String userId) {
        return mediaStorageRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("File with userId='{}' not found", userId);
                    throw new FileNotFoundException(userId);
                });
    }
}
