package com.heyl.recycleviewdemo;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MyOnItemClickLitener{

    private RecyclerView mRecyclerView;
    private List<String> mDatas;//数据
    private List<Integer> mHeights;//高度
    private MyRecyclerViewAdapter mRecyclerViewAdapter;//适配器
    private MyStaggeredAdapter mStaggeredAdapter;
    private RecyclerView.ItemDecoration decoration1;//分割线
    private RecyclerView.ItemDecoration decoration2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new android.support.v7.widget.RecyclerView(this);
        setContentView(mRecyclerView);
        initData();
        initAdapter();
        initRecylerView();
    }
    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 500));
        }
    }
    private void initAdapter() {
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(this, mDatas);
        mStaggeredAdapter = new MyStaggeredAdapter(this, mDatas, mHeights);
        mRecyclerViewAdapter.setOnItemClickLitener(this);
        mStaggeredAdapter.setOnItemClickLitener(this);
    }
    private void initRecylerView() {
        decoration1 = new DividerItemDecoration(this, 0);
        decoration2 = new DividerItemDecoration(this, 1);
        //mRecyclerView.setPadding(10, 10, 10,10);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);//设置adapter
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));//设置布局管理器
        //mRecyclerView.addItemDecoration(decoration1);//添加一个分割线
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //不是第一个的格子都设一个左边和底部的间距
                outRect.left = 50;
                outRect.bottom = 50;
                //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
                if (parent.getChildLayoutPosition(view) %3==0) {
                    outRect.left = 0;
                }

            }
        });//添加一个分割线

        //mRecyclerView.addItemDecoration(decoration2);//还可以再添加一个分割线
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画。github上有很多动画效果，如RecyclerViewItemAnimators
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mRecyclerView.removeItemDecoration(decoration1);//移除分割线，即使没有显示也可以移除
        mRecyclerView.removeItemDecoration(decoration2);
        switch (item.getItemId()) {
            case R.id.add:
                mRecyclerViewAdapter.addData(new Random().nextInt(5));
                break;
            case R.id.delete:
                mRecyclerViewAdapter.removeData(new Random().nextInt(5));
                break;
            //通过RecyclerView去实现ListView、GridView、瀑布流的效果基本上没有什么区别，仅仅通过设置不同的LayoutManager即可实现
            case R.id.listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.staggeredHorizontalGridView://Staggered：错列的，叉排的。
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));//5行
                break;
            case R.id.staggeredVerticalGridview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));//3列
                break;
            case R.id.staggeredAdapter:
                mRecyclerView.setAdapter(mStaggeredAdapter);
                break;
            case R.id.recyclerViewAdapter:
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                break;
            case R.id.chart:
                Intent intent = new Intent(MainActivity.this,ChartViewActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this, position + " 被点击了", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(MainActivity.this, position + "被长按了", Toast.LENGTH_SHORT).show();
    }


}
