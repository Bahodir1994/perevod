//package uz.perevods.perevod.configurations.connection;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.hibernate.jpa.HibernatePersistenceProvider;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.Objects;
//import java.util.Properties;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@Configuration
//@ConfigurationProperties("spring.datasource")
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class,
//        entityManagerFactoryRef = "entityManagerFactoryMain1",
//        transactionManagerRef = "transactionManagerMain1"
////        basePackages = {"uz.perevods.perevod.repository"}
//)
//public class DBConnectMain {
//    public static final String MODEL_PACKAGE = "uz.perevods.perevod.entity";
//    private static HikariDataSource hikariDataSource;
//
//    private static final Set<String> ALLOWED_IPS = Stream.of("192.168.224.224", "192.168.224.18", "192.168.224.127", "localhost")
//            .collect(Collectors.toSet());
//
//    private static final Properties ALLOWED_PROPS = new Properties() {{
//        put("minimumIdle", "5");
//        put("maximumPoolSize", "10");
//        put("idleTimeout", "500000");
//        put("maxLifetime", "600000");
//    }};
//
//    private static final Properties NON_ALLOWED_PROPS = new Properties() {{
//        put("minimumIdle", "10");
//        put("maximumPoolSize", "500");
//        put("idleTimeout", "600000");
//        put("maxLifetime", "3600000");
//    }};
//
//    protected final String PERSISTENCE_UNIT_NAME = "main1";
//    protected final Properties JPA_MAIN1 = new Properties() {{
//        put("database-platform", "org.hibernate.dialect.H2Dialect");
//        put("hibernate.hbm2ddl.auto", "update");
//        put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        put("show-sql", "true");
//        put("generate-ddl", "true");
//    }};
//
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//    }
//
//    @Bean
//    @Qualifier("main1")
//    public HikariDataSource main1() throws UnknownHostException, SocketException {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setAutoCommit(true);
//        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
//        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
//        hikariConfig.addDataSourceProperty("useUnicode", "true");
//        hikariConfig.setPoolName("main1");
//        hikariConfig.setDriverClassName("org.h2.Driver");
////        hikariConfig.setConnectionTestQuery("select current_timestamp cts from sysibm.sysdummy1");
//
//        String ip = getLocalIPAddress();
//
//        Properties poolProps = ALLOWED_IPS.contains(ip) ? ALLOWED_PROPS : NON_ALLOWED_PROPS;
//        poolProps.forEach((key, value) -> hikariConfig.addDataSourceProperty(key.toString(), value.toString()));
//
//        hikariConfig.setJdbcUrl("jdbc:h2:file:./src/main/resources/static/data/perevod");
//        hikariConfig.setUsername("user");
//        hikariConfig.setPassword("pas@1");
//        hikariConfig.setConnectionTimeout(30000);
//        hikariConfig.setValidationTimeout(5000);
//
//        hikariDataSource = new HikariDataSource(hikariConfig);
//        return hikariDataSource;
//    }
//
//    private String getLocalIPAddress() throws SocketException, UnknownHostException {
//        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//        while (interfaces.hasMoreElements()) {
//            NetworkInterface current = interfaces.nextElement();
//            if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
//                continue;
//            }
//
//            Enumeration<InetAddress> addresses = current.getInetAddresses();
//            while (addresses.hasMoreElements()) {
//                InetAddress currentAddr = addresses.nextElement();
//                if (currentAddr.isLoopbackAddress()) {
//                    continue;
//                }
//                if (currentAddr.isSiteLocalAddress()) {
//                    return currentAddr.getHostAddress();
//                }
//            }
//        }
//        return InetAddress.getLocalHost().getHostAddress();
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactoryMain1")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMain1(
//            final HikariDataSource main1) throws SQLException {
//        return new LocalContainerEntityManagerFactoryBean() {{
//            setDataSource(main1);
//            setPersistenceProviderClass(HibernatePersistenceProvider.class);
//            setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
//            setPackagesToScan(MODEL_PACKAGE);
//            setJpaProperties(JPA_MAIN1);
//        }};
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager transactionManagerMain1(
//            final @Qualifier("entityManagerFactoryMain1") LocalContainerEntityManagerFactoryBean entityManagerFactoryMain1) {
//        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryMain1.getObject()));
//    }
//}
