// Funções de controle do modal de cancelamento (já estavam aqui e permanecem fora do DOMContentLoaded)
function openCancelModal(id) {
    const cancelModal = document.getElementById('cancelModal');
    const consultaIdInput = document.getElementById('consultaId');

    if (typeof id === 'undefined' || id === null || id === '') {
        console.error("Tentativa de abrir modal de cancelamento com ID inválido:", id);
        alert("Não foi possível cancelar a consulta. ID inválido.");
        return;
    }
    if (consultaIdInput) { // Verifica se o elemento existe
        consultaIdInput.value = id;
    }
    if (cancelModal) { // Verifica se o elemento existe
        cancelModal.style.display = 'flex';
    }
}

// **MUDANÇA AQUI:** closeCancelModal agora pega suas próprias referências
function closeCancelModal() {
    const cancelModal = document.getElementById('cancelModal');
    const motivoInput = document.getElementById('motivo'); // Pega o motivoInput aqui

    if (motivoInput) { // Limpa o campo de motivo se ele existir
        motivoInput.value = '';
    }
    if (cancelModal) { // Esconde o modal se ele existir
        cancelModal.style.display = 'none';
    }
}


// Funções auxiliares (permanecem fora do DOMContentLoaded)
function setupMenu() {
    const navList = document.querySelector('.nav-list');
    const existingCloseBtn = document.querySelector('.close-menu');
    if (existingCloseBtn) {
        existingCloseBtn.remove();
    }

    const overlay = document.createElement('div');
    overlay.className = 'menu-overlay';
    document.body.appendChild(overlay);

    function toggleMenu() {
        navList.classList.toggle('active');
        overlay.style.opacity = navList.classList.contains('active') ? '1' : '0';
        overlay.style.visibility = navList.classList.contains('active') ? 'visible' : 'hidden';

        const menuToggle = document.getElementById('menuToggle');
        if (menuToggle) {
            if (navList.classList.contains('active')) {
                menuToggle.innerHTML = '<i class="fas fa-times"></i>';
                menuToggle.style.color = '#e74c3c';
            } else {
                menuToggle.innerHTML = '<i class="fas fa-bars"></i>';
                menuToggle.style.color = '#3498db';
            }
        }
    }

    const menuToggle = document.getElementById('menuToggle');
    if (menuToggle) {
        menuToggle.addEventListener('click', toggleMenu);
    }
    overlay.addEventListener('click', toggleMenu);

    const navLinks = document.querySelectorAll('.nav-list a');
    navLinks.forEach(link => {
        link.addEventListener('click', function () {
            if (window.innerWidth <= 768) {
                toggleMenu();
            }
        });
    });
}

async function carregarConsultas() {
    try {
        const response = await fetch('/agendar/api/consultas/');
        if (!response.ok) throw new Error('Erro ao carregar consultas');
        const data = await response.json();
        return data.consultas;
    } catch (error) {
        console.error('Erro:', error);
        return [];
    }
}

function parseConsultaDate(consulta) {
    const [datePart, timePart] = consulta.data.split(' - ');
    const [day, month, year] = datePart.split('/');
    const [hours, minutes] = timePart.split(':');
    return new Date(year, month - 1, day, hours, minutes);
}

function ordenarConsultas(consultas) {
    const agendadas = [], concluidas = [], canceladas = [];
    consultas.forEach(consulta => {
        if (consulta.status === 'pending') agendadas.push(consulta);
        else if (consulta.status === 'completed') concluidas.push(consulta);
        else if (consulta.status === 'cancelled') canceladas.push(consulta);
    });
    agendadas.sort((a, b) => parseConsultaDate(a) - parseConsultaDate(b));
    concluidas.sort((a, b) => parseConsultaDate(b) - parseConsultaDate(a));
    canceladas.sort((a, b) => parseConsultaDate(b) - parseConsultaDate(a));
    return [...agendadas, ...concluidas, ...canceladas];
}

async function cancelarConsulta(id, motivo) {
    try {
        const response = await fetch('/agendar/api/cancelar_consulta/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRFToken': getCookie('csrftoken'),
            },
            body: JSON.stringify({ id_consulta: id, motivo: motivo })
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error("Erro detalhado do backend (cancelarConsulta):", errorData);
            throw new Error(errorData.erro || 'Erro desconhecido ao cancelar consulta');
        }

        const data = await response.json();
        if (data.success) {
            alert('Consulta cancelada com sucesso!');
            await renderConsultas();
        } else {
            throw new Error(data.erro || 'Erro ao cancelar consulta');
        }
    } catch (error) {
        console.error('Erro na função cancelarConsulta:', error);
        alert(`Erro ao cancelar consulta: ${error.message}`);
    } finally {
        closeCancelModal(); // Esta chamada agora funcionará corretamente
    }
}

function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

// renderConsultas (permanece no escopo global)
async function renderConsultas() {
    const consultasContainer = document.getElementById('consultasContainer');
    if (!consultasContainer) {
        console.error("Elemento 'consultasContainer' não encontrado no DOM.");
        return;
    }

    consultasContainer.innerHTML = '<div class="loading">Carregando consultas...</div>';
    try {
        const consultas = await carregarConsultas();
        if (consultas.length === 0) {
            consultasContainer.innerHTML = '<div class="no-consultas">Nenhuma consulta encontrada.</div>';
            return;
        }

        const consultasOrdenadas = ordenarConsultas(consultas);
        consultasContainer.innerHTML = '';

        const grupos = [
            { status: 'pending', titulo: 'Consultas Agendadas' },
            { status: 'completed', titulo: 'Consultas Concluídas' },
            { status: 'cancelled', titulo: 'Consultas Canceladas' }
        ];

        grupos.forEach(grupo => {
            const consultasDoGrupo = consultasOrdenadas.filter(c => c.status === grupo.status);
            if (consultasDoGrupo.length > 0) {
                const header = document.createElement('h2');
                header.className = 'section-header';
                if (grupo.status === 'completed') header.classList.add('section-header-completed');
                if (grupo.status === 'cancelled') header.classList.add('section-header-cancelled');
                header.textContent = grupo.titulo;
                consultasContainer.appendChild(header);

                consultasDoGrupo.forEach(consulta => {
                    const card = document.createElement('div');
                    card.className = 'consulta-card consulta-lista';

                    let statusClass, statusText;
                    switch (consulta.status) {
                        case 'completed':
                            statusClass = 'status-completed';
                            statusText = 'Realizada';
                            break;
                        case 'pending':
                            statusClass = 'status-pending';
                            statusText = 'Agendada';
                            break;
                        case 'cancelled':
                            statusClass = 'status-cancelled';
                            statusText = 'Cancelada';
                            break;
                        default:
                            statusClass = 'status-pending';
                            statusText = 'Agendada';
                    }

                    card.innerHTML = `
                        <div class="consulta-header">
                            <h3 class="consulta-title">${consulta.tipo}</h3>
                            <span class="consulta-status ${statusClass}">${statusText}</span>
                        </div>
                        <div class="consulta-details">
                            <div class="detail-row">
                                <span class="detail-label">Médico:</span>
                                <span class="detail-value">${consulta.medico}</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Data:</span>
                                <span class="detail-value">${consulta.data}</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Descrição:</span>
                                <span class="detail-value">${consulta.descricao}</span>
                            </div>
                        </div>
                        <div class="consulta-actions">
                            <button class="btn btn-download" ${consulta.status !== 'completed' ? 'disabled' : ''}>
                                <i class="fas fa-download"></i> Baixar Documentos
                            </button>
                            <button class="btn btn-cancel" ${consulta.status !== 'pending' ? 'disabled' : ''} data-id="${consulta.id || ''}">
                                <i class="fas fa-times"></i> Cancelar
                            </button>
                        </div>
                    `;

                    consultasContainer.appendChild(card);
                });
            }
        });

        // Re-adicionar listeners após o DOM ser atualizado
        document.querySelectorAll('.btn-cancel').forEach(btn => {
            const idDoBotao = btn.dataset.id;
            if (!btn.disabled && idDoBotao && idDoBotao !== 'undefined') {
                btn.addEventListener('click', () => openCancelModal(idDoBotao));
            }
        });

    } catch (error) {
        console.error('Erro ao renderizar consultas:', error);
        if (consultasContainer) {
            consultasContainer.innerHTML = '<div class="error">Erro ao carregar consultas. Tente recarregar a página.</div>';
        }
    }
}


// DOMContentLoaded: apenas inicializa listeners
document.addEventListener('DOMContentLoaded', () => {
    // Variáveis para elementos do DOM - obtidas UMA VEZ aqui para os listeners
    const cancelForm = document.getElementById('cancelForm');
    const motivoInput = document.getElementById('motivo'); // Mantido aqui para o listener do form
    const consultaIdInput = document.getElementById('consultaId'); // Mantido aqui para o listener do form
    const closeModalBtn = document.getElementById('closeModal');
    const cancelCancelBtn = document.getElementById('cancelCancel');
    const cancelModal = document.getElementById('cancelModal');


    // Event Listeners para o Formulário do Modal
    if (cancelForm) {
        cancelForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const motivo = motivoInput ? motivoInput.value.trim() : ''; // Acesso seguro
            const idConsultaParaEnvio = consultaIdInput ? consultaIdInput.value : ''; // Acesso seguro

            if (motivo) {
                await cancelarConsulta(idConsultaParaEnvio, motivo);
            } else {
                alert('Por favor, informe o motivo do cancelamento.');
            }
        });
    }

    // Event Listeners para fechar o Modal (usando a função global closeCancelModal)
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', closeCancelModal);
    }
    if (cancelCancelBtn) {
        cancelCancelBtn.addEventListener('click', closeCancelModal);
    }

    if (cancelModal) {
        cancelModal.addEventListener('click', (e) => {
            if (e.target === cancelModal) {
                closeCancelModal();
            }
        });
    }

    // Inicialização
    setupMenu();
    renderConsultas(); // Chamada inicial para carregar e exibir as consultas
});