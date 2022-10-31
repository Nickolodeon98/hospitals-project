package com.example.hospitals.parser;

import java.util.List;

public interface Parser<T> {
    T parse(String line);
}
