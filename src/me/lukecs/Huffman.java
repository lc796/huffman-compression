package me.lukecs;

public class Huffman {
    private final String encodedData;
    private final Node root;

    /**
     * A Huffman object should have the encoded data (a String of bits), as well as the root node that holds the Huffman tree.
     *
     * @param encodedData The encoded data.
     * @param root The root node.
     */
    public Huffman(String encodedData, Node root) {
        this.encodedData = encodedData;
        this.root = root;
    }

    /**
     * Returns the root node.
     *
     * @return The root node.
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Returns the encoded data.
     *
     * @return The encoded data (as a String).
     */
    public String getEncodedData() {
        return encodedData;
    }

    /**
     * Calculate the padding, which must be a number from 0-7. This is the difference in length of the encoded data and the number of
     * additional characters required for length % 8 to equal 0.
     *
     * @return The number of bits of padding.
     */
    public byte calculatePadding() {
        int length = encodedData.length();
        if (length % 8 == 0) {
            return 0;
        }
        else {
            while (length % 8 != 0) {
                length++;
            }
        }
        return (byte) (length - encodedData.length());

    }
}
