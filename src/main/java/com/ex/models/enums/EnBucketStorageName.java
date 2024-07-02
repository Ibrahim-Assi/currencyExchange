package com.ex.models.enums;

import lombok.Getter;

@Getter
public enum EnBucketStorageName {
    ATTACHMENT_PROJECT("Attachment_Project"),
    BUCKET2("bucket2"),
    BUCKET3("bucket3");

    private final String name;

    EnBucketStorageName(String name) {
        this.name = name;
    }
}
