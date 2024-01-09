package br.com.erudio.mockito.static_with_params;

public class MyUtils {

    public static String getWelcomeMessage(String username, boolean isCostumer) {
        if (isCostumer) return "Dear " + username;
        return "Hello " + username;
    }
}
