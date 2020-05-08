package IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(FileInputStream fileInputStream) {
        this.in = fileInputStream;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
