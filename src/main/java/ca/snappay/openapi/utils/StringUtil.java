package com.wiseasy.openapi.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {
    private static String hexString = "0123456789ABCDEF";

    public StringUtil() {
    }

    public static String encode(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for(int i = 0; i < bytes.length; ++i) {
            sb.append(hexString.charAt((bytes[i] & 240) >> 4));
            sb.append(hexString.charAt((bytes[i] & 15) >> 0));
        }

        return sb.toString();
    }

    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);

        for(int i = 0; i < bytes.length(); i += 2) {
            baos.write(hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1)));
        }

        return new String(baos.toByteArray());
    }

    public static String bin2hex(String bin) {
        char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = bin.getBytes();

        for(int i = 0; i < bs.length; ++i) {
            int bit = (bs[i] & 240) >> 4;
            sb.append(digital[bit]);
            bit = bs[i] & 15;
            sb.append(digital[bit]);
        }

        return sb.toString();
    }

    public static String hex2bin(String hex) {
        String digital = hexString;
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte)(temp & 255);
        }

        return new String(bytes);
    }

    public static byte[] hex2byte(String str) {
        byte[] src = str.toLowerCase().getBytes();
        byte[] ret = new byte[src.length / 2];

        for(int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi = (byte)(hi >= 97 && hi <= 102 ? 10 + (hi - 97) : hi - 48);
            low = (byte)(low >= 97 && low <= 102 ? 10 + (low - 97) : low - 48);
            ret[i / 2] = (byte)(hi << 4 | low);
        }

        return ret;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                sign.append("0");
            }

            sign.append(hex);
        }

        return sign.toString();
    }

    public static String padLeft(String str, int len, char c) {
        int strLen;
        if (str == null) {
            for(strLen = len; strLen-- != 0; str = c + str) {
                ;
            }

            return str;
        } else {
            strLen = str.length();
            if (strLen < len) {
                for(int var4 = len - strLen; var4-- != 0; str = c + str) {
                    ;
                }
            }

            return str;
        }
    }

    public static String padRight(String str, int len, char c) {
        int strLen;
        if (str == null) {
            for(strLen = len; strLen-- != 0; str = str + c) {
                ;
            }

            return str;
        } else {
            strLen = str.length();
            if (strLen < len) {
                for(int var4 = len - strLen; var4-- != 0; str = str + c) {
                    ;
                }
            }

            return str;
        }
    }

    public static String array2String(String[] array) {
        String result = "";
        if (array != null && array.length >= 1) {
            StringBuffer tmp = new StringBuffer();
            String[] var3 = array;
            int var4 = array.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String tmps = var3[var5];
                tmp.append(tmps.trim());
            }

            tmp.deleteCharAt(tmp.length() - 1);
            return tmp.toString();
        } else {
            return result;
        }
    }

    public static String list2String(List<String> list) {
        String result = "";
        if (list != null && list.size() >= 1) {
            StringBuffer tmp = new StringBuffer();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                String tmps = (String)var3.next();
                tmp.append(tmps.trim());
            }

            tmp.deleteCharAt(tmp.length() - 1);
            return tmp.toString();
        } else {
            return result;
        }
    }

    public static String[] string2Array(String str) {
        String[] tmpArray = new String[0];
        if (str != null && !"".equals(str.trim())) {
            tmpArray = str.split(",");
        }

        return tmpArray;
    }

    public static List<String> string2List(String str) {
        List<String> tmpList = new ArrayList();
        if (str != null && !"".equals(str.trim())) {
            tmpList = Arrays.asList(str.split(","));
        }

        return (List)tmpList;
    }

    public static List<Map<String, Object>> lowerCaseParams(List<Map<String, Object>> list) {
        List<Map<String, Object>> ret = new ArrayList();
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            Map<String, Object> map = (Map)var2.next();
            ret.add(lowerCaseParams(map));
        }

        return ret;
    }

    public static Map<String, Object> lowerCaseParams(Map<String, Object> map) {
        Map<String, Object> ret = new HashMap();
        Iterator var2 = map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            Object value = map.get(key);
            key = lowerCaseData(key);
            ret.put(key, value);
        }

        return ret;
    }

    public static String lowerCaseData(String data) {
        String ret = "";
        String[] datas = data.toLowerCase().split("_");
        if (datas.length > 0) {
            ret = datas[0];
        }

        for(int i = 1; i < datas.length; ++i) {
            ret = ret + upCaseFirstChar(datas[i]);
        }

        return ret;
    }

    public static String upCaseFirstChar(String data) {
        if (data.length() > 0) {
            data = data.substring(0, 1).toUpperCase() + data.substring(1);
        }

        return data;
    }

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmpty(String value) {
        return !isNotEmpty(value);
    }

    public static boolean isNotEmpty(String value) {
        int strLen;
        if (value != null && (strLen = value.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(value.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        } else {
            char[] chars = obj.toString().toCharArray();
            int length = chars.length;
            if (length < 1) {
                return false;
            } else {
                int i = 0;
                if (length > 1 && chars[0] == '-') {
                    i = 1;
                }

                while(i < length) {
                    if (!Character.isDigit(chars[i])) {
                        return false;
                    }

                    ++i;
                }

                return true;
            }
        }
    }

    public static boolean areNotEmpty(String[] values) {
        boolean result = true;
        if (values != null && values.length != 0) {
            String[] var2 = values;
            int var3 = values.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String value = var2[var4];
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }

        return result;
    }
}
