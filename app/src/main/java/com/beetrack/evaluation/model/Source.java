package com.beetrack.evaluation.model;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;

/**
 * Created by mbot on 2/21/18.
 */

public class Source extends RealmObject implements Serializable {

    private String id;
    private String name;

    public Source() {
    }

    public void setData(Source source) {
        setId(source.getId());
        setName(source.getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
