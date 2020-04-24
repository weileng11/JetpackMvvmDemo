package me.hgj.jetpackmvvm.demo.ui.share

import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import kotlinx.android.synthetic.main.fragment_share_ariticle.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.navigation.NavHostFragment
import me.hgj.jetpackmvvm.demo.R
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.ext.getActivityMessageViewModel
import me.hgj.jetpackmvvm.demo.app.ext.initClose
import me.hgj.jetpackmvvm.demo.app.ext.showMessage
import me.hgj.jetpackmvvm.demo.app.util.SettingUtil
import me.hgj.jetpackmvvm.demo.databinding.FragmentShareAriticleBinding
import me.hgj.jetpackmvvm.demo.ui.tree.TreeViewModel
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/10
 * 描述　:
 */
class AddAriticleFragment:BaseFragment<AriticleViewModel,FragmentShareAriticleBinding>()  {

    override fun layoutId() = R.layout.fragment_share_ariticle

    override fun initView() {

        mDatabind.vm = mViewModel

        appViewModel.userinfo.value?.let { if(it.nickname.isEmpty()) mViewModel.shareName.set(it.username) else  mViewModel.shareName.set(it.nickname) }
        appViewModel.appColor.value?.let { SettingUtil.setShapColor(share_submit, it) }

        toolbar.run {
            initClose("分享文章") {
                Navigation.findNavController(it).navigateUp()
            }
            inflateMenu(R.menu.share_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share_guize -> {
                        activity?.let {activity ->
                            MaterialDialog(activity, BottomSheet())
                                .lifecycleOwner(viewLifecycleOwner)
                                .show {
                                title(text = "温馨提示")
                                customView(R.layout.customview,scrollable = true,horizontalPadding = true)
                                positiveButton(text = "知道了")
                                cornerRadius(16f)
                                getActionButton(WhichButton.POSITIVE).updateTextColor(SettingUtil.getColor(activity))
                                getActionButton(WhichButton.NEGATIVE).updateTextColor(SettingUtil.getColor(activity))
                            }
                        }

                    }
                }
                true
            }
        }
        share_submit.clickNoRepeat {
            if(mViewModel.shareTitle.get().isEmpty()){
                showMessage("请填写文章标题")
            }else if(mViewModel.shareUrl.get().isEmpty()){
                showMessage("请填写文章链接")
            }else{
                showMessage("确定分享吗？",positiveButtonText = "分享",positiveAction = {
                    mViewModel.addAriticle()
                },negativeButtonText = "取消")
            }
        }
    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {
        mViewModel.addData.observe(viewLifecycleOwner, Observer {
            parseState(it,{
                getActivityMessageViewModel().shareArticle.postValue(true)
                NavHostFragment.findNavController(this).navigateUp()
            },{
                showMessage(it.errorMsg)
            })
        })
    }
}