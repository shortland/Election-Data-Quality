package com.electiondataquality.jpa.tables;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "features")
@Inheritance(strategy = InheritanceType.JOINED)
public class FeatureTable implements Serializable {
    private static final long serialVersionUID = 6134308584578455620L;

}
