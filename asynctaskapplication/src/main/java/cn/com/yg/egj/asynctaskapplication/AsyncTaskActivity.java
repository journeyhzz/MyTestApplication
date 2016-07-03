package cn.com.yg.egj.asynctaskapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncTaskActivity extends AppCompatActivity {
    ProgressBar mProgressBar = null;
    ImageView mImageView = null;
    String URL = "http://g.hiphotos.baidu.com/baike/pic/item/37d3d539b6003af354ff0768352ac65c1038b675.jpg";

    public static Intent newIntent(Context PackageContext) {
        Intent intent = new Intent(PackageContext, AsyncTaskActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        mProgressBar = (ProgressBar) findViewById(R.id.asyncTask_progressBar);
        mImageView = (ImageView) findViewById(R.id.imageView);
        //实例化AsyncTask子类
        FetchImageTask fetchImageTask = new FetchImageTask();
        //设置传递进去的操作
        fetchImageTask.execute(URL);

    }


    private class FetchImageTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            //获取传递进来的参数
            String imgUri = params[0];
            Bitmap bitmap = null;
            URL url = null;
            URLConnection connection;
            InputStream inputStream = null;
            try {
                url = new URL(imgUri);
                //获取网络连接对象
                connection = url.openConnection();
                //获取输入流
                inputStream = connection.getInputStream();
                Thread.sleep(3000);
                //进行输入流的 包装
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                //解析输入流为BitMap
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                //关闭输入流
                inputStream.close();
                bufferedInputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //返回解析的图片
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
//            mImageView.setImageBitmap(null);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                Toast.makeText(AsyncTaskActivity.this, "成功获取图片！！", Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.GONE);
                mImageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(AsyncTaskActivity.this, "图片获取失败！！", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
