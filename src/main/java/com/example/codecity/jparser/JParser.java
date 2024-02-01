package com.example.codecity.jparser;

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

public class JParser {
    private File projDir;
    private List<File> files;
    private List<ClassInfo> classes;

    public JParser(File projDir){
        this.projDir = projDir;
        files = new ArrayList<>();
        classes = new ArrayList<>();
    }

    //Parses all files within the specified directory
    public void parseAll(){
        explore(0, projDir.getPath(), projDir);
        for(File file : files){
            //System.out.println(file);
            try{
                classes.add(parseClass(file));
            }catch (FileNotFoundException ex){
                System.out.print("Error processing file " + file.getName() + ". File not found.\n\tFile Path: '" + file.getPath()+"'");
            }
        }
    }

    public List<ClassInfo> getClasses(){
        return classes;
    }

    public int getTotalLOC(){
        int total = 0;
        for(ClassInfo cur : classes){
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


