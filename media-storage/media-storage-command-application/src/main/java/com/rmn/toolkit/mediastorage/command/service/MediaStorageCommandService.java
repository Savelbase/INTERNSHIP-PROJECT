package com.rmn.toolkit.mediastorage.command.service;

import com.rmn.toolkit.mediastorage.command.event.EventType;
import com.rmn.toolkit.mediastorage.command.message.EventSender;
import com.rmn.toolkit.mediastorage.command.event.avatar.AvatarUploadedEvent;
import com.rmn.toolkit.mediastorage.command.exception.badrequest.FileNotProvidedException;
import com.rmn.toolkit.mediastorage.command.model.MediaFileData;
import com.rmn.toolkit.mediastorage.command.repository.MediaStorageRepository;
import com.rmn.toolkit.mediastorage.command.util.EventPayloadUtil;
import com.rmn.toolkit.mediastorage.command.util.EventUtil;
import com.rmn.toolkit.mediastorage.command.util.MediaStorageCommandUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaStorageCommandService {
    private static final int VERSION = 1;

    @Value("${upload.path}")
    private String uploadPath;
    private final MediaStorageCommandUtil mediaStorageCommandUtil;
    private final MediaStorageRepository mediaStorageRepository;
    private final EventUtil eventUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventSender eventSender;

    @Transactional
    public void saveFile(String authorId, MultipartFile file) throws IOException, MimeTypeException {
        Path root = Paths.get(uploadPath);
        if (!Files.exists(root)) {
            mediaStorageCommandUtil.init();
        }
        if (file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
            mediaStorageCommandUtil.checkExtension(file);

            Optional<MediaFileData> optionalMediaFileData = mediaStorageRepository.findByUserId(authorId);
            String entityId = UUID.randomUUID().toString();
            if (optionalMediaFileData.isPresent()) {
                MediaFileData mediaFileData = optionalMediaFileData.get();
                entityId = mediaFileData.getId();
            }

            String filename = UUID.randomUUID().toString();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1);
            Files.copy(file.getInputStream(), root.resolve(filename + "." + extension));

            AvatarUploadedEvent.Payload payload = eventPayloadUtil.createAvatarCreatedEventPayload(filename, extension);
            AvatarUploadedEvent event = AvatarUploadedEvent.builder().build();
            eventUtil.populateEventFields(event, EventType.AVATAR_UPLOADED, entityId, VERSION, authorId, payload);
            eventSender.send(event);
        } else {
            throw new FileNotProvidedException();
        }
    }
}