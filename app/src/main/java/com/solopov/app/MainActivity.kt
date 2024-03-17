package com.solopov.app

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.solopov.app.R
import com.solopov.app.di.deps.findComponentDependencies
import com.solopov.app.di.main.MainComponent
import com.solopov.app.navigation.Navigator
import com.solopov.common.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    @Inject lateinit var navigator: Navigator

    private var navController: NavController? = null

    override fun inject() {
        MainComponent.init(this, findComponentDependencies())
            .inject(this)
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigator.attachNavController(navController!!, R.navigation.main_nav_graph)
    }

    override fun subscribe(viewModel: MainViewModel) {
    }

    override fun onDestroy() {
        super.onDestroy()
        navController?.let {
            navigator.detachNavController(it)
        }
    }
}
