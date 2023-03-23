public class CircularLinkedList {
    private Node last;
    private int size;

    public void add(String value) {
        if(last == null) {
            setFirst(value);
        } else {
            setNext(value);
        }
        size++;
    }

    private void setFirst(String value) {
        Node first = new Node(value);
        last = first;
        last.head = first;
    }

    private void setNext(String value) {
        Node newNode = new Node(value);
        last.next = newNode;
        newNode.head = last.head;
        last.head = null;
        last = newNode;
    }

    private boolean remove(String value) {
        if(last == null) return false;
        if(removeFirst(value)) return true;

        return remove(last.head, last.head.next, value);
    }

    private boolean removeFirst(String value) {
        if(last.head.value.equals(value)) {
            if(last.head.next == null) {
                last = null;
            } else {
                last.head = last.head.next;
            }
            size--;

            return true;
        }

        return false;
    }

    private boolean remove(Node prev, Node current, String value) {
        if(current == null) return false;

        if(current.value.equals(value)) {
            if(current.head == null) {
                prev.next = current.next;
            } else {
                prev.next = null;
                prev.head = current.head;
            }
            size--;

            return true;
        }

        return remove(current, current.next, value);
    }

    public int size() {
        return size;
    }

    private static class Node {
        private Node head;
        private String value;
        private Node next;

        public Node(String value) {
            this.value = value;
        }

        public Node(Node head, Node next) {
            this.head = head;
            this.next = next;
        }
    }
}