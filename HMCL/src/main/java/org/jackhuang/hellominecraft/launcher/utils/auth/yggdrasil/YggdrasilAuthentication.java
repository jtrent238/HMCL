package org.jackhuang.hellominecraft.launcher.utils.auth.yggdrasil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jackhuang.hellominecraft.C;
import org.jackhuang.hellominecraft.HMCLog;
import org.jackhuang.hellominecraft.utils.NetUtils;
import org.jackhuang.hellominecraft.utils.StrUtils;
import org.jackhuang.hellominecraft.launcher.utils.auth.yggdrasil.properties.PropertyMap;
import org.jackhuang.hellominecraft.launcher.utils.auth.yggdrasil.request.AuthenticationRequest;
import org.jackhuang.hellominecraft.launcher.utils.auth.yggdrasil.request.RefreshRequest;
import org.jackhuang.hellominecraft.launcher.utils.auth.yggdrasil.response.Response;
import org.jackhuang.hellominecraft.launcher.utils.auth.yggdrasil.response.User;

public class YggdrasilAuthentication {
    
    public static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(GameProfile.class, new GameProfile.GameProfileSerializer())
        .registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer())
        .registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    
    protected static final String BASE_URL = "https://authserver.mojang.com/";
    protected static final URL ROUTE_AUTHENTICATE = NetUtils.constantURL(BASE_URL + "authenticate");
    protected static final URL ROUTE_REFRESH = NetUtils.constantURL(BASE_URL + "refresh");
    protected static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";

    protected static final String STORAGE_KEY_PROFILE_NAME = "displayName";
    protected static final String STORAGE_KEY_PROFILE_ID = "uuid";
    protected static final String STORAGE_KEY_PROFILE_PROPERTIES = "profileProperties";
    protected static final String STORAGE_KEY_USER_NAME = "username";
    protected static final String STORAGE_KEY_USER_ID = "userid";
    protected static final String STORAGE_KEY_USER_PROPERTIES = "userProperties";
    
    private final Proxy proxy;
    private final String clientToken;
    
    private final PropertyMap userProperties = new PropertyMap();
    private String userid;
    private String username;
    private String password;
    private GameProfile selectedProfile;

    private GameProfile[] profiles;
    private String accessToken;
    private boolean isOnline;

    public YggdrasilAuthentication(Proxy proxy, String clientToken) {
        this.proxy = proxy;
        this.clientToken = clientToken;
    }

    public void setUsername(String username) {
        if ((isLoggedIn()) && (canPlayOnline()))
            throw new IllegalStateException("Cannot change username whilst logged in & online");

        this.username = username;
    }

    public void setPassword(String password) {
        if ((isLoggedIn()) && (canPlayOnline()) && (StrUtils.isNotBlank(password)))
            throw new IllegalStateException("Cannot set password whilst logged in & online");

        this.password = password;
    }

    protected String getUsername() {
        return this.username;
    }

    protected String getPassword() {
        return this.password;
    }

    protected void setSelectedProfile(GameProfile selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    public GameProfile getSelectedProfile() {
        return this.selectedProfile;
    }

    public String getUserID() {
        return this.userid;
    }

    public PropertyMap getUserProperties() {
        if (isLoggedIn()) {
            PropertyMap result = new PropertyMap();
            result.putAll(getModifiableUserProperties());
            return result;
        }
        return new PropertyMap();
    }

    protected PropertyMap getModifiableUserProperties() {
        return this.userProperties;
    }

    protected void setUserid(String userid) {
        this.userid = userid;
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    protected Response makeRequest(URL url, Object input) throws AuthenticationException {
        try {
            String jsonResult = input == null ? NetUtils.doGet(url) : NetUtils.post(url, GSON.toJson(input), "application/json", proxy);
            Response result = (Response) GSON.fromJson(jsonResult, Response.class);

            if (result == null)
                return null;

            if (StrUtils.isNotBlank(result.error))
                throw new AuthenticationException("InvalidCredentials " + result.errorMessage);

            return result;
        } catch (IOException | IllegalStateException | JsonParseException e) {
            throw new AuthenticationException(C.i18n("login.failed.connect_authentication_server"), e);
        }
    }

    public String getClientToken() {
        return this.clientToken;
    }

    public boolean canLogIn() {
        return (!canPlayOnline()) && (StrUtils.isNotBlank(getUsername())) && ((StrUtils.isNotBlank(getPassword())) || (StrUtils.isNotBlank(getAuthenticatedToken())));
    }

    public void logIn() throws AuthenticationException {
        if (StrUtils.isBlank(getUsername()))
            throw new AuthenticationException(C.i18n("login.invalid_username"));

        if (StrUtils.isNotBlank(getAuthenticatedToken()))
            logInWithToken();
        else if (StrUtils.isNotBlank(getPassword()))
            logInWithPassword();
        else
            throw new AuthenticationException(C.i18n("login.invalid_password"));
    }

    protected void logInWithPassword() throws AuthenticationException {
        if (StrUtils.isBlank(getUsername()))
            throw new AuthenticationException(C.i18n("login.invalid_username"));
        if (StrUtils.isBlank(getPassword()))
            throw new AuthenticationException(C.i18n("login.invalid_password"));

        HMCLog.log("Logging in with username & password");

        AuthenticationRequest request = new AuthenticationRequest(clientToken, getUsername(), getPassword());
        Response response = makeRequest(ROUTE_AUTHENTICATE, request);

        if (!response.clientToken.equals(clientToken))
            throw new AuthenticationException(C.i18n("login.changed_client_token"));

        User user = response.user;

        setUserid(user != null && user.id != null ? user.id : getUsername());

        this.isOnline = true;
        this.accessToken = response.accessToken;

        this.profiles = response.availableProfiles;
        setSelectedProfile(response.selectedProfile);
        getModifiableUserProperties().clear();

        updateUserProperties(user);
    }

    protected void updateUserProperties(User user) {
        if (user == null)
            return;

        if (user.properties != null)
            getModifiableUserProperties().putAll(user.properties);
    }

    protected void logInWithToken() throws AuthenticationException {
        if (StrUtils.isBlank(getUserID()))
            if (StrUtils.isBlank(getUsername()))
                setUserid(getUsername());
            else
                throw new AuthenticationException(C.i18n("login.invalid_uuid_and_username"));
        if (StrUtils.isBlank(getAuthenticatedToken()))
            throw new AuthenticationException(C.i18n("login.invalid_access_token"));

        HMCLog.log("Logging in with access token");

        RefreshRequest request = new RefreshRequest(this);
        Response response = makeRequest(ROUTE_REFRESH, request);

        if (!response.clientToken.equals(clientToken))
            throw new AuthenticationException(C.i18n("login.changed_client_token"));

        setUserid(response.user != null && response.user.id != null ? response.user.id : getUsername());

        this.isOnline = true;
        this.accessToken = response.accessToken;
        this.profiles = response.availableProfiles;
        setSelectedProfile(response.selectedProfile);
        getModifiableUserProperties().clear();

        updateUserProperties(response.user);
    }

    public void logOut() {
        this.password = null;
        this.userid = null;
        setSelectedProfile(null);
        getModifiableUserProperties().clear();

        this.accessToken = null;
        this.profiles = null;
        this.isOnline = false;
    }

    public GameProfile[] getAvailableProfiles() {
        return this.profiles;
    }

    public boolean isLoggedIn() {
        return StrUtils.isNotBlank(this.accessToken);
    }

    public boolean canPlayOnline() {
        return isLoggedIn() && getSelectedProfile() != null && this.isOnline;
    }

    public void loadFromStorage(Map<String, Object> credentials) {
        logOut();

        setUsername((String) credentials.get(STORAGE_KEY_USER_NAME));

        if (credentials.containsKey(STORAGE_KEY_USER_ID))
            this.userid = (String) credentials.get(STORAGE_KEY_USER_ID);
        else
            this.userid = this.username;

        if (credentials.containsKey(STORAGE_KEY_USER_PROPERTIES))
            getModifiableUserProperties().fromList((List<Map<String, String>>) credentials.get(STORAGE_KEY_USER_PROPERTIES));

        if ((credentials.containsKey(STORAGE_KEY_PROFILE_NAME)) && (credentials.containsKey(STORAGE_KEY_PROFILE_ID))) {
            GameProfile profile = new GameProfile(UUIDTypeAdapter.fromString((String) credentials.get(STORAGE_KEY_PROFILE_ID)), (String) credentials.get(STORAGE_KEY_PROFILE_NAME));
            if (credentials.containsKey(STORAGE_KEY_PROFILE_PROPERTIES))
                profile.properties.fromList((List<Map<String, String>>) credentials.get(STORAGE_KEY_PROFILE_PROPERTIES));
            setSelectedProfile(profile);
        }

        this.accessToken = (String) credentials.get(STORAGE_KEY_ACCESS_TOKEN);
    }

    public Map<String, Object> saveForStorage() {
        Map<String, Object> result = new HashMap<>();

        if (getUsername() != null)
            result.put(STORAGE_KEY_USER_NAME, getUsername());
        if (getUserID() != null)
            result.put(STORAGE_KEY_USER_ID, getUserID());

        if (!getUserProperties().isEmpty())
            result.put(STORAGE_KEY_USER_PROPERTIES, getUserProperties().list());

        GameProfile sel = getSelectedProfile();
        if (sel != null) {
            result.put(STORAGE_KEY_PROFILE_NAME, sel.name);
            result.put(STORAGE_KEY_PROFILE_ID, sel.id);
            if (!sel.properties.isEmpty())
                result.put(STORAGE_KEY_PROFILE_PROPERTIES, sel.properties.list());
        }

        if (StrUtils.isNotBlank(getAuthenticatedToken()))
            result.put(STORAGE_KEY_ACCESS_TOKEN, getAuthenticatedToken());

        return result;
    }

    @Deprecated
    public String getSessionToken() {
        if (isLoggedIn() && getSelectedProfile() != null && canPlayOnline())
            return String.format("token:%s:%s", new Object[]{getAuthenticatedToken(), getSelectedProfile().id});
        return null;
    }

    public String getAuthenticatedToken() {
        return this.accessToken;
    }
}
