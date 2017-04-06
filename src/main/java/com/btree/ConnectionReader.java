package com.btree;
/**
 * Created by cesar on 2/2/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionReader {

    /**
     * Queries a URL and returns it's contents as a JSON String
     * <p>
     * The method creates a URLConnection and a BufferedReader
     * object to query the URL from the user and returns the JSON
     * String for the user to parse.
     *
     * @param  theUrl  a String of the URL the user wishes to query
     * @return      a JSON in String form from the URL
     */

    public static String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);

            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            urlConnection.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
