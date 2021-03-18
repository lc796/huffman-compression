package me.lukecs;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtilities {
    /*
    Getting the initial raw input data
     */
    /**
     * Gets the raw input data from a file and returns a String.
     *
     * @param path The absolute path as a string.
     * @param name The file name to get the data from, including extension.
     * @return The data read from the file.
     */
    public static String getRawInputData(String path, String name) {
        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get(path + "\\" + name)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return data;
    }

    /*
    Storing the decoded data
     */
    /**
     * Stores the decoded data to a file.
     *
     * @param data The data to store to a file.
     * @param path The absolute path as a string.
     * @param name The file name to write the data to, including extension.
     */
    public static void storeDecodedData(String data, String path, String name) {
        try (FileOutputStream fos = new FileOutputStream(path + "\\" + name)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
    Storing
     */
    /**
     * Stores the Huffman data (Huffman tree, padding data, and binary data) to some files.
     *
     * @param huffman The Huffman object.
     * @param path The absolute path as a string.
     */
    public static void storeHuffmanEncodedResultToFile(Huffman huffman, String path) {
        try {
            Files.createDirectory(Paths.get(path + "\\encoded"));
        } catch (FileAlreadyExistsException fe) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        storeTreeToFile(huffman.getRoot(), path);
        storeDataToFile(BinaryString.getBinaryFromString(huffman.getEncodedData()), path);
        storePaddingToFile(huffman.calculatePadding(), path);
    }

    /**
     * Stores the Huffman tree to a file.
     *
     * @param root The root node that represents the tree.
     * @param path The absolute path as a string.
     */
    private static void storeTreeToFile(Node root, String path) {
        try (FileOutputStream fos = new FileOutputStream(path + "\\encoded\\" + "data.huff")) {
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the binary data to a file.
     *
     * @param bytes The byte array that represents the encoded data in binary.
     * @param path The absolute path as a string.
     */
    private static void storeDataToFile(byte[] bytes, String path) {
        try (FileOutputStream fos = new FileOutputStream(path + "\\encoded\\" + "data.bin")) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the amount of padding used to a file so that we can restore the data to original state.
     *
     * @param padding The amount of padding used.
     * @param path The absolute path as a string.
     */
    private static void storePaddingToFile(byte padding, String path) {
        try (FileOutputStream fos = new FileOutputStream(path + "\\encoded\\" + "data.pad")) {
            fos.write(padding);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Retrieving
     */
    /**
     * Gets the Huffman result from a file.
     *
     * @param path The absolute path as a string.
     * @return The Huffman object.
     */
    public static Huffman getHuffmanEncodedResultFromFile(String path) {
        Node root = getTreeFromFile(path);
        String data = getDataFromFile(path);
        return new Huffman(data, root);
    }

    /**
     * Gets the Huffman tree from a file.
     *
     * @param path The absolute path as a string.
     * @return The root node.
     */
    private static Node getTreeFromFile(String path) {
        Node root = null;
        try {
            FileInputStream fis = new FileInputStream(path + "\\encoded\\" + "data.huff");
            ObjectInputStream ois = new ObjectInputStream(fis);
            root = (Node) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return root;

    }

    /**
     * Gets the data from a file, and calls a method to convert from binary to string, before removing padding.
     *
     * @param path The absolute path as a string.
     * @return The data as a string.
     */
    private static String getDataFromFile(String path) {
        byte[] bytes = null;
        byte padding = 0;
        try {
            bytes = Files.readAllBytes(Paths.get(path + "\\encoded\\" + "data.bin"));
            padding = getPaddingFromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bytes != null) {
            return BinaryString.removePadding(BinaryString.getStringFromBinary(bytes), padding);
        }

        return null;
    }

    /**
     * Gets the padding from a file.
     *
     * @param path The absolute path as a string.
     * @return The padding.
     */
    private static byte getPaddingFromFile(String path) {
        byte padding = 0;

        try {
            String data = new String(Files.readAllBytes(Paths.get(path + "\\encoded\\" + "data.pad")));
            padding = (byte) data.toCharArray()[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return padding;
    }
}
