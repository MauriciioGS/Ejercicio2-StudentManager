package mx.mauriciogs.consumingwebservice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import mx.mauriciogs.consumingwebservice.R
import mx.mauriciogs.consumingwebservice.databinding.ActivityMainBinding
import mx.mauriciogs.consumingwebservice.view.fragments.DeleteStudentFragment
import mx.mauriciogs.consumingwebservice.view.fragments.GetStudentsFragment
import mx.mauriciogs.consumingwebservice.view.fragments.NewStudentFragment
import mx.mauriciogs.consumingwebservice.view.fragments.SearchStudentsFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        setInitialFragment()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        initNavView()
    }

    private fun setInitialFragment() {
        setToolbarTitle(getString(R.string.new_student))
        supportFragmentManager
            .beginTransaction()
            .add(binding.layoutMainContent.mainContent.id, NewStudentFragment())
            .commit()
    }

    private fun initNavView() {
        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when(item.itemId) {
            R.id.newStudent -> {
                setToolbarTitle(getString(R.string.new_student))
                transaction.replace(R.id.main_content, NewStudentFragment()).commit()
            }
            R.id.getStudents -> {
                setToolbarTitle(getString(R.string.get_students))
                transaction.replace(R.id.main_content, GetStudentsFragment()).commit()
            }
            R.id.searchStudent -> {
                setToolbarTitle(getString(R.string.search_students))
                transaction.replace(R.id.main_content, SearchStudentsFragment()).commit()
            }
            R.id.deleteStudent -> {
                setToolbarTitle(getString(R.string.delete_student))
                transaction.replace(R.id.main_content, DeleteStudentFragment()).commit()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}