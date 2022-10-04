package j2kb.ponicon.scrap.utils;

public interface IJwtService {

    public String createAccessToken(String username);
    public String createRefreshToken(String username);

}
