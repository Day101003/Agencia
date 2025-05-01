document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('success') === 'true') {
        Swal.fire({
            position: "top-end",
            icon: "success",
            title: "El administrador ha sido guardado",
            showConfirmButton: false,
            timer: 1500
        });
        // Opcional: Limpiar la URL
        window.history.replaceState({}, document.title, window.location.pathname);
    }
});