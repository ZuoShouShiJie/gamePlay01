package mkcloudadmin.service.business.impl;

import mkcloudadmin.mapper.mkcloud.*;
import mkcloudadmin.model.base.Page;
import mkcloudadmin.model.base.StaticEnum;
import mkcloudadmin.model.mkcloud.po.MKCloudAdvancePaymentPlan;
import mkcloudadmin.model.mkcloud.po.MKCloudApplicationImportDetail;
import mkcloudadmin.model.mkcloud.po.MKCloudDataDictionary;
import mkcloudadmin.model.mkcloud.po.MKCloudPrepaymentRuleConfiguration;
import mkcloudadmin.model.mkcloud.vo.MKCloudAdvancePaymentPlanVO;
import mkcloudadmin.model.mkcloud.vo.MKCloudPrepaymentRuleConfigurationVO;
import mkcloudadmin.service.business.AdvancePaymentPlanService;
import mkcloudadmin.service.business.PrepaymentRuleConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预付款计划管理
 */
@Service
public class AdvancePaymentPlanServiceImpl implements AdvancePaymentPlanService {
@Autowired
private MKCloudAdvancePaymentPlanMapper advancePaymentPlanMapper;

    @Autowired
    private MKCloudPrepaymentRuleConfigurationMapper prepaymentRuleConfigurationMapper;
    @Autowired
    private MKCloudBankImportDetailMapper bankImportDetailMapper;
    @Autowired
    private MKCloudDataDictionaryMapper dataDictionaryMapper;
    @Autowired
    private MKCloudPaymentRecordMapper paymentRecordMapper;
    /**
     * 预付款金额明细查询
     * @return
     */
    @Override
  public   Map<String,Object> selectByPayMentPlan(){
      Map<String,Object> resultMap = new HashMap<>();
      List<MKCloudAdvancePaymentPlanVO> list=new ArrayList<>();
        List<MKCloudPrepaymentRuleConfigurationVO> voList=new ArrayList<>();
        List<MKCloudAdvancePaymentPlan> paymentPlansAnd=null;
        List<MKCloudPrepaymentRuleConfiguration> configurationList=null;
        MKCloudDataDictionary  dictionarys=null;
        MKCloudDataDictionary  dictionaryOne=null;
        MKCloudAdvancePaymentPlanVO planVO=new MKCloudAdvancePaymentPlanVO();
        BigDecimal totalPailMoney =new BigDecimal(0);
        BigDecimal usedPailMoney =new BigDecimal(0);
        BigDecimal overplusPailMoney =new BigDecimal(0);
        paymentPlansAnd=advancePaymentPlanMapper.selectByPayMentPlan();
        if(null!=paymentPlansAnd && paymentPlansAnd.size()>0){
            for (MKCloudAdvancePaymentPlan plan:paymentPlansAnd){
                if(StaticEnum.djustType_add.getCode().equals(plan.getAdjustType())) {
                    totalPailMoney = totalPailMoney.add(plan.getAdvance());
                }else if(StaticEnum.djustType_subtract.getCode().equals(plan.getAdjustType())){
                    totalPailMoney = totalPailMoney.subtract(plan.getAdvance());
                }
            }
            //预付款总计
          planVO.setTotalPailMoney(totalPailMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
      }else{
          //预付款总计
          planVO.setTotalPailMoney( new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
      }
        configurationList=prepaymentRuleConfigurationMapper.selectByPrepaymentRuleConfiguration();
        if(null!=configurationList && configurationList.size()>0){
            int count=0;
            for (MKCloudPrepaymentRuleConfiguration  configuration:configurationList){
                dictionarys=  dataDictionaryMapper.selectByState(configuration.getSettlementType());
                if(null!=dictionarys){
                    dictionaryOne=  dataDictionaryMapper.selectByState(dictionarys.getParentCode());
                    if(null!=dictionaryOne) {
                        voList.add(reConver(configuration, dictionarys, count));
                        usedPailMoney = configuration.getPrice().multiply(BigDecimal.valueOf(count));
                        planVO.setUsedPailMoney(usedPailMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }else{
                        planVO.setUsedPailMoney( new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                    }
                }else{
                    planVO.setUsedPailMoney( new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                }
                if("成交结算".equals(configuration.getSettlementType())) {
                    /* details= applicationImportDetailMapper.selectByAuditStatus(StaticEnum.apply_for_success.getCode(),null);*/
                    count = bankImportDetailMapper.selectByBankCount();
                }
            }
        }else{
            //预付款已用
            planVO.setUsedPailMoney( new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        }
        //预付款结余
        overplusPailMoney=totalPailMoney.subtract(usedPailMoney);
        planVO.setOverPailplusMoney(overplusPailMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        list.add(planVO);

        resultMap.put("configurationList", voList);
        resultMap.put("planList", reConver(paymentPlansAnd));
        resultMap.put("planVO",list);
        return  resultMap;
  }
    public   List<MKCloudAdvancePaymentPlanVO> reConver( List<MKCloudAdvancePaymentPlan>   planList){
        List<MKCloudAdvancePaymentPlanVO> voList=new ArrayList<>();
        if (null!=planList && planList.size()>0){
            MKCloudAdvancePaymentPlanVO vo=null;
            for (MKCloudAdvancePaymentPlan plan :planList){
                vo=new MKCloudAdvancePaymentPlanVO();
                vo.setAccount(plan.getAccount());
                vo.setAccountBank(plan.getAccountBank());
                vo.setAccountDate(  new SimpleDateFormat("yyyy-MM-dd").format(plan.getAccountDate()));
                vo.setAccountName(plan.getAccountName());
                if("1".equals(plan.getAdjustType())){
                    vo.setAdjustType("新增");
                }else if("-1".equals(plan.getAdjustType())){
                    vo.setAdjustType("转出");
                }
                vo.setAdvance(plan.getAdvance().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
             voList.add(vo);
            }
        }
        return voList;
    }

    public  MKCloudPrepaymentRuleConfigurationVO reConver(MKCloudPrepaymentRuleConfiguration configuration,MKCloudDataDictionary  dictionarys,int size){
        MKCloudPrepaymentRuleConfigurationVO vo=new MKCloudPrepaymentRuleConfigurationVO();
        vo.setSettlementType(dictionarys.getDescribe());
        vo.setShow(configuration.getShow());
        vo.setTotalCount(size);
        vo.setPrice(configuration.getPrice().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        BigDecimal  toTalMoney=configuration.getPrice().multiply(BigDecimal.valueOf(size));
        vo.setTotalMoney(toTalMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        return vo;
    }
}
