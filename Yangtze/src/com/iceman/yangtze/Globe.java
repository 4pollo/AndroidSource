
package com.iceman.yangtze;

import java.util.ArrayList;

import com.iceman.yangtze.net.NetClient;

/**
 * @author iceman 一些静态全局变量
 */
public class Globe {
    public static NetClient sNetClient;

    public static String sCookieString;

    public static int sVersionInt = 3;// 表示2.2版本

    public static String sName, sId, sClassName;

    public static ArrayList<String[]> sHideParams = new ArrayList<String[]>();

    public static void clearAll() {
        sNetClient = null;
        sCookieString = null;
        sName = null;
        sId = null;
        sClassName = null;
        sHideParams.clear();
    }
}
