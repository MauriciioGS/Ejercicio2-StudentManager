package mx.mauriciogs.consumingwebservice.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mx.mauriciogs.consumingwebservice.data.StudentRemoteRepository
import mx.mauriciogs.consumingwebservice.data.VolleyAPI
import mx.mauriciogs.consumingwebservice.data.model.Students
import mx.mauriciogs.consumingwebservice.databinding.FragmentGetStudentsBinding
import mx.mauriciogs.consumingwebservice.view.fragments.viewmodels.GetStudentsFragmentViewModel

class GetStudentsFragment : Fragment() {

    private var _binding : FragmentGetStudentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyAPI: VolleyAPI

    private lateinit var studentsAdapter : StudentsAdapter

    private val getStudentsFragmentViewModel: GetStudentsFragmentViewModel by viewModels() {
        GetStudentsFragmentViewModel.GetStudentsFragmentVMFactory((VolleyAPI((requireContext()))))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        volleyAPI = VolleyAPI(requireContext())

        studentsAdapter = StudentsAdapter(requireContext())
        binding.rvStudents.visibility = View.VISIBLE
        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudents.adapter = studentsAdapter

        getStudents()
        initObservers()
    }

    private fun getStudents() {
        getStudentsFragmentViewModel.getAllStudents()
    }

    private fun initObservers() {
        getStudentsFragmentViewModel.allStudentsList.observe(requireActivity()) { studentList ->
            setRecyclerView(studentList)
        }
    }

    private fun setRecyclerView(studentList: Students?) {
        if (studentList != null) {
            binding.textView.visibility = View.GONE
            studentsAdapter.studentsList(studentList)
        }
    }

}