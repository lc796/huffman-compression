package me.lukecs;

import java.util.*;

public class Encoder {

    /**
     * Take some string of raw input data (i.e., "hello"), and generate the Huffman coding for it.
     *
     * @param data The raw input data to compress, as as String.
     * @return A Huffman object that contains the encoded data as well as the root node that contains the tree.
     */
    public static Huffman compress(String data) {
        Map<Character, Integer> occurrences = new HashMap<>();

        constructFrequencyMap(data, occurrences);
        Node root = buildHuffmanTree(occurrences);
        Map<Character, String> lookupTable = buildLookupTable(root);
        return new Huffman(generateEncodedData(data, lookupTable), root);
    }

    /**
     * Take a Map as well as some data, and populate the Map with frequencies. Populates initially with all Unicode characters.
     * This method's return type is void since we aren't returning a Map, we are populating a map by reference.
     *
     * @param data The data to construct a mapping for frequencies with.
     * @param occurrences The Map of occurrences.
     */
    private static void constructFrequencyMap(String data, Map<Character, Integer> occurrences) {
        for (char i=0; i<Main.SIZE; i++) {
            occurrences.put(i, 0);
        }
        for (char character : data.toCharArray()) {
            // Not the first occurrence
            occurrences.replace(character, occurrences.get(character) + 1);
        }
    }

    /**
     * Take the constructed mapping of Character : Frequency and return the root node of the Huffman tree.
     *
     * @param occurrences The Map of occurrences of all characters.
     * @return The root node of the Huffman tree.
     */
    private static Node buildHuffmanTree(Map<Character, Integer> occurrences) {
        // Use a PriorityQueue so we can have the lowest frequency element on the top of the priority heap internally.
        Queue<Node> queue = new PriorityQueue<>();
        for (char i = 0; i < Main.SIZE; i++) {
            /*
             For every character that actually occurs, we will create a new leaf node (i.e., left and right nodes are null) of this character,
             with the character and its frequency.
             */
            if (occurrences.get(i) > 0) {
                queue.add(new Node(i, occurrences.get(i), null, null));
            }
        }

        // A queue size of 1 indicates that the only Node left is the root node, so we add this to the queue before later returning the poll().
        if (queue.size() == 1) {
            queue.add(new Node('\0', 1, null, null));
        }

        // A queue size greater than 1 indicates that there are still 2 or more leaf nodes, so we should create a new parent node, and add it back onto the queue.
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node parent = new Node('\0', left.getFrequency() + right.getFrequency(), left, right);
            queue.add(parent);
        }

        return queue.poll();
    }

    /**
     * Takes the root node and constructs and returns a lookup table of each character and its corresponding Huffman code.
     *
     * @param root The root node.
     * @return The lookup table, a Map of Character : String.
     */
    private static Map<Character, String> buildLookupTable(Node root) {
        Map<Character, String> lookupTable = new HashMap<>();
        // Initial call to our recursive build function, with an initial empty string, and an empty lookup table.
        recursivelyBuildLookupTable(root, "", lookupTable);
        return lookupTable;
    }

    private static void recursivelyBuildLookupTable(Node node, String s, Map<Character, String> lookupTable) {
        /*
         In the case that our node is not a leaf, it means it is a parent node, and MUST have a left and right node,
         so we should perform the recursive build function on each of these nodes.
         */
        if (!node.isLeaf()) {
            /*
            For the left node, we will add a '0' to the sequence, and for the right node, we will add a '1' to the sequence.
             */
            recursivelyBuildLookupTable(node.getLeft(), s + '0', lookupTable);
            recursivelyBuildLookupTable(node.getRight(), s + '1', lookupTable);
        } else {
            /*
             At this stage, we know that we have reached the leaf for this particular recursive lookup call (since we call the recursive function twice above,
             it will continue to branch twice until it can't anymore, at which point we have reached a leaf and should put this in the lookup table.
             Essentially, we want our lookup table to only ever add the leaf nodes and their corresponding Huffman codes.
             */
            lookupTable.put(node.getCharacter(), s);
        }
    }

    /**
     * Using our lookup table and data, we will generate our encoded data, by going through each character, and looking up its Huffman code, before appending.
     *
     * @param data The data to encode.
     * @param lookupTable The lookup table that maps Character : String with Huffman code.
     * @return The encoded string of bits.
     */
    private static String generateEncodedData(String data, Map<Character, String> lookupTable) {
        // Use StringBuilder to improve performance.
        StringBuilder sb = new StringBuilder();
        for (char character : data.toCharArray()) {
            sb.append(lookupTable.get(character));
        }
        return sb.toString();
    }
}
