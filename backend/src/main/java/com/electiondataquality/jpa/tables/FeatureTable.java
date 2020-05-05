package com.electiondataquality.jpa.tables;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.electiondataquality.geometry.Geometry;
import com.electiondataquality.geometry.util.RawGeometryToShape;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "features")
public class FeatureTable {

    @Id
    @Column(name = "idn", insertable = false, updatable = false)
    private int featureId;

    @Column(name = "geometry")
    private String geometry;

    @OneToMany
    @JoinColumn(name = "feature_idn", referencedColumnName = "idn", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<ErrorTable> errors;

    public FeatureTable() {
    }

    public FeatureTable(int featureId, String geometry) {
        this.featureId = featureId;
        this.geometry = geometry;
    }

    public int getFeatureId() {
        return this.featureId;
    }

    public void setFeatureId(int id) {
        this.featureId = id;
    }

    public String getGeometry() {
        return this.geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public Set<ErrorTable> getErrors() {
        return this.errors;
    }

    public void setErrors(Set<ErrorTable> errors) {
        this.errors = errors;
    }

    public Set<Integer> getErrorsId() {
        Set<Integer> errorsId = new HashSet<>();

        for (ErrorTable et : this.errors) {
            errorsId.add(et.getErrorId());
        }

        return errorsId;
    }

    public void update(Geometry geometry) {
        this.geometry = RawGeometryToShape.convertGeometryToRaw(geometry);
    }

    @Override
    public String toString() {
        return "ID : " + Integer.toString(this.featureId) + "\nGeometry" + this.geometry;
    }
}
