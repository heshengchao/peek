package org.peek.service.impl;

import java.util.Date;
import java.util.List;

import org.peek.domain.AppInsState;
import org.peek.domain.AppInstance;
import org.peek.enums.InstanceState;
import org.peek.repository.AppInsStateRepository;
import org.peek.service.impl.weixin.WeixinNotifyService;
import org.peek.service.query.AppStateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppInsStateService {
	@Autowired AppInsStateRepository appInsStateRepository;
	@Autowired IdGenerate idGenerate;
	@Autowired WeixinNotifyService weixinNotifyService;
	
	
	public void add(AppInstance app,InstanceState state) {
		AppInsState appState=new AppInsState();
		appState.setId(idGenerate.getStrsId());
		appState.setInsId(app.getInsId());
		appState.setInsName(app.getInsName());
		appState.setState(state);
		appState.setSysTime(new Date());
		appInsStateRepository.save(appState);
		
		weixinNotifyService.serverAliveAlert(app,state);
	}


	public List<AppInsState> findTopN(AppStateQuery query, int topn) {
		return appInsStateRepository.find(query,topn);
	}

}
