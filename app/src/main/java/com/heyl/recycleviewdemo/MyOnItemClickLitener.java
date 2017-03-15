package com.heyl.recycleviewdemo;

/**
 * Created by heyl on 2017/3/15.
 */

import android.view.View;

/**系统没有提供ClickListener和LongClickListener，我们自己通过接口回调处理 */
public interface MyOnItemClickLitener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
