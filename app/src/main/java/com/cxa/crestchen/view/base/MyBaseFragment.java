package com.cxa.crestchen.view.base;


import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import androidx.fragment.app.Fragment;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

/**
 * A simple {@link Fragment} subclass.
 */
@Page(name = "基础Fragment", anim = CoreAnim.none)
public abstract class MyBaseFragment extends XPageFragment {

    private boolean mIsDataInited;

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public final Object mHandlerToken = hashCode();
//    @BindView(R.id.iv_back)
//    ImageView iv_back;

    public MyBaseFragment() {
        // Required empty public constructor
    }
    /**
     * 初始化页面, 可以重写该方法，以增强灵活性
     */
    protected void initPage() {
        initTitleBar();
        initViews();
        initListeners();
        if (!mIsDataInited) {
            if (getUserVisibleHint()) {
                initData();
                mIsDataInited = true;
            }
        }
    }
    

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
    }

    protected void initData() {

    }

    /**
     * 去掉标题栏
     * @return
     */
    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    /**
     * 延迟执行
     */
    public final boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    public final boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        // 发送和这个 Activity 相关的消息回调
        return HANDLER.postAtTime(r, mHandlerToken, uptimeMillis);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //防止数据预加载, 只预加载View，不预加载数据
        if (isVisibleToUser && isVisible() && !mIsDataInited) {
            initData();
            mIsDataInited = true;
        }
    }

}
