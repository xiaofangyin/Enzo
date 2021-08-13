package com.enzo.main.config;

import android.content.Context;
import android.content.Intent;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.main.R;
import com.enzo.main.model.bean.LeftMenuChildBean;
import com.enzo.main.model.bean.LeftMenuParentBean;
import com.enzo.main.ui.activity.SAAddDeviceActivity;
import com.enzo.main.ui.activity.SAAddDeviceActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: LeftMenuConfig
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SALeftMenuConfig {

    //企业查询
    private static final int ID_CHAXUN_SHENGCHANG_QIYE = 1001;//生产企业
    private static final int ID_CHAXUN_JINGYING_QIYE = 1002;//经营企业
    private static final int ID_CHAXUN_SHEBEI_SHANGKU = 1003;//设备商库
    private static final int ID_CHAXUN_YUANLIAO_SHANGKU = 1004;//原料商库
    private static final int ID_CHAXUN_GUIHUA_SHEJI = 1005;//规划设计
    private static final int ID_CHAXUN_SHIPING = 1006;//食品

    //标签管理
    private static final int ID_GUANLI_SHANGCHUAN_SHENHE = 2001;//上传审核
    private static final int ID_GUANLI_SHENHE_LIEBIAO = 2002;//审核列表

    //抽检引导
    private static final int ID_CHOUJIAN_LIEDA = 3001;//抽检雷达
    private static final int ID_CHOUJIAN_GONGSHI = 3002;//抽检公示
    private static final int ID_CHOUJIAN_FUJIAN_JIGOU = 3003;//复检机构
    private static final int ID_CHOUJIAN_JIANYAN_JIGOU = 3004;//检验机构

    //食安学苑
    private static final int ID_SHIAN_XUEYUAN = 4001;//食安学苑

    //食品行业认证
    private static final int ID_RENZHENG_FAGUI_BIAOZHUN = 5001;//法规与标准
    private static final int ID_RENZHENG_JIGOU_RENZHENG = 5002;//机构认证

    //我的信息
    private static final int ID_WODE_ZIZHANGHAO = 6001;//子账号
    private static final int ID_WODE_YUANLIAOSHANG = 6002;//我的原料商
    private static final int ID_WODE_XUKEZHENG = 6003;//我的许可证
    private static final int ID_WODE_SHIPING = 6004;//我的食品
    private static final int ID_WODE_KECHENG = 6005;//我的课程
    private static final int ID_WODE_JINGXIAOSHANG = 6006;//我的经销商
    private static final int ID_WODE_GONGHUOSHANG = 6007;//我的供货商
    private static final int ID_WODE_CHOUJIAN = 6008;//我的抽检
    private static final int ID_WODE_JIBEN_XINXI = 6009;//我的基本信息

    public static void launch(Context context, LeftMenuChildBean model) {
        LogUtil.d("name: " + model.getName() + "...id: " + model.getId());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (model.getId()) {
            case ID_CHAXUN_SHENGCHANG_QIYE://生产企业
                intent.setClass(context, SAAddDeviceActivity2.class);
                context.startActivity(intent);
                break;
            case ID_CHAXUN_JINGYING_QIYE://经营企业
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHAXUN_SHEBEI_SHANGKU://设备商库
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHAXUN_YUANLIAO_SHANGKU://原料商库
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHAXUN_GUIHUA_SHEJI://规划设计
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHAXUN_SHIPING://食品
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_GUANLI_SHANGCHUAN_SHENHE://上传审核
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_GUANLI_SHENHE_LIEBIAO://审核列表
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHOUJIAN_LIEDA://抽检雷达
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHOUJIAN_GONGSHI://抽检公示
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHOUJIAN_JIANYAN_JIGOU://检验机构
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_CHOUJIAN_FUJIAN_JIGOU://复检机构
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_ZIZHANGHAO://子账号
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_SHIAN_XUEYUAN://食安学苑
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_RENZHENG_FAGUI_BIAOZHUN://法规与标准
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_RENZHENG_JIGOU_RENZHENG://机构认证
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_YUANLIAOSHANG://我的原料商
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_XUKEZHENG://我的许可证
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_SHIPING://我的食品
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_KECHENG://我的课程
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_JINGXIAOSHANG://我的经销商
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_GONGHUOSHANG://我的供货商
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_CHOUJIAN://我的抽检
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
            case ID_WODE_JIBEN_XINXI://基本信息
                intent.setClass(context, SAAddDeviceActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    public static List<LeftMenuParentBean> getMenuList() {
        List<LeftMenuParentBean> list = new ArrayList<>();

        LeftMenuParentBean bean1 = new LeftMenuParentBean();
        bean1.setEnable(true);
        bean1.setIconWhite(R.mipmap.icon_function_qiye_chaxun_white);
        bean1.setIconBlue(R.mipmap.icon_function_qiye_chaxun_blue);
        bean1.setName("企业查询");
        bean1.setChildList(getCompanyChildList());

        LeftMenuParentBean bean2 = new LeftMenuParentBean();
        bean2.setEnable(true);
        bean2.setIconWhite(R.mipmap.icon_function_biaoqian_guanli_white);
        bean2.setIconBlue(R.mipmap.icon_function_biaoqian_guanli_blue);
        bean2.setName("标签管理");
        bean2.setChildList(getLabelChildList());

        LeftMenuParentBean bean3 = new LeftMenuParentBean();
        bean3.setEnable(true);
        bean3.setIconWhite(R.mipmap.icon_function_choujian_yindao_white);
        bean3.setIconBlue(R.mipmap.icon_function_choujian_yindao_blue);
        bean3.setName("抽检引导");
        bean3.setChildList(getYinDaoChildList());

        LeftMenuParentBean bean4 = new LeftMenuParentBean();
        bean4.setEnable(true);
        bean4.setIconWhite(R.mipmap.icon_function_shian_xueyuan_white);
        bean4.setIconBlue(R.mipmap.icon_function_shian_xueyuan_blue);
        bean4.setName("食安学苑");
        bean4.setChildList(getCollegeChildList());

        LeftMenuParentBean bean5 = new LeftMenuParentBean();
        bean5.setEnable(true);
        bean5.setIconWhite(R.mipmap.icon_function_hangye_renzhen_white);
        bean5.setIconBlue(R.mipmap.icon_function_hangye_renzhen_blue);
        bean5.setName("食品行业认证");
        bean5.setChildList(getAuthChildList());

        LeftMenuParentBean bean6 = new LeftMenuParentBean();
        bean6.setEnable(true);
        bean6.setIconWhite(R.mipmap.icon_function_wode_xinxi_white);
        bean6.setIconBlue(R.mipmap.icon_function_wode_xinxi_blue);
        bean6.setName("我的信息");
        bean6.setChildList(getMineChildList());

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        return list;
    }

    /**
     * 企业查询
     */
    private static List<LeftMenuChildBean> getCompanyChildList() {
        List<LeftMenuChildBean> childList = new ArrayList<>();

        LeftMenuChildBean childBean1 = new LeftMenuChildBean();
        childBean1.setId(ID_CHAXUN_SHENGCHANG_QIYE);
        childBean1.setEnable(true);
        childBean1.setIconWhite(R.mipmap.icon_menu_company_search_2_company_white);
        childBean1.setIconBlack(R.mipmap.icon_menu_company_search_2_company_black);
        childBean1.setName("生产企业");

        LeftMenuChildBean childBean2 = new LeftMenuChildBean();
        childBean2.setEnable(true);
        childBean2.setId(ID_CHAXUN_JINGYING_QIYE);
        childBean2.setIconWhite(R.mipmap.icon_menu_company_search_3_business_white);
        childBean2.setIconBlack(R.mipmap.icon_menu_company_search_3_business_black);
        childBean2.setName("经营企业");

        LeftMenuChildBean childBean3 = new LeftMenuChildBean();
        childBean3.setId(ID_CHAXUN_SHIPING);
        childBean3.setEnable(true);
        childBean3.setIconWhite(R.mipmap.icon_menu_company_search_1_food_white);
        childBean3.setIconBlack(R.mipmap.icon_menu_company_search_1_food_black);
        childBean3.setName("食品查询");

        LeftMenuChildBean childBean4 = new LeftMenuChildBean();
        childBean4.setId(ID_CHAXUN_SHEBEI_SHANGKU);
        childBean4.setEnable(true);
        childBean4.setIconWhite(R.mipmap.icon_menu_company_search_4_equipment_white);
        childBean4.setIconBlack(R.mipmap.icon_menu_company_search_4_equipment_black);
        childBean4.setName("设备商库");

        LeftMenuChildBean childBean5 = new LeftMenuChildBean();
        childBean5.setId(ID_CHAXUN_YUANLIAO_SHANGKU);
        childBean5.setEnable(true);
        childBean5.setIconWhite(R.mipmap.icon_menu_company_search_5_material_white);
        childBean5.setIconBlack(R.mipmap.icon_menu_company_search_5_material_black);
        childBean5.setName("原料商库");

        LeftMenuChildBean childBean6 = new LeftMenuChildBean();
        childBean6.setId(ID_CHAXUN_GUIHUA_SHEJI);
        childBean6.setEnable(true);
        childBean6.setIconWhite(R.mipmap.icon_menu_company_search_6_plan_white);
        childBean6.setIconBlack(R.mipmap.icon_menu_company_search_6_plan_black);
        childBean6.setName("规划设计");

        childList.add(childBean1);
        childList.add(childBean2);
        childList.add(childBean3);
        childList.add(childBean4);
        childList.add(childBean5);
        childList.add(childBean6);
        return childList;
    }

    /**
     * 标签管理
     */
    private static List<LeftMenuChildBean> getLabelChildList() {
        List<LeftMenuChildBean> childList = new ArrayList<>();
        LeftMenuChildBean childBean1 = new LeftMenuChildBean();
        childBean1.setId(ID_GUANLI_SHANGCHUAN_SHENHE);
        childBean1.setEnable(true);
        childBean1.setIconWhite(R.mipmap.icon_menu_label_1_upload_verify_white);
        childBean1.setIconBlack(R.mipmap.icon_menu_label_1_upload_verify_black);
        childBean1.setName("上传审核");

        LeftMenuChildBean childBean2 = new LeftMenuChildBean();
        childBean2.setId(ID_GUANLI_SHENHE_LIEBIAO);
        childBean2.setEnable(true);
        childBean2.setIconWhite(R.mipmap.icon_menu_label_2_verify_list_white);
        childBean2.setIconBlack(R.mipmap.icon_menu_label_2_verify_list_black);
        childBean2.setName("审核列表");

        childList.add(childBean1);
        childList.add(childBean2);
        return childList;
    }

    /**
     * 抽检引导
     */
    private static List<LeftMenuChildBean> getYinDaoChildList() {
        List<LeftMenuChildBean> childList = new ArrayList<>();

        LeftMenuChildBean childBean1 = new LeftMenuChildBean();
        childBean1.setId(ID_CHOUJIAN_LIEDA);
        childBean1.setEnable(true);
        childBean1.setIconWhite(R.mipmap.icon_menu_yindao_2_choujian_leida_white);
        childBean1.setIconBlack(R.mipmap.icon_menu_yindao_2_choujian_leida_black);
        childBean1.setName("抽检雷达");

        LeftMenuChildBean childBean2 = new LeftMenuChildBean();
        childBean2.setId(ID_CHOUJIAN_GONGSHI);
        childBean2.setEnable(true);
        childBean2.setIconWhite(R.mipmap.icon_menu_yindao_1_choujian_gongshi_white);
        childBean2.setIconBlack(R.mipmap.icon_menu_yindao_1_choujian_gongshi_black);
        childBean2.setName("抽检公示");

        LeftMenuChildBean childBean3 = new LeftMenuChildBean();
        childBean3.setId(ID_CHOUJIAN_JIANYAN_JIGOU);
        childBean3.setEnable(true);
        childBean3.setIconWhite(R.mipmap.icon_menu_yindao_4_jianyan_jigou_white);
        childBean3.setIconBlack(R.mipmap.icon_menu_yindao_4_jianyan_jigou_black);
        childBean3.setName("检验机构");

        LeftMenuChildBean childBean4 = new LeftMenuChildBean();
        childBean4.setId(ID_CHOUJIAN_FUJIAN_JIGOU);
        childBean4.setEnable(true);
        childBean4.setIconWhite(R.mipmap.icon_menu_yindao_3_fujian_jigou_white);
        childBean4.setIconBlack(R.mipmap.icon_menu_yindao_3_fujian_jigou_black);
        childBean4.setName("复检机构");

        childList.add(childBean1);
        childList.add(childBean2);
        childList.add(childBean3);
        childList.add(childBean4);
        return childList;
    }

    /**
     * 食安学苑
     */
    private static List<LeftMenuChildBean> getCollegeChildList() {
        List<LeftMenuChildBean> childList = new ArrayList<>();
        LeftMenuChildBean childBean1 = new LeftMenuChildBean();
        childBean1.setId(ID_SHIAN_XUEYUAN);
        childBean1.setEnable(true);
        childBean1.setIconWhite(R.mipmap.icon_menu_shian_xueyuan_1_white);
        childBean1.setIconBlack(R.mipmap.icon_menu_shian_xueyuan_1_black);
        childBean1.setName("食安学苑");

        childList.add(childBean1);
        return childList;
    }

    private static List<LeftMenuChildBean> getAuthChildList() {
        List<LeftMenuChildBean> childList = new ArrayList<>();
        LeftMenuChildBean childBean1 = new LeftMenuChildBean();
        childBean1.setId(ID_RENZHENG_FAGUI_BIAOZHUN);
        childBean1.setEnable(true);
        childBean1.setIconWhite(R.mipmap.icon_menu_renzhen_1_fagui_biaozhun_white);
        childBean1.setIconBlack(R.mipmap.icon_menu_renzhen_1_fagui_biaozhun_black);
        childBean1.setName("法规与标准");

        LeftMenuChildBean childBean2 = new LeftMenuChildBean();
        childBean2.setId(ID_RENZHENG_JIGOU_RENZHENG);
        childBean2.setEnable(true);
        childBean2.setIconWhite(R.mipmap.icon_menu_renzhen_2_renzhen_jigou_white);
        childBean2.setIconBlack(R.mipmap.icon_menu_renzhen_2_renzhen_jigou_black);
        childBean2.setName("机构认证");

        childList.add(childBean1);
        childList.add(childBean2);
        return childList;
    }

    private static List<LeftMenuChildBean> getMineChildList() {
        List<LeftMenuChildBean> childList = new ArrayList<>();

        LeftMenuChildBean childBean1 = new LeftMenuChildBean();
        childBean1.setId(ID_WODE_JIBEN_XINXI);
        childBean1.setEnable(true);
        childBean1.setIconWhite(R.mipmap.icon_menu_mine_9_xinxi_white);
        childBean1.setIconBlack(R.mipmap.icon_menu_mine_9_xinxi_black);
        childBean1.setName("基本信息");

        LeftMenuChildBean childBean2 = new LeftMenuChildBean();
        childBean2.setId(ID_WODE_ZIZHANGHAO);
        childBean2.setEnable(true);
        childBean2.setIconWhite(R.mipmap.icon_menu_mine_1_zizhanghao_white);
        childBean2.setIconBlack(R.mipmap.icon_menu_mine_1_zizhanghao_black);
        childBean2.setName("子帐号管理");

        LeftMenuChildBean childBean3 = new LeftMenuChildBean();
        childBean3.setId(ID_WODE_CHOUJIAN);
        childBean3.setEnable(true);
        childBean3.setIconWhite(R.mipmap.icon_menu_mine_8_choujian_white);
        childBean3.setIconBlack(R.mipmap.icon_menu_mine_8_choujian_black);
        childBean3.setName("我的抽检");

        LeftMenuChildBean childBean4 = new LeftMenuChildBean();
        childBean4.setId(ID_WODE_XUKEZHENG);
        childBean4.setEnable(true);
        childBean4.setIconWhite(R.mipmap.icon_menu_mine_3_xukezhen_white);
        childBean4.setIconBlack(R.mipmap.icon_menu_mine_3_xukezhen_black);
        childBean4.setName("我的许可证");

        LeftMenuChildBean childBean6 = new LeftMenuChildBean();
        childBean6.setId(ID_WODE_JINGXIAOSHANG);
        childBean6.setEnable(true);
        childBean6.setIconWhite(R.mipmap.icon_menu_mine_6_jingxiaoshang_white);
        childBean6.setIconBlack(R.mipmap.icon_menu_mine_6_jingxiaoshang_black);
        childBean6.setName("我的经营企业");

        LeftMenuChildBean childBean7 = new LeftMenuChildBean();
        childBean7.setId(ID_WODE_YUANLIAOSHANG);
        childBean7.setEnable(true);
        childBean7.setIconWhite(R.mipmap.icon_menu_mine_2_yuanliaoshang_white);
        childBean7.setIconBlack(R.mipmap.icon_menu_mine_2_yuanliaoshang_black);
        childBean7.setName("我的原料商");

        LeftMenuChildBean childBean8 = new LeftMenuChildBean();
        childBean8.setId(ID_WODE_KECHENG);
        childBean8.setEnable(true);
        childBean8.setIconWhite(R.mipmap.icon_menu_mine_5_wode_kecheng_white);
        childBean8.setIconBlack(R.mipmap.icon_menu_mine_5_wode_kecheng_black);
        childBean8.setName("我的课程");

        LeftMenuChildBean childBean9 = new LeftMenuChildBean();
        childBean9.setId(ID_WODE_GONGHUOSHANG);
        childBean9.setEnable(true);
        childBean9.setIconWhite(R.mipmap.icon_menu_mine_7_gonghuoshang_white);
        childBean9.setIconBlack(R.mipmap.icon_menu_mine_7_gonghuoshang_black);
        childBean9.setName("我的供货商");

        LeftMenuChildBean childBean10 = new LeftMenuChildBean();
        childBean10.setId(ID_WODE_SHIPING);
        childBean10.setEnable(true);
        childBean10.setIconWhite(R.mipmap.icon_menu_mine_4_shiping_white);
        childBean10.setIconBlack(R.mipmap.icon_menu_mine_4_shiping_black);
        childBean10.setName("我的食品");

        childList.add(childBean1);
        childList.add(childBean2);
        childList.add(childBean3);
        childList.add(childBean4);
        childList.add(childBean6);
        childList.add(childBean10);
        return childList;
    }
}
