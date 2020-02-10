package com.xpadro.testingwebapp.hospital;

import java.io.Serializable;

public class Hospital implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
