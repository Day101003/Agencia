document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Evita el envío inmediato del formulario

            Swal.fire({
                title: "¿Estás seguro?",
                text: "¡No podrás revertir esto!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#c6303e",
                cancelButtonColor: "#616970",
                confirmButtonText: "Sí, eliminar",
                cancelButtonText: "Cancelar"
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = this.closest('form');
                    const url = form.action;
                    const method = form.method;

                    // Enviar la solicitud AJAX
                    fetch(url, {
                        method: method,
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                    })
                        .then(response => {
                            if (response.ok) {
                                // Mostrar alerta de éxito
                                Swal.fire({
                                    title: "¡Eliminado!",
                                    text: "El carro ha sido eliminado.",
                                    icon: "success",
                                    confirmButtonColor: "#ffc60a"
                                }).then(() => {
                                    // Redirigir a la lista de carros
                                    window.location.href = '/controller_carro/listaCar';
                                });
                            } else {
                                // Mostrar alerta de error
                                Swal.fire({
                                    title: "Error",
                                    text: "No se pudo eliminar el carro.",
                                    icon: "error",
                                    confirmButtonColor: "#ffc60a"
                                });
                            }
                        })
                        .catch(error => {
                            // Mostrar alerta de error en caso de fallo de red
                            Swal.fire({
                                title: "Error",
                                text: "Ocurrió un error al intentar eliminar el carro.",
                                icon: "error",
                                confirmButtonColor: "#ffc60a"
                            });
                        });
                }
            });
        });
    });
});



// Manejo del botón de cambiar estado
document.querySelectorAll('.change-state-btn').forEach(button => {
    button.addEventListener('click', function (event) {
        event.preventDefault(); // Evita el envío inmediato del formulario

        Swal.fire({
            title: "¿Quieres cambiar el estado?",
            text: "El estado del carro será modificado.",
            icon: "question",
            iconHtml: "؟",
            showCancelButton: true,
            showCloseButton: true,
            confirmButtonColor: "#ffc60a",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí",
            cancelButtonText: "No",
            draggable: true
        }).then((result) => {
            if (result.isConfirmed) {
                const form = this.closest('form');
                const url = form.action;
                const method = form.method;

                // Enviar la solicitud AJAX
                fetch(url, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                })
                    .then(response => {
                        if (response.ok) {
                            // Mostrar alerta de éxito
                            Swal.fire({
                                title: "¡Estado Cambiado!",
                                text: "El estado del carro ha sido actualizado.",
                                icon: "success",
                                confirmButtonColor: "#ffc60a",
                                draggable: true
                            }).then(() => {
                                // Recargar la página para reflejar el cambio
                                window.location.reload();
                            });
                        } else {
                            // Mostrar alerta de error
                            Swal.fire({
                                title: "Error",
                                text: "No se pudo cambiar el estado del carro.",
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
                            text: "Ocurrió un error al intentar cambiar el estado.",
                            icon: "error",
                            confirmButtonColor: "#ffc60a",
                            draggable: true
                        });
                    });
            }
        });
    });
});
