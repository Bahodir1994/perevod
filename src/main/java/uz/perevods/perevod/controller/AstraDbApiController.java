package uz.perevods.perevod.controller;

import com.datastax.astra.sdk.AstraClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AstraDbApiController {

    @Autowired
    private AstraClient astraClient;

    @GetMapping("/ping")
    public String ping() {
        return astraClient.apiDevopsOrganizations()
                .organizationId();
    }

}