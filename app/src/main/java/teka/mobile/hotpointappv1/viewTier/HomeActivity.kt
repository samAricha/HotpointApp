package teka.mobile.hotpointappv1.viewTier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import teka.mobile.hotpointappv1.R
import teka.mobile.hotpointappv1.loginModule.LoginActivity

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView:NavigationView
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mAuth = FirebaseAuth.getInstance()

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout)
        navView = findViewById(R.id.nav_view)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when(item.itemId){
            R.id.nav_home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_notes -> {
                Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_library -> {
                Toast.makeText(this, "Library", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.nav_clubs -> {
                Toast.makeText(this, "Clubs", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_logout -> {
                mAuth.signOut()
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
            }
            else -> {
                false
            }

        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    // override the onSupportNavigateUp() function to launch the Drawer when the hamburger icon is clicked
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}