package com.bmw.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class TokenUtils2 {
	public static long EXPIRY_TIME = 1000*60*30;//过期时间30分钟
    public static Map<String,Object> tokenMap = new HashMap<>();

    /**
     * 用userId添加token
     */
    public static void putTokenMap(String userId){
        Map<String,Object> tokenInfo = new HashMap<>();
        tokenInfo.put("token",UUID.randomUUID().toString());
        tokenInfo.put("expiryTime",System.currentTimeMillis()+EXPIRY_TIME);
        tokenMap.put(userId,tokenInfo);
    }

    /**
     * 删除过期token
     */
    public static void deleteExpiryToken(){
        Iterator<Map.Entry<String, Object>> iterator = tokenMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            Map<String,Object> tokenInfo = (Map<String,Object>)next.getValue();
            if ((long)tokenInfo.get("expiryTime")<System.currentTimeMillis()){
                iterator.remove();
            }
        }
    }

 	/**
     * 验证token(有userId)
     */
    public static Boolean checkToken(String token,String userId){
        //调用时遍历，删除已过期的token
        deleteExpiryToken();

        Iterator<Map.Entry<String, Object>> iterator = tokenMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            Map<String,Object> tokenInfo = (Map<String,Object>)next.getValue();
            if (tokenInfo.get("token").equals(token)&&next.getKey().equals(userId)){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证token(无userId)
     */
    public static Boolean checkToken(String token){
        //调用时遍历，删除已过期的token
        deleteExpiryToken();

        Iterator<Map.Entry<String, Object>> iterator = tokenMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            Map<String,Object> tokenInfo = (Map<String,Object>)next.getValue();
            if (tokenInfo.get("token").equals(token)){
                return true;
            }
        }
        return false;
    }
}

//@RequestMapping("/getToken")
//public Map<String, Object> getToken(String userId) {
//	TokenUtils.putTokenMap(userId);
//	return (Map<String, Object>)TokenUtils.tokenMap.get(userId);
//}
//
//@RequestMapping("/checkToken")
//public Boolean checkToken(String token) {
//	return TokenUtils.checkToken(token);
//}

