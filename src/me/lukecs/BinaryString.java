package me.lukecs;

public class BinaryString {
    /**
     * Provides functionality to convert a string into an array of bytes, which could involve adding padding.
     *
     * @param binaryString The string to convert into binary.
     * @return The resulting byte array.
     */
    public static byte[] getBinaryFromString(String binaryString) {
        StringBuilder sBuilder = new StringBuilder(binaryString);
        while (sBuilder.length() % 8 != 0) {
            sBuilder.append('0');
        }
        binaryString = sBuilder.toString();

        byte[] data = new byte[binaryString.length() / 8];

        for (int i = 0; i < binaryString.length(); i++) {
            char c = binaryString.charAt(i);
            if (c == '1') {
                data[i >> 3] |= 0x80 >> (i & 0x7);
            }
        }
        return data;
    }

    /**
     * Provides functionality to turn an array of bytes into a String.
     *
     * @param bytes The array of bytes to convert to a String.
     * @return The converted String of binary data.
     */
    public static String getStringFromBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    /**
     * Remove padding from a String of encoded data.
     *
     * @param encodedData The encoded data to remove padding from.
     * @param paddingAmount The amount of padding to remove.
     * @return The encoded data that has had padding removed.
     */
    public static String removePadding(String encodedData, byte paddingAmount) {
        return encodedData.substring(0, encodedData.length() - paddingAmount);
    }
}
