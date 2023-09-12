// paciente_form.js

// Obtener referencias a los elementos HTML
const tieneObraSocialSelect = document.getElementById('tieneObraSocial');
const obraSocialContainer = document.getElementById('obraSocialContainer');
const numeroAfiliadoContainer = document.getElementById('numeroAfiliadoContainer');

// Agregar un controlador de eventos al campo "Tiene Obra Social"
tieneObraSocialSelect.addEventListener('change', function () {
    if (tieneObraSocialSelect.value === 'true') {
        // Si el valor es "true", mostrar los campos
        obraSocialContainer.style.display = 'block';
        numeroAfiliadoContainer.style.display = 'block';
    } else {
        // Si el valor es "false", ocultar los campos
        obraSocialContainer.style.display = 'none';
        numeroAfiliadoContainer.style.display = 'none';
    }
});

// Inicialmente, ocultar los campos si el valor es "false" por defecto
if (tieneObraSocialSelect.value === 'false') {
    obraSocialContainer.style.display = 'none';
    numeroAfiliadoContainer.style.display = 'none';
}

