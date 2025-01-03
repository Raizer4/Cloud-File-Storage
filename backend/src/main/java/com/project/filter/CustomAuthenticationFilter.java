package com.project.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.RegisterDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterDto loginRequest = objectMapper.readValue(request.getInputStream(), RegisterDto.class);


            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    );


            Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);


            HttpSession session = request.getSession(true);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return authResult;
        } catch (IOException e) {
            throw new AuthenticationServiceException("Ошибка при чтении JSON-запроса", e);
        }
    }

}
