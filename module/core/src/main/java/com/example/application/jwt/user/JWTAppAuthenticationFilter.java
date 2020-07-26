package com.example.application.jwt.user;


import com.auth0.jwt.JWT;

import com.example.application.exception.AppLogicException;
import com.example.application.exception.CustomException;
import com.example.application.exception.ErrorResponse;
import com.example.application.service.LoginUserDetailsService;
import com.example.application.utils.JsonUtil;
import com.example.domain.primary.dto.UserDTO;
import com.example.domain.primary.dto.user.UserInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zalando.problem.Status;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.application.utils.SecurityConstants.*;


public class JWTAppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private LoginUserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String CURRENT_ROLE;


    public JWTAppAuthenticationFilter(AuthenticationManager authenticationManager, LoginUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/user/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            System.out.println("attemptAuthentication");
            UserDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDTO.class);

            CURRENT_ROLE = ROLE + creds.roleName;

            UserDetails userDetails = userDetailsService.loadUserByUsername(creds.userName);

            UserInformation userInformation = (UserInformation) userDetails;

            if(!userInformation.isApproved()) {
                ErrorResponse errorResponse = new ErrorResponse("Approval","User not approval");
                tranferErrorResponse(res,errorResponse);
                return null;
            }
            if (userInformation.isDeleted()) {
                ErrorResponse errorResponse = new ErrorResponse("User", "User was delete");
                tranferErrorResponse(res, errorResponse);
                return null;
            }
            if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(CURRENT_ROLE))) {
                ErrorResponse errorResponse = new ErrorResponse("Permission","User not have permission");
                tranferErrorResponse(res,errorResponse);
                return null;
            }

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails, creds.passWord, Collections.emptyList()));

            //return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.userName, creds.passWord));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        System.out.println("successfulAuthentication");
        String token = JWT.create()
                .withSubject(auth.getName())
                .withClaim("AUTH",CURRENT_ROLE)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);


        UserInformation userInformation = ((UserInformation) auth.getPrincipal());

        UserDTO userDTO = new UserDTO();
        userDTO.userName = userInformation.getUserName();
        userDTO.fullName = userInformation.getFullName();

        res.setContentType("application/json");
        res.getWriter().println(JsonUtil.toJson(userDTO));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().println(EXCEPTION_ACCOUNT);
    }

    public void tranferErrorResponse(HttpServletResponse res, ErrorResponse errorResponse) throws IOException {
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        String ch = JsonUtil.toJson(errorResponse);
        res.getWriter().write(ch);
    }
}