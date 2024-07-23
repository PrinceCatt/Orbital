package org.example.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {
    //Expire in 7 days
    private static long expire = 604800;
    //32 letters long secret key
    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghiabcdfghi";

    //Generate token
    public static String generateToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //Analyse token
    public static Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //Verify token by token
    public static boolean validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            // verifying token
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Verify token by request
    public static boolean validateToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("X-Token");
            if (StringUtils.isBlank(token)) {
                return false;
            }
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}




















