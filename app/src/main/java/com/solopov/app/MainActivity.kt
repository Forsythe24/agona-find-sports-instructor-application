package com.solopov.app

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solopov.app.R
import com.solopov.app.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigator.attachNavController(navController!!, R.navigation.main_nav_graph)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bnv)
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.logInFragment ||
                destination.id == R.id.signUpFragment) {
                bottomNavigationView.visibility = GONE
            } else {
                bottomNavigationView.visibility = VISIBLE
            }
        }

        findViewById<BottomNavigationView>(R.id.main_bnv).apply {
            navController?.let {
                setupWithNavController(this, it)
            }
        }
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
