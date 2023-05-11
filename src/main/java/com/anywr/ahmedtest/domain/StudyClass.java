package com.anywr.ahmedtest.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.*;

import jakarta.persistence.*;

/**
 * A StudyClass.
 */
@Entity
@Table(name = "study_class")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudyClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @JsonIgnoreProperties(value = { "studyClass" }, allowSetters = true)
    @OneToOne(mappedBy = "studyClass")
    private Teacher teacher;


    public Long getId() {
        return this.id;
    }

    public StudyClass id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public StudyClass name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (this.teacher != null) {
            this.teacher.setStudyClass(null);
        }
        if (teacher != null) {
            teacher.setStudyClass(this);
        }
        this.teacher = teacher;
    }

    public StudyClass teacher(Teacher teacher) {
        this.setTeacher(teacher);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyClass)) {
            return false;
        }
        return id != null && id.equals(((StudyClass) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyClass{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
