package rest.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "record")
public class Record {
    @Id
    private UUID id;
    private String title;
    private String address;
    private Integer berth;
    private Integer rent;
    private String description;
}