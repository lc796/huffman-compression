## Introduction
This is an implementation of Huffman Coding in Java. This project is hosted on Github at https://github.com/lc796/huffman-compression

## How to use
To use, you should run the following command:
``java <compress/decompress> <absolute path> <name and extension of file to compress/decompress from/to>``

For example, to compress, I might do something like:
``java -Dfile.encoding=UTF-8 me.lukecs.Main "compress" "D:\\Explorer\\University\\Data Structures and Algorithms\\HuffmanCompressionAlgorithm\\test_data_sets" "cintra_pt.txt"``

And then to decompress, I might do something like:
``java -Dfile.encoding=UTF-8 me.lukecs.Main "decompress" "D:\\Explorer\\University\\Data Structures and Algorithms\\HuffmanCompressionAlgorithm\\test_data_sets" "decoded.txt"``

It is also possible to use "c" or "d" as aliases for "compress" and "decompress" respectively.