package com.example.server_manager_application;

import com.example.server_manager_application.enumeration.Status;
import com.example.server_manager_application.model.Server;
import com.example.server_manager_application.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepo serverRepo) {

        return args -> {
            serverRepo.save(new Server(null, "192.168.56.1", "Ubunto Linux", "16 GB",
                    "Personal PC", "http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
            serverRepo.save(new Server(null, "192.168.0.5", "Fedora Linux", "16 GB",
                    "Personal PC", "http://localhost:8080/server/image/server2.png", Status.SERVER_DOWN));
            serverRepo.save(new Server(null, "192.168.0.1", "MS 2008", "32 GB",
                    "Personal PC", "http://localhost:8080/server/image/server3.png", Status.SERVER_UP));
            serverRepo.save(new Server(null, "192.168.1.14", "Red Hat Enterprise Linux", "64 GB",
                    "Personal PC", "http://localhost:8080/server/image/server4.png", Status.SERVER_DOWN));
        };
    }

}
