package mx.mauriciogs.consumingwebservice.data.model

data class StudentsItem(
    val cuenta: String,
    val edad: String,
    val materias: List<Materia>,
    val nombre: String
)