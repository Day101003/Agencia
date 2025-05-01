document.addEventListener('DOMContentLoaded', () => {
    // Mostrar SweetAlert2 para mensajes de éxito o error
    const successMessage = document.querySelector('#success-message')?.textContent;
    const errorMessage = document.querySelector('#error-message')?.textContent;

    if (successMessage) {
        Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: successMessage,
            confirmButtonText: 'Aceptar',
            timer: 2000,
            timerProgressBar: true
        });
    }

    if (errorMessage) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: errorMessage,
            confirmButtonText: 'Aceptar'
        });
    }

    // Mostrar/Ocultar contraseña
    const togglePasswordButton = document.getElementById('toggle-password');
    const passwordField = document.getElementById('contrasena');
    if (togglePasswordButton && passwordField) {
        togglePasswordButton.addEventListener('click', () => {
            const icon = togglePasswordButton.querySelector('i');
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordField.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    }
});

// Mostrar SweetAlert2 para confirmar eliminación
function confirmDelete(event) {
    event.preventDefault();
    Swal.fire({
        icon: 'warning',
        title: '¿Eliminar?',
        text: '¿Estás seguro de eliminar este administrador?',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#dc3545',
        cancelButtonColor: '#6c757d'
    }).then((result) => {
        if (result.isConfirmed) {
            event.target.closest('form').submit();
        }
    });
    return false;
}