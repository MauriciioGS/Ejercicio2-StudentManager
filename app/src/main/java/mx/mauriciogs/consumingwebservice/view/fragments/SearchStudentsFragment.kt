package mx.mauriciogs.consumingwebservice.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import mx.mauriciogs.consumingwebservice.R
import mx.mauriciogs.consumingwebservice.data.VolleyAPI
import mx.mauriciogs.consumingwebservice.data.model.StudentsItem
import mx.mauriciogs.consumingwebservice.databinding.FragmentSearchStudentsBinding
import mx.mauriciogs.consumingwebservice.view.fragments.viewmodels.GetStudentsFragmentViewModel

class SearchStudentsFragment : Fragment() {
    private var _binding : FragmentSearchStudentsBinding? = null
    private val binding get() = _binding!!

    private val searchStudentsFragmentViewModel: GetStudentsFragmentViewModel by viewModels() {
        GetStudentsFragmentViewModel.GetStudentsFragmentVMFactory((VolleyAPI((requireContext()))))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initButton()
    }

    private fun initButton() {
        with(binding) {
            tilSearch.setEndIconOnClickListener {
                val id = tilSearch.editText?.text.toString().trim()
                if (id.isBlank())
                    Toast.makeText(requireContext(),
                        getString(R.string.student_id_error),
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    searchStudentsFragmentViewModel.getAStudent(id)

            }
        }
    }

    private fun initObservers() {
        searchStudentsFragmentViewModel.student.observe(requireActivity()) { student ->
            setUI(student)
        }
        searchStudentsFragmentViewModel.searchFailure.observe(requireActivity()) {
            if (it) {
                Toast.makeText(requireContext(),
                    getString(R.string.student_id_error3,
                        binding.tilSearch.editText?.text.toString().trim()
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                binding.tilSearch.error = getString(R.string.student_id_error2)
                binding.tilSearch.editText?.text?.clear()
            }
        }
    }

    private fun setUI(student: StudentsItem?) {
        if (student != null) {
            with(binding) {
                tvAccount.text = student.cuenta
                tvName.text = student.nombre
                tvAge.text = student.edad + " Años"
                tvSubjects.text = student.materias.count().toString() + " Materias"
            }
        }
    }
}