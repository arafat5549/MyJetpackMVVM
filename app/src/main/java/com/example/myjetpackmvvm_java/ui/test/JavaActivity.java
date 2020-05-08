package com.example.myjetpackmvvm_java.ui.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.example.myjetpackmvvm_java.R;
import com.example.myjetpackmvvm_java.app.base.BaseActivity;
import com.example.myjetpackmvvm_java.app.weight.customview.PriorityDialog;
import com.example.myjetpackmvvm_java.data.enums.TodoType;
import com.example.myjetpackmvvm_java.databinding.ActivityTestBinding;

import org.jetbrains.annotations.NotNull;

import me.hgj.jetpackmvvm.util.ActivityMessenger;
import rx_activity_result2.RxActivityResult;

public class JavaActivity extends BaseActivity<TestViewModel, ActivityTestBinding> {
    static  int REQUEST_CAMERA_1 = 9999;
    @Override
    public int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        setMContext(this);

        mDatabind.setVm(mViewModel);
        mViewModel.getTodoLeve().set(TodoType.TodoType1.getContent());
        mViewModel.getTodoColor().set(TodoType.TodoType1.getColor());
        mDatabind.addTodoProxlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PriorityDialog(getMContext(), TodoType.Companion.byValue(mViewModel.getTodoLeve().get()).getType()).setPriorityInterface(
                        new PriorityDialog.PriorityInterface(){
                            @Override
                            public void onSelect(@NotNull TodoType type) {
                                mViewModel.getTodoLeve().set(type.getContent());
                                mViewModel.getTodoColor().set(type.getColor());
                            }
                        }
                );
            }
        });

        mDatabind.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera_1();
            }
        });
    }

    @Override
    public void createObserver() {

    }

    @Override
    public void showToast(@NotNull String s) {

    }

    /**
     *
     */
    private void openCamera_1() {
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        setResult(REQUEST_CAMERA_1,intent);
        RxActivityResult.on(this).startIntent(intent).subscribe(result->{
            Intent data = result.data();
            Bundle bundle = data.getExtras();
            int resultCode = result.resultCode();
            int requestCode = result.requestCode();

            if (resultCode == RESULT_OK) {
                if(requestCode == REQUEST_CAMERA_1)
                {
                    Bitmap bitmap = bundle!=null ?(Bitmap)bundle.get("data") : null;
                    if(bitmap!=null) mDatabind.ivPicture.setImageBitmap(bitmap);// 显示图片
                }
            } else {
            }

        });
    }
}
