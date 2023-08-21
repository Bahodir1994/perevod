package uz.perevods.perevod.component.jsonReader.HelperClassesForJson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserParam {
    private List<Map<String, List<LocationJsons>>> roles;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocationJsons {
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        // Constructor, getters, and setters
    }
}
