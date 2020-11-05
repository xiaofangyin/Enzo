package com.enzo.module_d.practice.datastructure;


/**
 * 文 件 名: MyHashMap
 * 创 建 人: xiaofy
 * 创建日期: 2019/6/24
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MyHashMap<K, V> {

    private final Entry<K, V>[] table;
    private static final Integer CAPACITY = 16;//容量

    public MyHashMap() {
        table = new Entry[CAPACITY];
    }

    public Object get(Object key) {
        int hash = key.hashCode();
        int index = Math.abs(hash % CAPACITY);

//        Entry<K, V> en = table[i];
//        while (en != null) {
//            if (en.key.equals(key)) {
//                return en.value;
//            } else {
//                en = en.next;
//            }
//        }
//        return null;

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        int hash = key.hashCode();
        int index = Math.abs(hash % CAPACITY);

//        Entry<K, V> en = table[index];
//        while (en != null) {
//            if (en.key.equals(key)) {
//                V old = en.value;
//                en.value = value;
//                return old;
//            }
//        }
//        addEntry(key, value, index);

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        addEntry(key, value, index);
        return null;
    }

    private void addEntry(K key, V value, int index) {
        Entry<K, V> entry = new Entry(key, value, table[index]);
        table[index] = entry;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        Entry(K k, V v, Entry<K, V> entry) {
            this.key = k;
            this.value = v;
            this.next = entry;
        }
    }
}
