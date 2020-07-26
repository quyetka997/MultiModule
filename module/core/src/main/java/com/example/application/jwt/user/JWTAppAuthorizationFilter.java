package com.example.application.jwt.user;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.example.application.exception.ErrorResponse;
import com.example.application.service.LoginUserDetailsService;
import com.example.application.utils.JsonUtil;
import com.example.domain.primary.dto.user.UserInformation;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.application.utils.SecurityConstants.*;


public class JWTAppAuthorizationFilter extends BasicAuthenticationFilter {

    private LoginUserDetailsService userDetailsService;

    public JWTAppAuthorizationFilter(AuthenticationManager authManager, LoginUserDetailsService userDetailsService) {
        super(authManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        System.out.println("doFilterInternal");
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req, res);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("getAuthentication");
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
            String userName = jwt.getSubject();
            Claim claim = jwt.getClaim("AUTH");

            if (userName != null) {

                UserDetails newUser = userDetailsService.loadUserByUsername(userName);

                UserInformation userInformation = (UserInformation) newUser;

                if(!userInformation.isApproved()) {
                    ErrorResponse errorResponse = new ErrorResponse("Permission","User not approval");
                    tranferErrorResponse(response,errorResponse);
                    return null;
                }
                if (userInformation.isDeleted()) {
                    ErrorResponse errorResponse = new ErrorResponse("User", "User was delete");
                    tranferErrorResponse(response, errorResponse);
                    return null;
                }
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority((claim.asString())));

                return new UsernamePasswordAuthenticationToken(userName, null, grantedAuthorities);
            }
            return null;
        }
        return null;
    }

    public void tranferErrorResponse(HttpServletResponse res, ErrorResponse errorResponse) throws IOException {
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        String ch = JsonUtil.toJson(errorResponse);
        res.getWriter().write(ch);
    }


}