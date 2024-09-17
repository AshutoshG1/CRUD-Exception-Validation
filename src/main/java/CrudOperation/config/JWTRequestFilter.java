package CrudOperation.config;


import CrudOperation.entity.Registration;
import CrudOperation.repository.RegistrationRepository;
import CrudOperation.service.Impl.JWTService;
import javax.servlet.FilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private RegistrationRepository registrationRepository;

    public JWTRequestFilter(JWTService jwtService, RegistrationRepository registrationRepository) {
        this.jwtService = jwtService;
        this.registrationRepository = registrationRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8,tokenHeader.length()-1);

            String userName = jwtService.getUserName(token);
            Optional<Registration> opUser = registrationRepository.findByUsername(userName);
            if(opUser.isPresent()){
                Registration user = opUser.get();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }

        }

        filterChain.doFilter(request, response);
    }
}
