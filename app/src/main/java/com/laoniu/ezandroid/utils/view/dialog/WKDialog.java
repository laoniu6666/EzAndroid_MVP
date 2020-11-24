package com.laoniu.ezandroid.utils.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.laoniu.ezandroid.App;
import com.laoniu.ezandroid.utils.other.MyActivityManager;
import com.laoniu.ezandroid.utils.other.OnFastClickListener;
import com.laoniu.ezandroid.utils.other.T;
import com.laoniu.ezandroid.utils.other.WKCallback;

/**
 * Time:2020/05/27 17:12
 * Author: laoniu
 * Description:
 */
public class WKDialog {

    public static ProgressDialog showProgressDialog(){
        return showProgressDialog(false,"加载中..");//默认不可手动取消
    }

    static ProgressDialog waitingDialog;
    public static ProgressDialog showProgressDialog(boolean cancelable, String msg){
        Context act = MyActivityManager.getInstance().getCurrentActivity();
        dissmissProgressDialog();
        waitingDialog= new ProgressDialog(act);
        waitingDialog.setTitle("系统提示");
        waitingDialog.setMessage(msg);
        waitingDialog.setIndeterminate(true);
//        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDialog.setCancelable(cancelable);
        waitingDialog.show();
        return waitingDialog;
    }
    public static void dissmissProgressDialog(){
        if(null!=waitingDialog && waitingDialog.isShowing()){
            waitingDialog.dismiss();
            waitingDialog=null;
        }
    }


    /*************************************************************/

    public static void showSureDialog(String content){
        showSureDialog(content,null);
    }
    public static void showSureDialog(String content, final WKDialogCallback callback){
        Context act = MyActivityManager.getInstance().getCurrentActivity();
        final AlertDialog.Builder builder= new AlertDialog.Builder(act);
        builder.setTitle("系统提示");
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(null!=callback){
                    callback.onCall();
                }
            }
        });
        builder.show();
    }



    /*************************************************************/
    static String password="123";
    public static void showKeyDialog(Context act,final WKDialogCallback call){
        showInputDialog(new WKCallback<String>() {
            @Override
            public void onCall(String text) {
                if(text.equals(password)){
                    call.onCall();
                }else{
                    T.toast("密码输入错误!");
                }
            }
        });
    }

    public static void showInputDialog(final WKCallback call){
        Context act = MyActivityManager.getInstance().getCurrentActivity();
        int width = ScreenUtils.getScreenWidth()-SizeUtils.dp2px(40);
        Dialog d=new Dialog(act);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                width, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(act);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lp);

        final EditText et = new EditText(act);
        et.setLayoutParams(lp);
        et.setLayoutParams(lp);
        Button btn = new Button(act);
        btn.setLayoutParams(lp);
//        btn.setBackgroundColor(Color.parseColor("#00a5e0"));
//        btn.setTextColor(Color.parseColor("#ffffff"));
        btn.setText("确定");
        btn.setOnClickListener(new OnFastClickListener() {
            @Override
            public void onFastClick(View v) {
                d.dismiss();
                Editable text = et.getText();
                if(!TextUtils.isEmpty(text)&&!TextUtils.isEmpty(text.toString().trim())){
                    call.onCall(text.toString().trim());
                }
            }
        });
        ll.addView(et);
        ll.addView(btn);

        d.setContentView(ll);
        d.setCancelable(true);
//        d.setTitle("系统提示");
        d.show();
    }

    public interface WKDialogCallback{
        void onCall();
    }
}
