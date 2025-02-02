package es.uniovi.avib.morphing.projections.backend.configuration;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import es.uniovi.avib.morphing.projections.backend.domain.StompPrincipal;

public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, 
    								  WebSocketHandler wsHandler,
    								  Map<String, Object> attributes) {
        return new StompPrincipal(UUID.randomUUID().toString());
    }
}
