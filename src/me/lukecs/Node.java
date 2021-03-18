package me.lukecs;

import java.io.Serializable;

public class Node implements Comparable<Node>, Serializable {
    private final char character;
    private final int frequency;
    private final Node left;
    private final Node right;

    /**
     * A Node represents a part of a tree. A node is either a parent, i.e., has both a left and right node, or a leaf, i.e., has a null left and right node.
     * A parent node will use the null character to represent (since we can't actually use 'null' for 'char' data type.
     *
     * @param character The character.
     * @param frequency The frequency this character occurs.
     * @param left The left node.
     * @param right The right node.
     */
    public Node(char character, int frequency, Node left, Node right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * Determines if the node is a leaf if the node has no left and right node.
     *
     * @return Whether the node is a leaf.
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Compares the frequency of this node with the frequency of some other node.
     *
     * @param other The other node to compare frequency with.
     * @return The comparison, a value that can be <0, =0, or >0.
     */
    @Override
    public int compareTo(Node other) {
        return Integer.compare(frequency, other.frequency);
    }

    /**
     * Gets the character this node represents.
     *
     * @return The node this character represents.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Gets the frequency this character occurs. Should be >=1 although this is not hard enforced, and is implementation-dependent.
     *
     * @return The frequency this character represents.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Gets the left node.
     *
     * @return The left node.
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Gets the right node.
     *
     * @return The right node.
     */
    public Node getRight() {
        return right;
    }
}
