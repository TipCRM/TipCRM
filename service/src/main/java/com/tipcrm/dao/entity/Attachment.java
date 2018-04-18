package com.tipcrm.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment extends BaseCreateEntity{
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "extension")
    private String ext;

    @ManyToOne
    @JoinColumn(name = "type")
    private ListBox type;

    @ManyToOne
    @JoinColumn(name = "location_type")
    private ListBox locationType;

    @Column(name = "path")
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ListBox getType() {
        return type;
    }

    public void setType(ListBox type) {
        this.type = type;
    }

    public ListBox getLocationType() {
        return locationType;
    }

    public void setLocationType(ListBox locationType) {
        this.locationType = locationType;
    }
}
