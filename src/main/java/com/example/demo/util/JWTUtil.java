package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {
    private static String secret = "csdntonghuasdfni#2323!@seirninfsasdf";

    public static String getToken(Map<String, String> map, Integer day) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, day);
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    public static boolean verify(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
