package com.app.walktopia


import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Bundle

class dashboard : AppCompatActivity() {
    var bottom_navigation: BottomNavigationView? = null
    var HomeFragment = homeFragment()
    var ProfileFragment = profileFragment()
    var OrderFragement = walkFragnent()
    var ThemeFragment = themeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        bottom_navigation = findViewById(R.id.bottom_navigation)
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment).commit()
        bottom_navigation!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ProfileFragment).commit()


                    return@OnNavigationItemSelectedListener true
                }
                R.id.walk -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, OrderFragement).commit()

                    return@OnNavigationItemSelectedListener true
                }

                R.id.themes -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ThemeFragment).commit()

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onBackPressed() {
        val currentFragment =
            this.supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is profileFragment || currentFragment is walkFragnent) {
            supportFragmentManager.beginTransaction()
                .apply { replace(R.id.container, HomeFragment) .commit() }
        } else {
            super.onBackPressed()
        }
    }


}