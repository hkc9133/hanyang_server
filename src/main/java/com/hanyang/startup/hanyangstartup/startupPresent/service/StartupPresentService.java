package com.hanyang.startup.hanyangstartup.startupPresent.service;

import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.startupPresent.dao.StartupPresentDao;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.GubunCount;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import com.sun.rowset.internal.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StartupPresentService {

    @Autowired
    private StartupPresentDao startupPresentDao;

    @Autowired
    private FileSaveService fileSaveService;


    public Map<String, Object> getFieldList(){


        Map<String, Object> map = new HashMap<>();

        map.put("business", startupPresentDao.getBusinessFieldList());
        map.put("tech", startupPresentDao.getTechFieldList());

        return map;

    }

    @Transactional(rollbackFor = {Exception.class})
    public void addStartupPresent(StartupPresent startupPresent){

        startupPresentDao.addStartupPresent(startupPresent);

        startupPresentDao.addBusinessFieldList(startupPresent);
        startupPresentDao.addTechFieldList(startupPresent);

        if(startupPresent.getCompanyLogo() != null){
            fileSaveService.fileSave(startupPresent.getCompanyLogo(),startupPresent.getStartupId(), FILE_DIVISION.STARTUP_LOGO);
        }

    }
    @Transactional(rollbackFor = {Exception.class})
    public void updateStartupPresent(StartupPresent startupPresent){

        startupPresentDao.deleteBusinessFieldList(startupPresent);
        startupPresentDao.deleteTechFieldList(startupPresent);

        startupPresentDao.addBusinessFieldList(startupPresent);
        startupPresentDao.addTechFieldList(startupPresent);

        //새로 추가한 이미지가 있으면
        if(startupPresent.getCompanyLogo() != null){

            if(startupPresent.getRemoveFileId() != null){
                List<AttachFile> attachFileList = new ArrayList<>();
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(startupPresent.getRemoveFileId());

                attachFileList.add(attachFile);
                fileSaveService.deleteAttachFile(attachFileList);

            }

            fileSaveService.fileSave(startupPresent.getCompanyLogo(),startupPresent.getStartupId(), FILE_DIVISION.STARTUP_LOGO);
        }

        startupPresentDao.updateStartupPresent(startupPresent);
    }

    public StartupPresent getStartupPresent(StartupPresent startupPresent){
        return startupPresentDao.getStartupPresent(startupPresent);
    }
    public Map<String, Object> getStartupPresentList(StartupPresent startupPresent){

        startupPresent.setTotalCount(startupPresentDao.getStartupPresentListCnt(startupPresent));
        List<StartupPresent> startupPresentList = startupPresentDao.getStartupPresentList(startupPresent);


        Map<String, Object> map = new HashMap<>();

        map.put("page", startupPresent);
        map.put("list", startupPresentList);

        return map;

    }

    public void deleteStartupPresent(StartupPresent startupPresent){
        startupPresentDao.deleteBusinessFieldList(startupPresent);
        startupPresentDao.deleteTechFieldList(startupPresent);


        if(startupPresent.getAttachFile() != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            AttachFile attachFile = new AttachFile();
            attachFile.setFileId(startupPresent.getAttachFile().getFileId());

            attachFileList.add(attachFile);
            fileSaveService.deleteAttachFile(attachFileList);
        }

        startupPresentDao.deleteStartupPresent(startupPresent);
    }

    public Map<String, Object> getBestStartupList(){

        List<StartupPresent> startupPresentList = startupPresentDao.getBestStartupList();
        List<GubunCount> gubunCountList = startupPresentDao.getStartupGubunCnt();


        Map<String, Object> map = new HashMap<>();

        map.put("list", startupPresentList);
        map.put("count", gubunCountList);

        return map;
    }

    public HSSFWorkbook excelDownloadAll(){
        StartupPresent startupPresent = new StartupPresent();
        startupPresent.setTotalCount(startupPresentDao.getStartupPresentListCnt(startupPresent));
        startupPresent.setPageSize(startupPresent.getTotalCount());
        List<StartupPresent> startupPresentList = startupPresentDao.getStartupPresentList(startupPresent);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("스타트업 배출현황");

        HSSFRow row = null;

        HSSFCell cell = null;

        row = sheet.createRow(0);
        String[] headerKey = {"기업명", "대표", "회사 번호", "회사 이메일", "회사 주소", "창업 구분","기업 구분","사업자 번호","기업 상태","창업일","아이템", "홈페이지", "인스타", "페이스북","네이버 블로그","트위터","우수 기업"};

        for(int i=0; i<headerKey.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerKey[i]);
        }

        for(int i=0; i<startupPresentList.size(); i++) {
            row = sheet.createRow(i + 1);
            StartupPresent vo = startupPresentList.get(i);

            cell = row.createCell(0);
            cell.setCellValue(vo.getCompanyName());

            cell = row.createCell(1);
            cell.setCellValue(vo.getCompanyOwner());

            cell = row.createCell(2);
            cell.setCellValue(vo.getCompanyPhoneNum());

            cell = row.createCell(3);
            cell.setCellValue(vo.getCompanyEmail());

            cell = row.createCell(4);
            cell.setCellValue(vo.getAddress());
            cell = row.createCell(5);
            cell.setCellValue(vo.getGubun());
            cell = row.createCell(6);
            cell.setCellValue(vo.getCompanyKind());
            cell = row.createCell(7);
            cell.setCellValue(vo.getCompanyNum());
            cell = row.createCell(8);
            cell.setCellValue(vo.getCompanyStatus());

            cell = row.createCell(9);
            if(vo.getCreateDate() != null){
                cell.setCellValue(vo.getCreateDate().toString());
            }else{
                cell.setCellValue("");
            }

            cell = row.createCell(10);
            cell.setCellValue(vo.getItem());
            cell = row.createCell(11);
            cell.setCellValue(vo.getHomepage());
            cell = row.createCell(12);
            cell.setCellValue(vo.getInsta());
            cell = row.createCell(13);
            cell.setCellValue(vo.getFacebook());
            cell = row.createCell(14);
            cell.setCellValue(vo.getNaverBlog());
            cell = row.createCell(15);
            cell.setCellValue(vo.getTwitter());
            cell = row.createCell(16);
            cell.setCellValue(vo.getIsBest());
//            cell = row.createCell(18);
//            cell.setCellValue(vo.getRegDate().toString());

        }

        return workbook;

    }
}
