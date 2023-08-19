//package uz.perevods.perevod.configurations.connection;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.Value;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.Map;
//
//@Repository
//public class DocumentClient {
//    private String baseUrl = "https://2512cc46-7cbd-4884-937c-c5eb5871f1c5-us-east1.apps.astra.datastax.com/api/rest/v2/namespaces/perevods";
//
//    private String token = "AstraCS:fcCgdfIwsuUnJiNAwfwzizCE:c2c4f4e6459447415643c9e193dc0fd486efe6a724c4d2c568e772b73a7295c9";
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private RestTemplate restTemplate;
//
//    public DocumentClient() {
//        this.restTemplate = new RestTemplate();
//        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//    }
//
//    public <T> T getDocument(String collection, String id, Class<T> cls) {
//        var uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
//                .pathSegment("collections", collection, id)
//                .build()
//                .toUri();
//        var request = RequestEntity.get(uri)
//                .header("X-Cassandra-Token", token)
//                .build();
//        var response = restTemplate.exchange(request, cls);
//        return response.getBody();
//    }
//
//    public void patchSubDocument(String collection, String id, String key, Map<String, Object> updates) {
//        var updateUri = UriComponentsBuilder.fromHttpUrl(baseUrl)
//                .pathSegment("collections", collection, id, key)
//                .build()
//                .toUri();
//        var updateRequest = RequestEntity.patch(updateUri)
//                .header("X-Cassandra-Token", token)
//                .body(updates);
//        restTemplate.exchange(updateRequest, Map.class);
//    }
//}
