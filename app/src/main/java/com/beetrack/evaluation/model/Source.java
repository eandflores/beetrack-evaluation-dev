package com.beetrack.evaluation.model;

import java.io.Serializable;

/**
 * Created by mbot on 2/21/18.
 */

public class Source implements Serializable {

    private String id;
    private String name;

    Source() {

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
