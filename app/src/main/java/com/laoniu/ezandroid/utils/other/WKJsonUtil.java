package com.laoniu.ezandroid.utils.other;


import com.google.gson.Gson;

public class WKJsonUtil {

    public static final int TYPE_REQ=1;
    public static final int TYPE_RESP=2;

    public static String formatJson(Object obj){
        return formatJson(obj,0);
    }
    public static String formatJson(Object obj,int type){
        String text=formatString(new Gson().toJson(obj));
        if(type==TYPE_REQ){
            text="请求串：\n"+text;
        }else if(type==TYPE_RESP){
            text="返回参数：\n"+text;
        }
        return text;
    }

    private static String formatString(String text){
        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }

}
