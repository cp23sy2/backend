package com.example.backend2.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Slf4j
@Getter
@Setter
@Entity
@Table(name = "semester")
public class Semester {
    @Id
    @Column(name = "idSemester", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academic_Year_idAcademic_Year", nullable = false)
    private AcademicYear academicYearIdacademicYear;

}