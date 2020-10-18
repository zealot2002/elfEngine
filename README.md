# elfEngine
#### Android 页面绘制引擎。旨在最大限度减少android开发者重复性的劳动。
<img src="https://github.com/zealot2002/elfEngine/blob/master/elfpage1.png" width="50%">
<img src="https://github.com/zealot2002/elfEngine/blob/master/elfpage2.png" width="50%">

引擎功能：  

1，提供自动化的页面绘制。  

2，不同app各自提供elf-template，参考elf-template。  

3，宿主app只提供dataProvider（遵守elf协议）。  

4，引擎拿到app相关的template和data，自动完成绘制。  


### 添加依赖
```
compile 'com.zzy.elfengine:0.1.2'
```

### 用法
一，制作app用到的所有template，参考elf-template例子。  
  
  
二，初始化template，注册到elfEngine。 参考MyApplication中的初始化。   


三，Activity中提供dataProvider。    

```

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment_container);

        Fragment f = getNormalFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_framelayout,f);
        ft.commit();
    }

    /*普通fragment*/
    private Fragment getNormalFragment(){
        return ElfProxy.getInstance().makeNormalPage(ElfConstants.ELF_PAGE_HOME,new ElfConstact.DataProvider() {
            @Override
            public void onGetDataEvent(Context context, int pageNum, ElfConstact.Callback callback) {
                try{
                    String data = FileUtils.readFileFromAssets(HomeActivity.this,"projectPage.json");
                    Page page = PageJsonParser.parse(data);
                    callback.onCallback(true,page);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /*带有下拉刷新和上拉加载*/
    private Fragment getRefreshFragment() {
        return ElfProxy.getInstance().makeRefreshPage(ElfConstants.ELF_PAGE_HOME,new ElfConstact.DataProvider() {
            @Override
            public void onGetDataEvent(Context context, int pageNum, ElfConstact.Callback callback) {
                try{
                    String data = FileUtils.readFileFromAssets(HomeActivity.this,"projectPage.json");
                    Page page = PageJsonParser.parse(data);
                    callback.onCallback(true,page);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
```

使用到的设计模式：
1，抽象工厂模式   
2，工厂方法模式
3，原型模式
4，单例模式
5，Builder模式
6，Adapter模式
7，Bridge模式
8，装饰模式
9，Proxy模式
10，外观模式
11，策略模式
12，模板模式
13，组合模式

<p><a href="https://github.com/zealot2002/elfEngine/wiki/elf-page介绍">
查看更多介绍</a>.</p>
