package com.example.hospitals.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadLineContext<T> {
    private Parser<T> parser;
    private List<T> list;
    public ReadLineContext(Parser<T> parser) {
        this.parser = parser;
        this.list = new ArrayList<>();
    }

    public List<T> readLines(String filename) {
        String str = "";
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
            while((str = reader.readLine()) != null) this.list.add(parser.parse(str));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.list;
    }
}
