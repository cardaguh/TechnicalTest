package com.zemoga.zemogatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayout
import com.zemoga.zemogatest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0F

        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text){
                    getString(R.string.all) -> {
                        navController.navigate(R.id.postsFragment)
                    }

                    getString(R.string.favorites) -> {
                        navController.navigate(R.id.favoritesFragment)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    navController.addOnDestinationChangedListener { controller, _, _ ->
        if(binding.tabLayout.visibility == View.GONE ){
            binding.tabLayout.visibility = View.VISIBLE
        }

        when(controller.currentDestination?.id){
            R.id.postsFragment -> {
                binding.tabLayout.getTabAt(0)?.select()
            }
            R.id.detailFragment -> {
                binding.tabLayout.visibility = View.GONE
            }

        }


    }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}