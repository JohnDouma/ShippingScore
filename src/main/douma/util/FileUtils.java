package douma.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

/**
 * Functions to read files given filenames
 */
public class FileUtils {

    /**
     * Returns <code>List<String></></code> containing newline separated content from an input filename
     * @param fileName - name of file to be read
     */
    public static List<String> readContentsOfFile(String fileName) throws FileNotFoundException {
        List<String> contents = new ArrayList<>();
        File file = new File(fileName);
        Scanner reader = new Scanner(file);
        String line;
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            contents.add(line);
        }

        return contents;
    }
}
