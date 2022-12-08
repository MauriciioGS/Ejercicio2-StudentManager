package mx.mauriciogs.consumingwebservice.view.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.mauriciogs.consumingwebservice.data.model.Students
import mx.mauriciogs.consumingwebservice.data.model.StudentsItem
import mx.mauriciogs.consumingwebservice.databinding.StudentsItemBinding

class StudentsAdapter(
    context: Context
) : RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var listOfStudents: Students = Students()

    inner class ViewHolder(view: StudentsItemBinding): RecyclerView.ViewHolder(view.root) {
        val tvNombre = view.tvName
        val tvCuenta = view.tvAccount
        val tvEdad = view.tvAge
        val tvMaterias = view.tvMaterias
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StudentsItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudiante = listOfStudents[position]
        holder.tvCuenta.text = estudiante.cuenta
        holder.tvNombre.text = estudiante.nombre
        holder.tvEdad.text = estudiante.edad + " años"
        holder.tvMaterias.text = estudiante.materias.count().toString() + " materias"
    }

    override fun getItemCount(): Int = listOfStudents.size

    fun studentsList(list: Students){
        Log.d("TEST", "$list")
        listOfStudents = list
        notifyItemRangeChanged(listOfStudents.indexOf(listOfStudents.first()), itemCount)
    }

}