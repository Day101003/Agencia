

    async function agregarAFavoritos(carroId) {
        try {
            const response = await fetch(`/favoritos/agregar/${carroId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                alert('¡Carro agregado a favoritos!');
                await cargarFavoritos();
            } else {
                alert('❌ Ya se encuentra en favoritos.');
            }
        } catch (error) {
            console.error('Ya se encuentra en favoritos:', error);
            alert('❌ Hubo un error al agregar.');
        }
    }

    async function eliminarDeFavoritos(carroId) {
        if (!confirm('¿Eliminar este carro de tus favoritos?')) return;

        try {
            const response = await fetch(`/favoritos/eliminar/${carroId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                alert('Carro eliminado de favoritos.');
                await cargarFavoritos();
            } else {
                alert('❌ Error al eliminar favorito.');
            }
        } catch (error) {
            console.error('Error al eliminar favorito:', error);
            alert('❌ Error crítico al eliminar.');
        }
    }

    async function agregarAlCarritoDesdeFavoritos(carroId) {
        const token = getJwtToken();

        if (!token) {
            window.location.href = '/home';
            return;
        }

        try {
            const response = await fetch(`/cart/add?carroId=${carroId}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                alert('¡Carro agregado al carrito!');
                await cargarCarrito();  // Opcional: actualizar carrito
                await cargarFavoritos(); // Opcional: actualizar favoritos
            } else {
                alert('❌ Error al agregar al carrito.');
            }
        } catch (error) {
            console.error('Error al agregar al carrito desde favoritos:', error);
        }
    }
    async function cargarFavoritos() {
    const favoritosContent = document.getElementById('favoritosContent');
    const favoritosCount = document.getElementById('favoritosCount');

    try {
        const response = await fetch('/favoritos', { method: 'GET' });

        if (response.ok) {
            const favoritos = await response.json();
            favoritosCount.textContent = favoritos.length;

            if (favoritos.length > 0) {
                let html = '<ul class="list-group">';
                favoritos.forEach(favorito => {
                    html += `
                        <li class="list-group-item d-flex align-items-start flex-column mb-2">
                            <div class="d-flex w-100 align-items-center">
                                <!-- Imagen del carro -->
                                <img src="${favorito.imagenUrl}" alt="${favorito.marca} ${favorito.modelo}" 
                                     style="width: 100px; height: 60px; object-fit: cover; margin-right: 15px; border-radius: 5px;">
                                <div class="flex-grow-1">
                                    <strong>${favorito.marca}</strong> ${favorito.modelo} (${favorito.ano})
                                </div>
                                <div>
                                    <span class="badge bg-primary rounded-pill">$${favorito.precio.toLocaleString()}</span>
                                </div>
                            </div>
                            <div class="mt-2 d-flex justify-content-end gap-2 w-100">
                                <!-- Botón Agregar al carrito -->
                                <button class="btn btn-sm btn-success" onclick="agregarAlCarritoDesdeFavoritos(${favorito.idCarro})">
                                    <i class="fas fa-cart-plus"></i> Agregar al carrito
                                </button>
                                <!-- Botón Eliminar de favoritos -->
                                <button class="btn btn-sm btn-danger" onclick="eliminarDeFavoritos(${favorito.idCarro})">
                                    <i class="fas fa-trash"></i> Eliminar
                                </button>
                            </div>
                        </li>
                    `;
                });
                html += '</ul>';

                favoritosContent.innerHTML = html;
            } else {
                favoritosContent.innerHTML = '<p>No tienes favoritos guardados.</p>';
            }
        } else {
            favoritosContent.innerHTML = '<p>❌ Error al cargar los favoritos.</p>';
            favoritosCount.textContent = '0';
        }
    } catch (error) {
        console.error('Error al cargar favoritos:', error);
        favoritosContent.innerHTML = '<p>❌ Error al cargar los favoritos.</p>';
        favoritosCount.textContent = '0';
    }
}
    // ✅ Cargar favoritos al abrir modal
    document.getElementById('favoritosModal').addEventListener('show.bs.modal', cargarFavoritos);