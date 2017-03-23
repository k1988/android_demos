package com.zhaoxiuyuan.demos.edit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
1、编辑框手点或代码设置焦点时，显示键盘
2、手点别的空白的地方或者切走焦点时，隐藏键盘
3、activity显示时不默认显示键盘
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;
    @BindView(R.id.btnFocusIn)
    Button btnFocusIn;
    @BindView(R.id.btnFocusOut)
    Button btnFocusOut;
    @BindView(R.id.editTextSend)
    EditText editTextSend;
    @BindView(R.id.editTextCustom)
    EditText editTextCustom;
    @BindView(R.id.btnEnableFocus)
    Button btnEnableFocus;
    @BindView(R.id.editTextFocusUnable)
    EditText editTextFocusUnable;
    @BindView(R.id.btnShowForce)
    Button btnShowForce;
    @BindView(R.id.btnShowImplicit)
    Button btnShowImplicit;
    @BindView(R.id.btnHideImplicitOnly)
    Button btnHideImplicitOnly;
    @BindView(R.id.btnHideNotAlways)
    Button btnHideNotAlways;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // FIXME：设置软键盘动作时必须重新指定InputType，默认Type是不会使软键盘出现特殊按钮(因为默认是多行，所以直接显示回车按钮了）
        Log.d(TAG, String.format("onCreate: mEdtSearch default input type is %x", mEdtSearch.getInputType()));
        mEdtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
        // 代码动态设置软键盘动作
        mEdtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d(TAG, "onEditorAction: On Search Clicked!");
                    return true;
                } else if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Log.d(TAG, "onEditorAction: 点击了回车，清除焦点，并关闭键盘");
                    mEdtSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
        mEdtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: " + (hasFocus ? "Focus" : "lose focus"));
                if (!hasFocus) {
                    /*
                    * FIXME：虽然可以在丢失焦点事件这里隐藏键盘，但有以下缺点：
                    * 1.如果是从一个TextEdit切换到另外一个TextEdit，有些系统\输入法窗口会闪烁一次。如果在标题栏处有显示键盘图标的，图标也会闪烁一次
                    * 2.好多可以点击的元素，比如背景，比如按钮，并不会抢占TextEdit的焦点或者说抢完后会还回去。
                    */
                    //hideKeyboard(v);
                }
            }
        });
        editTextSend.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.d(TAG, "onEditorAction: IME_ACTION_SEND");
                }
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
        // 通过重载dispatchTouchEvent来判断接收到点击事件时，当前焦点控件是否是EditText并且所在位置就是点击所在区域。如果不是则隐藏键盘
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }

        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @OnClick({R.id.btnShowForce, R.id.btnShowImplicit, R.id.btnHideImplicitOnly, R.id.btnHideNotAlways,R.id.btnHideNoFlag,R.id.btnShowNoFlag})
    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Log.d(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.btnShowNoFlag:
                // FIXME: 默认打开的键盘，可以用btnHideNotAlways和0来隐藏
                imm.showSoftInput(mEdtSearch, 0);
                break;
            case R.id.btnShowForce:
                // FIXME: SHOW_FORCED 显示的键盘，只能用0标志来隐藏
                imm.showSoftInput(mEdtSearch, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.btnShowImplicit:
                // FIXME: SHOW_IMPLICIT 显示的键盘，可以用任意标志隐藏
                imm.showSoftInput(mEdtSearch, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.btnHideNoFlag:
                // FIXME: 第二个参数设置成0时，可以隐藏所有键盘
                imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
                break;
            case R.id.btnHideNotAlways:
                // FIXME: HIDE_NOT_ALWAYS 能隐藏点击编辑框自动弹出的键盘(可能也是标志为0的键盘)、通过SHOW_IMPLICIT标志显示的键盘；
                imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btnHideImplicitOnly:
                // FIXME: HIDE_IMPLICIT_ONLY 仅能隐藏通过SHOW_IMPLICIT标志显示的键盘；
                imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                break;
        }
    }

    @OnClick({R.id.btnFocusIn, R.id.btnFocusOut})
    public void onClickFocus(View view) {
        switch (view.getId()) {
            case R.id.btnFocusIn:
                // FIXME：用代码将焦点切换到某Edit控件，并不会自动打开小键盘
                mEdtSearch.requestFocus();
                break;
            case R.id.btnFocusOut:
                // FIXME：焦点已经在编辑框的情况下，点击按钮清除编辑框的焦点，会触发两次focus change一次是hasFocus是false，一次是true。代表焦点切出后又切回来？
                mEdtSearch.clearFocus();
                // FIXME：如果FocusableInTouchMode和Focusable有一个为false，则requestFocus和点击都不会得到焦点但点击时会有动画效果
                if (!findViewById(R.id.editTextCustom).requestFocus()) {
                    Log.d(TAG, "onClickFocus: requestFocus Failed!");
                }
                break;
        }
    }
}
