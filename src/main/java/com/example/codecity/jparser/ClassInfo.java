package com.example.codecity.jparser;

import java.util.List;

public class ClassInfo {
    private String parent;
    private String name;
    private int LOC;
    private List<String> methods;
    private List<String> fields;

    public ClassInfo(String name, int LOC, List<String> methods, List<String> fields) {
        this("", name, LOC, methods, fields);
    }

    public ClassInfo(String parent, String name, int LOC, List<String> methods, List<String> fields) {
        this.parent = parent;
        this.name = name;
        this.LOC = LOC;
        this.methods = methods;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", LOC=" + LOC +
                ", methods=" + methods +
                ", fields=" + fields +
                '}';
    }

    public String getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public int getLOC() {
        return LOC;
    }

    public List<String> getMethods() {
        return methods;
    }

    public List<String> getFields() {
        return fields;
    }

    public int getMethodCount() {
        return methods.size();
    }

    public int getFieldCount() {
        return fields.size();
    }

}
