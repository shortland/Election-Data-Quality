package com.electiondataquality.jpa.tables;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "counties")
@Inheritance(strategy = InheritanceType.JOINED)
public class CountyTable implements Serializable {
    private static final long serialVersionUID = 1834911169146683396L;

}
