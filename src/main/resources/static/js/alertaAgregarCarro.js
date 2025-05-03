
document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const btnAgregarCarro = document.getElementById('btnAgregarCarro');

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Evita el envío inmediato del formulario

        // Mostrar la alerta de SweetAlert2
        Swal.fire({
            title: "¡Carro Agregado!",
            text: "El carro ha sido agregado exitosamente.",
            icon: "success",
            draggable: true,
            confirmButtonText: "OK"
        }).then((result) => {
            if (result.isConfirmed) {
                // Enviar el formulario después de que el usuario confirme
                form.submit();
            }
        });
    });
});