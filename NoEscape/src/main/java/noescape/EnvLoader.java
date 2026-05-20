package noescape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private final Map<String, String> values = new HashMap<>();

    public EnvLoader(String filePath) {
        loadFile(filePath);
    }

    private void loadFile(String filePath) {
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty() || line.startsWith("#")) continue;
                int eq = line.indexOf('=');
                if(eq > 0) {
                    String key = line.substring(0, eq).trim();
                    String value = line.substring(eq + 1).trim();
                    values.put(key, value);
                }
            }
            reader.close();
        }catch(Exception e) {
            System.out.println("Warning: .env not found. Using defaults.");
        }
    }

    public String get(String key, String defaultValue) {
        return values.getOrDefault(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        try{
            return Integer.parseInt(values.getOrDefault(key, String.valueOf(defaultValue)));
        }catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String val = values.get(key);
        if(val == null) return defaultValue;
        return val.equalsIgnoreCase("true");
    }
}