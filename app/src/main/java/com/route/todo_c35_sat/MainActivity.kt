package com.route.todo_c35_sat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var addButton: FloatingActionButton
    val todoListFragment = TodoListFragment();
    val settingsFragment = SettingsFragment();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.navigation_view)
        addButton = findViewById(R.id.add)
        addButton.setOnClickListener {
            //   val intent = Intent(this,UpdateTodoActivity::class.java)
            //   startActivity(intent)
            showAddBottomSheet()
        }
        bottomNavigation.setOnItemSelectedListener(
            NavigationBarView.OnItemSelectedListener { item ->
                if (item.itemId == R.id.navigation_list) {
                    pushFragment(todoListFragment)
                } else if (item.itemId == R.id.navigation_settings) {
                    pushFragment(settingsFragment)
                }
                return@OnItemSelectedListener true;
            }
        )
        bottomNavigation.selectedItemId = R.id.navigation_list

    }

    private fun showAddBottomSheet() {
        val addBottomSheet = AddTodoBottomSheet();
        addBottomSheet.show(supportFragmentManager, "");
        addBottomSheet.onTodoAddedListener = object : AddTodoBottomSheet.OnTodoAddedListener {
            override fun onTodoAdded() {
                //refresh Todos List from database inside listFragment
                if (todoListFragment.isVisible)
                    todoListFragment.getTodosListFromDB();
            }
        }
    }

    fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}