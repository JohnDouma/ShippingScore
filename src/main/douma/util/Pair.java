package douma.util;

/**
 * Simple data structure to compensate for the lack of a Pair class in Java 8
 */
public class Pair<S,T> {
    public Pair(S s, T t) {
        this.first = s;
        this.second = t;
    }
    public S first;
    public T second;
}
