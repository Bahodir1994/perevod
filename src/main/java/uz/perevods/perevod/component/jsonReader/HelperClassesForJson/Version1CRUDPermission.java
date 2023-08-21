package uz.perevods.perevod.component.jsonReader.HelperClassesForJson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version1CRUDPermission {
    private Integer[] create;
    private Integer[] read;
    private Integer[] update;
    private Integer[] delete;
}
