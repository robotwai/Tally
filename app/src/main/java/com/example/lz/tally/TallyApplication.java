package com.example.lz.tally;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by liz on 16-12-21.
 */

public class TallyApplication extends TinkerApplication {
    public TallyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.lz.tally.TallyapplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
