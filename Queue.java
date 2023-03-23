public class Queue<T> {
    private T[] data;
    private int rear, front;
    
    public Queue(int capacity) {
        front = rear = 0;
        data = (T[]) new Object[capacity + 1];
    }

    public T enqueue(T element) {
        if(isFull()) throw new RuntimeException("Queue is full");
        
        data[rear++] = element;
        rear = changeIndex(rear, data.length);

        return element;
    }

    public T dequeue() {
        if(isEmpty()) throw new RuntimeException("Queue is empty");
        
        front = changeIndex(front, data.length);

        return (T) data[front++];
    }

    public T peek() {
        if(isEmpty()) throw new RuntimeException("Queue is empty");
        
        front = changeIndex(front, data.length);

        return (T) data[front];
    }

    public boolean isEmpty() {
        return front == rear;
    }
    
    public int size() {
        return changeIndex(rear + data.length - front, data.length);
    }

    public boolean isFull() {
        return (front + data.length - rear) % data.length == 1;
    }
    
    private int changeIndex(int index, int capacity) {
        if(index >= capacity)
            return index - capacity;

        return index;
    }
}
