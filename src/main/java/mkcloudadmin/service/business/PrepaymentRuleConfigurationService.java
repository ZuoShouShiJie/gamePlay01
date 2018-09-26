package mkcloudadmin.service.business;

import mkcloudadmin.model.base.Page;
import mkcloudadmin.model.mkcloud.po.MKCloudBankImportDetail;
import mkcloudadmin.model.mkcloud.po.MKCloudPrepaymentRuleConfiguration;

import java.util.List;
import java.util.Map;

public interface PrepaymentRuleConfigurationService {
    /**
     * 用款记录
     * @return
     */
    Map<String,Object> selectByPrepaymentRuleConfiguration();
    /**
     * 成交明细查询
     * @param detailPage
     * @return
     */
    Map<String,Object> selectByApplicationImportDetail(Page<MKCloudBankImportDetail> detailPage,String beginDate,String endDate);
}