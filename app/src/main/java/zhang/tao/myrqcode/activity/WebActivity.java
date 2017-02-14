package zhang.tao.myrqcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import zhang.tao.myrqcode.R;

/**
 * =========================
 *
 * @author 张涛
 * @project ${PROJECT_NANE}
 * @file ${FILE}
 * @DATE 2017/2/15
 * =========================
 */
public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        WebView wb = (WebView) findViewById(R.id.wb);
        Toast.makeText(this,getIntent().getStringExtra("url")+"<<<<<",Toast.LENGTH_SHORT).show();
        wb.loadUrl("https://www.baidu.com");
    }
}
