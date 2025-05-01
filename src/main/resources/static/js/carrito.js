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

                const message = await response.text();

                if (response.ok) {
                    // Alerta de éxito con SweetAlert2
                    await Swal.fire({
                        icon: 'success',
                        title: '¡Añadido!',
                        text: 'Carro añadido al carrito exitosamente',
                        confirmButtonText: 'Aceptar',
                        timer: 2000,
                        timerProgressBar: true
                    });

                    const modal = button.closest('.modal');
                    if (modal) {
                        const bootstrapModal = bootstrap.Modal.getInstance(modal);
                        bootstrapModal.hide();
                    }
                    await cargarCarrito();
                } else if (response.status === 401 || response.status === 403) {
                    window.location.href = '/home';
                } else {
                    // Alerta de error con SweetAlert2
                    await Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'El vehículo ya se encuentra en el carrito de compras.',
                        confirmButtonText: 'Aceptar'
                    });
                }
            } catch (error) {
                console.error('Error:', error);
                await Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error al añadir el carro al carrito',
                    confirmButtonText: 'Aceptar'
                });
            }
        });
    });
});

// Función para cargar el carrito
async function cargarCarrito() {
    const cartContent = document.getElementById('cartContent');
    const cartTotal = document.getElementById('cartTotal');
    const payButton = document.getElementById('payButton');
    const token = getJwtToken();

    if (!token) {
        window.location.href = '/home';
        return;
    }

    try {
        console.log('Cargando carrito...');
        const response = await fetch('/cart/view', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const order = await response.json();
            console.log('Orden cargada:', order);

            if (!order || order.detalles.length === 0) {
                cartContent.innerHTML = '<p class="text-center text-muted">Tu carrito está vacío.</p>';
                cartTotal.textContent = '0';
                payButton.disabled = true;
            } else {
                let html = '<div class="list-group">';
                order.detalles.forEach(detalle => {
                    const carro = detalle.carro;
                    const imagenSrc = carro.rutaImagen
                        ? `/${carro.rutaImagen}`
                        : '/assets/img/default-car.jpg';
                    const marca = carro.marca?.nombreMarca || 'Sin Marca';
                    const modelo = carro.modelo?.nombreModelo || 'Sin Modelo';
                    const precio = carro.precioCarro ? carro.precioCarro.toFixed(2) : '0.00';
                    const ano = carro.ano || 'N/A';

                    html += `
                        <div class="list-group-item d-flex align-items-center mb-3" style="border-radius: 8px;">
                            <img src="${imagenSrc}" alt="${marca} ${modelo}" style="width: 100px; height: 60px; object-fit: cover; margin-right: 15px; border-radius: 5px;">
                            <div class="flex-grow-1">
                                <h5 class="mb-1">${marca} ${modelo}</h5>
                                <p class="mb-1 text-muted">Año: ${ano} | Precio: $${precio}</p>
                            </div>
                            <button class="btn btn-danger btn-sm btn-eliminar-carrito" onclick="eliminarDelCarrito(${detalle.idDetalle})">
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
}

// Función para eliminar un detalle del carrito
async function eliminarDelCarrito(detalleId) {
    // Confirmación con SweetAlert2
    const result = await Swal.fire({
        icon: 'warning',
        title: '¿Eliminar?',
        text: '¿Estás seguro de eliminar este carro de tu carrito?',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#dc3545',
        cancelButtonColor: '#6c757d'
    });

    if (!result.isConfirmed) return;

    const token = getJwtToken();
    if (!token) {
        window.location.href = '/home';
        return;
    }

    try {
        console.log('Eliminando detalle con ID:', detalleId);
        const response = await fetch(`/cart/remove?detalleId=${detalleId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const message = await response.text();
            await Swal.fire({
                icon: 'success',
                title: '¡Eliminado!',
                text: message || 'Carro eliminado del carrito.',
                confirmButtonText: 'Aceptar',
                timer: 2000,
                timerProgressBar: true
            });
            await cargarCarrito();
        } else {
            const errorText = await response.text();
            console.error('Error al eliminar:', errorText);
            await Swal.fire({
                icon: 'error',
                title: 'Error',
                text: `Error al eliminar del carrito: ${errorText}`,
                confirmButtonText: 'Aceptar'
            });
        }
    } catch (error) {
        console.error('Error al eliminar del carrito:', error);
        await Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Error crítico al eliminar del carrito.',
            confirmButtonText: 'Aceptar'
        });
    }
}

// Cargar el carrito cuando se muestra el modal
document.getElementById('cartModal').addEventListener('show.bs.modal', cargarCarrito);

// Handle "Pay" button click
document.getElementById('payButton').addEventListener('click', async () => {
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
            await Swal.fire({
                icon: 'success',
                title: '¡Compra Finalizada!',
                text: 'Compra finalizada exitosamente',
                confirmButtonText: 'Aceptar',
                timer: 2000,
                timerProgressBar: true
            });
            await cargarCarrito();
        } else if (response.status === 401 || response.status === 403) {
            window.location.href = '/home';
        } else {
            await Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Error al procesar el pago',
                confirmButtonText: 'Aceptar'
            });
        }
    } catch (error) {
        console.error('Error al procesar el pago:', error);
        await Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Error al procesar el pago',
            confirmButtonText: 'Aceptar'
        });
    }
});