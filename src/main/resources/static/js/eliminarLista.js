document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent immediate form submission

            Swal.fire({
                title: "¿Estás seguro?",
                text: "¡No podrás revertir esta acción!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#c6303e",
                cancelButtonColor: "#616970",
                confirmButtonText: "Sí, eliminar",
                cancelButtonText: "Cancelar",
                draggable: true
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = this.closest('form');
                    const url = form.action;
                    const method = form.method;

                    // Send AJAX request
                    fetch(url, {
                        method: method,
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                    })
                        .then(response => {
                            if (response.ok) {
                                // Show success alert
                                Swal.fire({
                                    title: "¡Eliminado!",
                                    text: "El registro ha sido eliminado.",
                                    icon: "success",
                                    confirmButtonColor: "#ffc60a",
                                    draggable: true
                                }).then(() => {
                                    // Reload the page to reflect changes
                                    window.location.reload();
                                });
                            } else {
                                // Show error alert
                                Swal.fire({
                                    title: "Error",
                                    text: "No se pudo eliminar el registro.",
                                    icon: "error",
                                    confirmButtonColor: "#ffc60a",
                                    draggable: true
                                });
                            }
                        })
                        .catch(error => {
                            // Show error alert for network issues
                            Swal.fire({
                                title: "Error",
                                text: "Ocurrió un error al intentar eliminar el registro.",
                                icon: "error",
                                confirmButtonColor: "#ffc60a",
                                draggable: true
                            });
                        });
                }
            });
        });
    });
});