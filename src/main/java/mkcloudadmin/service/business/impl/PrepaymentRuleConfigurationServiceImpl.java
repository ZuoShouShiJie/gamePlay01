package mkcloudadmin.service.business.impl;

import mkcloudadmin.mapper.mkcloud.MKCloudApplicationImportDetailMapper;
import mkcloudadmin.mapper.mkcloud.MKCloudBankImportDetailMapper;
import mkcloudadmin.mapper.mkcloud.MKCloudPrepaymentRuleConfigurationMapper;
import mkcloudadmin.model.base.Page;
import mkcloudadmin.model.base.StaticEnum;
import mkcloudadmin.model.mkcloud.po.MKCloudAdvancePaymentPlan;
import mkcloudadmin.model.mkcloud.po.MKCloudApplicationImportDetail;
import mkcloudadmin.model.mkcloud.po.MKCloudBankImportDetail;
import mkcloudadmin.model.mkcloud.po.MKCloudPrepaymentRuleConfiguration;
import mkcloudadmin.model.mkcloud.vo.MKCloudAdvancePaymentPlanVO;
import mkcloudadmin.model.mkcloud.vo.MKCloudPrepaymentRuleConfigurationVO;
import mkcloudadmin.service.business.PrepaymentRuleConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PrepaymentRuleConfigurationServiceImpl implements PrepaymentRuleConfigurationService {
@Autowired
private MKCloudPrepaymentRuleConfigurationMapper prepaymentRuleConfigurationMapper;
   @Autowired
   private MKCloudBankImportDetailMapper bankImportDetailMapper;
   @Autowired
   private MKCloudApplicationImportDetailMapper applicationImportDetailMapper;

   /**
    * 用款记录
    * @return
    */
   @Override
   public Map<String,Object> selectByPrepaymentRuleConfiguration(){
      Map<String,Object> resultMap = new HashMap<>();
      List<MKCloudPrepaymentRuleConfigurationVO> voList=new ArrayList<>();
      List<MKCloudPrepaymentRuleConfiguration> configurationList=prepaymentRuleConfigurationMapper.selectByPrepaymentRuleConfiguration();
      if(null!=configurationList && configurationList.size()>0){
         List<MKCloudApplicationImportDetail>   details=null;
         int count=0;
         for (MKCloudPrepaymentRuleConfiguration  configuration:configurationList){
            if("成交结算".equals(configuration.getSettlementType())){
           /* details= applicationImportDetailMapper.selectByAuditStatus(StaticEnum.apply_for_success.getCode(),null);*/
              count=bankImportDetailMapper.selectByBankCount();
            }/*else if("短信结算".equals(configuration.getSettlementType())){
               details= applicationImportDetailMapper.selectByAuditStatus(null,null);
            }*/
            voList.add(reConver(configuration,count));
         }
      }
      resultMap.put("configurationList", voList);

      return  resultMap;
   }

   /**
    * 成交明细查询
    * @param detailPage
    * @return
    */
   @Override
   public Map<String,Object> selectByApplicationImportDetail(Page<MKCloudBankImportDetail> detailPage,String beginDate,String endDate){
      Map<String,Object> resultMap = new HashMap<>();
      List<MKCloudBankImportDetail> details= bankImportDetailMapper.selectByBankImportDetail(detailPage);
      int count=bankImportDetailMapper.selectByBankCount();
      resultMap.put("detailsList", details);
      resultMap.put("count", count);
      return  resultMap;
   }

   public  MKCloudPrepaymentRuleConfigurationVO reConver(MKCloudPrepaymentRuleConfiguration configuration,int size){
         MKCloudPrepaymentRuleConfigurationVO vo=new MKCloudPrepaymentRuleConfigurationVO();
            vo.setSettlementType(configuration.getSettlementType());
            vo.setShow(configuration.getShow());
            vo.setTotalCount(size);
            vo.setPrice(configuration.getPrice().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            BigDecimal  toTalMoney=configuration.getPrice().multiply(BigDecimal.valueOf(size));
            vo.setTotalMoney(toTalMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            vo.setSettltmentBeginDate(new SimpleDateFormat("yyyy-MM-dd").format(configuration.getSettltmentBeginDate()));
            vo.setSettlementEndDate(new SimpleDateFormat("yyyy-MM-dd").format(configuration.getSettlementEndDate()));
            return vo;
   }



}