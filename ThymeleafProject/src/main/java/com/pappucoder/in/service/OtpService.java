package com.pappucoder.in.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public String generateOtp(String username) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        otpStorage.put(username, otp);
        return otp;
    }

    public boolean validateOtp(String username, String otp) {
        return otp.equals(otpStorage.get(username));
    }

    public void clearOtp(String username) {
        otpStorage.remove(username);
    }
}
