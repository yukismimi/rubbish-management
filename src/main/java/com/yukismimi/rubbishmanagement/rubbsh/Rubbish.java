package com.yukismimi.rubbishmanagement.rubbsh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "rubbish")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rubbish {

    @JsonIgnoreProperties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "_name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "aipre")
    private int aipre;

    @Column(name = "_explain")
    private String explain;

    @Column(name = "contain")
    private String contain;

    @Column(name = "tip")
    private String tip;
}
