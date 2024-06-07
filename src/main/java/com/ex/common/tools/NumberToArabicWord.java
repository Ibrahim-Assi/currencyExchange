package com.ex.common.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberToArabicWord {
    private static Map keyNumbers;

    static {
        keyNumbers = new HashMap();
        keyNumbers.put("0", "صفر");
        keyNumbers.put("1", "واحد");
        keyNumbers.put("2", "اثنان");
        keyNumbers.put("3", "ثلاثة");
        keyNumbers.put("4", "أربعة");
        keyNumbers.put("5", "خمسة");
        keyNumbers.put("6", "سته");
        keyNumbers.put("7", "سبعه");
        keyNumbers.put("8", "ثمانية");
        keyNumbers.put("9", "تسعه");
        keyNumbers.put("10", "عشره");
        keyNumbers.put("11", "إحدا عشر");
        keyNumbers.put("12", "اثنا عشر");
        keyNumbers.put("13", "ثلاثة عشر");
        keyNumbers.put("14", "أربعة عشر");
        keyNumbers.put("15", "خمسة عشر");
        keyNumbers.put("16", "ستة عشر");
        keyNumbers.put("17", "سبعة عشر");
        keyNumbers.put("18", "ثمانية عشر");
        keyNumbers.put("19", "تسعة عشر");
        keyNumbers.put("20", "عشرون");
        keyNumbers.put("30", "ثلاثون");
        keyNumbers.put("40", "أربعون");
        keyNumbers.put("50", "خمسون");
        keyNumbers.put("60", "ستون");
        keyNumbers.put("70", "سبعون");
        keyNumbers.put("80", "ثمانون");
        keyNumbers.put("90", "تسعون");
        keyNumbers.put("100", "مائه");
        keyNumbers.put("200", "مائتين");
        keyNumbers.put("300", "ثلاثمائة");
        keyNumbers.put("400", "أربعمائه");
        keyNumbers.put("500", "خمسمائه");
        keyNumbers.put("600", "ستمائه");
        keyNumbers.put("700", "سبعمائه");
        keyNumbers.put("800", "ثمانمائه");
        keyNumbers.put("900", "تسعمائه");
        keyNumbers.put("1000", "ألف");
        keyNumbers.put("2000", "ألفان");
        keyNumbers.put("3000", "ثلاثة آلاف");
        keyNumbers.put("4000", "أربعة آلاف");
        keyNumbers.put("5000", "خمسة آلاف");
        keyNumbers.put("6000", "ستة آلاف");
        keyNumbers.put("7000", "سبعة آلاف");
        keyNumbers.put("8000", "ثمانية آلاف");
        keyNumbers.put("9000", "تسعة آلاف");
        keyNumbers.put("10000", "عشرة آلاف");
    }

    public NumberToArabicWord() {

    }

    public String convertNumberToWord(String total) {
        String msg = "";
        String num = "";
        int dec=0;
        if (total != null && !total.equals("")) {
            if (total.indexOf("-") >= 0) {
                total=total.substring(total.indexOf("-")+1,total.length());
            }
            if (total.indexOf(".") >= 0) {
                num = total.substring(0, total.indexOf("."));
                String decPart=total.substring(total.indexOf(".") + 1, total.length());
                dec=Integer.parseInt(decPart);
                String decStr=dec+"";
                if(dec>0)
                {
                    if(decStr.length()<3 && decPart.length()<3)
                    {
                        for(int i=decStr.length();i<3;i++)
                        {
                            decStr=decStr+"0";
                        }
                    }
                    msg = "دينارا و" + "("+decStr +")"+ "   فلسا فقط لاغير";
                }
                else
                    msg = " دينارا فقط لا غير";
            } else {
                num = total;
                msg = " دينارا فقط لا غير";
            }
        }
        String arabicTranslatedNumber = getArabicTranslatedNumber(Integer.parseInt(num), msg);
        return arabicTranslatedNumber + " " + msg;
    }

    public  String convertNumberToWordFIN(String total ,String Currency) {
        String msg = "";
        String num = "";
        int dec=0;
        if (total != null && !total.equals("")) {
            if (total.indexOf("-") >= 0) {
                total=total.substring(total.indexOf("-")+1,total.length());
            }
            if (total.indexOf(".") >= 0) {
                num = total.substring(0, total.indexOf("."));
                String decPart=total.substring(total.indexOf(".") + 1, total.length());
                dec=Integer.parseInt(decPart);
                String decStr=dec+"";
                if(dec>0)
                {
                    if(decStr.length()<3 && decPart.length()<3 && Currency.equals("JD"))
                    {
                        for(int i=decStr.length();i<3;i++)
                        {
                            decStr=decStr+"0";
                        }
                    }
                    switch (Currency) {
                        case "ILS":
                            msg = "شيكل و" + "("+decStr +")"+ "  اغورة فقط لاغير";
                            break;
                        case "USD":
                            msg = "دولار و" + "("+decStr +")"+ "   سنتا فقط لاغير";
                            break;
                        case "JOD":
                            msg = "دينارا و" + "("+decStr +")"+ "   فلسا فقط لاغير";
                            break;
                        default:
                            msg = " و" + "("+decStr +")"+ "    فقط لاغير";
                    }

                }
                else
                    switch (Currency) {
                        case "ILS":
                            msg = " شيكل فقط لا غير";
                            break;
                        case "USD":
                            msg = " دولار فقط لا غير";
                            break;
                        case "JOD":
                            msg = " دينارا فقط لا غير";
                            break;
                        default:
                            msg = "  فقط لا غير";
                    }

            } else {
                num = total;
                switch (Currency) {
                    case "ILS":
                        msg = " شيكل فقط لا غير";
                        break;
                    case "USD":
                        msg = " دولار فقط لا غير";
                        break;
                    case "JOD":
                        msg = " دينارا فقط لا غير";
                        break;
                    default:
                        msg = "  فقط لا غير";
                }
            }
        }
        String arabicTranslatedNumber = getArabicTranslatedNumber(Integer.parseInt(num), msg);
        return arabicTranslatedNumber + " " + msg;
    }
    public  String getArabicTranslatedNumber(int number, String msg) {

        StringBuffer buffer = new StringBuffer();
        String keyNumber = "";
        boolean first = true;
        if (number < 0 || number > 50000) {
            return "Invalid Number";
        }
        String directRes = (String) keyNumbers.get(number + "");
        if (directRes != null) {
            return directRes;
        }

        List digits = numberToDigits(number);
        int digitsCount = digits.size() - 1;
        while (digitsCount > 1) {
            keyNumber = (String) digits.get(digitsCount--);
            if (keyNumber != null && keyNumber.length() > 0 && !keyNumber.trim().equals("0")) {
                buffer.append(first == true ? "" : " و");
                buffer.append(keyNumbers.get(keyNumber));
                first = false;
            }

        }
        int lastTwoNum = toNumber((String) digits.get(0), (String) digits.get(1));
        if (lastTwoNum >= 11 && lastTwoNum <= 19) {
            buffer.append(" و");
            buffer.append(keyNumbers.get(lastTwoNum + ""));
            return buffer.toString();
        }
        keyNumber = (String) digits.get(0);
        if (keyNumber != null && keyNumber.length() > 0 && !keyNumber.trim().equals("0")) {
            buffer.append(digits.size() == 2 ? "" : " و");
            buffer.append(keyNumbers.get(keyNumber));
        }

        keyNumber = (digits.size() > 1) ? (String) digits.get(1) : null;
        if (keyNumber != null && keyNumber.length() > 0 && !keyNumber.trim().equals("0")) {
            buffer.append((number >= 13 && number <= 19) ? " " : " و");
            buffer.append(keyNumbers.get(keyNumber));
        }

        return buffer.toString();
    }


    private int toNumber(String num1, String num2) {
        return  Integer.parseInt(num1)+  Integer.parseInt(num2);
    }

    private List numberToDigits(int number) {
        List digits = new ArrayList();
        int base = 1;
        do {
            digits.add("" + (number % 10) * base);
            number = number / 10;
            base *= 10;
        } while (number > 0);
        return digits;
    }

}

