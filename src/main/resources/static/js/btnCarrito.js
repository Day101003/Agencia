  // Función para obtener el token JWT de la cookie
  function getJwtToken() {
    const cookieValue = document.cookie.split('; ').find(row => row.startsWith('JWT_TOKEN='));
    return cookieValue ? cookieValue.split('=')[1] : null;
}

// Load cart data when the cart modal is shown
document.getElementById('cartModal')?.addEventListener('show.bs.modal', async () => {
    const cartContent = document.getElementById('cartContent');
    const cartTotal = document.getElementById('cartTotal');
    const payButton = document.getElementById('payButton');
    const token = getJwtToken();

    if (!token) {
        window.location.href = '/home';
        return;
    }

    try {
        const response = await fetch('/cart/view', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const order = await response.json();
            if (!order || order.detalles.length === 0) {
                cartContent.innerHTML = '<p>Tu carrito está vacío.</p>';
                cartTotal.textContent = '0';
                payButton.disabled = true;
            } else {
                let html = '<ul class="list-group">';
                order.detalles.forEach(detalle => {
                    const carro = detalle.carro;
                    html += `
                        <li class="list-group-item">
                            ${carro.marca.nombreMarca} ${carro.modelo.nombreModelo} (${carro.ano}) - 
                            Precio: $${carro.precioCarro.toFixed(2)}
                        </li>
                    `;
                });
                html += '</ul>';
                cartContent.innerHTML = html;
                cartTotal.textContent = order.precio.toFixed(2);
                payButton.disabled = false;
            }
        } else if (response.status === 401 || response.status === 403) {
            window.location.href = '/home';
        } else {
            cartContent.innerHTML = '<p>Error al cargar el carrito.</p>';
            cartTotal.textContent = '0';
            payButton.disabled = true;
        }
    } catch (error) {
        console.error('Error al cargar el carrito:', error);
        cartContent.innerHTML = '<p>Error al cargar el carrito.</p>';
        cartTotal.textContent = '0';
        payButton.disabled = true;
    }
});

// Handle "Pay" button click
document.getElementById('payButton')?.addEventListener('click', async () => {
    const token = getJwtToken();

    if (!token) {
        window.location.href = '/home';
        return;
    }

    try {
        const response = await fetch('/cart/checkout', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert('Compra finalizada exitosamente');
            const cartModal = document.getElementById('cartModal');
            const bootstrapModal = bootstrap.Modal.getInstance(cartModal);
            bootstrapModal.hide();
            setTimeout(() => {
                bootstrapModal.show();
            }, 500);
        } else if (response.status === 401 || response.status === 403) {
            window.location.href = '/home';
        } else {
            alert('Error al procesar el pago');
        }
    } catch (error) {
        console.error('Error al procesar el pago:', error);
        alert('Error al procesar el pago');
    }
});

// Existing user profile and logout code
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
                userAvatar.src = user.ruta_imagen_usuario || "/assets/img/FotoPerfil/usuario2.jpg";
                userEmail.textContent = user.correo || "Correo no disponible";

                loginButton?.classList.add('d-none');
                userProfile?.classList.remove('d-none');
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