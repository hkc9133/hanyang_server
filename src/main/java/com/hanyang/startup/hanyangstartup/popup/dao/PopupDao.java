package com.hanyang.startup.hanyangstartup.popup.dao;

import com.hanyang.startup.hanyangstartup.popup.domain.Popup;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalPlace;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PopupDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;
    public void addPopup(Popup popup){
        sqlSession.insert("popup.addPopup",popup);
    }
    public void updatePopup(Popup popup){
        sqlSession.update("popup.updatePopup",popup);
    }

    public Popup getPopup(Popup popup){
        return sqlSession.selectOne("popup.getPopup",popup);
    }
    public List<Popup> getPopupList(Popup popup){
        return sqlSession.selectList("popup.getPopupList",popup);
    }

    public int getPopupListCnt(Popup popup){
        return sqlSession.selectOne("popup.getPopupListCnt",popup);
    }


    public int deletePopup(Popup popup){
        return sqlSession.delete("popup.deletePopup",popup);
    }
}
