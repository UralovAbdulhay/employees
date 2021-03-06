package com.example.demo.file.fileInStorage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class InStorageFile implements Serializable {

    @Id
    @Column(unique = true)
    private String hashId;

    private String name;

    @JsonIgnore
    private String contentType;

    @JsonIgnore
    private String extension;

    @JsonIgnore
    private Long fileSize;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;

    @JsonIgnore
    private String uploadPath;


    public InStorageFile(String hashId, String name, String contentType, String extension, Long fileSize, byte[] data) {
        this.hashId = hashId;
        this.name = name;
        this.contentType = contentType;
        this.extension = extension;
        this.fileSize = fileSize;
        this.data = data;
    }

    @Override
    public String toString() {
        return "\nMyFile{" +
                ",\n name='" + name + '\'' +
                ",\n hashId='" + hashId + '\'' +
                ",\n contentType='" + contentType + '\'' +
                ",\n extension='" + extension + '\'' +
                ",\n fileSize=" + fileSize +
                "\n}";
    }
}
