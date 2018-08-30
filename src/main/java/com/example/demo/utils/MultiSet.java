package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class MultiSet {

    private Map<String, Integer> map = new HashMap<>(0);

    public void add(String value) {
        Integer count = map.get(value);
        if(count == null) {
            map.put(value, 1);
        } else {
            map.put(value, count + 1);
        }
    }

    public int size() {return map.size();}

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}
