package me.hgj.jetpackmvvm.demo.ui.tree

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.include_viewpager.*
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.bindViewPager2
import me.hgj.jetpackmvvm.demo.app.ext.init
import me.hgj.jetpackmvvm.demo.app.ext.setUiTheme
import me.hgj.jetpackmvvm.demo.databinding.FragmentViewpagerBinding

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/2
 * 描述　: 广场模块父Fragment管理四个子fragment
 */
class TreeArrFragment : BaseFragment<TreeViewModel, FragmentViewpagerBinding>() {

    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")

    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(PlazaFragment())
        fragments.add(AskFragment())
        fragments.add(SystemFragment())
        fragments.add(NavigationFragment())
    }

    override fun layoutId() = R.layout.fragment_viewpager

    override fun initView() {
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { viewpager_linear.setBackgroundColor(it) }
        include_viewpager_toolbar.run {
            inflateMenu(R.menu.todo_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.todo_add -> {
                        Navigation.findNavController(this)
                            .navigate(R.id.action_mainfragment_to_addAriticleFragment)
                    }
                }
                true
            }
        }
    }

    override fun lazyLoadData() {
        //初始化viewpager2
        view_pager.init(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mStringList = titleData) {
            if (it != 0) {
                include_viewpager_toolbar.menu.clear()
            } else {
                include_viewpager_toolbar.menu.hasVisibleItems().let { flag ->
                    if (!flag) include_viewpager_toolbar.inflateMenu(R.menu.todo_menu)
                }
            }
        }
    }

    override fun createObserver() {
        appViewModel.appColor.observe(viewLifecycleOwner, Observer {
            setUiTheme(it, viewpager_linear)
        })
    }

}