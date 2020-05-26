package IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is for reading the compressed maze from InputStream.
 */

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(FileInputStream fileInputStream) {
        this.in = fileInputStream;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
    }
}
