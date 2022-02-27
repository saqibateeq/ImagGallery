package com.app.imaggallery;

import static com.google.common.truth.Truth.assertThat;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.app.imaggallery.datamodels.GalleryItem;
import com.app.imaggallery.service.GalleryService;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

@RunWith(JUnit4.class)
public class GalleryServiceTest {

    private static final String BASE_URL_IMAGE_GALLERY = "https://jsonplaceholder.typicode.com/";

    @Rule
    TestRule testRule = new InstantTaskExecutorRule();
    MockWebServer mockWebServer;
    GalleryService galleryService;

    @Before
    public void start() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        galleryService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url(BASE_URL_IMAGE_GALLERY))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build()
                .create(GalleryService.class);
    }

    @Test
    public void test_verifySuccessResponse(){
        MockResponse response = new MockResponse();
        response.setBody(new MockFileReader("gallery_item_response.json").getContent())
                .setResponseCode(HttpURLConnection.HTTP_OK);
        mockWebServer.enqueue(response);

        Call<List<GalleryItem>> actualResponse = galleryService.fetchImagesFromServer();
        assertThat(response.toString().contains(String.valueOf(HttpURLConnection.HTTP_OK)) == (actualResponse.toString().contains(String.valueOf(HttpURLConnection.HTTP_OK))));
    }

    @After
    public void end(){

    }
}
