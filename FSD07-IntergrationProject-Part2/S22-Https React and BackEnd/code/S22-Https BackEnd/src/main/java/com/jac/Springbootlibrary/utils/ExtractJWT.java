package com.jac.Springbootlibrary.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {
    public static String payloadJWTExtraction(String token, String extraction) {
        // remove the "Bearer" in the beginning of the JWT token
        token.replace("Bearer ", "");
        // jWT token includes three parts: header, payload and verify signature, which are split by "."
        // we use split() to get the three elements and save them into array chunk.
        String[] chunks = token.split("\\.");
        //decode the token into the info we can understand
        Base64.Decoder decoder = Base64.getUrlDecoder();
        //get the second element of the decoded token
        String payload = new String(decoder.decode(chunks[1]));
        // use split() to get each entry of  payload and save them into array entries
        String[] entries = payload.split(",");
        Map<String, String> map = new HashMap<String, String>();
        // loop in the array entries,
        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue[0].equals(extraction)) {
                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;}
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);
                map.put(keyValue[0], keyValue[1]);}
        }
        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }
        return null;
    }
}
