package com.ex.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_reference")
public class FileReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String objectName;

    @Column(nullable = false)
    private String eTag;

    @Column(nullable = false)
    private String bucketName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileReference that = (FileReference) o;
        return Objects.equals(id, that.id) && Objects.equals(objectName, that.objectName) && Objects.equals(eTag, that.eTag) && Objects.equals(bucketName, that.bucketName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, objectName, eTag, bucketName);
    }

}
