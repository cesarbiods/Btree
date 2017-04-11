package com.btree;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by cesar on 4/5/17.
 */
public class CacheRAF {
    static String placeholder = "countRaf";
    private static final double byteCap = Math.pow(2, 23);

    private CacheRAF(String num) throws FileNotFoundException {
    }

    public static void entryWrite(Cache.Entry e, int hashI) {
        try (RandomAccessFile bucket = new RandomAccessFile("rafs/cache/" + placeholder + hashI, "rw")) {
            bucket.seek(bucket.length());

            bucket.writeUTF(e.url);
            bucket.write(serialize(e.json));
            bucket.writeInt(e.hash);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Optional<Cache.Entry> entryRead(int hashI, String key) {
        try (RandomAccessFile bucket = new RandomAccessFile("rafs/cache/" + placeholder + hashI, "rw")){
            bucket.seek(0);

            while (bucket.getFilePointer() != bucket.length()) {
                String tUrl = bucket.readUTF();
                byte[] b = new byte[(int) byteCap];
                bucket.read(b);
                String tJson = deserialize(b);
                int tHash = bucket.readInt();
                if (tUrl.equals(key)) {
                    return Optional.of(new Cache.Entry(tUrl, tJson, tHash));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static byte[] serialize(String json) {
        ByteArrayOutputStream myStream = new ByteArrayOutputStream();

        try (ObjectOutputStream sos = new ObjectOutputStream(myStream)){
            sos.writeObject(json);

            byte[] temp = new byte[(int) byteCap];
            byte[] jsonBytes = myStream.toByteArray();
            if (temp.length >= jsonBytes.length) {
                System.arraycopy(jsonBytes, 0, temp, 0, jsonBytes.length);
                return temp;
            } else {
                throw new RuntimeException("OH SHIT WHAT UP");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String deserialize(byte[] b) {
        try (ObjectInputStream sis = new ObjectInputStream(new ByteArrayInputStream(b))) {
            return (String) sis.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}