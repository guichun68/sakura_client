package austin.mysakuraapp.fragments.setting;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import austin.mysakuraapp.MainActivity;
import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.FragTAG;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.utils.BeanFactoryUtil;
import austin.mysakuraapp.utils.UIManager;
import austin.mysakuraapp.utils.UIUtil;
import austin.mysakuraapp.utils.UpdateService;
import austin.mysakuraapp.views.CustomToggleBtn;

/**
 * Created by austin on 2016/6/28.
 * Desc: Sakura 单词
 */
public class SetingFrag extends Fragment implements View.OnClickListener {
    private String TAG = SetingFrag.class.getSimpleName();

    private View view;
    private CardView mCvCheckupdate,mCvGrammar;
    private LinearLayoutCompat mLlcAbout;
    private CustomToggleBtn mSwitchBtn;
    private ImageView headIcon;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_setting,container,false);
        bindView();
        configView();
        return view;
    }

    private void configView() {
        mCvCheckupdate.setOnClickListener(this);
        mLlcAbout.setOnClickListener(this);
        mCvGrammar.setOnClickListener(this);
        mSwitchBtn.setOnClickedListener(new CustomToggleBtn.MyBtnOnClickListener(){

            @Override
            public void clicked(boolean isOn) {
                GlobalParams.showNihonngo = isOn;
                if(GlobalParams.globalWordAdapterB != null)
                {
                    GlobalParams.globalWordAdapterB.notifyDataSetChanged();
                }
                if(isOn){
                    showSnack("确定","已开启,在语法页面刷新即可生效");
                }else{
                    showSnack("确定","已关闭,在语法页面刷新即可生效");
                }
            }
        });
    }

    public void showSnack(String btnText,String msg) {
        final Snackbar snackbar = Snackbar.make(mSwitchBtn, msg, Snackbar.LENGTH_LONG);
        snackbar.setAction(btnText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void bindView() {
        headIcon = (ImageView)view.findViewById(R.id.imageView_head_icon);
        mCvCheckupdate = (CardView) view.findViewById(R.id.cv_checkupdate);
        mLlcAbout = (LinearLayoutCompat) view.findViewById(R.id.ll_about);
        mSwitchBtn = (CustomToggleBtn) view.findViewById(R.id.ctb_show_nihonngo);
        mCvGrammar = (CardView) view.findViewById(R.id.cv_grammar);
        headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threeClick();
            }
        });
    }
    long[] mHits = new long[3];
    private void threeClick() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
            ipConfigDialog();
        }
    }
    private void ipConfigDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_ipconfig, null);
        ((TextView)view.findViewById(R.id.tv_curr_server)).setText("current server:"+GlobalParams.BASE_URL);

        final EditText etIpInput = (EditText) view.findViewById(R.id.et_input);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etIpInput.getText().toString())){
                    GlobalParams.BASE_URL = "http://"+etIpInput.getText().toString();
                    UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
                    GlobalParams.refreshIP();
                }
            }
        });

        Button btnEclipseServer,btnGenymotion,btnFj,btnChan;
        btnEclipseServer = (Button) view.findViewById(R.id.btn_localserver);
        btnGenymotion = (Button) view.findViewById(R.id.btn_test_server);
        btnChan = (Button) view.findViewById(R.id.btn_chan);
        btnFj = (Button) view.findViewById(R.id.btn_hbx_server);
        btnChan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_fjjsp");
                GlobalParams.refreshIP();
                UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
            }
        });
        btnEclipseServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_eclipse");
                GlobalParams.refreshIP();
                UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
            }
        });
        btnGenymotion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("BaseURL_genymotion");
                GlobalParams.refreshIP();
                UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
            }
        });
        btnFj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GlobalParams.BASE_URL = BeanFactoryUtil.properties.getProperty("FJJSP");
                GlobalParams.refreshIP();
                UIUtil.showToastSafe("已设置为:"+GlobalParams.BASE_URL);
            }
        });
        final AlertDialog dialog = adb.create();
        dialog.setView(view, 0,0,0,0);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Fragment target;
        switch (v.getId()){
            case R.id.cv_checkupdate:
                ((MainActivity)getActivity()).mBinder.callCheckUpdate(new UpdateService.CheckUpdateCallBack() {
                    @Override
                    public void shouldUpdate(boolean shoudUpdate) {
                        if(!shoudUpdate){
                            showSnack(null,"已经是最新版本");
                        }else{
                            //do nothing, if app should update,the UpdateActivity will auto evoked.
                            Log.i(TAG,"is already the latest version.");
                        }
                    }
                });
                break;
            case R.id.ll_about:
                target = getActivity().getSupportFragmentManager().findFragmentByTag(FragTAG.TAG_ABOUT_ME);
                if(target == null){
                    target = new AboutMeFrag();
                }
                UIManager.getInstance().changeFragmentAndSaveViews2(SetingFrag.this,target,true,null, FragTAG.TAG_ABOUT_ME);
                break;
            case R.id.cv_grammar:
                target = getActivity().getSupportFragmentManager().findFragmentByTag(FragTAG.FRAG_TAG_GRAMMAR_LIST);
                if(target == null){
                    target = new GramAndCultureListFrag();
                }
                UIManager.getInstance().changeFragmentAndSaveViews2(SetingFrag.this,target,true,null, FragTAG.FRAG_TAG_GRAMMAR_LIST);

                break;
        }
    }

}
