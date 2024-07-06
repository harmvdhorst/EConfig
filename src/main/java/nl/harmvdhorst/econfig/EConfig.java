package nl.harmvdhorst.econfig;

import net.intelie.tinymap.TinyMap;
import net.intelie.tinymap.TinyMapBuilder;

import java.io.*;

public class EConfig {

    private int currentLine = 1;

    private TinyMap<String, Object> configMap;

    public EConfig(File file){
        if(file.getName().endsWith(".ec") || file.getName().endsWith(".econfig")){
            this.load(file);
        }
    }

    public String getString(String key){
        return (String) configMap.get(key);
    }

    public int getInt(String key){
        return Integer.parseInt((String) configMap.get(key));
    }

    public double getDouble(String key){
        return Double.parseDouble((String) configMap.get(key));
    }

    public float getFloat(String key){
        return Float.parseFloat((String) configMap.get(key));
    }

    public boolean getBoolean(String key){
        return Boolean.parseBoolean((String) configMap.get(key));
    }

    private void load(File file){
        //long time = System.currentTimeMillis();

        TinyMapBuilder<String, Object> builder = TinyMap.builder();

        boolean categoryMode = false;
        String currentCategory = "";

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null){

                if(line.startsWith("//")){
                    // ignore (comment)
                } else if(line.startsWith("(")){
                    categoryMode = true;
                    currentCategory = line.replaceAll("\\(", "").replaceAll("\\)", "");
                } else if(categoryMode){
                    if(line.startsWith("\t") || line.startsWith(" ")){
                        loadValue(line, currentCategory, builder);
                    } else {
                        currentCategory = "";
                        categoryMode = false;

                        if(!line.isEmpty()){
                            loadValue(line, null, builder);
                        }
                    }
                } else {
                    loadValue(line, null, builder);
                }

                line = reader.readLine();
                currentLine++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        configMap = builder.build();

        //System.out.println("time: " + (System.currentTimeMillis() - time) + "ms");
    }

    private void loadValue(String line, String category, TinyMapBuilder<String, Object> builder){
        try {
            if(line.isEmpty()) return;

            // TODO parse array + map

            String[] lineSplit = line.split(":");
            String key = lineSplit[0].replaceAll("\t", "").replaceAll(" ", "");
            String value = lineSplit[1];
            String modifiedValue = (value.startsWith(" ") ? value.replaceFirst(" ", "") : value);

            if(line.contains("\"")){
                modifiedValue = line.split("\"")[1].replaceAll("\"", "");
            }

            if(category != null){
                builder.put(category + "." + key, modifiedValue);
            } else {
                builder.put(key, modifiedValue);
            }
        } catch (Exception e){
            throw new EConfigSyntaxException("Error on line " + currentLine);
        }
    }

}
