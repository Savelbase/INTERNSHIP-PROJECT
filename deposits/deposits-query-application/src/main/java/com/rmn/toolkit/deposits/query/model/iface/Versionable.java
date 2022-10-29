package com.rmn.toolkit.deposits.query.model.iface;

public interface Versionable {

    Integer getVersion();

    void setVersion(Integer version);

    default void incrementVersion() {
        this.setVersion(this.getVersion() + 1);
    }
}
