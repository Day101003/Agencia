document.addEventListener('DOMContentLoaded', () => {
    const contactForm = document.getElementById('contactForm');
    const submitButton = document.getElementById('submitButton');
    const successMessageDiv = document.getElementById('submitSuccessMessage');
    const errorMessageDiv = document.getElementById('submitErrorMessage');

    if (contactForm && submitButton) {
        // Observar cambios en el mensaje de éxito
        const successObserver = new MutationObserver((mutations) => {
            mutations.forEach((mutation) => {
                if (successMessageDiv.classList.contains('d-none') === false) {
                    Swal.fire({
                        icon: 'success',
                        title: '¡Éxito!',
                        text: 'Mensaje enviado correctamente. Pronto nos pondremos en contacto contigo.',
                        confirmButtonText: 'Aceptar',
                        timer: 2000,
                        timerProgressBar: true
                    }).then(() => {
                        // Limpiar el formulario después de mostrar la alerta
                        contactForm.reset();
                        successMessageDiv.classList.add('d-none');
                    });
                }
            });
        });

        // Observar cambios en el mensaje de error
        const errorObserver = new MutationObserver((mutations) => {
            mutations.forEach((mutation) => {
                if (errorMessageDiv.classList.contains('d-none') === false) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Error al enviar el mensaje. Por favor, intenta de nuevo.',
                        confirmButtonText: 'Aceptar'
                    }).then(() => {
                        errorMessageDiv.classList.add('d-none');
                    });
                }
            });
        });

        // Configurar los observadores
        successObserver.observe(successMessageDiv, { attributes: true, attributeFilter: ['class'] });
        errorObserver.observe(errorMessageDiv, { attributes: true, attributeFilter: ['class'] });

        // Validación manual antes de enviar el formulario
        contactForm.addEventListener('submit', (event) => {
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const phone = document.getElementById('phone').value.trim();
            const message = document.getElementById('message').value.trim();

            if (!name || !email || !phone || !message) {
                event.preventDefault(); // Evitar el envío si los campos están vacíos
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Por favor, completa todos los campos requeridos.',
                    confirmButtonText: 'Aceptar'
                });
            }
        });
    }
});