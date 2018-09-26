package mkcloudadmin.mapper.mkcloud;

import mkcloudadmin.model.base.Page;
import mkcloudadmin.model.mkcloud.po.MKCloudCommSettlement;
import mkcloudadmin.model.mkcloud.po.MKCloudCommissionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MKCloudCommSettlementMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MKCloudCommSettlement record);

    int insertSelective(MKCloudCommSettlement record);

    MKCloudCommSettlement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MKCloudCommSettlement record);

    int updateByPrimaryKey(MKCloudCommSettlement record);

    Long selectCommissionManageDataCount(@Param("businessPeopleId") String businessPeopleId,@Param("businessPeopleName") String businessPeopleName,
                                         @Param("beginDate") String beginDate,@Param("endDate") String endDate);

    List<MKCloudCommSettlement> selectCommissionManageDataList(@Param("businessPeopleId") String businessPeopleId, @Param("businessPeopleName") String businessPeopleName,
                                                               @Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("page") Page<MKCloudCommissionDetail> page);
    Long updateStateBySettlementId(@Param("settlementId") String settlementId);

}