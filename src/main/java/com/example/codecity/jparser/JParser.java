package com.example.codecity.jparser;

import com.example.codecity.Buildings;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JParser is a class for parsing Java source code files and extracting information about classes, methods, and fields.
 */
public class JParser {

    private File projDir;
    private List<File> files;
    private List<ClassInfo> classes;
    private Buildings buildings;

    /**
     * Constructs a JParser instance with the specified project directory.
     *
     * @param projDir The project directory containing Java source code files.
     */
    public JParser(File projDir) {
        this.projDir = projDir;
        files = new ArrayList<>();
        classes = new ArrayList<>();
        buildings = new Buildings();
    }

    /**
     * Parses all files within the specified directory and extracts class information.
     */
    public void parseAll() throws Exception {
        explore(0, projDir.getPath(), projDir);
        for(File file : files){
            System.out.println(file);
            try{
                classes.add(parseClass(file));
            } catch (FileNotFoundException ex) {
                System.out.print("Error processing file " + file.getName() + ". File not found.\n\tFile Path: '" + file.getPath() + "'");
            }
        }
    }

    /**
     * Returns the list of ClassInfo objects representing parsed classes.
     *
     * @return The list of ClassInfo objects.
     */
    public List<ClassInfo> getClasses() {
        return classes;
    }

    public Buildings getBuildings(){
        return buildings;
    }

    /**
     * Calculates and returns the total lines of code (LOC) for all parsed classes.
     *
     * @return The total lines of code.
     */
    public int getTotalLOC() {
        int total = 0;
        for (ClassInfo cur : classes) {
            total += cur.getLOC();
        }
        return total;
    }

    private void explore(int level, String path, File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if(!Objects.isNull(child)){
                    explore(level + 1, path + "/" + child.getName(), child);
                }
            }
        } else {
            if (file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("java") ) {
                System.out.println("Processing " + file.getName());
                files.add(file);
            }
        }
    }

    private ClassInfo parseClass(File toParse) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(toParse);
        int LOC = (int) cu.toString().lines().count();

        List<String> classNames = new ArrayList<>();
        VoidVisitor<List<String>> classNameCollector = new ClassNameCollector();
        classNameCollector.visit(cu, classNames);
        String name = ""; //assumes one class declared per file
        if(!classNames.isEmpty()){
            name = classNames.get(0);
        }

        List<String> methods = new ArrayList<>();
        VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
        methodNameCollector.visit(cu, methods);
        methods.forEach(n -> System.out.println("Found method '" + n + "' in " + toParse.getName()));

        List<String> fields = new ArrayList<>(); // aka global variables
        VoidVisitor<List<String>> fieldNameCollector = new FieldNameCollector();
        fieldNameCollector.visit(cu, fields);
        fields.forEach(n -> System.out.println("Found field '" + n + "' in " + toParse.getName()));

        System.out.println(name.length());
        if(LOC > 100){
            buildings.makeLargeBuilding(100, 100, name.length() * name.length(), name.length() * name.length());
        } else{
            buildings.makeSmallBuilding(50, 50, name.length() * name.length(), name.length() * name.length());
        }

        return new ClassInfo(name, LOC, methods, fields);
    }

    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(MethodDeclaration md, List<String> collector){
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }

    private static class FieldNameCollector extends VoidVisitorAdapter<List<String>>{
        @Override
        public void visit(FieldDeclaration fd, List<String> collector){
            super.visit(fd, collector);
            collector.add(fd.getVariable(0).getNameAsString());
        }
    }

    private static class ClassNameCollector extends VoidVisitorAdapter<List<String>>{
        @Override
        public void visit(ClassOrInterfaceDeclaration cls, List<String> collector){
            super.visit(cls, collector);
            collector.add(cls.getNameAsString());
        }
    }

}


