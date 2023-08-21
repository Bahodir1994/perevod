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
public class ApplicationParam {
    private List<Map<String, List<StatusJsons>>> cardTypes;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatusJsons {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
