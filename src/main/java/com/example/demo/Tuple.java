package com.example.demo;

public class Tuple<A, B> {
    private A first;
    private B second;

    public Tuple() {

    }

    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}

