//package uz.perevods.perevod.configurations.connection;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.nio.file.Paths;
//
//@Configuration
//public class DataStaxConfig {
//
//    @Value("${astra.api.application-token}")
//    private String astraApplicationToken;
//
//    @Value("${astra.api.database-id}")
//    private String astraDatabaseId;
//
//    @Value("${astra.api.database-region}")
//    private String astraDatabaseRegion;
//
//    @Value("${astra.api.keyspace}")
//    private String keyspace;
//
//    @Bean
//    public CqlSession cqlSession() {
//        return CqlSession.builder()
//                .withCloudSecureConnectBundle(Paths.get(astraDatabaseId + "-" + astraDatabaseRegion + ".zip"))
//                .withAuthCredentials("token", astraApplicationToken) // Use token-based authentication
//                .withKeyspace(keyspace) // Set the keyspace
//                .build();
//    }
//}
//
