package mkcloudadmin.controller.base;

import mkcloudadmin.model.mkcloud.po.MKCloudManageUser;
import org.springframework.stereotype.Service;

@Service
public class BaseApi extends SessionApi {
	
	public String getUserId(){
	    return ((MKCloudManageUser)getSession().getAttribute(USER_KEY_IN_SESSION)).getUserId();
	}
}
