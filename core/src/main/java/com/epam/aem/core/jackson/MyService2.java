package com.epam.aem.core.jackson;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Component
@Service(MyService2.class)
@Path("/osgi-jax-rs-consumer")
@Produces(MediaType.APPLICATION_JSON)
public class MyService2 {

    private static final HostnameVerifier ALL_TRUSTED_VERIFIER = (s, sslSession) -> true;

    private static final TrustManager[] UNTRUSTED_MANAGER = {new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
        }
    },
    };


    @GET
    public Response getEntity() {

        String baseUrl = "https://10.6.131.90:9002/aem/v2/apparel-uk/products/300076932";

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, UNTRUSTED_MANAGER, new SecureRandom());
            SSLContext.setDefault(sslContext);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        Client client = ClientBuilder.newBuilder().sslContext(sslContext).hostnameVerifier(ALL_TRUSTED_VERIFIER).build();
        WebTarget webTarget = client.target(baseUrl);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();

        String employees = response.readEntity(String.class);
        //JSONObject
        //DTO
        return Response.ok(employees, MediaType.TEXT_PLAIN_TYPE).build();
    }
}
