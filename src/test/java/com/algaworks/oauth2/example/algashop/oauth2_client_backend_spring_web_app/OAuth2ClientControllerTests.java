package com.algaworks.oauth2.example.algashop.oauth2_client_backend_spring_web_app;

import com.algaworks.oauth2.example.algashop.oauth2_client_backend_spring_web_app.web.OAuth2ClientController;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OAuth2ClientControllerTests {

    @Test
    void shouldRenderHomeView() {
        OAuth2ClientController controller = new OAuth2ClientController();
        Model model = new ConcurrentModel();

        String viewName = controller.home(
            model,
            new UsernamePasswordAuthenticationToken("maria", "n/a", List.of())
        );

        assertEquals("home", viewName);
        assertEquals(true, model.getAttribute("authenticated"));
        assertEquals("maria", model.getAttribute("username"));
    }

    @Test
    void shouldRenderProtectedViewWithModelData() {
        OAuth2ClientController controller = new OAuth2ClientController();
        Model model = new ConcurrentModel();
        DefaultOidcUser oidcUser = new DefaultOidcUser(
            List.of(),
            new OidcIdToken(
                "dummy-id-token",
                Instant.now(),
                Instant.now().plusSeconds(300),
                Map.of(IdTokenClaimNames.SUB, "maria","fullname", "Maria")
            ),
            new OidcUserInfo(Map.of(IdTokenClaimNames.SUB, "maria", "fullname", "Maria"))
        );

        String viewName = controller.protectedPage(
            model,
            oidcUser,
            TestAuthorizedClients.webClientWithScopes("orders:read", "openid")
        );

        assertEquals("protected-resource", viewName);
        assertEquals("Maria", model.getAttribute("username"));
        assertEquals("dummy-id-token", model.getAttribute("idToken"));
        assertEquals("dummy-token", model.getAttribute("accessToken"));
        assertTrue(((Set<?>) model.getAttribute("scopes")).contains("orders:read"));
    }
}
