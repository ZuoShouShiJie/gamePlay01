package mkcloudadmin.controller.business;

import mkcloudadmin.model.base.Page;
import mkcloudadmin.model.mkcloud.po.MKCloudAdvancePaymentPlan;
import mkcloudadmin.model.mkcloud.po.MKCloudBankImportDetail;
import mkcloudadmin.service.business.AdvancePaymentPlanService;
import mkcloudadmin.service.business.PrepaymentRuleConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/PrepaymentRuleConfiguration")
public class PrepaymentRuleConfigurationController {
    private static Logger logger = LoggerFactory.getLogger(BusinessPeopleController.class);

    @Autowired
    private PrepaymentRuleConfigurationService prepaymentRuleConfigurationService;

    @Autowired
    private AdvancePaymentPlanService advancePaymentPlanService;
    /**
     * 用款记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/selectByPrepaymentRuleList")
    @ResponseBody
    public Object selectWchatTel(HttpServletRequest request/*, @RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize*/){
/*        Page<MKCloudAdvancePaymentPlan> cardInfoPage = new Page<MKCloudAdvancePaymentPlan>(pageSize,pageNum.longValue());*/
        Map<String,Object> map = advancePaymentPlanService.selectByPayMentPlan();
        map.put("code",0);
        map.put("msg", "");
        map.put("data",map.get("configurationList"));
        return map;
    }

    /**
     * 成交明细查询
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/importDetailList")
    @ResponseBody
    public Object selectByApplicationImportDetail(HttpServletRequest request,
                                                  @RequestParam("page") Integer pageNum,
                                                  @RequestParam("limit") Integer pageSize,
                                                  @RequestParam("beginDate") String beginDate,
                                                  @RequestParam("endDate")String endDate){
              Page<MKCloudBankImportDetail> cardInfoPage = new Page<MKCloudBankImportDetail>(pageSize,pageNum.longValue());
        Map<String,Object> map = prepaymentRuleConfigurationService.selectByApplicationImportDetail(cardInfoPage,beginDate,endDate);
        map.put("code",0);
        map.put("msg", "");
        map.put("count",map.get("count"));
        map.put("data",map.get("detailsList"));
        return map;
    }
}
