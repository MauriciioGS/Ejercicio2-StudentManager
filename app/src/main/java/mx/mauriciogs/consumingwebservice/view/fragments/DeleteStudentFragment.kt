package mx.mauriciogs.consumingwebservice.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mx.mauriciogs.consumingwebservice.R
import mx.mauriciogs.consumingwebservice.data.VolleyAPI
import mx.mauriciogs.consumingwebservice.databinding.FragmentDeleteStudentBinding
import mx.mauriciogs.consumingwebservice.databinding.FragmentSearchStudentsBinding
import mx.mauriciogs.consumingwebservice.view.fragments.viewmodels.GetStudentsFragmentViewModel

class DeleteStudentFragment : Fragment() {

    private var _binding : FragmentDeleteStudentBinding? = null
    private val binding get() = _binding!!

    private val deleteStudentFragmentViewModel: GetStudentsFragmentViewModel by viewModels() {
        GetStudentsFragmentViewModel.GetStudentsFragmentVMFactory((VolleyAPI((requireContext()))))
    }

    private lateinit var studentsAdapter : StudentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeleteStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initButton()
        initRecyclerView()
    }

    private fun initButton() {
        with(binding) {
            tilDelete.setEndIconOnClickListener {
                val id = tilDelete.editText?.text.toString().trim()
                if (id.isBlank())
                    Toast.makeText(requireContext(),
                        getString(R.string.student_id_error),
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    deleteStudentFragmentViewModel.deleteAStudent(id)
            }
        }
    }

    private fun initObservers() {
        deleteStudentFragmentViewModel.allStudentsDeleted.observe(requireActivity()) { students ->
            if (students != null) {
                studentsAdapter.studentsList(students)
            }
        }
        deleteStudentFragmentViewModel.deleteFailure.observe(requireActivity()) {
            if (it) {
                Toast.makeText(requireContext(),
                    getString(R.string.student_id_error3,
                        binding.tilDelete.editText?.text.toString().trim()
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                binding.tilDelete.error = getString(R.string.student_id_error2)
                binding.tilDelete.editText?.text?.clear()
            }
        }
    }

    private fun initRecyclerView() {
        studentsAdapter = StudentsAdapter(requireContext())
        binding.rvStudents.visibility = View.VISIBLE
        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudents.adapter = studentsAdapter
    }

}