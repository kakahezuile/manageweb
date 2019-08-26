<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Properties properties = PropertyTool
			.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<script type="text/javascript">
	$(function() {
		$("#right_ul > li a")
				.click(
						function() {

							$(this).addClass("select").parent().siblings("li")
									.find("a").removeClass("select");
							setNone();
							if (this.id == "yezhuzixun") {
								console.info("去业主咨询页");
								$("#accordion1").html("所有业主");
								$("#yezhu_div_id").attr("style",
										"display:block");
								usersOrShops = 0;
								$("#contactlistUL li").remove();
								$("#mychat_user_title").html("");
								$("#null-nouser").html("");
								addHomeUserList();
							} else if (this.id == "dianjiazixun") {
								console.info("店家咨询页");
								$("#accordion1").html("所有店家");
								$("#yezhu_div_id").attr("style",
										"display:block");
								usersOrShops = 1;
								$("#contactlistUL li").remove();
								$("#null-nouser").html("");
								$("#mychat_user_title").html("");
								addHomeUserList();
							} else if (this.id == "gonggao") {
								console.info("公告通知");
								
						/*		$("#gonggao_div_id").attr("style",
										"display:block");
								getFaBuList();*/
								
								notice_notice_all_jsp();
							} else if (this.id == "tousu") {
								console.info("投诉处理");
								$("#tousu_div_id").attr("style",
										"display:block");
								$("#tousu_contactlistUL li").remove();
								getComplaintList();
							} else if (this.id == "jiaofei") {
								console.info("物业缴费");
								$("#jiaofei_div_id").attr("style",
										"display:block");
								getPayTop(jiaofei_type, jiaofei_status);
								getNewPayList(jiaofei_type, jiaofei_status);
							} else if (this.id == "zhoubian") {
								console.info("周边商铺");
								$("#zhoubian_div_id").attr("style",
										"display:block");
							} else if (this.id == "qianyue") {
								console.info("签约店家");
								$("#qianyue_div_id").attr("style",
										"display:block");
								getServiceCategoryList();
								getShopsList("");
								getServiceCategory();
							} else if (this.id == "huodong") {
								console.info("活动管理");
								$("#activities_div_id").attr("style",
										"display:block");

								getActivitiesList("");

							} else if (this.id == "community_service") {
								console.info("物业管理");
								$("#community_service_div_id").attr("style",
										"display:block");
                                 dataDay();
								overman();
								maintainData();
								//communityServiceGetItemCategory(5);
							} else if (this.id == "life_page") {
								console.info("生活黄页");
								//		$("#life_page_div_id").attr("style",
								//				"display:block");
								$("#shops_phone_id_fei").attr("style",
										"display:block");

								shopsPhoneLeft("shops_phone_1_id");
							} else if (this.id == "bangbang_juan") {
								$("#bangbang_juan_div_id").attr("style",
										"display:block");
								$("#create_bangbang_td").attr("style",
										"display:block");
								bangBangGetServicesList();
							}

						});

		//$("#tousu_lie").click(function(){
		//setNone();
		//$("#tousu_lie_div_id").attr("style","display:block");

		//});
	});
	
	 function dataDay(){
	
     	var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var day = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getMonth();
				
		var to_day_fei=	document.getElementsByName("to_day_fei");
		
		for(var i=0;i<to_day_fei.length;i++){
		    to_day_fei[i].innerHTML=day;
		
		}
     
     }
	function setNone() {
		$("#yezhu_div_id").attr("style", "display:none");
		$("#dianjia_div_id").attr("style", "display:none");
		$("#gonggao_div_id").attr("style", "display:none");
		$("#tousu_div_id").attr("style", "display:none");
		$("#zhoubian_div_id").attr("style", "display:none");
		$("#qianyue_div_id").attr("style", "display:none");
		$("#jiaofei_div_id").attr("style", "display:none");
		$("#rule_div_id").attr("style", "display:none");
		$("#tousu_lie_div_id").attr("style", "display:none");
		$("#activities_div_id").attr("style", "display:none");
		$("#community_service_div_id").attr("style", "display:none");
		$("#life_page_div_id").attr("style", "display:none");
		$("#community_cleaning_div_id").attr("style", "display:none");
		$("#express_div_id").attr("style", "display:none");
		$("#bangbang_juan_div_id").attr("style", "display:none");
		$("#create_bangbang_td").attr("style", "display:none");
		$("#send_bangbang_td").attr("style", "display:none");
		$("#repair_price_div_id").attr("style", "display:none");
		$("#add_master_div_id").attr("style", "display:none");
		$("#repair_price_div_id_fei").attr("style", "display:none");
		$("#master_repair_datail_id_fei").attr("style", "display:none");
		$("#experss_id_fei").attr("style", "display:none");
		$("#experss_place_id_fei").attr("style", "display:none");
		$("#shops_phone_id_fei").attr("style", "display:none");
		$("#shops_edit_id_fei").attr("style", "display:none");
		$("#shops_phone_express_id_fei").attr("style", "display:none");
		$("#shops_phone_clean_id_fei").attr("style", "display:none");

		$("#payment_edit_div_id").attr("style", "display:none");
		$("#add_clean_fei").attr("style", "display:none");
		$("#nearby_home_fei").attr("style", "display:none");
		$("#record_bangbang_td").attr("style", "display:none");
		$("#password_home_id").attr("style", "display:none");
		$("#notice_notice_all_jsp").attr("style", "display:none");

	}
	
	function notice_notice_all_jsp(){
	
	setNone();
	$("#notice_notice_all_jsp").attr("style", "display:block");
	}

	function paymentEditClick() {
		getStandard(1);
		getStandardEntry(1);
		setNone();

		$("#payment_edit_div_id").attr("style", "display:block");

	}

	function addRule() {
		setNone();
		$("#rule_div_id").attr("style", "display:block");
	}

	function weiXiuClick() {
		setNone();
		$("#community_service_div_id").attr("style", "display:block");
		communityServiceGetItemCategory(5);
	}

	function baoJieClick() {
		setNone();
		$("#community_cleaning_div_id").attr("style", "display:block");
	}

	function kuaiDiClick() {
		setNone();
		$("#express_div_id").attr("style", "display:block");
		$("#express_table").html("");
		getExpressList("-1");
	}

	function bangbang_send_bangbang_sub() {
		setNone();
		$("#bangbang_juan_div_id").attr("style", "display:block");
		$("#send_bangbang_td").attr("style", "display:block");
		getBonusList();
	}

	function bangbang_create_bangbang_sub() {
		setNone();
		$("#bangbang_juan_div_id").attr("style", "display:block");
		$("#create_bangbang_td").attr("style", "display:block");
	}
	function bangbang_record_bangbang_sub() {
		setNone();
		$("#bangbang_juan_div_id").attr("style", "display:block");
		$("#record_bangbang_td").attr("style", "display:block");
		record_bonus(1);
	}

	function repairPriceUpdateJsp() {
		setNone();
		$("#repair_price_div_id").attr("style", "display:block");
	}

	function addMasterJsp() {
		setNone();
		addMasterGetItemCategory(5);
		$("#add_master_div_id").attr("style", "display:block");
		 document.getElementById("clean_add_fei").reset();
		 document.getElementById("clean_none").style.display="";
	    add_none();
	}
	function addCleanJsp() {
		setNone();
		$("#add_clean_fei").attr("style", "display:block");
	}

	function repairRecordFei(sort) {
		setNone();
		$("#repair_price_div_id_fei").attr("style", "display:block");
		maintainDataRecord(sort);
	}
	function masterRepairDatailIdFei(shopId, sort, isComplaints) {
		setNone();
		$("#master_repair_datail_id_fei").attr("style", "display:block");

		masterRepairDatailId3("", "", shopId, sort, isComplaints);

	}
	function experss_id_fei() {
		setNone();
		$("#experss_id_fei").attr("style", "display:block");
		getExpressList(-1);
	}
	function experss_repair_id_fei() {
		setNone();
		$("#experss_place_id_fei").attr("style", "display:block");
		express_place_fei_jsp();
	}
	function shops_phone_clean_id_fei() {
		setNone();
		$("#shops_phone_clean_id_fei").attr("style", "display:block");
		overmanClean();
		maintainCleanData();
	}

	//扩展Date的format方法   
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S" : this.getMilliseconds()
		};
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	};
	/**   
	 *转换日期对象为日期字符串   
	 * @param date 日期对象   
	 * @param isFull 是否为完整的日期数据,   
	 *               为true时, 格式如"2000-03-05 01:05:04"   
	 *               为false时, 格式如 "2000-03-05"   
	 * @return 符合要求的日期字符串   
	 */
	function getSmpFormatDate(date, isFull) {
		var pattern = "";
		if (isFull == true || isFull == undefined) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		} else {
			pattern = "yyyy-MM-dd";
		}
		return getFormatDate(date, pattern);
	}
	/**   
	 *转换当前日期对象为日期字符串   
	 * @param date 日期对象   
	 * @param isFull 是否为完整的日期数据,   
	 *               为true时, 格式如"2000-03-05 01:05:04"   
	 *               为false时, 格式如 "2000-03-05"   
	 * @return 符合要求的日期字符串   
	 */

	function getSmpFormatNowDate(isFull) {
		return getSmpFormatDate(new Date(), isFull);
	}
	/**   
	 *转换long值为日期字符串   
	 * @param l long值   
	 * @param isFull 是否为完整的日期数据,   
	 *               为true时, 格式如"2000-03-05 01:05:04"   
	 *               为false时, 格式如 "2000-03-05"   
	 * @return 符合要求的日期字符串   
	 */

	function getSmpFormatDateByLong(l, isFull) {
		return getSmpFormatDate(new Date(l), isFull);
	}
	/**   
	 *转换long值为日期字符串   
	 * @param l long值   
	 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
	 * @return 符合要求的日期字符串   
	 */

	function getFormatDateByLong(l, pattern) {
		return getFormatDate(new Date(l), pattern);
	}
	/**   
	 *转换日期对象为日期字符串   
	 * @param l long值   
	 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
	 * @return 符合要求的日期字符串   
	 */
	function getFormatDate(date, pattern) {
		if (date == undefined) {
			date = new Date();
		}
		if (pattern == undefined) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		}

		return date.format(pattern);
	}
	function repairPriceUpdateJsp() {
		setNone();
		$("#repair_price_div_id").attr("style", "display:block");
		repairPriceGetItemCategory(5);
	}

    
   
/*	function addMasterJsp() {
		setNone();
		addMasterGetItemCategory(5);
		$("#add_master_div_id").attr("style", "display:block");
	}*/
</script>
<nav class="navbar">
<ul class="clearfix" id="right_ul">
	<c:if test="${!empty list }">
		<c:forEach items="${list }" varStatus="status" var="item">
		
			<c:if test="${status.index == 0 }">
				<li>
					<a href="javascript:void(0);" id="${item.navId }" class="select"
						mark="${item.navId }">${item.modelName }</a>
				</li>
			</c:if>

			<c:if test="${status.index != 0 }">
				<li>
					<a href="javascript:void(0);" id="${item.navId }"
						mark="${item.navId }">${item.modelName }</a>
				</li>
			</c:if>
		
		</c:forEach>
	</c:if>

</ul>
</nav>