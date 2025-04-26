// carrito.js

// Función para obtener el token JWT de la cookie
function getJwtToken() {
    const cookieValue = document.cookie.split('; ').find(row => row.startsWith('JWT_TOKEN='));
    return cookieValue ? cookieValue.split('=')[1] : null;
}

// Handle "Add to Cart" button click
document.addEventListener('DOMContentLoaded', () => {
    const addToCartButtons = document.querySelectorAll('.btn-agregar-carrito');
    addToCartButtons.forEach(button => {
        button.addEventListener('click', async (e) => {
            const carroId = button.getAttribute('data-id-carro');
            const token = getJwtToken();

            if (!token) {
                window.location.href = '/home';
                return;
            }

            try {
                const response = await fetch('/cart/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': `Bearer ${token}`
                    },
                    body: `carroId=${carroId}`
                });

                if (response.ok) {
                    alert('Carro añadido al carrito exitosamente');
                    const modal = button.closest('.modal');
                    if (modal) {
                        const bootstrapModal = bootstrap.Modal.getInstance(modal);
                        bootstrapModal.hide();
                    }
                } else if (response.status === 401 || response.status === 403) {
                    window.location.href = '/home';
                } else {
                    alert('Error al añadir el carro al carrito');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Error al añadir el carro al carrito');
            }
        });
    });
});

// Existing code for user profile and logout
window.addEventListener('DOMContentLoaded', async () => {
    const cookieValue = document.cookie.split('; ').find(row => row.startsWith('JWT_TOKEN='));
    const token = cookieValue ? cookieValue.split('=')[1] : null;

    const loginButton = document.getElementById('loginButton');
    const userProfile = document.getElementById('userProfile');
    const userAvatar = document.getElementById('userAvatar');
    const userName = document.getElementById('userName');
    const userEmail = document.getElementById('userEmail');

    if (token) {
        try {
            const res = await fetch('/api/user/profile', {
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });

            if (res.ok) {
                const user = await res.json();
                userName.textContent = user.nombre || "Usuario";
                userAvatar.src = user.ruta_imagen_usuario || "/assets/img/FotoPerfil/default.png";
                userEmail.textContent = user.correo || "Correo no disponible";

                loginButton.classList.add('d-none');
                userProfile.classList.remove('d-none');
            } else {
                console.warn("⚠ Token no válido o expirado");
            }
        } catch (err) {
            console.error("❌ Error al cargar perfil:", err);
        }
    } else {
        console.warn("⚠ JWT_TOKEN no encontrado en cookies.");
    }
});

function logout() {
    document.cookie = 'JWT_TOKEN=; Max-Age=0; path=/; SameSite=Strict';
    window.location.href = '/home';
}