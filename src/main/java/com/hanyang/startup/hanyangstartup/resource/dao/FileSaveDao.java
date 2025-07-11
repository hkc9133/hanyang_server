package com.hanyang.startup.hanyangstartup.resource.dao;

import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileSaveDao {

    @Autowired
    protected SqlSessionTemplate sqlSession;

    public void addAttachFile(AttachFile attachFile){
        sqlSession.insert("attach_file.addAttachFile",attachFile);
    }

    public List<AttachFile> getAttachFileList(AttachFile attachFile){
        return sqlSession.selectList("attach_file.getAttachFileList",attachFile);
    }

    public AttachFile getAttachFile(AttachFile attachFile){
        return sqlSession.selectOne("attach_file.getAttachFile",attachFile);
    }

    public void updateAttachFileList(List<AttachFile> attachFileList){
        sqlSession.update("attach_file.updateAttachFileList",attachFileList);
    }

    public void updateAttachFile(AttachFile attachFile){
        sqlSession.update("attach_file.updateAttachFile",attachFile);
    }

    public void deleteAttachFile(List<AttachFile> attachFileList){
        sqlSession.update("attach_file.deleteAttachFile",attachFileList);
    }
    public void deleteAttachFileWithContentId(List<AttachFile> attachFileList){
        sqlSession.update("attach_file.deleteAttachFileWithContentId",attachFileList);
    }

}
