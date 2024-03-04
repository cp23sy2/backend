package com.example.backend2.services;

import com.example.backend2.Security.CustomUserDetailsService;
import com.example.backend2.Security.JwtAuthenticationFilter;
import com.example.backend2.Entity.Role;
import com.example.backend2.Entity.User;
import com.example.backend2.Repository.UserRepository;
import com.example.backend2.Security.JwtUtil;
import com.example.backend2.models.AuthenticationResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.Key;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.ldap.query.LdapQueryBuilder.query;


@Service
public class LdapAuthenticationService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final ThreadLocal<String> userRole = new ThreadLocal<>();

//    private static final Key SECRET_KEY = JwtAuthenticationFilter.SECRET_KEY;

    public static String getUserRole() {
        return userRole.get();
    }

    public ResponseEntity<AuthenticationResponse> Authenticate(String username, String password) {
        trustSelfSignedSSL();

        try {
            String userDn = "(uid=" + username + ")";
            String baseDn = "";

            if (username.startsWith("std")) {
                baseDn = "ou=People,ou=st,dc=sit,dc=kmutt,dc=ac,dc=th";
            } else if (username.startsWith("stf")) {
                baseDn = "ou=People,ou=staff,dc=sit,dc=kmutt,dc=ac,dc=th";
            }

            boolean isAuthenticated = ldapTemplate.authenticate(baseDn, userDn, password);

            if (isAuthenticated) {
                LdapQuery query = query().base(baseDn).filter(userDn);
                DirContextOperations userContext = ldapTemplate.searchForContext(query);

                Attributes attributes = userContext.getAttributes();
                Map<String, Object> attributeMap = convertAttributesToMap(attributes);

                User existingUser = userRepository.findByUserName(username);
                String email = userContext.getStringAttribute("mail");
                String role = userContext.getStringAttribute("radiusGroupName");

                if (existingUser == null) {
                    User newUser = new User();
                    newUser.setUserName(username);
                    newUser.setUserEmail(email);

                    if ("staff_group".equals(role)) {
                        newUser.setRole(Role.staff_group);
                    } else if ("st_group".equals(role)) {
                        newUser.setRole(Role.st_group);
                    }

                    userRepository.save(newUser);
                }

                System.out.println("Role from LDAP: " + role);

                AuthenticationResponse response = new AuthenticationResponse("Login with LDAP Success");
                response.setAttributes(attributeMap);


                String accessToken = jwtUtil.generateAccessToken(email, role);
                String refreshToken = jwtUtil.generateRefreshToken(email, role);

                response.setJwtToken(accessToken);
                response.setRefreshToken(refreshToken);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace for debugging purposes

            String errorMessage = "Login with LDAP Fail. Error: " + e.getMessage();
            AuthenticationResponse response = new AuthenticationResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    private Map<String, Object> convertAttributesToMap(Attributes attributes) {
        Map<String, Object> attributeMap = new HashMap<>();

        try {
            NamingEnumeration<? extends Attribute> attributeEnum = attributes.getAll();
            while (attributeEnum.hasMore()) {
                Attribute attribute = attributeEnum.next();
                String attributeName = attribute.getID();

                attributeMap.put(attributeName, getAttributeValues(attribute));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (javax.naming.NamingException e) {
            throw new RuntimeException(e);
        }

        return attributeMap;
    }

    private List<Object> getAttributeValues(Attribute attribute) throws NamingException, javax.naming.NamingException {
        List<Object> values = new ArrayList<>();
        NamingEnumeration<?> valueEnum = attribute.getAll();

        while (valueEnum.hasMore()) {
            Object value = valueEnum.next();
            values.add(value);
        }

        return values;
    }

    public static void trustSelfSignedSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

