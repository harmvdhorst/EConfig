package nl.harmvdhorst.econfig.dotenv;

import net.intelie.tinymap.TinyMap;
import net.intelie.tinymap.TinyMapBuilder;

import java.io.*;

public class DotEnv {

    private TinyMap<String, String> configMap;

    public DotEnv(File file){
        if(file.getName().equals(".dotenv") || file.getName().equals(".env")){
            load(file);
        }
    }

    public String get(String key){
        return configMap.get(key);
    }

    private void load(File file){

        TinyMapBuilder<String, String> builder = TinyMap.builder();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null){

                if(line.startsWith("#") || line.isEmpty()) continue;

                String key;
                String content;

                key = line.split("=")[0].replaceAll(" ", "");

                if(line.contains("\"")){
                    content = line.split("\"")[1].replaceAll("\"", "");
                } else if(line.contains("'")){
                    content = line.split("'")[1].replaceAll("'", "");
                } else {
                    content = line.split("=")[1];
                }

                builder.put(key, content);

                line = reader.readLine();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        configMap = builder.build();

    }

}
