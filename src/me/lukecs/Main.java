package me.lukecs;

public class Main {
    /**
     * The number of characters to consider.
     */
    public static final int SIZE = 65356;

    /**
     * Main method that drives program.
     *
     * @param args Arguments that should be in the form: [compress/decompress] [absolute path] [file name with extension]
     */
    public static void main(String[] args) {
        final String ERROR = "Error: cannot understand command! Args must be in the form \"[compress/decompress] [absolute path] [file name with extension]\"";

        if (args.length < 2 || args.length > 3) {
            System.out.println(ERROR);
            return;
        }

        if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("compress")) {
            String path = args[1];
            String encodedFileName = "";
            if (args.length == 3) {
                encodedFileName = args[2];
            }

            long startTimeEncoding = System.currentTimeMillis();
            Huffman hf = Encoder.compress(FileUtilities.getRawInputData(path, encodedFileName));

            FileUtilities.storeHuffmanEncodedResultToFile(hf, path);
            long endTimeEncoding = System.currentTimeMillis();

            System.out.println("Compressed successfully!");
            System.out.println("Compressing file took: " + (endTimeEncoding - startTimeEncoding) + " ms!");
        } else if (args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("decompress")) {
            String path = args[1];
            String decodedFileName = "";
            if (args.length == 3) {
                decodedFileName = args[2];
            }

            long startTimeDecoding = System.currentTimeMillis();
            Huffman resulted = FileUtilities.getHuffmanEncodedResultFromFile(path);

            FileUtilities.storeDecodedData(Decoder.decompress(resulted), path, decodedFileName);
            long endTimeDecoding = System.currentTimeMillis();

            System.out.println("Decompressed successfully!");
            System.out.println("Decompressing file took: " + (endTimeDecoding - startTimeDecoding) + " ms!");
        } else {
            System.out.println(ERROR);
        }
    }
}
