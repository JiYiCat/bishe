package com.yushilong.bishe.security;

public class UserContext {

    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_ROLE_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_EMAIL_HOLDER = new ThreadLocal<>();

    public static void set(Long userId, String role, String email) {
        USER_ID_HOLDER.set(userId);
        USER_ROLE_HOLDER.set(role);
        USER_EMAIL_HOLDER.set(email);
    }

    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    public static String getRole() {
        return USER_ROLE_HOLDER.get();
    }

    public static String getEmail() {
        return USER_EMAIL_HOLDER.get();
    }

    public static void clear() {
        USER_ID_HOLDER.remove();
        USER_ROLE_HOLDER.remove();
        USER_EMAIL_HOLDER.remove();
    }
}