package mx.mauriciogs.consumingwebservice.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mx.mauriciogs.consumingwebservice.R
import mx.mauriciogs.consumingwebservice.data.VolleyAPI
import mx.mauriciogs.consumingwebservice.data.model.Materia
import mx.mauriciogs.consumingwebservice.data.model.StudentsItem
import mx.mauriciogs.consumingwebservice.databinding.FragmentNewStudentBinding
import mx.mauriciogs.consumingwebservice.view.fragments.viewmodels.NewStudentFragmentViewModel

class NewStudentFragment : Fragment() {

    private var _binding : FragmentNewStudentBinding? = null
    private val binding get() = _binding!!

    private val newStudentFragmentViewModel: NewStudentFragmentViewModel by viewModels() {
        NewStudentFragmentViewModel.NewStudentFragmentVMFactory((VolleyAPI((requireContext()))))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        binding.btnAdd.setOnClickListener {
            checkData()
        }
    }

    private fun initObservers() {
        newStudentFragmentViewModel.addSuccess.observe(requireActivity()) { isSuccess ->
            if (!isSuccess)
                Toast.makeText(requireContext(),
                    "Ha ocurrido un erro al insertar",
                    Toast.LENGTH_SHORT
                ).show()
            else
                openDialog()
        }
    }

    private fun checkData() {
        with(binding) {
            val account = tilCuenta.editText?.text.toString().trim()
            val name = tilNombre.editText?.text.toString().trim()
            val age = tilEdad.editText?.text.toString().trim()
            when {
                account.isBlank() -> {
                    tilCuenta.editText?.let { emptyField(it, R.string.txt_no_account) }
                }
                name.isBlank() -> {
                    tilNombre.editText?.let { emptyField(it, R.string.txt_no_name) }
                }
                age.isBlank() -> {
                    tilEdad.editText?.let { emptyField(it, R.string.txt_no_age) }
                }
                else -> {
                    val subjects = getCheckBox()
                    if (subjects.isEmpty())
                        Toast.makeText(requireContext(),
                            getText(R.string.txt_no_subjects),
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        val newStudent = StudentsItem(cuenta = account, edad = age, nombre = name, materias = subjects)
                        newStudentFragmentViewModel.addStudent(newStudent)
                    }
                }
            }
        }
    }

    private fun openDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Estudiante agregado con éxito")
            .setMessage("¿Desea agregar otro estudiante?")
            .setPositiveButton("Si") { dialog, which ->
                clearInputs()
            }
            .setNegativeButton("No") { dialog, which ->
                clearInputs()
            }
            .show()
    }

    private fun clearInputs() {
        with(binding){
            tilNombre.editText?.text?.clear()
            tilCuenta.editText?.text?.clear()
            tilEdad.editText?.text?.clear()
        }
    }

    private fun getCheckBox(): List<Materia> {
        val subSelected = arrayListOf<Materia>()
        with(binding) {
            if (checkbox1.isChecked)
                subSelected.add(Materia(id = subjects[0].id, nombre = "${checkbox1.text}", creditos = subjects[0].creditos))
            if (checkbox2.isChecked)
                subSelected.add(Materia(id = subjects[1].id, nombre = "${checkbox2.text}", creditos = subjects[1].creditos))
            if (checkbox3.isChecked)
                subSelected.add(Materia(id = subjects[2].id, nombre = "${checkbox3.text}", creditos = subjects[2].creditos))
            if (checkbox4.isChecked)
                subSelected.add(Materia(id = subjects[3].id, nombre = "${checkbox4.text}", creditos = subjects[3].creditos))
            if (checkbox5.isChecked)
                subSelected.add(Materia(id = subjects[4].id, nombre = "${checkbox5.text}", creditos = subjects[4].creditos))
            if (checkbox6.isChecked)
                subSelected.add(Materia(id = subjects[5].id, nombre = "${checkbox6.text}", creditos = subjects[5].creditos))
            if (checkbox7.isChecked)
                subSelected.add(Materia(id = subjects[6].id, nombre = "${checkbox7.text}", creditos = subjects[6].creditos))
            if (checkbox8.isChecked)
                subSelected.add(Materia(id = subjects[7].id, nombre = "${checkbox8.text}", creditos = subjects[7].creditos))
            if (checkbox9.isChecked)
                subSelected.add(Materia(id = subjects[8].id, nombre = "${checkbox9.text}", creditos = subjects[8].creditos))
            if (checkbox10.isChecked)
                subSelected.add(Materia(id = subjects[9].id, nombre = "${checkbox10.text}", creditos = subjects[9].creditos))
        }
        return subSelected
    }

    private fun emptyField(editText: EditText, message: Int) {
        editText.error = getString(message)
        Toast.makeText(
            requireContext(),
            getText(R.string.txt_no_fields),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        val subjects = listOf<Materia>(
            Materia(id = 1, nombre = "Logica", creditos = 10),
            Materia(id = 2, nombre = "Fundamentos de programacion", creditos = 10),
            Materia(id = 3, nombre = "Calculo 1", creditos = 8),
            Materia(id = 4, nombre = "Calculo 2", creditos = 8),
            Materia(id = 5, nombre = "Calculo 3", creditos = 8),
            Materia(id = 6, nombre = "Fisica", creditos = 6),
            Materia(id = 7, nombre = "Quimica", creditos = 6),
            Materia(id = 8, nombre = "Estadistica", creditos = 6),
            Materia(id = 9, nombre = "Probabilidad", creditos = 6),
            Materia(id = 10, nombre = "Administración", creditos = 8)
        )
    }

}