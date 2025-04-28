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

// Load cart data when the cart modal is shown
document.getElementById('cartModal').addEventListener('show.bs.modal', async () => {
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
                cartContent.innerHTML = '<p class="text-center text-muted">Tu carrito está vacío.</p>';
                cartTotal.textContent = '0';
                payButton.disabled = true;
            } else {
                let html = '<div class="list-group">';
                order.detalles.forEach(detalle => {
                    const carro = detalle.carro;
                    // Manejo seguro de la imagen corregido
                    const imagenSrc = carro.rutaImagen
                        ? `/${carro.rutaImagen}`
                        : '/assets/img/default-car.jpg';
                    const marca = carro.marca?.nombreMarca || 'Sin Marca';
                    const modelo = carro.modelo?.nombreModelo || 'Sin Modelo';
                    const precio = carro.precioCarro ? carro.precioCarro.toFixed(2) : '0.00';
                    const ano = carro.ano || 'N/A';

                    html += `
                        <div class="list-group-item d-flex align-items-center mb-3" style="border-radius: 8px;">
                            <img src="${imagenSrc}" alt="${marca} ${modelo}" style="width: 100px; height: 100px; object-fit: cover; border-radius: 8px; margin-right: 15px;">
                            <div class="flex-grow-1">
                                <h5 class="mb-1">${marca} ${modelo}</h5>
                                <p class="mb-1 text-muted">Año: ${ano} | Precio: $${precio}</p>
                            </div>
                            <button class="btn btn-danger btn-sm btn-eliminar-carrito" data-id-carro="${carro.id_carro}">
                                <i class="fas fa-trash"></i> Eliminar
                            </button>
                        </div>
                    `;
                });
                html += '</div>';
                cartContent.innerHTML = html;
                cartTotal.textContent = order.precio ? order.precio.toFixed(2) : '0.00';
                payButton.disabled = false;
            }
        } else {
            console.error('Error en la respuesta del servidor:', response.status, response.statusText);
            cartContent.innerHTML = '<p class="text-center text-danger">Error al cargar el carrito.</p>';
            cartTotal.textContent = '0';
            payButton.disabled = true;
        }
    } catch (error) {
        console.error('Error al cargar el carrito:', error);
        cartContent.innerHTML = '<p class="text-center text-danger">Error al cargar el carrito.</p>';
        cartTotal.textContent = '0';
        payButton.disabled = true;
    }
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
                userAvatar.src = user.ruta_imagen_usuario || "/assets/img/FotoPerfil/usuario2.jpg";
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