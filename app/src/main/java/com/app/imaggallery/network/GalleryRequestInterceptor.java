package com.app.imaggallery.network;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public class GalleryRequestInterceptor implements okhttp3.Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }
}
