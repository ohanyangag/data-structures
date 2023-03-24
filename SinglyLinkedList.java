public class SinglyLinkedList {
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
        head = node;
    }

    private void setTail(Node node) {
        tail.next = node;
    }

    private boolean removeFirst() {
        head = head.next;
        if(head == null) {
            tail = null;
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
        if (index == 0) {
            return removeFirst();
        }
        if (index == lastIndex()) {
            return remove();
        }
        Node elementForDelete = find(index - 1);
        elementForDelete.next = elementForDelete.next.next;
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
        private String value;
        private Node next;

        public Node(String value) {
            this.value = value;
        }

        public Node(String value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}