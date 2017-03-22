package com.zhaoxiuyuan.demos.edit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
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
    @BindView(R.id.btnShowKeyboard)
    Button btnShowKeyboard;
    @BindView(R.id.btnHideKeyboard)
    Button btnHideKeyboard;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 设置软键盘动作时必须指定InputType，默认Type是不会使软键盘生效按钮
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
                    hideKeyboard(v);
                }
            }
        });

        editTextSend.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    Log.d(TAG, "onEditorAction: IME_ACTION_SEND");
                }
                return false;
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //切换键盘显示
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    @OnClick({R.id.btnShowKeyboard, R.id.btnHideKeyboard})
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.btnShowKeyboard:
                showKeyboard(mEdtSearch);
                break;
            case R.id.btnHideKeyboard:
                hideKeyboard(mEdtSearch);
                break;
        }
    }

    @OnClick({R.id.btnFocusIn, R.id.btnFocusOut})
    public void onClickFocus(View view) {
        switch (view.getId()) {
            case R.id.btnFocusIn:
                // 用代码将焦点切换到某Edit控件，并不会自动打开小键盘
                mEdtSearch.requestFocus();
                break;
            case R.id.btnFocusOut:
                // 焦点已经在编辑框的情况下，点击按钮清除编辑框的焦点，会触发两次focus change
                // 一次是hasFocus是false，一次是true。代表焦点切出后又切回来？
                mEdtSearch.clearFocus();
                // 如果FocusableInTouchMode和Focusable有一个为false，则requestFocus和点击都不会得到焦点
                // 但点击时会有动画效果
                if (!findViewById(R.id.editTextCustom).requestFocus()) {
                    Log.d(TAG, "onClickFocus: requestFocus Failed!");
                }
                break;
        }
    }
}
