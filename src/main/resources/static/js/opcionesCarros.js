document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-agregar').forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Evita el envío inmediato del formulario

            const form = this.closest('form');
            const formData = new FormData(form);
            const redirectUrl = this.getAttribute('data-redirect');

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
                            text: "Guardado",
                            icon: "success",
                            draggable: true,
                            confirmButtonColor: "#ffc60a"
                        }).then(() => {
                            // Redirigir a la URL especificada
                            window.location.href = redirectUrl;
                        });
                    } else {
                        // Mostrar alerta de error
                        Swal.fire({
                            title: "Error",
                            text: "No se pudo guardar los datos.",
                            icon: "error",
                            draggable: true,
                            confirmButtonColor: "#ffc60a"
                        });
                    }
                })
                .catch(error => {
                    // Mostrar alerta de error en caso de fallo de red
                    Swal.fire({
                        title: "Error",
                        text: "Ocurrió un error al intentar guardar los datos.",
                        icon: "error",
                        draggable: true,
                        confirmButtonColor: "#ffc60a"
                    });
                });
        });
    });
});