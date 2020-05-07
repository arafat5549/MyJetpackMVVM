package com.example.myjetpackmvvm_java.ui.test

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.base.BaseActivity
import com.example.myjetpackmvvm_java.app.weight.customview.PriorityDialog
import com.example.myjetpackmvvm_java.data.enums.TodoType
import com.example.myjetpackmvvm_java.databinding.ActivityTestBinding
import kotlinx.android.synthetic.main.activity_test.*
import me.hgj.jetpackmvvm.util.ActivityMessenger


/**
 * 作者　: WangYao
 * 时间　: 2020/4/10
 * 描述　:
 */
class TestActivity : BaseActivity<TestViewModel, ActivityTestBinding>() {

    override fun layoutId() = R.layout.activity_test
    var REQUEST_CAMERA_1 = 9999;


    override fun initView() {
        mDatabind.vm = mViewModel
        mViewModel.todoLeve.set(TodoType.TodoType1.content)
        mViewModel.todoColor.set(TodoType.TodoType1.color)

        add_todo_proxlinear.setOnClickListener {
            let {
                PriorityDialog(it, TodoType.byValue(mViewModel.todoLeve.get()).type).apply {
                    setPriorityInterface(object : PriorityDialog.PriorityInterface {
                        override fun onSelect(type: TodoType) {
                            mViewModel.todoLeve.set(type.content)
                            mViewModel.todoColor.set(type.color)
                        }
                    })
                }.show()
            }
        }

        tv_test.setOnClickListener{ openCamera_1();}


//        RxPermissions.Lazy { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(arrayOf(Manifest.permission.CAMERA),REQUEST_CAMERA_1)
//            }
//        }

    }

    private fun openCamera_1() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // 启动系统相机
        //startActivityForResult(intent, REQUEST_CAMERA_1)

        ActivityMessenger.startActivityForResult(this,intent){
            if (it == null) {
                 //未成功处理，即（ResultCode != RESULT_OK）

             } else {
                  //处理成功，这里可以操作返回的intent
                val bundle = it!!.extras // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                val bitmap = bundle!!["data"] as Bitmap? // 将data中的信息流解析为Bitmap类型
                iv_picture.setImageBitmap(bitmap) // 显示图片
             }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int,data:Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) { // 如果返回数据
//            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
//                val bundle = data!!.extras // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
//                val bitmap = bundle!!["data"] as Bitmap? // 将data中的信息流解析为Bitmap类型
//                iv_picture.setImageBitmap(bitmap) // 显示图片
//            }
//        }
//    }



    override fun createObserver() {

    }

    override fun showToast(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}