package com.example.hastakayit;

public class HastaNode {
    private Hasta hasta;
    private HastaNode leftChild;
    private HastaNode rightChild;
    private HastaNode parent;

    public HastaNode(Hasta hasta) {
        this.hasta = hasta;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public Hasta getHasta() {
        return hasta;
    }

    public void setHasta(Hasta hasta) {
        this.hasta = hasta;
    }

    public HastaNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HastaNode leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.setParent(this);
        }
    }

    public HastaNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HastaNode rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.setParent(this);
        }
    }

    public HastaNode getParent() {
        return parent;
    }

    public void setParent(HastaNode parent) {
        this.parent = parent;
    }
}