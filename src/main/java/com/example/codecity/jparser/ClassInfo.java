package com.example.codecity.jparser;

import java.util.List;

/**
 * ClassInfo represents information about a Java class, including its parent class (if any),
 * name, lines of code (LOC), methods, and fields.
 */
public class ClassInfo {

    private final String parent;
    private final String name;
    private final int LOC;
    private final List<String> methods;
    private final List<String> fields;

    /**
     * Constructs a ClassInfo object with the specified name, LOC, methods, and fields.
     *
     * @param name    The name of the class.
     * @param LOC     The number of lines of code in the class.
     * @param methods The list of method names in the class.
     * @param fields  The list of field names in the class.
     */
    public ClassInfo(String name, int LOC, List<String> methods, List<String> fields) {
        this("", name, LOC, methods, fields);
    }

    /**
     * Constructs a ClassInfo object with the specified parent, name, LOC, methods, and fields.
     *
     * @param parent  The name of the parent class.
     * @param name    The name of the class.
     * @param LOC     The number of lines of code in the class.
     * @param methods The list of method names in the class.
     * @param fields  The list of field names in the class.
     */
    public ClassInfo(String parent, String name, int LOC, List<String> methods, List<String> fields) {
        this.parent = parent;
        this.name = name;
        this.LOC = LOC;
        this.methods = methods;
        this.fields = fields;
    }

    /**
     * Returns a string representation of the ClassInfo object.
     *
     * @return A string representation of the object.
     */
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

    /**
     * Gets the name of the parent class.
     *
     * @return The name of the parent class.
     */
    public String getParent() {
        return parent;
    }

    /**
     * Gets the name of the class.
     *
     * @return The name of the class.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of lines of code in the class.
     *
     * @return The number of lines of code.
     */
    public int getLOC() {
        return LOC;
    }

    /**
     * Gets the list of method names in the class.
     *
     * @return The list of method names.
     */
    public List<String> getMethods() {
        return methods;
    }

    /**
     * Gets the list of field names in the class.
     *
     * @return The list of field names.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Gets the count of methods in the class.
     *
     * @return The count of methods.
     */
    public int getMethodCount() {
        return methods.size();
    }

    /**
     * Gets the count of fields in the class.
     *
     * @return The count of fields.
     */
    public int getFieldCount() {
        return fields.size();
    }
}
