package com.example.backend2.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "academic_year")
public class AcademicYear {
    @Id
    @Column(name = "idAcademic_Year", nullable = false)
    private Integer id;

    //TODO [JPA Buddy] generate columns from DB
}