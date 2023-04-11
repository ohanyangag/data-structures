public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public void add(String value) {
        Node node = new Node(value);
        if(head == null) {
            setHead(node);
        } else {
            setTail(node);
        }
        tail = node;
        size++;
    }

    private void setHead(Node node) {
        node.prev = null;
        head = node;
    }

    private void setTail(Node node) {
        node.next = null;
        tail.next = node;
        node.prev = tail;
    }

    private boolean removeFirst() {
        head = head.next;
        if(head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;

        return true;
    }

    public boolean remove() {
        Node elementForDelete = find(lastIndex() - 1);
        tail = elementForDelete;
        elementForDelete.next = null;
        size--;

        return true;
    }

    public boolean remove(int index) {
        if(index < 0 || index >= size) throw new IllegalArgumentException("Illegal argument for index: " + index);
        if(index == 0) {
            return removeFirst();
        }
        if(index == lastIndex()) {
            return remove();
        }

        Node deleteElementPrev = find(index - 1);
        deleteElementPrev.next = deleteElementPrev.next.next;
        deleteElementPrev.next.prev = deleteElementPrev;
        size--;

        return true;
    }

    private Node find(final int length) {
        Node node = head;
        for (int i = 0; i < length; i++) node = node.next;

        return node;
    }

    public int size() {
        return size;
    }

    private int lastIndex() {
        return size - 1;
    }

    private static class Node {
        private Node prev;
        private String value;
        private Node next;

        public Node(String value) {
            this.value = value;
        }

        public Node(Node prev, String value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}