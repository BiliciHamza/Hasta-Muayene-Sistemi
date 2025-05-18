package com.example.hastakayit;

public class MaxHeap {
    private HastaNode root;
    private int size;
    private HastaNode lastNode;

    public MaxHeap(int i) {
        this.root = null;
        this.size = 0;
        this.lastNode = null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(Hasta hasta) {
        HastaNode newNode = new HastaNode(hasta);
        if (root == null) {
            root = newNode;
            lastNode = root;
        } else {
            HastaNode parent = findInsertionParent();
            if (parent.getLeftChild() == null) {
                parent.setLeftChild(newNode);
            } else {
                parent.setRightChild(newNode);
            }
            lastNode = newNode;
            bubbleUp(newNode);
        }
        size++;
    }

    private HastaNode findInsertionParent() {
        return findInsertionParent(size + 1);
    }

    private HastaNode findInsertionParent(int index) {
        if (index <= 1) return null;
        String path = Integer.toBinaryString(index).substring(1);
        HastaNode current = root;
        for (int i = 0; i < path.length() - 1; i++) {
            current = (path.charAt(i) == '0') ? current.getLeftChild() : current.getRightChild();
        }
        return current;
    }

    private void bubbleUp(HastaNode node) {
        while (node.getParent() != null &&
                node.getHasta().getOncelikPuani() >
                        node.getParent().getHasta().getOncelikPuani()) {
            swapValues(node, node.getParent());
            node = node.getParent();
        }
    }

    private void bubbleDown(HastaNode node) {
        while (true) {
            HastaNode largest = node;
            HastaNode left = node.getLeftChild();
            HastaNode right = node.getRightChild();

            if (left != null && left.getHasta().getOncelikPuani() >
                    largest.getHasta().getOncelikPuani()) {
                largest = left;
            }

            if (right != null && right.getHasta().getOncelikPuani() >
                    largest.getHasta().getOncelikPuani()) {
                largest = right;
            }

            if (largest == node) {
                break;
            }

            swapValues(node, largest);
            node = largest;
        }
    }

    private void swapValues(HastaNode node1, HastaNode node2) {
        Hasta temp = node1.getHasta();
        node1.setHasta(node2.getHasta());
        node2.setHasta(temp);
    }

    public Hasta peek() {
        if (root == null) return null;
        return root.getHasta();
    }

    public Hasta remove() {
        if (root == null) return null;

        Hasta result = root.getHasta();

        if (size == 1) {
            root = null;
            lastNode = null;
            size--;
            return result;
        }


        HastaNode lastParent = findInsertionParent(size);
        boolean isLastNodeLeft = (lastParent.getLeftChild() == lastNode);

        root.setHasta(lastNode.getHasta());


        if (isLastNodeLeft) {
            lastParent.setLeftChild(null);
        } else {
            lastParent.setRightChild(null);
        }


        lastNode = findLastNode(size - 1);

        size--;
        bubbleDown(root);

        return result;
    }

    private HastaNode findLastNode(int size) {
        if (size <= 0) return null;
        return findNodeAtIndex(size);
    }

    private HastaNode findNodeAtIndex(int index) {
        if (index <= 0) return null;
        String path = Integer.toBinaryString(index);
        HastaNode current = root;
        for (int i = 1; i < path.length(); i++) {
            current = (path.charAt(i) == '0') ? current.getLeftChild() : current.getRightChild();
        }
        return current;
    }

    public HastaNode getRoot() {
        return root;
    }


    public Hasta getElementAt(int index) {
        HastaNode node = findNodeAtIndex(index + 1);
        return node != null ? node.getHasta() : null;
    }
}
