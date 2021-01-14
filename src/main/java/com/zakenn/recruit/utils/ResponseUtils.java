package com.zakenn.recruit.utils;

import java.util.List;
import java.util.Map;

public final class ResponseUtils {
    private static Map<String, String> rejectionResponse = Map.of(
                   "message", "We're sorry to inform you that your recent application has been rejected. We have stored your information in our data systems and will contact you if in the future a potential matching application is posted on our website.");
    private static Map<String, String> approvalResponse = Map.of(
                  "message", " Your recent application has been approved, Welcome to our team !");
    public static Map<String, Map<String, String>> defaultResponseData = Map.of("approval", approvalResponse, "rejection", rejectionResponse);
}
