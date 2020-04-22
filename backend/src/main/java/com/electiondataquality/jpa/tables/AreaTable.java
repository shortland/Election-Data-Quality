package com.electiondataquality.jpa.tables;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "areas")
@Inheritance(strategy = InheritanceType.JOINED)
public class AreaTable implements Serializable {
    private static final long serialVersionUID = 2728200145339584600L;

}
