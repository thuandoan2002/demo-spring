package jmaster.io.project2.DTO;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserDTO {

    private Integer id;

    @NotBlank //validation
    private String name;

    private String avatar; //URL
    private String username;
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;


    private MultipartFile file;

    @CreatedDate
    private Date createAt;

}
