package com.ex.common.tools;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateTool {

    private ValidateTool() {
    }

    public static String arabicText(String txt, boolean editOperation) {
        if (!editOperation) {
            try {
                txt = new String(txt.getBytes("ISO8859_1"), "utf8");
            } catch (Exception var3) {
            }
        }

        return txt;
    }

    public static String arabicText(String txt) {
        try {
            txt = new String(txt.getBytes("ISO8859_1"), "utf8");
        } catch (Exception var2) {
        }

        return txt;
    }

    public static boolean isEmpty(String txt) {
        boolean res = true;
        if (txt != null && !txt.trim().equals("") && !txt.trim().equalsIgnoreCase("null")) {
            res = false;
        }

        return res;
    }

    public static boolean isEmpty(Collection list) {
        boolean res = true;
        if (list != null && list.size() > 0) {
            res = false;
        }

        return res;
    }

    public static boolean isEmpty(String... param) {
        if (param != null && param.length != 0) {
            String[] var1 = param;
            int var2 = param.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String p = var1[var3];
                if (isEmpty(p)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public static boolean isBoolen(String txt) {
        boolean res = false;

        try {
            res = txt.equalsIgnoreCase("true") || txt.equalsIgnoreCase("false");
        } catch (Exception var3) {
            res = false;
        }

        return res;
    }

    public static boolean equals(Object val1, Object val2) {
        boolean res = false;

        try {
            if (val1 != null && val2 != null && val1.equals(val2)) {
                res = true;
            }
        } catch (Exception var4) {
            res = false;
        }

        return res;
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj + "";
    }

    protected String toString(List l) {
        StringBuffer sb = new StringBuffer();
        Iterator itrLocal = l.iterator();

        while(itrLocal.hasNext()) {
            sb.append("'").append(itrLocal.next().toString()).append("'");
            if (itrLocal.hasNext()) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public static boolean isNumberFormat(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern numberPattern = Pattern.compile("[0-9]*");
            Matcher numberMatch = numberPattern.matcher(value);
            return numberMatch.matches();
        } else {
            return true;
        }
    }

    public static boolean isMailFormat(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern emailPattern = Pattern.compile("^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
            Matcher email1Match = emailPattern.matcher(value);
            return email1Match.matches();
        } else {
            return true;
        }
    }

    public static boolean isPhoneFormat(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern phonePattern = Pattern.compile("[0-9]+[-]*[0-9]+");
            Matcher phoneMatch = phonePattern.matcher(value);
            return phoneMatch.matches();
        } else {
            return true;
        }
    }

    public static boolean isNumberFormat(String value, int digitsNo, boolean includeNull) {
        if (value != null && !value.trim().equals("")) {
            if (value.trim().length() > digitsNo && digitsNo > 0) {
                return false;
            } else {
                Pattern numberPattern = Pattern.compile("[0-9]*");
                Matcher numberMatch = numberPattern.matcher(value.trim());
                return numberMatch.matches();
            }
        } else {
            return includeNull;
        }
    }

    public static boolean isDoubleFormat(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern doublePattern = Pattern.compile("\\d{0,5}[.]\\d*");
            Matcher doubleMatcher = doublePattern.matcher(value);
            return doubleMatcher.matches();
        } else {
            return true;
        }
    }

    public static boolean isDoubleFormatWithTwoDecimals(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern doublePattern = Pattern.compile("\\d{0,5}[.]\\d{0,2}");
            Matcher doubleMatcher = doublePattern.matcher(value);
            return doubleMatcher.matches();
        } else {
            return true;
        }
    }

    public static boolean isDoubleFormatWithThreeDecimals(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern doublePattern = Pattern.compile("\\d{0,5}[.]\\d{0,3}");
            Matcher doubleMatcher = doublePattern.matcher(value);
            return doubleMatcher.matches();
        } else {
            return true;
        }
    }

    public static boolean isDoubleFormatWithThreeDecimals(String value, int digitsNo, boolean includeNull) {
        if (value != null && !value.trim().equals("")) {
            if (value.trim().length() > digitsNo && digitsNo > 0) {
                return false;
            } else {
                Pattern doublePattern = Pattern.compile("\\d{0,5}[.]\\d{0,3}");
                Matcher numberMatch = doublePattern.matcher(value.trim());
                return numberMatch.matches();
            }
        } else {
            return includeNull;
        }
    }

    public static boolean isPercentFormatWithThreeDecimals(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern doublePattern = Pattern.compile("\\d{0,2}[.]\\d{0,3}");
            Matcher doubleMatcher = doublePattern.matcher(value);
            return doubleMatcher.matches();
        } else {
            return true;
        }
    }

    public static boolean isDateFormat(String value) {
        if (value != null && !value.trim().equals("")) {
            Pattern datePattern = Pattern.compile("\\d{2}[/]\\d{2}[/]\\d{4}");
            Matcher dateMatcher = datePattern.matcher(value);
            return dateMatcher.matches();
        } else {
            return true;
        }
    }

    public static boolean isEmptyAll(String... param) {
        if (param != null && param.length != 0) {
            String[] var1 = param;
            int var2 = param.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String p = var1[var3];
                if (p != null && !p.trim().equals("")) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
