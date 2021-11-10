package com.hanyang.startup.hanyangstartup.resource;


import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.UploadResult;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Value(value = "${config.uploadPath}")
    private String UPLOAD_PATH;

    @Value(value = "${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @Autowired
    private FileSaveService fileSaveService;

    @RequestMapping(path = "/**/{fileName}", method = RequestMethod.GET,produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public @ResponseBody ResponseEntity<byte[]>
    showImage(@PathVariable("fileName") String fileName, HttpServletRequest req,HttpServletResponse res) throws IOException {

        String url = req.getRequestURL().toString();
        String path = url.split("/resource")[1];
        String filePath = path.split("/"+fileName)[0];

        InputStream image = new FileInputStream(UPLOAD_PATH+filePath+"/"+fileName);

//        byte[] ib = IOUtils.toByteArray(image);
//        ib.clone();
//        return ib;
        final HttpHeaders headers = new HttpHeaders();
        String extension = StringUtils.getFilenameExtension(fileName); // jpg
        if(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg")){
            headers.setContentType(MediaType.IMAGE_JPEG);
        }else if(extension.toLowerCase().equals("png")){
            headers.setContentType(MediaType.IMAGE_PNG);
        }else if(extension.toLowerCase().equals("gif")){
            headers.setContentType(MediaType.IMAGE_GIF);
        }



        return new ResponseEntity<byte[]>(IOUtils.toByteArray(image), headers, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/attach_file/{FILE_DIVISION}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadResult fileUpload(@RequestParam(name = "file") MultipartFile file,@PathVariable("FILE_DIVISION") FILE_DIVISION fileDivision, HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        UploadResult uploadResult = new UploadResult();
        try {

            AttachFile attachFile = fileSaveService.fileSave(file, null, fileDivision);

            String path = attachFile.getFilePath()+"/"+attachFile.getFileName()+attachFile.getFileExtension();
//            String fileUrl = req.getRequestURL().substring(0, req.getRequestURL().indexOf(CONTEXT_PATH)+CONTEXT_PATH.length()) +"/resource"+path;
            String fileUrl = "/resource"+path;


            uploadResult.setUrl(fileUrl);
            uploadResult.setUid(UUID.randomUUID().toString());
            uploadResult.setName(file.getOriginalFilename());
            uploadResult.setStatus("done");
            uploadResult.setFileId(attachFile.getFileId());
            return uploadResult;
        }
        catch(Exception e){

            uploadResult.setUid(UUID.randomUUID().toString());
            uploadResult.setName(file.getOriginalFilename());
            uploadResult.setStatus("error");
            e.printStackTrace();
            return uploadResult;
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Integer fileId) throws IOException {

        AttachFile attachFile = new AttachFile();
        attachFile.setFileId(fileId);
        AttachFile result = fileSaveService.getAttachFile(attachFile);

        Path path = Paths.get(UPLOAD_PATH+"/"+result.getFilePath()+"/"+result.getFileName()+result.getFileExtension());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(result.getFileOriginName(), StandardCharsets.UTF_8)
                .build());

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}
