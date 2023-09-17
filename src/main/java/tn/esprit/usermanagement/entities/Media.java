package tn.esprit.usermanagement.entities;




import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Media implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String imagenUrl;
    private String codeImage;
    public Media(String name, String imagenUrl, String imagencode) {

        this.name = name;
        this.imagenUrl = imagenUrl;
        this.codeImage = imagencode;
    }

}