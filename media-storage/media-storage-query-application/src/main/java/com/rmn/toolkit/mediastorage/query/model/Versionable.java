package com.rmn.toolkit.mediastorage.query.model;

public interface Versionable {
    Integer getVersion();

    void setVersion(Integer version);

    default void incrementVersion() {
        this.setVersion(this.getVersion() + 1);
    }
}
