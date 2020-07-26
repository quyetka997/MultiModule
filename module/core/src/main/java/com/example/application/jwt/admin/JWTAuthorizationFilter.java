package com.example.application.jwt.admin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.application.exception.ErrorResponse;
import com.example.application.service.LoginAdminDetailsService;
import com.example.application.service.LoginUserDetailsService;
import com.example.application.utils.JsonUtil;
import com.example.domain.primary.dto.admin.AdminInformation;
import com.example.domain.primary.dto.user.UserInformation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.application.utils.SecurityConstants.*;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private LoginAdminDetailsService loginAdminDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, LoginAdminDetailsService loginAdminDetailsService) {
        super(authManager);
        this.loginAdminDetailsService = loginAdminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException{

        System.out.println("doFilterInternal");
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req, res);

        if(authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException {

        System.out.println("getAuthentication");
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String userName = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (userName != null) {
                UserDetails newUser = loginAdminDetailsService.loadUserByUsername(userName);

                AdminInformation adminInformation = (AdminInformation) newUser;
                if(adminInformation.isDeleted()) {
                    ErrorResponse errorResponse = new ErrorResponse("Admin","Admin was delete");
                    tranferErrorResponse(response,errorResponse);
                    return null;
                }

                return new UsernamePasswordAuthenticationToken(userName, null, newUser.getAuthorities());
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