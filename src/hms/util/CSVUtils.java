package hms.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV reader/writer with comma separator.
 * Assumes there is no comma inside fields.
 */
public class CSVUtils {

    public static List<String[]> read(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            // If file does not exist, create empty one with no rows
            file.createNewFile();
            return rows;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;
                rows.add(line.split(","));
            }
        }
        return rows;
    }

    public static void write(String filePath, List<String[]> rows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}
