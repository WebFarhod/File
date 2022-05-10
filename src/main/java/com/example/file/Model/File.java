package com.example.file.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private  String afilenom;

    @Column(nullable = false)
    private long hajm;

    @Column(nullable = false)
    private String turi;

    //@Column(nullable = false)
    private String yanginom;


}
