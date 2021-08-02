package com.example.materialtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private Fruit[] fruits = {new Fruit("Apple",R.drawable.apple),
    new Fruit("Banana",R.drawable.banana),new Fruit("Pear",R.drawable.pear),
    new Fruit("Watermelon",R.drawable.watermelon),new Fruit("Grape",R.drawable.grape),
    new Fruit("Pineapple",R.drawable.pineapple),new Fruit("Cherry",R.drawable.cherry),
    new Fruit("Mango",R.drawable.mango),new Fruit("Strawberry",R.drawable.strawberry)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toobler = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toobler);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮的图标
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        //获取NavigationView实例
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        //将call菜单项设置为默认选中
        navigationView.setCheckedItem(R.id.nav_call);
        //设置菜单选中事件的监听器
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //将滑动菜单关闭
                drawerLayout.closeDrawers();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用Snackbar的make()方法创建Snackbar对象
                //第一个参数传入view，当前界面布局的任意一个view就行，Snackbar会根据这个view查找最外层布局来提示信息
                //第二个参数就是Snackbar中显示的内容
                //第三个参数时Snackbar显示时长
                //setAction()方法用来指定一个动作
                Snackbar.make(view,"删除文件",
                        Snackbar.LENGTH_SHORT).setAction("Undo",
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"文件恢复",
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //第二个参数用于指定列数
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        //设置下拉进度条的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits(adapter);
            }
        });
    }

    private void refreshFruits(final FruitAdapter adapter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将线程切换会主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        //通知数据发生了变化
                        adapter.notifyDataSetChanged();
                        //表示刷新事件结束，并且隐藏进度条
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    //加载菜单文件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    //处理每个按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //对HomeAsUp按钮(导航按钮)绑定点击
            //该控件的id如下
            case android.R.id.home:
                //openDrawer()方法可以将滑动菜单显示出来
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(MainActivity.this,"你点击了上传",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this,"你点击了删除",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this,"你点击了设置",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
