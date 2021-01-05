package com.hanyang.startup.hanyangstartup.resource.service;

import com.hanyang.startup.hanyangstartup.resource.dao.FileSaveDao;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileSaveService {

    @Autowired
    private FileSaveDao fileSaveDao;
    @Value(value = "${config.uploadPath}")
    private String UPLOAD_PATH;

    @Value(value = "${server.servlet.context-path}")
    private String CONTEXT_PATH;

    public AttachFile fileSave(MultipartFile file, Integer contentId, FILE_DIVISION division){

        try{
            AttachFile saveFile = new AttachFile();
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            String year = new SimpleDateFormat("yyyy").format(today);
            String month = new SimpleDateFormat("MM").format(today);

            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."));

            UUID uuid = UUID.randomUUID();

            String fileUploadPath = "/"+division+"/"+year+"/"+month;

            File dir = new File(UPLOAD_PATH + fileUploadPath);
            if (!dir.exists()){
                dir.mkdirs();
            }

            String path = fileUploadPath+"/"+uuid.toString()+extension;

            //파일 저장
            file.transferTo(new File(UPLOAD_PATH + path));

            //디비에 정보 저장
            saveFile.setDivision(division);
            saveFile.setFileOriginName(fileName);
            saveFile.setFileName(uuid.toString());
            saveFile.setFilePath(fileUploadPath);
            saveFile.setFileExtension(extension);
            saveFile.setFileSize(file.getSize());
            if(contentId != null){
                saveFile.setContentId(contentId);
            }

            fileSaveDao.addAttachFile(saveFile);

            return saveFile;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public AttachFile getAttachFile(AttachFile attachFile){
        return fileSaveDao.getAttachFile(attachFile);
    }

    public List<AttachFile> getAttachFileList(AttachFile attachFile){
        return fileSaveDao.getAttachFileList(attachFile);
    }

    public void updateAttachFile(List<AttachFile> attachFileList){
        fileSaveDao.updateAttachFile(attachFileList);
    }
}
