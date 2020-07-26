package com.example.application.jwt.admin;

import com.auth0.jwt.JWT;

import com.example.application.service.LoginAdminDetailsService;
import com.example.application.service.LoginUserDetailsService;
import com.example.application.utils.JsonUtil;
import com.example.domain.primary.dto.AdminDTO;
import com.example.domain.primary.dto.UserDTO;
import com.example.domain.primary.dto.admin.AdminInformation;
import com.example.domain.primary.dto.user.UserInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.application.utils.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private LoginAdminDetailsService loginAdminDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, LoginAdminDetailsService loginAdminDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/admin/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.loginAdminDetailsService = loginAdminDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            System.out.println("attemptAuthentication");
            UserDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDTO.class);

            UserDetails userDetails = loginAdminDetailsService.loadUserByUsername(creds.userName);
//            if(!bCryptPasswordEncoder.matches(creds.passWord, userDetails.getPassword())) {
//                return null;
//            }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails, creds.passWord, new ArrayList<>()));

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
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);


        AdminInformation adminInformation = ((AdminInformation)auth.getPrincipal());

        AdminDTO adminDTO = new AdminDTO();
        adminDTO.userName = adminInformation.getUserName();
        adminDTO.fullName = adminInformation.getFullName();

        res.setContentType("application/json");
        res.getWriter().println(JsonUtil.toJson(adminDTO));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().println(EXCEPTION_ACCOUNT);
    }
}