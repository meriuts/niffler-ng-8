package guru.qa.niffler.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ControllerClientGenerator {
    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

    public static <T> T createControllerClient(Class<T> service, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }
}
