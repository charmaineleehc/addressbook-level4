package seedu.address.commons;

import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.GetRedirectURLEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GmailAuthenticator {

    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    String clientId = "650819214900-b3m4dv6igjlf9q3nq9eqsbmspask57kp.apps.googleusercontent.com";
    String clientSecret = "ttunyBEmZMrK_a9MH_qc1kus";
    String redirectUri = "https://contacts.google.com";

    private static final String SCOPE_1 = "https://www.googleapis.com/auth/contacts.readonly";
    private static final String SCOPE_2 = "https://www.googleapis.com/auth/plus.login";
    private static final String SCOPE_3 = "https://www.googleapis.com/auth/user.phonenumbers.read";
    private static final String SCOPE_4 = "https://www.googleapis.com/auth/contacts";
    private static final String SCOPE_5 = "https://mail.google.com/";


    String authorizationUrl;


    //Constructor
    public GmailAuthenticator() throws IOException {
        InputStream in = GmailAuthenticator.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        this.authorizationUrl = new GoogleBrowserClientRequestUrl(clientSecrets, redirectUri,
                Arrays.asList(SCOPE_1, SCOPE_2, SCOPE_3, SCOPE_4, SCOPE_5)).build();
    }


    //Getter for Authorization URL for user login
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }



    //This method obtains the token from the redirect URL after successful login
    public String getToken() {
        String token = "";
        try {
            GetRedirectURLEvent event = new GetRedirectURLEvent();
            EventsCenter.getInstance().post(event);
            String URL = event.getReDirectURL();
            token = URL.substring(URL.indexOf("token=") + 6, URL.indexOf("&"));
        } catch (StringIndexOutOfBoundsException e){
            EventsCenter.getInstance().post(new NewResultAvailableEvent("Authentication Failed. Please login again."));
        }
        return token;
    }


    //Obtain google credentials from token
    public GoogleCredential getCredential(String token) throws IOException{

        GoogleTokenResponse Token = new GoogleTokenResponse();
        Token.setAccessToken(token);

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setFromTokenResponse(Token);
        return credential;
    }


    //Build PeopleService using google credentials
    public PeopleService BuildPeopleService(GoogleCredential credential){
        PeopleService peopleService =
                new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
        return peopleService;
    }


    //Obtain the list of Contacts from google
    public List<Person> getConnections(PeopleService peopleService)  throws IOException{
        ListConnectionsResponse response = new ListConnectionsResponse();
        response = peopleService.people().connections().list("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers,addresses")
                .execute();
        List<Person> connections = response.getConnections();

        return connections;
    }
}