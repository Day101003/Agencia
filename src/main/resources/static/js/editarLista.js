document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent immediate form submission

            const form = this.closest('form');
            const formData = new FormData(form);
            const redirectUrl = this.getAttribute('data-redirect');

            // Send AJAX request
            fetch(form.action, {
                method: form.method,
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        // Show success alert
                        Swal.fire({
                            title: "Guardado!",
                            text: "Guardado",
                            icon: "success",
                            confirmButtonColor: "#ffc60a",
                            draggable: true
                        }).then(() => {
                            // Redirect to the specified URL
                            window.location.href = redirectUrl;
                        });
                    } else {
                        // Show error alert
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
                    // Show error alert for network issues
                    Swal.fire({
                        title: "Error",
                        text: "Ocurrió un error al intentar guardar los cambios.",
                        icon: "error",
                        confirmButtonColor: "#ffc60a",
                        draggable: true
                    });
                });
        });
    });
});