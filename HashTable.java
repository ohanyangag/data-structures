package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 5;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private LinkedList<Entry<K, V>>[] table;

    public HashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    public HashTable(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }
    public HashTable(int capacity, double maxLoadFactor) {
        if(capacity < 0 ||
           maxLoadFactor <= 0 ||
           Double.isNaN(maxLoadFactor) ||
           Double.isInfinite(maxLoadFactor)
        ) throw new IllegalArgumentException("Illegal 'capacity' or 'Max load factor' argument");

        this.capacity = capacity;
        this.maxLoadFactor = max(maxLoadFactor, DEFAULT_LOAD_FACTOR);
        this.threshold = calculateThreshold();

        table = new LinkedList[this.capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) table[i] = null;
        size = 0;
    }

    public boolean containsKey(K key) {
        int tableIndex = normalizeIndex(key.hashCode());

        return bucketSeekEntry(tableIndex, key) != null;
    }

    public V insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Illegal 'key' argument");
        Entry<K ,V> newEntry = new Entry<>(key, value);
        int tableIndex = normalizeIndex(newEntry.hash);

        return bucketInsertEntry(tableIndex, newEntry);
    }

    public V get(K key) {
        if (key == null) return null;
        int tableIndex = normalizeIndex(key.hashCode());
        Entry<K, V> entry = bucketSeekEntry(tableIndex, key);
        if(entry == null) return null;

        return entry.value;
    }

    public V remove(K key) {
        if (key == null) return null;
        int tableIndex = normalizeIndex(key.hashCode());

        return bucketRemoveEntry(tableIndex, key);
    }

    public List<K> keys() {

        List<K> keys = new ArrayList<>(size);
        for (LinkedList<Entry<K, V>> bucket : table)
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) keys.add(entry.key);
            }
        return keys;
    }

    public List<V> values() {

        List<V> values = new ArrayList<>(size);
        for (LinkedList<Entry<K, V>> bucket : table)
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) values.add(entry.value);
            }
        return values;
    }

    private V bucketInsertEntry(int tableIndex, Entry<K, V> entry) {
        LinkedList<Entry<K, V>> record = table[tableIndex];
        if(record == null) {
            table[tableIndex] = record = new LinkedList<>();
        }

        Entry<K, V> existentEntry = bucketSeekEntry(tableIndex, entry.key);
        if(existentEntry == null) {
            record.add(entry);

            if(++size > threshold) resizeTable();

            return null;
        } else {
            V oldValue = existentEntry.value;
            existentEntry.value = entry.value;

            return oldValue;
        }
    }

    private V bucketRemoveEntry(int tableIndex, K key) {
        Entry<K, V> record = bucketSeekEntry(tableIndex, key);

        if(record != null) {
            LinkedList<Entry<K, V>> links = table[tableIndex];
            links.remove(record);
            size--;

            return record.value;
        }

        return null;
    }

    private Entry<K, V> bucketSeekEntry(int tableIndex, K key) {
        if(key == null) return null;
        LinkedList<Entry<K, V>> record = table[tableIndex];
        if(record == null) return null;

        for (Entry<K ,V> entry : record) {
            if(entry.key.equals(key)) return entry;
        }

        return null;
    }

    private void resizeTable() {
        capacity *= 2;
        threshold = calculateThreshold();

        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {

                for (Entry<K, V> entry : table[i]) {
                    int tableIndex = normalizeIndex(entry.hash);
                    LinkedList<Entry<K, V>> record = newTable[tableIndex];
                    if (record == null) {
                        newTable[tableIndex] = record = new LinkedList<>();
                    }
                    record.add(entry);
                }

                table[i].clear();
                table[i] = null;
            }
        }

        table = newTable;
    }

    private int calculateThreshold() {
        return (int) (this.capacity * this.maxLoadFactor);
    }

    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    private static class Entry<K, V> {
        int hash;
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }

        public boolean equals(Entry<K, V> prototype) {
            if(hash != prototype.hash) return false;
            return key.equals(prototype.key);
        }

        @Override
        public String toString() {
            return key + " => " + value;
        }
    }
}
