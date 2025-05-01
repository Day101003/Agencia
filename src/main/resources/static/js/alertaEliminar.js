

    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', function() {
            Swal.fire({
                title: "¿Estás seguro?",
                text: "¡No podrás revertir esto!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#ffc60a",
                cancelButtonColor: "#616970",
                confirmButtonText: "Sí, ¡elimínalo!",
                cancelButtonText: "Cancelar"
            }).then((result) => {
                if (result.isConfirmed) {
                    // Enviar el formulario correspondiente
                    this.closest('form').submit();
                    Swal.fire({
                        title: "¡Eliminado!",
                        text: "El administrador ha sido eliminado.",
                        icon: "success"
                    });
                }
            });
        });
    });