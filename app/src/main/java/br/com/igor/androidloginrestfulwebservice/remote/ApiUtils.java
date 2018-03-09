package br.com.igor.androidloginrestfulwebservice.remote;

/**
 * Created by igor on 07/03/2018.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://dev.people.com.ai/mobile/api/v2/";

    public static UserService getUserService(){
        return RetroFitClient.getClient(BASE_URL).create(UserService.class);
    }
}
