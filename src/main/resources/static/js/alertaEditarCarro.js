document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const btnGuardarCambios = document.getElementById('btnGuardarCambios');

    if (form && btnGuardarCambios) {
        form.addEventListener('submit', function (event) {
            event.preventDefault(); // Evita el envío inmediato del formulario

            const formData = new FormData(form);

            // Enviar la solicitud AJAX
            fetch(form.action, {
                method: form.method,
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        // Mostrar la alerta de éxito
                        Swal.fire({
                            title: "Guardado!",
                            icon: "success",
                            draggable: true,
                            confirmButtonColor: "#ffc60a"
                        }).then(() => {
                            // Redirigir a la lista de carros
                            window.location.href = '/controller_carro/listaCar';
                        });
                    } else {
                        // Mostrar alerta de error
                        Swal.fire({
                            title: "Error",
                            text: "No se pudieron guardar los cambios.",
                            icon: "error",
                            confirmButtonColor: "#ffc60a",
                            draggable: true
                        });
                    }
                })
                .catch(error => {
                    // Mostrar alerta de error en caso de fallo de red
                    Swal.fire({
                        title: "Error",
                        text: "Ocurrió un error al intentar guardar los cambios.",
                        icon: "error",
                        confirmButtonColor: "#ffc60a",
                        draggable: true
                    });
                });
        });
    }
});