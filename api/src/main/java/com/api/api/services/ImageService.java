package com.api.api.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class ImageService {

    @Async
    public CompletableFuture<byte[]> getImageAsycn(String imageName) throws IOException {
        var imgFile = new ClassPathResource("images/"+imageName+".jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return CompletableFuture.completedFuture(bytes);
    }

    public byte[] getImage(String imageName) throws IOException {
        var imgFile = new ClassPathResource("images/"+imageName+".jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return bytes;
    }
}
