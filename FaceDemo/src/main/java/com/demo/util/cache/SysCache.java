package com.demo.util.cache;

import com.demo.model.SysConfig;
import com.demo.service.SysConfigService;
import com.demo.util.ApplicationContextUtil;


public class SysCache {

	private static boolean inited = false;
	private static  SysConfig map = new SysConfig();

	private synchronized static void init() {
		if (inited) {
			return;
		}
		try {
			// // 调用dubbo服务获取数据
			SysConfigService sysConfigService = (SysConfigService) ApplicationContextUtil.getBean(SysConfig.class);

			SysConfig sys = sysConfigService.findSysConfig();
			map=sys;
			inited = true;
		} catch (Exception e) {
			inited = false;
		}
	}

	public static SysConfig getSysConfig() {
		if (!inited) {
			init();
		}

		return map;
	}

	public static void initAgain() {
		inited = false;
		init();
	}

}
