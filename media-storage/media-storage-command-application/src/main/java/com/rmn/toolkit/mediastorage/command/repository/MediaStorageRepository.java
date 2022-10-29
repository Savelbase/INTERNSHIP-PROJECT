package com.rmn.toolkit.mediastorage.command.repository;

import com.rmn.toolkit.mediastorage.command.model.MediaFileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaStorageRepository extends JpaRepository<MediaFileData, String> {
    Optional<MediaFileData> findByUserId(String userId);
}
