package jmaster.io.project2.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @NotBlank
    private String name;

    @Column(name = "avatar")
    private String avatar; // url

    @Column(unique = true)
    private String username;
    private String password;

    @DateTimeFormat (pattern = "dd/MM/yyyy")
    private Date birthdate;

    @LastModifiedDate
    private Date lastUpdateAt;

    @Transient // field is not persistent
    private MultipartFile file;

    @CreatedDate //tu gen
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;
}
