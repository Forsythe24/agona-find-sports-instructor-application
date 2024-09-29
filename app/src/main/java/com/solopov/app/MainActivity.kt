package com.solopov.app

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solopov.app.di.deps.findComponentDependencies
import com.solopov.app.di.main.MainComponent
import com.solopov.app.navigation.Navigator
import com.solopov.common.base.BaseActivity
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.utils.ParamsKey.FROM_INSTRUCTORS_SCREEN_FLAG_KEY
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var userDataStore: UserDataStore

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
        attachNavControllerWithRespectToStartDestination()
        setupBottomNavigation()
    }

    private fun isFromInstructorsList(bundle: Bundle?): Boolean {
        val isFromInstructorsList = bundle?.getBoolean(FROM_INSTRUCTORS_SCREEN_FLAG_KEY)

        return isFromInstructorsList ?: false
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bnv)

        navController?.addOnDestinationChangedListener { _, destination, bundle ->

            if (destination.id == R.id.logInFragment ||
                destination.id == R.id.signUpFragment ||
                destination.id == R.id.chatFragment ||
                destination.id == R.id.passwordRecoveryFragment ||
                destination.id == R.id.instructApplicationFragment ||
                destination.id == R.id.editProfileFragment ||
                isFromInstructorsList(bundle)
            ) {

                bottomNavigationView.visibility = GONE
            } else {
                bottomNavigationView.visibility = VISIBLE
            }
        }

        bottomNavigationView.apply {
            navController?.let {
                setupWithNavController(this, it)
            }
        }
    }

    private fun attachNavControllerWithRespectToStartDestination() {
        lifecycleScope.launch {
            val id = userDataStore.getUserId()
            val graph = navController!!.navInflater.inflate(R.navigation.main_nav_graph)
            if (id != null) {
                graph.setStartDestination(R.id.userProfileFragment)
            } else {
                graph.setStartDestination(R.id.logInFragment)
            }
            navigator.attachNavController(navController!!, graph)
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
