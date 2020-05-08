package com.example.myjetpackmvvm_java.ui.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myjetpackmvvm_java.R;
import com.example.myjetpackmvvm_java.adapter.AriticleAdapter;
import com.example.myjetpackmvvm_java.app.AppViewModel;
import com.example.myjetpackmvvm_java.app.base.BaseFragment;
import com.example.myjetpackmvvm_java.app.ext.AdapterExtKt;
import com.example.myjetpackmvvm_java.app.ext.CustomViewExtKt;
import com.example.myjetpackmvvm_java.app.network.stateCallback.ListDataUiState;
import com.example.myjetpackmvvm_java.app.util.CacheUtil;
import com.example.myjetpackmvvm_java.Const;
import com.example.myjetpackmvvm_java.app.weight.banner.HomeBannerViewHolder;
import com.example.myjetpackmvvm_java.app.weight.customview.CollectView;
import com.example.myjetpackmvvm_java.app.weight.loadCallBack.EmptyCallback;
import com.example.myjetpackmvvm_java.app.weight.loadCallBack.ErrorCallback;
import com.example.myjetpackmvvm_java.app.weight.loadCallBack.LoadingCallback;
import com.example.myjetpackmvvm_java.app.weight.recyclerview.DefineLoadMoreView;
import com.example.myjetpackmvvm_java.app.weight.recyclerview.SpaceItemDecoration;
import com.example.myjetpackmvvm_java.data.entity.AriticleResponse;
import com.example.myjetpackmvvm_java.data.entity.BannerResponse;
import com.example.myjetpackmvvm_java.data.entity.CollectBus;
import com.example.myjetpackmvvm_java.data.entity.UserInfo;
import com.example.myjetpackmvvm_java.databinding.FragmentTestBinding;
import com.example.myjetpackmvvm_java.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kingja.loadsir.core.LoadService;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zhpan.bannerview.BannerViewPager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import me.hgj.jetpackmvvm.ext.BaseViewModelExtKt;
import me.hgj.jetpackmvvm.state.ResultState;

/**
 * 重写HomeFragment
 */
public class JavaFragment extends BaseFragment<HomeViewModel, FragmentTestBinding> {
    //适配器
    private AriticleAdapter articleAdapter;
    //界面状态管理者
    private LoadService loadsir ;
    private DefineLoadMoreView footView;
    //UI id
    private View rootView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private SwipeRecyclerView recyclerView;
    private FloatingActionButton floatbtn;
    @Override
    public int layoutId() {
        return R.layout.fragment_test;
    }


    @Override
    public void initView() {
        View root = initUI();
        this.rootView = root;
        initComponent();
        //状态页配置
        loadsir = CustomViewExtKt.LoadServiceInit(swipeRefresh, new Function0<Unit>(){
            @Override
            public Unit invoke() {
                loadsir.showCallback(LoadingCallback.class);
                mViewModel.reqBannerData();
                mViewModel.reqHomeData(true);
                return null;
            }
        });
        //toolbar设置
        CustomViewExtKt.init(toolbar,"JavaFragment");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home_search:
                            Navigation.findNavController(root).navigate(R.id.action_mainfragment_to_searchFragment);
                            return true;
                    }
                    return false;
                }
        });
        toolbar.inflateMenu(R.menu.home_menu);
        //初始化recyclerView
        CustomViewExtKt.init(recyclerView,new LinearLayoutManager(getContext()),articleAdapter,true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false));
        footView = CustomViewExtKt.initFooter(recyclerView,new SwipeRecyclerView.LoadMoreListener(){
            @Override
            public void onLoadMore() {
                mViewModel.reqHomeData(false);
            }
        });
        CustomViewExtKt.initFloatBtn(recyclerView,floatbtn);

        //初始化 SwipeRefreshLayout
        CustomViewExtKt.init(swipeRefresh,new Function0<Unit>(){
            @Override
            public Unit invoke() {
                mViewModel.reqHomeData(true);
                return null;
            }
        });
    }


    @Override
    public void lazyLoadData() {
        //设置界面 加载中
        loadsir.showCallback(LoadingCallback.class);
        //请求轮播图数据
        mViewModel.reqBannerData();
        //请求文章列表数据
        mViewModel.reqHomeData(true);
    }

    @Override
    public void createObserver() {
        Context context = getContext();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        //监听数据变化
        mViewModel.getHomeDataState().observe(viewLifecycleOwner,new Observer<ListDataUiState<AriticleResponse>>(){
            @Override
            public void onChanged(ListDataUiState<AriticleResponse> o) {
                swipeRefresh.setRefreshing(false);
                recyclerView.loadMoreFinish(o.isEmpty(),o.getHasMore());
                if(o.isSuccess()){
                    //成功
                    if(o.isFirstEmpty()){
                        //第一页并没有数据 显示空布局界面
                        loadsir.showCallback(EmptyCallback.class);
                    }else if(o.isRefresh()){
                        //是第一页
                        loadsir.showSuccess();
                        articleAdapter.setNewData(o.getListData());
                    }else{
                        //不是第一页
                        loadsir.showSuccess();
                        articleAdapter.addData(o.getListData());
                    }
                }else{
                    //失败
                    if (o.isRefresh()){
                        //如果是第一页，则显示错误界面，并提示错误信息
                        CustomViewExtKt.setErrorText(loadsir,o.getErrMessage());
                        loadsir.showCallback(ErrorCallback.class);
                    }else{
                        recyclerView.loadMoreError(0, o.getErrMessage());
                    }
                }
            }
        });
        //监听轮播图请求的数据变化
        mViewModel.getBannerData().observe(viewLifecycleOwner, new Observer<ResultState<ArrayList<BannerResponse>>>() {
            @Override
            public void onChanged(ResultState<ArrayList<BannerResponse>> o) {
                BaseViewModelExtKt.parseState(JavaFragment.this, o, new Function1<ArrayList<BannerResponse>, Unit>() {
                    @Override
                    public Unit invoke(ArrayList<BannerResponse> data) {
                        //请求轮播图数据成功，添加轮播图到headview ，如果等于0说明没有添加过头部，添加一个
                        if (recyclerView.getHeaderCount() == 0) {
                            View headview = LayoutInflater.from(context).inflate(R.layout.include_banner, null);
                            BannerViewPager bannerview = headview.findViewById(R.id.banner_view);
                            bannerview.setHolderCreator(HomeBannerViewHolder::new);
                            bannerview.setOnPageClickListener(new BannerViewPager.OnPageClickListener(){
                                @Override
                                public void onPageClick(int position) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(Const.BK_BannerData,data.get(position));
                                    Navigation.findNavController(rootView).navigate(R.id.action_mainfragment_to_webFragment, bundle);
                                }
                            });
                            bannerview.create(data);
                            recyclerView.addHeaderView(headview);
                        }
                        return null;
                    }
                }, null, null);
            }
        });

        AppViewModel appViewModel = getAppViewModel();
        //监听账户信息是否改变 有值时(登录)将相关的数据设置为已收藏，为空时(退出登录)，将已收藏的数据变为未收藏
        appViewModel.getUserinfo().observe(viewLifecycleOwner, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo o) {
                if (o != null) {
                    for(String id:o.getCollectIds()){
                        for (AriticleResponse item :articleAdapter.getData()) {
                            if (Integer.parseInt(id) == item.getId()) {
                                item.setCollect(true);
                                break;
                            }
                        }
                    }
                } else {
                    for (AriticleResponse item :articleAdapter.getData()) {
                        item.setCollect(false);
                    }
                }
                articleAdapter.notifyDataSetChanged();
            }
        });

        //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
        appViewModel.getCollect().observe(viewLifecycleOwner, new Observer<CollectBus>() {
            @Override
            public void onChanged(CollectBus o) {
                for (int index=0;index<articleAdapter.getData().size();index++) {
                    AriticleResponse a = articleAdapter.getData().get(index);
                    if (a.getId() == o.getId()) {
                        a.setCollect(o.getCollect());
                        articleAdapter.notifyItemChanged(index);
                        break;
                    }
                }
            }
        });

        //监听全局的主题颜色改变
        appViewModel.getAppColor().observe(viewLifecycleOwner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer o) {
                CustomViewExtKt.setUiTheme(o, toolbar, floatbtn, swipeRefresh, loadsir, footView);
            }
        });
        //监听全局的列表动画改编
        appViewModel.getAppAnimation().observe(viewLifecycleOwner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer o) {
                CustomViewExtKt.setAdapterAnimion(articleAdapter,o);
            }
        });
    }
    /**
     *
     */
    private View initUI(){
        View root = mDatabind.getRoot();
        toolbar = root.findViewById(R.id.toolbar);
        swipeRefresh = root.findViewById(R.id.swipeRefresh);
        recyclerView = root.findViewById(R.id.recyclerView);
        floatbtn = root.findViewById(R.id.floatbtn);
        return root;
    }
    private void initComponent() {
        articleAdapter = new AriticleAdapter(new ArrayList<>(),true);

        articleAdapter.setOnCollectViewClickListener(new AriticleAdapter.OnCollectViewClickListener() {
            @Override
            public void onClick(@NotNull AriticleResponse item, @NotNull CollectView v, int position) {
                if (CacheUtil.INSTANCE.isLogin()) {
                    if (v.isChecked()) {
                        mViewModel.uncollect(item.getId());
                    } else {
                        mViewModel.collect(item.getId());
                    }

                } else {
                    v.setChecked(true);
                    Navigation.findNavController(v)
                            .navigate(R.id.action_mainFragment_to_loginFragment);
                }
            }
        });
        AdapterExtKt.setNbOnItemClickListener(articleAdapter, Const.interval_item , new Function3<BaseQuickAdapter<?, ?>, View, Integer, Unit>() {
            @Override
            public Unit invoke(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, Integer position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.BK_ArticalData,articleAdapter.getData().get(position-recyclerView.getHeaderCount()));
                Navigation.findNavController(view)
                        .navigate(R.id.action_mainfragment_to_webFragment,bundle);
                return null;
            }
        });
        AdapterExtKt.setNbOnItemChildClickListener(articleAdapter, Const.interval_item, new Function3<BaseQuickAdapter<?, ?>, View, Integer, Unit>() {
            @Override
            public Unit invoke(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, Integer position) {
                switch (view.getId()){
                    case R.id.item_home_author:
                    case R.id.item_project_author :
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",articleAdapter.getData().get(position-recyclerView.getHeaderCount()).getUserId());
                        Navigation.findNavController(view).navigate(R.id.action_mainfragment_to_lookInfoFragment,bundle);
                    break;
                }
                return null;
            }
        });
        articleAdapter.addChildClickViewIds(R.id.item_home_author);
        articleAdapter.addChildClickViewIds(R.id.item_project_author);
    }
}
