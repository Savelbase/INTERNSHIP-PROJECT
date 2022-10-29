package com.rmn.toolkit.mediastorage.query.service;

import com.rmn.toolkit.mediastorage.query.util.MediaStorageQueryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MediaStorageQueryService {
    private final MediaStorageQueryUtil mediaStorageQueryUtil;

    @Transactional(readOnly = true)
    public byte[] getFileByClientId(String userId) {
        return mediaStorageQueryUtil.getFile(userId);
    }
}
