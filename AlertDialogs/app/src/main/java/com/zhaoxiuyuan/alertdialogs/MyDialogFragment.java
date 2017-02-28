package com.zhaoxiuyuan.alertdialogs;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhaohaiyang
 *         Created at 2017.2.27.
 */

public class MyDialogFragment extends DialogFragment {
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.btnCustom)
    Button btnCustom;
    @BindView(R.id.editText)
    EditText editText;
    static int customCount = 0;

    private static String TAG = "MyDialogFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, getTag() + " onCreateView");
        View view = inflater.inflate(R.layout.custom_alert, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, getTag() + " onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, getTag() + " onDetach");
        super.onDetach();
    }

    @Override
    public void onStart() {
        Log.d(TAG, getTag() + " onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, getTag() + " onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, getTag() + " onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        Log.d(TAG, getTag() + " onPause");
        super.onPause();
    }

    @OnClick({R.id.btnCustom,R.id.btnCancel, R.id.btnSecondPop})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCustom:{
                // 直接在此对话框上弹出，按后退键回到上一个对话框
                new MyDialogFragment().show(getFragmentManager(), String.format("child custom %d", ++customCount));
            }
            break;
            case R.id.btnCancel: {
                dismiss();
            }
            break;
            case R.id.btnSecondPop:{
                // 先消除自己，再弹一个新的
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(this);
                ft.addToBackStack(null);
                dismiss();
                new MyDialogFragment().show(getFragmentManager(),"second pop");
            }
            break;
        }
    }
}
