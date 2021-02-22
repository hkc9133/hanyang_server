package com.hanyang.startup.hanyangstartup.notice.service;

import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.board.domain.Reply;
import com.hanyang.startup.hanyangstartup.notice.dao.NoticeDao;
import com.hanyang.startup.hanyangstartup.notice.domain.Notice;
import com.hanyang.startup.hanyangstartup.notice.domain.NoticeCategoryCode;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_STATUS;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private FileSaveService fileSaveService;

    //컨텐츠 조회
    public Map<String, Object> getNotice(Notice notice) {
        //조회수 업데이트
        noticeDao.updateNoticeCnt(notice);

        AttachFile attachFile = new AttachFile();
        attachFile.setContentId(notice.getNoticeId());
        attachFile.setDivision(FILE_DIVISION.NOTICE_ATTACH);
        attachFile.setStatus(FILE_STATUS.A);
        Map<String, Object> map = new HashMap<>();
        Notice resultNotice = noticeDao.getNotice(notice);

        map.put("notice", resultNotice);
        map.put("prev", noticeDao.getNoticePrev(notice));
        map.put("next", noticeDao.getNoticeNext(notice));
        map.put("files", fileSaveService.getAttachFileList(attachFile));
        return map;
    }

    //컨텐츠 생성
    @Transactional(rollbackFor = {Exception.class})
    public void addNotice(Notice notice) {
        noticeDao.addNotice(notice);

        if(notice.getFiles() != null){
            for (MultipartFile file : notice.getFiles()) {
                fileSaveService.fileSave(file, notice.getNoticeId(), FILE_DIVISION.NOTICE_ATTACH);
            }
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateNotice(Notice notice) {
        List<Integer> removeFiles = notice.getRemoveFiles();
        if(removeFiles != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            for (Integer file : removeFiles) {
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(file.intValue());
                attachFileList.add(attachFile);
            }
            fileSaveService.deleteAttachFile(attachFileList);
        }
        if(notice.getFiles() != null){
            for (MultipartFile file : notice.getFiles()) {
                fileSaveService.fileSave(file, notice.getNoticeId(), FILE_DIVISION.BOARD_ATTACH);
            }
        }
        noticeDao.updateNotice(notice);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteNotice(Notice notice) {
        noticeDao.deleteNotice(notice);
    }


    //이벤트
    public Map<String,Object> getNoticeList(Notice notice) {
        notice.setTotalCount(noticeDao.getNoticeListCnt(notice));

        System.out.println(notice);
        Map<String,Object> map = new HashMap<>();
        List<Notice> noticeList = noticeDao.getNoticeList(notice);

        map.put("page", notice);
        map.put("list", noticeList);
        map.put("cate",noticeDao.getNoticeCategoryCodeList());

        return map;
    }

    public List<NoticeCategoryCode> getNoticeCategoryCodeList() {
        return noticeDao.getNoticeCategoryCodeList();
    }

}
