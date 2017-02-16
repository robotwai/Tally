package com.example.lz.tally.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.lz.tally.R;
import com.example.lz.tally.customview.FabTagLayout;
import com.example.lz.tally.customview.FloatingActionButtonPlus;
import com.example.lz.tally.fragment.MainFragment;
import com.example.lz.tally.fragment.SettingFragment;
import com.example.lz.tally.fragment.StatisticsFragment;
import com.example.lz.tally.utils.ToastUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity {
    private ViewPager mPager;
    BottomNavigationBar bottomNavigationBar;
    private FloatingActionButtonPlus mActionButtonPlus;


    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initVoice();
    }


    private void initVoice(){
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(MainActivity.this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
//        mIatDialog = new RecognizerDialog(MainActivity.this, mInitListener);




    }

    public void initUI(){
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mActionButtonPlus = (FloatingActionButtonPlus) findViewById(R.id.FabPlus);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.home_recent, "RecentRecent"))
                .addItem(new BottomNavigationItem(R.mipmap.home_statistics, "Statistics"))
                .addItem(new BottomNavigationItem(R.mipmap.home_setting, "Setting"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                mPager.setCurrentItem(position);
                if (mActionButtonPlus.mStatus){
                    mActionButtonPlus.HideFloat();
                    mActionButtonPlus.mStatus = false;
                }
                if (position != 0){
                    mActionButtonPlus.hideFab();
                }else {
                    mActionButtonPlus.showFab();
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
        mActionButtonPlus.setPosition(FloatingActionButtonPlus.POS_RIGHT_BOTTOM);
        mActionButtonPlus.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                Toast.makeText(MainActivity.this, "Click btn" + position, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        Intent intent =new Intent(MainActivity.this,EditBillActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.ac_enter,R.anim.ac_out);
                        break;
                    case 1:
                        startVoice();
                        break;
                    case 2:
                        break;
                }
            }
        });
        InitViewPager();
    }
    int ret = 0;
    public void startVoice(){

        mIatResults.clear();
        // 设置参数
        setParam();

        // 不显示听写对话框
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            ToastUtils.Tip("听写失败,错误码：" + ret);
        } else {
//            ToastUtils.Tip("error");
        }

    }

    public void InitViewPager() {
        mPager = (ViewPager) super.findViewById(R.id.vp_content);
        mPager.setAdapter(new LoginPagerFragmentAdapter(getSupportFragmentManager()));
        mPager.setCurrentItem(0);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class LoginPagerFragmentAdapter extends FragmentPagerAdapter {
        public LoginPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MainFragment.newInstance();
                case 1:
                    return StatisticsFragment.newInstance();
                case 2:
                    return SettingFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {

            }
        }
    };


    /**
     * 参数设置
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = "mandarin";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            ToastUtils.Tip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            ToastUtils.Tip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            ToastUtils.Tip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
//            printResult(results);
            ToastUtils.Tip(results.getResultString());
            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            ToastUtils.Tip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}
