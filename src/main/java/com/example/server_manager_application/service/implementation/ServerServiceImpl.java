package com.example.server_manager_application.service.implementation;

import com.example.server_manager_application.enumeration.Status;
import com.example.server_manager_application.model.Server;
import com.example.server_manager_application.repo.ServerRepo;
import com.example.server_manager_application.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {

        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());

        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {

        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {

        log.info("Fetching al servers");
        return serverRepo.findAll(of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {

        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {

        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {

        log.info("Deleting server by id: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {

        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" +
                imageNames[new Random().nextInt(4)]).toUriString();
    }


}
