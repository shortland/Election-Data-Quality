package com.electiondataquality.jpa.objects;

import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.electiondataquality.jpa.tables.CommentTable;

@Entity
@Table(name = "errors")
public class ErrorComment {
    
}
