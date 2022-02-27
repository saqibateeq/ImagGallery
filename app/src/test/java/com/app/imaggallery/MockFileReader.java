package com.app.imaggallery;

import java.io.IOException;
import java.io.InputStreamReader;

public class MockFileReader {

    private String content;

    public MockFileReader(String path){
        InputStreamReader inputStreamReader = new InputStreamReader(this.getClass().getResourceAsStream(path));
        try {
            inputStreamReader.read();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContent(){
        return this.content;
    }
}
