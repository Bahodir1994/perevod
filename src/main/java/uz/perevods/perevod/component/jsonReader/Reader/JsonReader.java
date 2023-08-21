package uz.perevods.perevod.component.jsonReader.Reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JsonReader {
    public <T> T readJsonFile(Class<T> clazz, T input, String dir) {
        try {
            // Read the JSON file as a string
            String jsonContent = new String(Files.readAllBytes(Paths.get(dir)));

            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Deserialize the JSON content into your target object(s)
            input = objectMapper.readValue(jsonContent, clazz);

            return input;
            // Process the deserialized objects as needed
            // ...
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
