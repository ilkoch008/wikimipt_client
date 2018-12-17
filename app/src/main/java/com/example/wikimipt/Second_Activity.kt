package com.example.wikimipt

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second_.*
import kotlinx.android.synthetic.main.app_bar_second_.*

class Second_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var router : Router
    var first_tap = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(this, R.id.fragment_container)
        setContentView(R.layout.activity_second_)
        router.navigateTo (false, :: Main_page)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (!router.navigateBack()) {
//        } else{
            if(first_tap){
                super.onBackPressed()
            } else {
                first_tap = true
                Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    first_tap = false
                }, 2000)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.second_, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_main -> {
                router.navigateTo (false, ::Main_page)
            }
            R.id.nav_teachers -> {
                router.navigateTo (false, ::Teachers)
            }
            R.id.nav_faculties -> {
                router.navigateTo (false, ::Faculties)
            }
            R.id.nav_departments -> {
                router.navigateTo (false, ::Departments)
            }
            R.id.nav_subjects -> {
                router.navigateTo (false, ::Subjects)
            }
            R.id.nav_rating ->{
                router.navigateTo (false, ::Rating)
            }
            R.id.nav_login -> {
                router.navigateTo (false, ::Login)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}

