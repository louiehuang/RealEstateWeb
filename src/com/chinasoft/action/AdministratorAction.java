package com.chinasoft.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.Var;

import com.chinasoft.pojo.Advertisement;
import com.chinasoft.pojo.HouseSellEnterprise;
import com.chinasoft.pojo.HouseSellRent;
import com.chinasoft.pojo.HouseSellSecondhand;
import com.chinasoft.pojo.Users;
import com.chinasoft.pojo.Verification;
import com.chinasoft.service.AdvertisementService;
import com.chinasoft.service.HouseSellEnterpriseService;
import com.chinasoft.service.HouseSellRentService;
import com.chinasoft.service.HouseSellSecondhandService;
import com.chinasoft.service.VerificationService;

public class AdministratorAction {
	/* 查询房屋 */
	private HouseSellEnterpriseService houseSellEnterpriseService;
	private HouseSellSecondhandService houseSellSecondhandService;
	private HouseSellRentService houseSellRentService;

	private Map<String, Object> dataMap_AllHouse;
	private String HId;
	private int house_pageIndex = 1;
	private int house_pageSize = 10; // 默认分页大小
	private int house_pageCount = 0;
	private String house_queryMode;

	/* 查询验证 */
	private VerificationService verificationService;
	private Map<String, Object> dataMap_Veri;
	private int veri_pageIndex = 1;
	private int veri_pageSize = 10; // 默认分页大小
	private int veri_pageCount = 0;
	private String veri_queryMode;
	private String VId; // 验证的id
	private Verification veri; // 验证对象
	private String processRes; // 处理结果
	
	
	/*广告管理*/
	private AdvertisementService advertisementService;
	private Map<String, Object> dataMap_Ad;
	private int ad_pageIndex = 1;
	private int ad_pageSize = 10; // 默认分页大小
	private int ad_pageCount = 0;
	private String AId; // 验证的id
	private Advertisement ad; // 验证对象
	
	
	
	
	
	

	public String json_deleteHouse() {
		System.out.println("json_deleteHouse执行, 删除 " + HId);

		try {
			if (house_queryMode.equals("HouseSellEnterprise")) {
				HouseSellEnterprise house = houseSellEnterpriseService
						.findById(Integer.parseInt(HId));
				houseSellEnterpriseService.delete(house);
				// 删除后更新前台企业新房表显示
				String update = json_queryAllHouse(); // 不需要接收返回值
			} else if (house_queryMode.equals("HouseSellSecondhand")) {
				HouseSellSecondhand house = houseSellSecondhandService
						.findById(Integer.parseInt(HId));
				houseSellSecondhandService.delete(house);
				// 删除后更新前台二手房表显示
				String update = json_queryAllHouse(); // 不需要接收返回值
			} else if (house_queryMode.equals("HouseSellRent")) {
				HouseSellRent house = houseSellRentService.findById(Integer
						.parseInt(HId));
				houseSellRentService.delete(house);
				// 删除后更新前台二手房表显示
				String update = json_queryAllHouse(); // 不需要接收返回值
			}
			dataMap_AllHouse.put("deleteHouse_success", true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return "deleteHouse_success";
	}

	public String json_queryAllHouse() {
		System.out.println("json_queryAllHouse执行, 查询:" + house_queryMode);
		/* 判断查询类型 */

		try {
			dataMap_AllHouse = new HashMap<String, Object>();

			if (house_queryMode.equals("HouseSellEnterprise")) {
				List<HouseSellEnterprise> enterList = houseSellEnterpriseService
						.findAll();
				enterList = cutPage(enterList, house_pageIndex, house_pageSize); // 分页
				dataMap_AllHouse.put("enterList", enterList);
			} else if (house_queryMode.equals("HouseSellSecondhand")) {
				List<HouseSellSecondhand> secondList = houseSellSecondhandService
						.findAll();
				secondList = cutPage(secondList, house_pageIndex,
						house_pageSize); // 分页
				dataMap_AllHouse.put("secondList", secondList);
			} else if (house_queryMode.equals("HouseSellRent")) {
				List<HouseSellRent> rentList = houseSellRentService.findAll();
				rentList = cutPage(rentList, house_pageIndex, house_pageSize); // 分页
				dataMap_AllHouse.put("rentList", rentList);

			}

			dataMap_AllHouse.put("house_pageIndex", house_pageIndex);
			dataMap_AllHouse.put("house_pageSize", house_pageSize);
			dataMap_AllHouse.put("house_pageCount", house_pageCount);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回结果
		return "findAllHouse_success";
	}

	
	
	/**
	 * 根据当前所在页 和 每页大小进行分页
	 * 
	 * @param list
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List cutPage(List list, int pageIndex, int pageSize) {
		List newList = new ArrayList();

		if (list != null) {
			if (list.size() % pageSize == 0) {
				house_pageCount = list.size() / pageSize;
			} else {
				house_pageCount = list.size() / pageSize + 1;
			}

			int start = (pageIndex - 1) * pageSize;
			int end = pageIndex * pageSize;
			if (end > list.size()) {
				end = list.size();
			}

			for (int i = start; i < end; i++) {
				newList.add(list.get(i));
			}
		}

		return newList;
	}

	public String json_queryVeri() {
		System.out.println("json_queryVeri执行, 查询:" + veri_queryMode);
		/* 判断查询类型 */

		try {
			dataMap_Veri = new HashMap<String, Object>();

			if (veri_queryMode.equals("queryVerificaton")) {
				int status = 0; //查询未处理的
				List<Verification> unprocessedList = verificationService
						.findByVstatus(status);
				unprocessedList = cutVeriPage(unprocessedList, veri_pageIndex,
						veri_pageSize); // 分页
				dataMap_Veri.put("unprocessedList", unprocessedList);
			} else if (veri_queryMode.equals("queryVerificaton_processed")) {
				int status = 1;
				List<Verification> processedList = verificationService
						.findByVstatus(status);
				processedList = cutVeriPage(processedList, veri_pageIndex,
						veri_pageSize); // 分页
				dataMap_Veri.put("processedList", processedList);
			}

			dataMap_Veri.put("veri_pageIndex", veri_pageIndex);
			dataMap_Veri.put("veri_pageSize", veri_pageSize);
			dataMap_Veri.put("veri_pageCount", veri_pageCount);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回结果
		return "findVeri_success";
	}

	public List cutVeriPage(List list, int pageIndex, int pageSize) {
		List newList = new ArrayList();

		if (list != null) {
			if (list.size() % pageSize == 0) {
				veri_pageCount = list.size() / pageSize;
			} else {
				veri_pageCount = list.size() / pageSize + 1;
			}

			int start = (pageIndex - 1) * pageSize;
			int end = pageIndex * pageSize;
			if (end > list.size()) {
				end = list.size();
			}

			for (int i = start; i < end; i++) {
				newList.add(list.get(i));
			}
		}

		return newList;
	}

	/**
	 * 更新验证处理结果，并更新相应状态（结果设为未处理时状态置为未处理0）
	 * @return
	 */
	public String json_updateVeri() {
		System.out.println("json_updateVeri执行...");
		try {
			Verification veri = verificationService.findById(Integer
					.parseInt(VId));

			System.out.println(processRes);
			
			if(processRes.equals("未处理")){
				veri.setVres(0);
				veri.setVstatus(0); //处理状态置为未处理
			}else if(processRes.equals("已通过")){
				veri.setVres(1);
				veri.setVstatus(1); //处理状态置为处理
			}else if(processRes.equals("不通过")){
				veri.setVres(2);
				veri.setVstatus(1); //处理状态置为处理
			}

			verificationService.update(veri);
			String update = json_queryVeri(); // 调用json_queryVeri更新显示
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateVeri_success";
	}

	
	
	
	/*广告管理*/
	public String json_querySingleAd(){
		System.out.println("json_querySingleAd执行: " + AId);
		
		try {
			dataMap_Ad = new HashMap<String, Object>();
			// List list = usersService.findByUAccount(uAccount);
			Advertisement res = advertisementService.findById(Integer.parseInt(AId));
			dataMap_Ad.put("ad", res);
			dataMap_Ad.put("success_queryUser", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return "findSignleAd_success";
	}
	
	
	
	/**
	 * 查询所有广告
	 * @return
	 */
	public String json_queryAllAd() {
		System.out.println("json_queryAllAd执行...");
		/* 判断查询类型 */

		try {
			dataMap_Ad = new HashMap<String, Object>();
			List<Advertisement> adList = advertisementService.findAll();
			adList = cutAdPage(adList, ad_pageIndex, ad_pageSize); // 分页
			
			dataMap_Ad.put("adList", adList);
			dataMap_Ad.put("ad_pageIndex", ad_pageIndex);
			dataMap_Ad.put("ad_pageSize", ad_pageSize);
			dataMap_Ad.put("ad_pageCount", ad_pageCount);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回结果
		return "findAllAd_success";
	}
	
	
	
	public List cutAdPage(List list, int pageIndex, int pageSize) {
		List newList = new ArrayList();

		if (list != null) {
			if (list.size() % pageSize == 0) {
				ad_pageCount = list.size() / pageSize;
			} else {
				ad_pageCount = list.size() / pageSize + 1;
			}

			int start = (pageIndex - 1) * pageSize;
			int end = pageIndex * pageSize;
			if (end > list.size()) {
				end = list.size();
			}

			for (int i = start; i < end; i++) {
				newList.add(list.get(i));
			}
		}

		return newList;
	}
	
	
	
	
	
	
	
	public HouseSellRentService getHouseSellRentService() {
		return houseSellRentService;
	}

	public void setHouseSellRentService(
			HouseSellRentService houseSellRentService) {
		this.houseSellRentService = houseSellRentService;
	}

	public HouseSellSecondhandService getHouseSellSecondhandService() {
		return houseSellSecondhandService;
	}

	public void setHouseSellSecondhandService(
			HouseSellSecondhandService houseSellSecondhandService) {
		this.houseSellSecondhandService = houseSellSecondhandService;
	}

	public HouseSellEnterpriseService getHouseSellEnterpriseService() {
		return houseSellEnterpriseService;
	}

	public void setHouseSellEnterpriseService(
			HouseSellEnterpriseService houseSellEnterpriseService) {
		this.houseSellEnterpriseService = houseSellEnterpriseService;
	}

	public Map<String, Object> getDataMap_AllHouse() {
		return dataMap_AllHouse;
	}

	public void setDataMap_AllHouse(Map<String, Object> dataMap_AllHouse) {
		this.dataMap_AllHouse = dataMap_AllHouse;
	}

	public String getHId() {
		return HId;
	}

	public void setHId(String hId) {
		HId = hId;
	}

	public int getHouse_pageIndex() {
		return house_pageIndex;
	}

	public void setHouse_pageIndex(int house_pageIndex) {
		this.house_pageIndex = house_pageIndex;
	}

	public int getHouse_pageSize() {
		return house_pageSize;
	}

	public void setHouse_pageSize(int house_pageSize) {
		this.house_pageSize = house_pageSize;
	}

	public int getHouse_pageCount() {
		return house_pageCount;
	}

	public void setHouse_pageCount(int house_pageCount) {
		this.house_pageCount = house_pageCount;
	}

	public String getHouse_queryMode() {
		return house_queryMode;
	}

	public void setHouse_queryMode(String house_queryMode) {
		this.house_queryMode = house_queryMode;
	}

	/**/
	public VerificationService getVerificationService() {
		return verificationService;
	}

	public void setVerificationService(VerificationService verificationService) {
		this.verificationService = verificationService;
	}

	public Map<String, Object> getDataMap_Veri() {
		return dataMap_Veri;
	}

	public void setDataMap_Veri(Map<String, Object> dataMap_Veri) {
		this.dataMap_Veri = dataMap_Veri;
	}

	public int getVeri_pageIndex() {
		return veri_pageIndex;
	}

	public void setVeri_pageIndex(int veri_pageIndex) {
		this.veri_pageIndex = veri_pageIndex;
	}

	public int getVeri_pageSize() {
		return veri_pageSize;
	}

	public void setVeri_pageSize(int veri_pageSize) {
		this.veri_pageSize = veri_pageSize;
	}

	public int getVeri_pageCount() {
		return veri_pageCount;
	}

	public void setVeri_pageCount(int veri_pageCount) {
		this.veri_pageCount = veri_pageCount;
	}

	public String getVeri_queryMode() {
		return veri_queryMode;
	}

	public void setVeri_queryMode(String veri_queryMode) {
		this.veri_queryMode = veri_queryMode;
	}

	public String getVId() {
		return VId;
	}

	public void setVId(String vId) {
		VId = vId;
	}

	public Verification getVeri() {
		return veri;
	}

	public void setVeri(Verification veri) {
		this.veri = veri;
	}

	public String getProcessRes() {
		return processRes;
	}

	public void setProcessRes(String processRes) {
		this.processRes = processRes;
	}
	
	
	
	/*广告管理*/
	public AdvertisementService getAdvertisementService() {
		return advertisementService;
	}

	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}

	public Map<String, Object> getDataMap_Ad() {
		return dataMap_Ad;
	}

	public void setDataMap_Ad(Map<String, Object> dataMap_Ad) {
		this.dataMap_Ad = dataMap_Ad;
	}

	public int getAd_pageIndex() {
		return ad_pageIndex;
	}

	public void setAd_pageIndex(int ad_pageIndex) {
		this.ad_pageIndex = ad_pageIndex;
	}

	public int getAd_pageSize() {
		return ad_pageSize;
	}

	public void setAd_pageSize(int ad_pageSize) {
		this.ad_pageSize = ad_pageSize;
	}

	public int getAd_pageCount() {
		return ad_pageCount;
	}

	public void setAd_pageCount(int ad_pageCount) {
		this.ad_pageCount = ad_pageCount;
	}

	public String getAId() {
		return AId;
	}

	public void setAId(String aId) {
		AId = aId;
	}

	public Advertisement getAd() {
		return ad;
	}

	public void setAd(Advertisement ad) {
		this.ad = ad;
	}
	
	
	
	

}
