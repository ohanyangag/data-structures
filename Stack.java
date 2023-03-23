import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<T> {
    private T[] data;
    private int capacity, top = 0;

    public Stack() {
        capacity = 10;
        data = (T[]) new Object[capacity];
    }

    public T push(T t) {
        if(top == capacity) increaseCapacity();

        data[top++] = t;
        
        return t;
    }

    public T pop() {
        if(isEmpty()) throw new EmptyStackException();

        T element = (T) data[--top];
        data[top] = null;
        
        return element;
    }

    public T peek() {
        if(isEmpty()) throw new EmptyStackException();

        return (T) data[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    private void increaseCapacity() {
        capacity *= 2;
        data = Arrays.copyOf(data, capacity);
    }
}
