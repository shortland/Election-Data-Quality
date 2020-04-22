package com.electiondataquality.jpa.tables;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "precincts")
@Inheritance(strategy = InheritanceType.JOINED)
public class PrecinctTable implements Serializable {
    private static final long serialVersionUID = 8042848908221995169L;

}
