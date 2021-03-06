package com.api.api.controllers;

import com.api.api.services.DelayService;
import com.api.api.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("images")
public class ImagesController {

    @Autowired
    private DelayService delayService;

    @Autowired
    private ImageService imageService;


    //http://localhost:8080/images/async/{imageName}
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/async/{imageName}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageAsync(@PathVariable("imageName") String imageName) throws IOException, InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();

        CompletableFuture<byte[]> bytes = imageService.getImageAsync(imageName);
        CompletableFuture<String> asyncDelay1 = delayService.asyncDelay(2);
        CompletableFuture<String> asyncDelay2 = delayService.asyncDelay(4);
        CompletableFuture<String> asyncDelay3 = delayService.asyncDelay(1);

        System.out.println("asyncDelay1 -> "+asyncDelay1.get());
        System.out.println("asyncDelay2 -> "+asyncDelay2.get());
        System.out.println("asyncDelay3 -> "+asyncDelay3.get());

        Double duration = (System.currentTimeMillis() - startTime) / 1000.0;
        System.out.println("Duracion: " + duration + " secs");


        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes.get());
    }

    //http://localhost:8080/images/{imageName}
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{imageName}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) throws IOException, InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();

        byte[] bytes = imageService.getImage(imageName);
        String delay1 = delayService.delay(2);
        System.out.println("Delay1 -> "+delay1);

        String delay2 = delayService.delay(4);
        System.out.println("Delay2 -> "+delay2);

        String delay3 = delayService.delay(1);
        System.out.println("Delay3 -> "+delay3);

        Double duration = (System.currentTimeMillis() - startTime) / 1000.0;
        System.out.println("Duracion: " + duration + " secs");


        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
