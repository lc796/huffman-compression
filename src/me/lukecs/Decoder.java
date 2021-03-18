package me.lukecs;

public class Decoder {

    /**
     * Decompresses a Huffman object using it's tree and encoded data.
     *
     * @param result The Huffman object that contains encoded data and a root node that holds the tree.
     * @return The decoded text.
     */
    public static String decompress(Huffman result) {
        // Use StringBuilder for improved performance.
        StringBuilder sb = new StringBuilder();

        // Initialise our current node with the root node.
        Node current = result.getRoot();

        // Start reading in from the first (or '0th') character.
        int i = 0;
        while (i < result.getEncodedData().length()) { // Keep decoding until we have read all of the bits in the encoded string of data.
            /*
             Every iteration of this internal loop is essentially a new byte to decode, so this internal loop will iterate until we reach a leaf,
             at which point we will append the character that the leaf node we arrive at represents.
             */
            while (!current.isLeaf()) {
                char bit = result.getEncodedData().charAt(i);
                // '1' means right.
                if (bit == '1') {
                    current = current.getRight();
                } else if (bit == '0') { // '0' means left.
                    current = current.getLeft();
                } else { // Anything that is neither '0' or '1' is not valid, and we should throw an error!
                    throw new IllegalArgumentException("Bit must be either 1 or 0!");
                }
                /*
                 We are iterating our counter variable on the internal loop because this is a string of 1s and 0s, and the majority of times 1 character is represented
                 with more than 1 bit.
                 */
                i++;
            }
            // Append the character this leaf represents to our StringBuilder object.
            sb.append(current.getCharacter());
            // Set our node back to the root node so we can begin the tree traversal again from the Nth bit.
            current = result.getRoot();
        }
        return sb.toString();
    }
}
