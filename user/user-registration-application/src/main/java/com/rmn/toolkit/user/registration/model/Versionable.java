package com.rmn.toolkit.user.registration.model;

public interface Versionable {
    Integer getVersion();

    void setVersion(Integer version);

    default void incrementVersion() {
        this.setVersion(this.getVersion() + 1);
    }
}
