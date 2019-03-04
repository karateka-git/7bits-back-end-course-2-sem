package it.sevenbits.homework.repository;

import java.util.HashMap;
import java.util.UUID;

public class CookieRepository {
    private static CookieRepository instance;
    private HashMap<UUID, String> cookie;

    private CookieRepository() {
        cookie = new HashMap<UUID, String>();
    }

    public static CookieRepository getInstance() {
        if (instance == null) {
            instance = new CookieRepository();
        }
        return instance;
    }

    public UUID addCookieUser(String name) {
        UUID id = UUID.randomUUID();
        cookie.put(id,name);
        return id;
    }

    public String getNameCookieUser(UUID id) throws NullPointerException {
        if (cookie.get(id).equals("null")) {
            throw new NullPointerException();
        } else {
            return cookie.get(id);
        }
    }
}
