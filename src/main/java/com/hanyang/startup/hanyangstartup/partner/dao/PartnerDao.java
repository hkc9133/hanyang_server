package com.hanyang.startup.hanyangstartup.partner.dao;

import com.hanyang.startup.hanyangstartup.partner.domain.ContinentCode;
import com.hanyang.startup.hanyangstartup.partner.domain.Partner;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartnerDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;


    public void addPartner(Partner partner){
        sqlSession.insert("partner.addPartner", partner);
    }

    public void updatePartner(Partner partner){
        sqlSession.update("partner.updatePartner", partner);
    }

    public Partner getPartner(Partner partner){
        return sqlSession.selectOne("partner.getPartner", partner);
    }
    public List<Partner> getPartnerList(Partner partner){
        return sqlSession.selectList("partner.getPartnerList", partner);
    }

    public int getPartnerListCnt(Partner partner){
        return sqlSession.selectOne("partner.getPartnerListCnt", partner);
    }

    public int deletePartner(Partner partner){
        return sqlSession.delete("partner.deletePartner", partner);
    }

    public List<ContinentCode> getContinentList(){
        return sqlSession.selectList("partner.getContinentList");
    }

}
