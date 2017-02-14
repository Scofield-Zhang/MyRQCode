package zhang.tao.myrqcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import zhang.tao.myrqcode.activity.WebActivity;

public class MainActivity extends AppCompatActivity implements QRCodeView.Delegate , View.OnClickListener {

    private ImageView ivRqCode;
    private AsyncTask task;
    private ZBarView zv;
    private Button button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ivRqCode = (ImageView) findViewById(R.id.iv_rq_code);
        zv = (ZBarView) findViewById(R.id.zv);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        zv.setDelegate(this);
        //createChineseQRCodeWithLogo();
        zv.startSpot();

    }

    @Override
    protected void onStart() {
        super.onStart();
        zv.startCamera();
        zv.showScanRect();
    }

    @Override
    protected void onStop() {
        zv.stopCamera();
        super.onStop();
    }



    private void createChineseQRCodeWithLogo() {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%0%BB%E7%BB%93.md
         */
        task = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.mipmap.ic_launcher);
                return QRCodeEncoder.syncEncodeQRCode("张涛是你大爷", BGAQRCodeUtil.dp2px(MainActivity.this, 150), Color.parseColor("#000000"), logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    ivRqCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MainActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();}
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        zv.onDestroy();
        super.onDestroy();
        //task.cancel(true);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        zv.startSpot();
        if (result!=null){
            Intent intent = new Intent();
            intent.putExtra("url",result);
            intent.setClass(this,WebActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this,"相機出錯了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                zv.openFlashlight();

                break;
            case R.id.button2:
                zv.closeFlashlight();
                break;
        }
    }
}
