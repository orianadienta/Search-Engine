package com.projekstrukdat.model;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Tree<K extends Comparable<K>, V> {
    Node<K, V> root;
    final Node<K, V> TNULL; //leaf atau node kosong

    public Tree() {
        TNULL = new Node<>(null, null);
        TNULL.setRed(false); // TNULL berwarna hitam
        TNULL.setLeft(null);
        TNULL.setRight(null);
        this.root = TNULL;
    }

    public V find(K key) {
        Node<K, V> node = findHelper(root, key);
        return node == TNULL ? null : node.getValue();
    }

    private Node<K, V> findHelper(Node<K, V> node, K key) {
        if (node == TNULL || key.equals(node.getKey())) {
            return node;
        }
        if (key.compareTo(node.getKey()) < 0) {
            return findHelper(node.getLeft(), key);
        }
        return findHelper(node.getRight(), key);
    }

    private void fixAdd(Node<K, V> k) {
        Node<K, V> u;
        while (k.getParent().isRed()) {
            if (k.getParent() == k.getParent().getParent().getRight()) {
                u = k.getParent().getParent().getLeft();
                if (u.isRed()) {
                    u.setRed(false);
                    k.getParent().setRed(false);
                    k.getParent().getParent().setRed(true);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getLeft()) {
                        k = k.getParent();
                        rotateRight(k);
                    }
                    k.getParent().setRed(false);
                    k.getParent().getParent().setRed(true);
                    rotateLeft(k.getParent().getParent());
                }
            } else {
                u = k.getParent().getParent().getRight();
                if (u.isRed()) {
                    u.setRed(false);
                    k.getParent().setRed(false);
                    k.getParent().getParent().setRed(true);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getRight()) {
                        k = k.getParent();
                        rotateLeft(k);
                    }
                    k.getParent().setRed(false);
                    k.getParent().getParent().setRed(true);
                    rotateRight(k.getParent().getParent());
                }
            }
            if (k == root) {
                break;
            }
        }
        root.setRed(false);
    }

    public void add(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        node.setLeft(TNULL);
        node.setRight(TNULL);
        node.setParent(null);
        node.setRed(true);

        Node<K, V> y = null;
        Node<K, V> x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.getKey().compareTo(x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        node.setParent(y);
        if (y == null) {
            root = node;
        } else if (node.getKey().compareTo(y.getKey()) < 0) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }

        if (node.getParent() == null) {
            node.setRed(false);
            return;
        }

        if (node.getParent().getParent() == null) {
            return;
        }

        fixAdd(node);
    }

    private void rotateLeft(Node<K, V> node) {
        Node<K, V> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != TNULL) {
            rightChild.getLeft().setParent(node);
        }
        rightChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = rightChild;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(rightChild);
        } else {
            node.getParent().setRight(rightChild);
        }
        rightChild.setLeft(node);
        node.setParent(rightChild);
    }

    private void rotateRight(Node<K, V> node) {
        Node<K, V> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        if (leftChild.getRight() != TNULL) {
            leftChild.getRight().setParent(node);
        }
        leftChild.setParent(node.getParent());
        if (node.getParent() == null) {
            root = leftChild;
        } else if (node == node.getParent().getRight()) {
            node.getParent().setRight(leftChild);
        } else {
            node.getParent().setLeft(leftChild);
        }
        leftChild.setRight(node);
        node.setParent(leftChild);
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        if (root != TNULL) {
            toJSONHelper(root, jsonObject);
        }
        return jsonObject;
    }

    private void toJSONHelper(Node<K, V> node, JSONObject jsonObject) {
        if (node == TNULL) {
            return;
        }
        jsonObject.put(String.valueOf(node.getKey()), node.getValue());
        toJSONHelper(node.getLeft(), jsonObject);
        toJSONHelper(node.getRight(), jsonObject);
    }

    public boolean saveToFile(String filename) {
        JSONObject jsonObject = toJSON();
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonObject.toString(4));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void preOrderTraversal(Node<K, V> node) {
        if (node != TNULL) {
            preOrderTraversal(node.getLeft());
            preOrderTraversal(node.getRight());
        }
    }

    public void inOrderTraversal(Node<K, V> node) {
        if (node != TNULL) {
            inOrderTraversal(node.getLeft());
            inOrderTraversal(node.getRight());
        }
    }

    public void postOrderTraversal(Node<K, V> node) {
        if (node != TNULL) {
            postOrderTraversal(node.getLeft());
            postOrderTraversal(node.getRight());
        }
    }
}
