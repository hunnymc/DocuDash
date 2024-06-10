package doc.backendapi.notification;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String userId = null;

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            userId = servletRequest.getParameter("userId");

            if (userId == null) {
                LOG.error("User ID is missing");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is missing in the request parameters for websocket connection" +
                        "Please provide the user ID as a query parameter in the URL. Example: /api/kw2-websocket?userId=1");
            } else {
                LOG.info("User ID : " + userId + " " + "is connect to websocket");
            }
        }

        assert userId != null;
        return new UserPrincipal(userId);
    }
}