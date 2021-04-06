package br.com.sgm.api.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain filterChain)
            throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer" + " ")) {
            token = token.replace("Bearer" + " ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("secret-sgm".getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();

                List<LinkedHashMap<String, String>> authorities = claims.get("auth", List.class);
                if (username != null) {
                    List<SimpleGrantedAuthority> roles  = getRoles(authorities);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, roles);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(req, rsp);
    }

    private List<SimpleGrantedAuthority>  getRoles(List<LinkedHashMap<String, String>> authorities) {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        authorities.forEach(auth ->{
            auth.entrySet().forEach(i -> roles.add(new SimpleGrantedAuthority(i.getValue())));
        });
        return roles;
    }
}
