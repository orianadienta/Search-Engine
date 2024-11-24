package com.projekstrukdat.model;

public class Node<K extends Comparable<K>, V> {
    K key;
    V value;
    Node<K, V> left;
    Node<K, V> right;
    Node<K, V> parent;
    boolean red;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.red = true;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setRight(Node<K, V> right) {
        this.right = right;
    }

    public void setLeft(Node<K, V> left) {
        this.left = left;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }
}

