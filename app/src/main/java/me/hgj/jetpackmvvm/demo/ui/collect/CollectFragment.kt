package me.hgj.jetpackmvvm.demo.ui.collect

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.CollectViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.bindViewPager2
import me.hgj.jetpackmvvm.demo.app.ext.init
import me.hgj.jetpackmvvm.demo.app.ext.initClose
import me.hgj.jetpackmvvm.demo.databinding.FragmentCollectBinding

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/10
 * 描述　: 收藏
 */
class CollectFragment:BaseFragment<CollectViewModel,FragmentCollectBinding>() {

    var titleData = arrayListOf("文章","网址")
    private var fragments : ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(CollectAriticleFragment())
        fragments.add(CollectUrlFragment())
    }
    override fun layoutId() = R.layout.fragment_collect

    override fun initView() {
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { collect_viewpager_linear.setBackgroundColor(it) }
        //初始化viewpager2
        collect_view_pager.init(this,fragments)
        //初始化 magic_indicator
        collect_magic_indicator.bindViewPager2(collect_view_pager,mStringList = titleData)
        toolbar.initClose(){
            Navigation.findNavController(it).navigateUp()
        }

    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {
    }
}