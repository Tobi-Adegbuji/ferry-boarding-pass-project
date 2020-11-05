package main.java.fi_objects;

@FunctionalInterface
public interface TriConsumer<A,B,C>{
     void apply(A a, B b, C c);
}
