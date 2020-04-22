package com.electiondataquality.jpa.tables;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "congressional_districts")
@Inheritance(strategy = InheritanceType.JOINED)
public class CongressionalDistrictTable implements Serializable {
    private static final long serialVersionUID = -8569224563877989119L;

}
