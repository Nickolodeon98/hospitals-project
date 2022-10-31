package com.example.hospitals.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    public List<T> readLines(String filename) throws IOException {
        String str = "";
        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_16);
            reader.readLine();
            while ((str = reader.readLine()) != null) try {
                this.list.add(parser.parse(str));
            } catch (Exception e) {
                System.out.printf("파일 내용: %s 에 문제가 생겨 넘어갑니다.\n", str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reader.close();
        return this.list;
    }
}
