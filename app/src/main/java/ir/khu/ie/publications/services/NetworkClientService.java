package ir.khu.ie.publications.services;

import ir.khu.ie.publications.BuildConfig;
import ir.khu.ie.publications.utils.Variables;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClientService {

    private static final String BASE_URL = BuildConfig.DEBUG ? "https://saad-application.herokuapp.com/api/v1/" : "https://saad-application.herokuapp.com/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(chain -> {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("token", Variables.accountData == null ? "" : Variables.accountData.getJwt())
                        .build();
                return chain.proceed(request);
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

}
