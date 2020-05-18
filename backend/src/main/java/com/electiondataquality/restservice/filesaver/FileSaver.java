package com.electiondataquality.restservice.filesaver;

import java.io.File;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.channels.CompletionHandler;
import java.nio.channels.AsynchronousFileChannel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.electiondataquality.restservice.RestServiceApplication;

public class FileSaver {

    private static String storePath;

    private static String extension = ".json";

    /**
     * How long a file should exist in milliseconds
     * 
     * milliseconds = #minutes * 60000;
     * 
     * e.g.) 8 * 60000; // files last for 8 minutes
     * 
     * (b/c: 60000 milliseconds in a minute)
     * 
     * This is 2 hours.
     */
    public static long expireTime = 2 * 60 * 60000;

    /**
     * Default .json file extension and 5 minute valid data time.
     * 
     * @param storePath
     * @throws IOException
     */
    public FileSaver(String storePath) throws IOException {
        FileSaver.storePath = storePath + "/";

        this.initialize();
    }

    /**
     * Default .json extension.
     * 
     * @param storePath
     * @param expireTime
     * @throws IOException
     */
    public FileSaver(String storePath, long expireTime) throws IOException {
        FileSaver.storePath = storePath + "/";
        FileSaver.expireTime = expireTime;

        this.initialize();
    }

    /**
     * Default 2 hourss valid data time.
     * 
     * @param storePath
     * @param extension
     * @throws IOException
     */
    public FileSaver(String storePath, String extension) throws IOException {
        FileSaver.storePath = storePath + "/";
        FileSaver.extension = "." + extension;

        this.initialize();
    }

    /**
     * Initialize anything at startup of app.
     * 
     * Currently it clears out the cache upon every startup.
     * 
     * @throws IOException
     */
    private void initialize() throws IOException {
        FileUtils.cleanDirectory(new File(FileSaver.storePath));
    }

    /**
     * Searches the data store for non-expired data with the specified key.
     * 
     * @param keyName key of the specified data to search for
     * @return boolean - true if found valid data; false if otherwise.
     */
    public boolean isValid(String keyName) {
        File file = new File(FileSaver.storePath + keyName + FileSaver.extension);

        if (file.lastModified() + FileSaver.expireTime > System.currentTimeMillis()) {
            RestServiceApplication.logger.info("valid cache");

            return true;
        }

        RestServiceApplication.logger.info("invalid cache");

        return false;
    }

    /**
     * Asynchronously Save the specified jsonString with the specified key. Will
     * always overwrite the jsonString data if there exists a pre-existing valid
     * key.
     * 
     * @param keyName
     * @param jsonString
     * @throws IOException
     */
    public void saveFile(String keyName, Object serializableObj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(serializableObj);

        Path path = Paths.get(FileSaver.storePath + keyName + FileSaver.extension);
        Files.deleteIfExists(path);
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);

        ByteBuffer buffer = ByteBuffer.allocate(jsonString.length() + 1);
        buffer.put(jsonString.getBytes());
        buffer.flip();

        fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                // result is number of bytes written
                // attachment is the buffer
                RestServiceApplication.logger.info("FileSaver finished writing " + result + " bytes to file");
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                RestServiceApplication.logger.error("FileSaver failed to saveFile()");
            }
        });

    }

    /**
     * Read the contents of the file specified by keyName.
     * 
     * NOTE: I'm not sure on the exact maximum, but keep note that this method will
     * be returning megabytes of data in a single String data structure...
     * 
     * Should beware in the future & probably change up this method since it may be
     * possible for Gigabytes of data needed to be read; which may exceed the max
     * size a String type can hold...
     * 
     * @param keyName
     * @return Contents of the file as a string
     * @throws IOException
     */
    public String readFile(String keyName) throws IOException {
        Path path = Paths.get(FileSaver.storePath + keyName + FileSaver.extension);

        List<String> lines = Files.readAllLines(path);

        return String.join(System.lineSeparator(), lines);
    }
}
