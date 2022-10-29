package com.rmn.toolkit.mediastorage.command.message.service;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import com.rmn.toolkit.mediastorage.command.event.avatar.AvatarUploadedEvent;
import com.rmn.toolkit.mediastorage.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.mediastorage.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.mediastorage.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.mediastorage.command.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.mediastorage.command.message.projector.ClientProjector;
import com.rmn.toolkit.mediastorage.command.message.projector.MediaFileDataProjector;
import com.rmn.toolkit.mediastorage.command.model.MediaFileData;
import com.rmn.toolkit.mediastorage.command.model.User;
import com.rmn.toolkit.mediastorage.command.repository.MediaStorageRepository;
import com.rmn.toolkit.mediastorage.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final UserRepository userRepository;
    private final MediaStorageRepository mediaStorageRepository;
    private final MediaFileDataProjector mediaFileDataProjector;
    private final ClientProjector clientProjector;

    @Transactional
    public void handle(ClientRegisteredEvent event){
        User user = clientProjector.project(event);
        userRepository.save(user);
        log.info("'sign.up' event with user id {} saved" , user.getId());
    }

    @Transactional
    public void handle(ClientStatusChangedEvent event){
        User user = getUserFromEvent(event);
        clientProjector.project(event, user);
        userRepository.save(user);
        log.info("user with id {} status changed" , user.getId());
    }

    @Transactional
    public void handle(AvatarUploadedEvent event){
        MediaFileData mediaFileData = mediaFileDataProjector.project(event);
        mediaStorageRepository.save(mediaFileData);
        log.info("User's avatar with id {} saved" , mediaFileData.getId());
    }

    @Transactional
    public void handle(UserDeletedEvent event) {
        User user = getUserFromEvent(event);
        userRepository.deleteById(user.getId());
        log.info("User with id='{}' deleted after consuming event", user.getId());
    }

    private User getUserFromEvent(Event<? extends EventPayload> event) {
        String userId = event.getEntityId();
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("User with id {} not found", userId);
                    throw new UserNotFoundException(userId);
                });
    }
}
