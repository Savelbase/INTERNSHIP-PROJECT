package com.rmn.toolkit.mediastorage.command.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name="media_storage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaFileData implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String url;

    private boolean confirmed;

    @NotNull
    private ZonedDateTime uploadedDateTime;

    @NotNull
    private String userId ;

    @NotNull
    private Integer version;
}