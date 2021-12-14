package com.route.todo_c35_sat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.route.todo_c35_sat.database.MyDataBase
import java.util.*

class TodoListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    lateinit var recyclerView: RecyclerView
    lateinit var calendarView: MaterialCalendarView
    val adapter = TodosRecyclerAdapter(null);
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews();
    }

    override fun onStart() {
        super.onStart()//once fragment is visible
    }

    override fun onResume() {
        super.onResume()
        // once user can interact
        getTodosListFromDB();
    }

    var calendar = Calendar.getInstance();


    fun getTodosListFromDB() {
        //  if(context==null)return;
        val todosList = MyDataBase.getInstance(requireContext())
            .todoDao()
            .getTodosByDate(calendar.clearTime().time);// calendar.time (returns time in millisecond)
        adapter.changeData(todosList.toMutableList())
    }

    private fun initViews() {
        recyclerView = requireView().findViewById(R.id.todos_recycler)
        calendarView = requireView().findViewById(R.id.calendarView)
        calendarView.selectedDate = CalendarDay.today()
        recyclerView.adapter = adapter
        calendarView.setOnDateChangedListener { widget, calendarDay, selected ->
            Log.e("calendar day", "" + calendarDay.month);
            Log.e("calendar inside class", "" + calendar.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, calendarDay.day)
            calendar.set(Calendar.MONTH, calendarDay.month - 1)
            calendar.set(Calendar.YEAR, calendarDay.year)
            getTodosListFromDB();
        }


    }
}