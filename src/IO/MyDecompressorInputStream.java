package IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is for reading the compressed maze from InputStream.
 */

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    /**
     * Constructor
     * @param Input - the fileInput used for the class
     */
    public MyDecompressorInputStream(InputStream Input) {
        this.in = Input;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Fill an empty byte array with the data we decompress from the fileInputStream.
     * @param b - the empty byte array we are filling.
     * @return int 0.
     * @throws IOException - if something went wrong in I/O
     */
    @Override
    public int read(byte[] b) throws IOException {
        try {
            int MazeLen = b.length - 12;
            int mod8 = MazeLen % 8;
            // The first 12 array elements will be read as they are
            for (int i = 0; i < 12; i++) {
                b[i] = (byte)read();
            }

            String strToNum = "";
            String curBinary;
            int i = 12;

            // Filling the byte array until size divides by 8
            while ((i < b.length - mod8) && (i + 8 <= b.length)) {

                // Convert the byte that was read to a binary number
                int cur = read();
                curBinary = String.format("%8s", Integer.toBinaryString(cur)).replace(' ', '0');

                for (int j = 0; j < 8; j++) {
                    // setting b[i] to be the j pos of curBinary string
                    // charAt() func is giving ASCII value so we decrease by 48
                    b[i] = (byte) (curBinary.charAt(j) - 48);
                    i++;
                }
            }

            curBinary = "";

            // Needs to represent the remainder from 8
            if (mod8 != 0) {
                int cur = read();
                String strToBin = Integer.toBinaryString(cur);

                // format the binary number to the size of mod8 filling with leading zeros
                curBinary =  String.format("%0"+ ((mod8+1) - strToBin.length() )+"d%s",0 ,strToBin).substring(1,mod8+1);
                for (int j=0; j < curBinary.length(); j++){
                    b[i] = (byte) (curBinary.charAt(j) - 48);
                    i++;
                }
            }
            in.close();
            return 0;
        }
        catch (IOException e) {
            System.out.println("There has been something wrong with the reading process (byte[])");
            return 0;
        }
    }
}
