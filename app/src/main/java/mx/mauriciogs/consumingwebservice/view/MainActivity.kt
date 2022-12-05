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
    private lateinit var newStudentFragment: NewStudentFragment
    private lateinit var getStudentsFragment: GetStudentsFragment
    private lateinit var searchStudentsFragment: SearchStudentsFragment
    private lateinit var deleteStudentFragment: DeleteStudentFragment

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
        newStudentFragment = NewStudentFragment()
        supportFragmentManager
            .beginTransaction()
            .add(binding.frameLayout.id, newStudentFragment)
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
                transaction.replace(binding.frameLayout.id, newStudentFragment).commit()
            }
            R.id.getStudents -> {
                setToolbarTitle(getString(R.string.get_students))
                getStudentsFragment = GetStudentsFragment()
                transaction.replace(binding.frameLayout.id, getStudentsFragment).commit()
            }
            R.id.searchStudent -> {
                setToolbarTitle(getString(R.string.search_students))
                searchStudentsFragment = SearchStudentsFragment()
                transaction.replace(binding.frameLayout.id, searchStudentsFragment).commit()
            }
            R.id.deleteStudent -> {
                setToolbarTitle(getString(R.string.delete_student))
                deleteStudentFragment = DeleteStudentFragment()
                transaction.replace(binding.frameLayout.id, deleteStudentFragment).commit()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}