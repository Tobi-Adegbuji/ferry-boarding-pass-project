package main.java.fi_objects;

@FunctionalInterface
public interface TriConsumer<A,B,C>{
    public void apply(A a, B b, C c);
}
