package uz.perevods.perevod.configurations.asycConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Установка начального размера пула потоков
        executor.setMaxPoolSize(20); // Установка максимального размера пула потоков
        executor.setQueueCapacity(100); // Установка размера очереди задач
        executor.setThreadNamePrefix("AsyncTask-"); // Префикс имени потока
        executor.initialize();
        return executor;
    }

}
