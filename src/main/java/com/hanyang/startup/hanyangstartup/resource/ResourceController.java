package com.hanyang.startup.hanyangstartup.resource;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Value(value = "${config.uploadPath}")
    private String UPLOAD_PATH;

    @Value(value = "${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @RequestMapping(path = "/{fileCate}/{fileName}", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[]
    showImage(@PathVariable("fileCate") String fileCate, @PathVariable("fileName") String fileName) throws IOException {

        // ...
//        File file = new File(UPLOAD_PATH+"/"+fileCate+"/"+fileName);
//
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
//        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        header.add("Pragma", "no-cache");
//        header.add("Expires", "0");
//
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//

        InputStream image = new FileInputStream(UPLOAD_PATH+"/"+fileCate+"/"+fileName);
        byte[] ib = IOUtils.toByteArray(image);
        ib.clone();
//            res.setContentType(MediaType.IMAGE_JPEG_VALUE);
//            IOUtils.copy(in, res.getOutputStream());
//            return IOUtils.toByteArray(in);
        return ib;
    }

}
