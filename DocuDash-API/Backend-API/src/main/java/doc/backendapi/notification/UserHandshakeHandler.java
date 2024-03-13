package doc.backendapi.notification;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;


public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        Cookie userIdCookie = null;

        // Extract the userId from the cookies by cookie name
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            userIdCookie = WebUtils.getCookie(servletRequest, "userId");
            if (userIdCookie != null) {
                System.out.println("userID is " + userIdCookie.getValue());
            } else {
                System.out.println("userID cookie not found");
            }
        }

        assert userIdCookie != null;
        LOG.info("User with ID '{}' opened the page", userIdCookie.getValue());

//        final String randomId = UUID.randomUUID().toString();
//        LOG.info("User with ID '{}' opened the page", randomId);

        return new UserPrincipal(userIdCookie.getValue());
    }
}