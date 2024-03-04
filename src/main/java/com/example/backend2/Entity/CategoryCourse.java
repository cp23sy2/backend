package com.example.backend2.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "category_course")
public class CategoryCourse {
    @Id
    @Column(name = "idCategory_Course", nullable = false)
    private Integer id;

    @Size(max = 3)
    @NotNull
    @Column(name = "categoryName", nullable = false, length = 3)
    private String categoryName;

    @Size(max = 500)
    @NotNull
    @Column(name = "categoryDescription", nullable = false, length = 500)
    private String categoryDescription;

}