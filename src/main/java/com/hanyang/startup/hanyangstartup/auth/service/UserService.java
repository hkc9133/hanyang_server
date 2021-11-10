package com.hanyang.startup.hanyangstartup.auth.service;

import com.hanyang.startup.hanyangstartup.auth.dao.UserDao;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.mentoring.domain.Mentor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Map<String,Object> getUserList(User user) {
        user.setPageSize(10);
        user.setTotalCount(userDao.getUserListCnt(user));

        Map<String,Object> map = new HashMap<>();

        map.put("page",user);
        map.put("list",userDao.getUserList(user));

        return map;
    }

    public Map<String,Object> getUser(User user) {
        Map<String,Object> map = new HashMap<>();

        User searchUser = userDao.getUser(user);
        searchUser.setUserPassword("");
        map.put("user",searchUser);

        return map;
    }
    public void updateUser(User user) {
        userDao.updateUser(user);

    }

    public HSSFWorkbook excelDownloadAll(){
        User user = new User();
        user.setTotalCount(userDao.getUserListCnt(user));

        user.setPageSize(user.getTotalCount());

        List<User> userList = userDao.getUserList(user);

//        userList.stream().map(item-> {
//            if(item.getMentorCareerStr() != null){
//                item.setMentorCareer(Arrays.asList(item.getMentorCareerStr().split(";").clone()));
//            }else{
//                item.setMentorCareer(new ArrayList<>());
//            }
//
//            if(item.getMentorKeywordStr() != null){
//                item.setMentorKeyword(Arrays.asList(item.getMentorKeywordStr().split(";").clone()));
//            }else{
//                item.setMentorKeyword(new ArrayList<>());
//            }
//            item.setMentorFieldList(mentoringDao.getCounselFieldMentor(item));
//            return item;
//        }).collect(Collectors.toList());

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("사용자 현황");

        HSSFRow row = null;

        HSSFCell cell = null;

        row = sheet.createRow(0);
        String[] headerKey = {"아이디", "이메일", "이름", "유형", "권한", "가입일", "마지막 로그인"};

        for(int i=0; i<headerKey.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerKey[i]);
        }

        for(int i=0; i<userList.size(); i++) {
            row = sheet.createRow(i + 1);
            User vo = userList.get(i);

            cell = row.createCell(0);
            cell.setCellValue(vo.getUserId());

            cell = row.createCell(1);
            cell.setCellValue(vo.getUserEmail());

            cell = row.createCell(2);
            cell.setCellValue(vo.getUserName());

            cell = row.createCell(3);
            cell.setCellValue(this.getType(vo.getType().toString()));

            cell = row.createCell(4);
            cell.setCellValue(this.getRole(vo.getRole().toString()));
            cell = row.createCell(5);
            cell.setCellValue(vo.getRegDate().toString());
            cell = row.createCell(6);
            cell.setCellValue(vo.getLastLogin().toString());

        }

        return workbook;

    }

    public String getRole(String role) {

        String result = "";
        switch (role){
            case "ROLE_SD":
                result = "학생";
                break;
            case "ROLE_TC":
                result = "교직원";
                break;
            case "ROLE_MT":
                result = "멘토";
                break;
            case "ROLE_ADMIN":
                result = "관리자";
                break;
        }

        return result;

    }

    public String getType(String type) {
        String result = "";
        switch (type){
            case "HANYANG":
                result = "한양인";
                break;
            case "KAKAO":
                result = "카카오";
                break;
            case "NAVER":
                result = "네이버";
                break;
            case "GOOGLE":
                result = "구글";
                break;
            case "FACEBOOK":
                result = "페이스북";
                break;
            case "NORMAL":
                result = "일반";
                break;
        }

        return result;

    }

}
