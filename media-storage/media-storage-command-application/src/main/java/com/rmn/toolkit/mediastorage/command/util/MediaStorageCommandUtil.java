package com.rmn.toolkit.mediastorage.command.util;

import com.rmn.toolkit.mediastorage.command.exception.conflict.FolderCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class MediaStorageCommandUtil {
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * @throws FolderCreationException failed to create download folder
     */
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new FolderCreationException(uploadPath);
        }
    }

    /**
     * @param file               file whose extension is being checked
     * @throws IOException       failed to read file
     * @throws MimeTypeException wrong format
     */
    public void checkExtension(MultipartFile file) throws IOException, MimeTypeException {
        Tika tika = new Tika();
        String fileType = tika.detect(file.getBytes());
        if (!(Objects.equals(fileType, MediaType.IMAGE_PNG_VALUE)
                || Objects.equals(fileType, MediaType.IMAGE_JPEG_VALUE))
        ) {
            throw new MimeTypeException(String.format("Invalid file extension '%s'", fileType));
        }
    }
}
