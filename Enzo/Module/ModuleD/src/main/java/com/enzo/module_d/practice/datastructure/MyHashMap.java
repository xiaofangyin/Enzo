package com.enzo.module_d.practice.datastructure;


/**
 * 文 件 名: MyHashMap
 * 创 建 人: xiaofy
 * 创建日期: 2019/6/24
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MyHashMap<K, V> {

    private final Entry[] table;
    private static final Integer CAPACITY = 8;//容量

    public MyHashMap() {
        table = new Entry[CAPACITY];
    }

    public Object get(Object key) {
        int hash = key.hashCode();
        int i = Math.abs(hash % CAPACITY);

        for (Entry<K, V> entry = table[i]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        int hash = key.hashCode();
        int i = Math.abs(hash % CAPACITY);

        for (Entry<K, V> entry = table[i]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        addEntry(key, value, i);
        return null;
    }

    private void addEntry(K key, V value, int i) {
        Entry entry = new Entry(key, value, table[i]);
        table[i] = entry;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;
        private Entry next;

        Entry(K k, V v, Entry entry) {
            this.key = k;
            this.value = v;
            this.next = entry;
        }
    }
}
