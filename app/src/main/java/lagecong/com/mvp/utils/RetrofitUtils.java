package lagecong.com.mvp.utils;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitUtils {

    private RetrofitUtils(){}

    public static Retrofit getInstance(@NonNull String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance(@NonNull String baseUrl, long timeOutMilisecond){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(timeOutMilisecond > 0) {
            builder.readTimeout(timeOutMilisecond, TimeUnit.MILLISECONDS);
            builder.connectTimeout(timeOutMilisecond, TimeUnit.MILLISECONDS);
            builder.writeTimeout(timeOutMilisecond, TimeUnit.MILLISECONDS);
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static Retrofit getInstanceWithLoggingInterceptor(@NonNull String baseUrl, @NonNull HttpLoggingInterceptor.Level loggingLevel, long timeOutMilisecond){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(timeOutMilisecond > 0) {
            builder.readTimeout(timeOutMilisecond, TimeUnit.MILLISECONDS);
            builder.connectTimeout(timeOutMilisecond, TimeUnit.MILLISECONDS);
            builder.writeTimeout(timeOutMilisecond, TimeUnit.MILLISECONDS);
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(loggingLevel);
        builder.addInterceptor(interceptor);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }
}

