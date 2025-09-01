document.addEventListener('DOMContentLoaded', function() {
    async function init() {
        await carregarDadosMedicos(); // Carrega os dados da API
        renderCalendar();
        setupEventListeners();
        updateUI();
    }

    let medicosPorEspecialidade = {}; // Objeto vazio que será preenchido

    async function carregarDadosMedicos() {
        try {
            // Carrega especialidades primeiro
            const responseEsp = await fetch('/agendar/api/especialidades/');
            const especialidades = await responseEsp.json();
            
            // Carrega todos os médicos
            const responseMed = await fetch('/agendar/api/medicos/');
            const data = await responseMed.json();
            
            // Transforma o JSON no formato que você precisa
            medicosPorEspecialidade = {};
            
            especialidades.especialidades.forEach(especialidade => {
                medicosPorEspecialidade[especialidade.toLowerCase()] = data.medicos
                    .filter(medico => medico.especialidade.toLowerCase() === especialidade.toLowerCase())
                    .map(medico => ({
                        id: medico.id,
                        nome: medico.nome,
                        especialidade: medico.especialidade,
                        sexo: medico.sexo
                    }));
            });
            
            console.log('Dados dos médicos carregados:', medicosPorEspecialidade);
            
        } catch (error) {
            console.error('Erro ao carregar dados:', error);
            // Fallback para os dados fictícios caso a API falhe
            medicosPorEspecialidade = {
                'cardiologia': [
                    { id: 1, nome: 'Carlos Silva', especialidade: 'Cardiologista', sexo: 'Masculino' },
                    { id: 2, nome: 'Ana Souza', especialidade: 'Cardiologista Pediátrica', sexo: 'Feminino' }
                ],
                'dermatologia': [
                    { id: 3, nome: 'Marcos Oliveira', especialidade: 'Dermatologista', sexo: 'Masculino' },
                    { id: 4, nome: 'Juliana Costa', especialidade: 'Dermatologista Estética', sexo: 'Feminino' }
                ],
                'ortopedia': [
                    { id: 5, nome: 'Roberto Santos', especialidade: 'Ortopedista', sexo: 'Masculino' }
                ],
                'pediatria': [
                    { id: 6, nome: 'Patricia Lima', especialidade: 'Pediatra', sexo: 'Feminino' },
                    { id: 7, nome: 'Fernando Rocha', especialidade: 'Neonatologista', sexo: 'Masculino' }
                ]
            };
        }
    }

    async function renderServices() {
        const response = await fetch('/agendar/api/especialidades/');
        const { especialidades } = await response.json();
        const container = document.getElementById('service-options');
        
        // Ícones por especialidade (personalize conforme necessidade)
        const icons = {
            'cardiologia': 'fa-heartbeat',
            'dermatologia': 'fa-allergies',
            'ortopedia': 'fa-bone',
            'pediatria': 'fa-baby'
        };

        container.innerHTML = especialidades.map(esp => `
            <div class="service-card" data-service="${esp.toLowerCase()}">
                <i class="fas ${icons[esp.toLowerCase()] || 'fa-user-md'}"></i>
                <h3>${esp}</h3>
            </div>
        `).join('');

        // Reativa os event listeners
        document.querySelectorAll('.service-card').forEach(card => {
            card.addEventListener('click', function() {
                // Sua lógica existente de seleção
                elements.serviceCards.forEach(c => c.classList.remove('selected'));
                this.classList.add('selected');
                state.selected.servico = this.dataset.service;
                state.currentStep = 2;
                renderDoctors();
                updateUI();
            });
        });
    }

    function formatarNomeArquivo(nome) {
        return nome
            .toLowerCase()                   // Tudo em minúsculo
            .normalize('NFD')                // Remove acentos
            .replace(/[\u0300-\u036f]/g, '') // Remove caracteres especiais
            .replace(/\s+/g, '_')            // Substitui espaços por _
            .replace(/[^\w_]/g, '');         // Remove outros caracteres especiais
    }


    // Estado da aplicação
    const state = {
        currentStep: 1,
        selected: {
            servico: null,
            medico: null, // Agora será { id, nome }
            data: null,
            horario: null
        },
        calendar: {
            month: new Date().getMonth(),
            year: new Date().getFullYear()
        }
    };

    // Horários padrão (segunda a sexta) em intervalos de 30 minutos
    const horariosPadrao = {
        seg: ['07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30'],
        ter: ['07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30'],
        qua: ['07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30'],
        qui: ['07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30'],
        sex: ['07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30'],
        sab: [],
        dom: []
    };

    // Função para obter o dia da semana
    function getDiaSemana(date) {
        const dias = ['dom', 'seg', 'ter', 'qua', 'qui', 'sex', 'sab'];
        return dias[date.getDay()];
    }

    // Elementos DOM
    const elements = {
        steps: document.querySelectorAll('.step'),
        stepIndicators: document.querySelectorAll('.step-indicator'),
        serviceCards: document.querySelectorAll('.service-card'),
        doctorOptions: document.getElementById('doctor-options'),
        currentMonth: document.getElementById('current-month'),
        calendarGrid: document.getElementById('calendar'),
        prevMonth: document.getElementById('prev-month'),
        nextMonth: document.getElementById('next-month'),
        timeSlots: document.getElementById('time-slots'),
        confirmBtn: document.getElementById('confirm-btn'),
        summary: {
            service: document.getElementById('summary-service'),
            doctor: document.getElementById('summary-doctor'),
            date: document.getElementById('summary-date'),
            time: document.getElementById('summary-time')
        }
    };

    // Inicialização
    init();

    async function init() {
        await carregarDadosMedicos();
        await renderServices();  // 
        renderCalendar();
        setupEventListeners();
        updateUI();
    }

    function setupEventListeners() {
        // Seleção de serviço
        elements.serviceCards.forEach(card => {
            card.addEventListener('click', function() {
                elements.serviceCards.forEach(c => c.classList.remove('selected'));
                this.classList.add('selected');
                state.selected.servico = this.dataset.service;
                state.currentStep = 2;
                
                // Verifica se já temos os médicos carregados
                if (Object.keys(medicosPorEspecialidade).length > 0) {
                    renderDoctors();
                } else {
                    // Se não, carrega os dados primeiro
                    carregarDadosMedicos().then(renderDoctors);
                }
                
                updateUI();
            });
        });

        // Navegação do calendário
        elements.prevMonth.addEventListener('click', function() {
            state.calendar.month--;
            if (state.calendar.month < 0) {
                state.calendar.month = 11;
                state.calendar.year--;
            }
            renderCalendar();
        });

        elements.nextMonth.addEventListener('click', function() {
            state.calendar.month++;
            if (state.calendar.month > 11) {
                state.calendar.month = 0;
                state.calendar.year++;
            }
            renderCalendar();
        });

        // Botão de confirmação
        elements.confirmBtn.addEventListener('click', async function() {
            if (validateSelection()) {
                await enviarAgendamento();
            }
        });
        
    }

    
    async function enviarAgendamento() {
        const btnConfirmar = document.getElementById('confirm-btn');
        
        try {
            // Validação final no frontend
            if (!validateSelection()) {
                throw new Error('Preencha todos os campos');
            }

            // Mostra loading
            btnConfirmar.disabled = true;
            btnConfirmar.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Agendando...';

            // Formata os dados - agora envia data e horário separados
            const payload = {
                medico_id: state.selected.medico.id,
                data: state.selected.data.toISOString().split('T')[0], // Apenas a data
                horario: state.selected.horario, // Horário separado
                especialidade: state.selected.servico
            };

            // Resto do código permanece igual...
            const response = await fetch('/agendar/api/criar-agendamento/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRFToken': getCookie('csrftoken')
                },
                credentials: 'include',
                body: JSON.stringify(payload),
            });

            const contentType = response.headers.get('content-type');
            if (!contentType || !contentType.includes('application/json')) {
                const text = await response.text();
                throw new Error(`Resposta inválida: ${text.substring(0, 100)}`);
            }

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.error || 'Erro no servidor');
            }

            // Ajuste na mensagem de sucesso para mostrar data e horário separados
            alert(`✅ Agendamento confirmado!\n\nMédico: ${data.medico}\nData: ${data.data}\nHorário: ${data.horario}\nEspecialidade: ${data.especialidade}`);
            window.location.href = "/";

            // Reseta o estado
            state.selected = {
                servico: null,
                medico: null,
                data: null,
                horario: null
            };
            updateUI();

        } catch (error) {
            console.error('Erro no agendamento:', error);
            alert(`❌ Falha no agendamento:\n${error.message}`);
        } finally {
            // Restaura o botão
            btnConfirmar.disabled = false;
            btnConfirmar.innerHTML = '<i class="fas fa-check-circle"></i> Confirmar Agendamento';
        }
    }
    
    // Função auxiliar para pegar o token CSRF
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

    function renderDoctors() {
        if (!state.selected.servico) return;

        // Pega os médicos diretamente do objeto medicosPorEspecialidade
        const medicos = medicosPorEspecialidade[state.selected.servico] || [];
        elements.doctorOptions.innerHTML = '';

        medicos.forEach(medico => {
            // Define a imagem baseada no sexo
            const imgPath = `/static/img/${medico.sexo}.jpg`;
            
            const doctorCard = document.createElement('div');
            doctorCard.className = 'doctor-card';
            doctorCard.innerHTML = `
                <img src="${imgPath}" alt="${medico.nome}" 
                    onerror="this.src='/static/img/padrao.jpg'">
                <div class="doctor-info">
                    <h3>${medico.nome}</h3>
                    <p>${medico.especialidade}</p>
                </div>
            `;
            
            doctorCard.addEventListener('click', function() {
            document.querySelectorAll('.doctor-card').forEach(c => c.classList.remove('selected'));
            this.classList.add('selected');
            state.selected.medico = {
                id: medico.id,
                nome: medico.nome
            };
            state.currentStep = 3;
            updateUI();
            });
            
            elements.doctorOptions.appendChild(doctorCard);
        });
    }

    function renderCalendar() {
        const { month, year } = state.calendar;
        const monthNames = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
        
        elements.currentMonth.textContent = `${monthNames[month]} ${year}`;
        
        const firstDay = new Date(year, month, 1);
        const lastDay = new Date(year, month + 1, 0);
        const daysInMonth = lastDay.getDate();
        
        elements.calendarGrid.innerHTML = '';
        
        // Dias vazios no início
        for (let i = 0; i < firstDay.getDay(); i++) {
            const emptyDay = document.createElement('div');
            emptyDay.className = 'calendar-day empty';
            elements.calendarGrid.appendChild(emptyDay);
        }
        
        // Dias do mês
        const today = new Date();
        const threeDaysFromToday = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3);

        for (let day = 1; day <= daysInMonth; day++) {
            const date = new Date(year, month, day);
            const dayElement = document.createElement('div');
            dayElement.className = 'calendar-day';
            dayElement.textContent = day;
            
            // Verificar se é hoje
            if (date.getDate() === today.getDate() && 
                date.getMonth() === today.getMonth() && 
                date.getFullYear() === today.getFullYear()) {
                dayElement.classList.add('today');
            }
            
            // Desabilitar dias antes de 3 dias à frente
            if (date < threeDaysFromToday) {
                dayElement.classList.add('disabled');
            } else {
                dayElement.addEventListener('click', function() {
                    document.querySelectorAll('.calendar-day').forEach(d => d.classList.remove('selected'));
                    this.classList.add('selected');
                    state.selected.data = date;
                    state.currentStep = 4;
                    renderTimeSlots();
                    updateUI();
                });
            }
            
            // Marcar dia selecionado
            if (state.selected.data && 
                state.selected.data.getDate() === day && 
                state.selected.data.getMonth() === month && 
                state.selected.data.getFullYear() === year) {
                dayElement.classList.add('selected');
            }
            
            elements.calendarGrid.appendChild(dayElement);
        }
    }

    async function renderTimeSlots() {
        if (!state.selected.data || !state.selected.medico) return;

        elements.timeSlots.innerHTML = '<div class="loading-message"><div class="loading-spinner"></div><p>Carregando horários...</p></div>';

        try {
            const diaSemana = getDiaSemana(state.selected.data);
            let horarios = [];

            // 1. Tenta obter horários específicos do médico
            const horariosMedico = await getHorariosMedico(
                state.selected.medico.id, 
                state.selected.data
            );

            console.log('Horários do médico:', horariosMedico); // Debug

            // 2. Se médico não trabalha (array vazio)
            if (Array.isArray(horariosMedico) && horariosMedico.length === 0) {
                elements.timeSlots.innerHTML = `
                    <div class="no-slots-message">
                        <p>O médico não trabalha neste dia</p>
                    </div>
                `;
                
                // Desmarca o dia selecionado
                document.querySelectorAll('.calendar-day.selected').forEach(d => d.classList.remove('selected'));
                state.selected.data = null;
                return;
            }

            // Restante da lógica permanece igual...
            if (horariosMedico && Array.isArray(horariosMedico)) {
                horarios = horariosMedico;
            } else {
                console.log('Usando horários padrão');
                const horariosPadraoDia = horariosPadrao[diaSemana] || [];
                horarios = horariosPadraoDia.map(horario => ({
                    horario: horario,
                    periodo: 'padrao',
                    disponivel: true
                }));
            }

            const horariosDisponiveis = await filtrarHorariosOcupados(
                state.selected.medico.id,
                state.selected.data,
                horarios
            );

            renderizarHorariosDisponiveis(horariosDisponiveis);

        } catch (error) {
            console.error('Erro ao carregar horários:', error);
            elements.timeSlots.innerHTML = `
                <div class="error-message">
                    <p>Erro ao carregar horários. Tente novamente.</p>
                    <button class="btn-voltar">← Voltar</button>
                </div>
            `;
        }
    }

    // Função para buscar horários do médico na API
    async function getHorariosMedico(medicoId, data) {
        const dataISO = data.toISOString().split('T')[0];
        try {
            const response = await fetch(`/agendar/api/horarios/?medico_id=${medicoId}&data=${dataISO}`);
            
            if (!response.ok) {
                console.error('Erro na resposta da API:', response.status);
                return null;
            }
            
            const result = await response.json();
            console.log('Resposta completa da API:', result); // Debug detalhado
            
            // Médico não trabalha neste dia
            if (result.medico_nao_trabalha) {
                return []; // Array vazio indica que médico não trabalha
            }
            
            // Se existirem horários cadastrados
            if (result.horarios && result.horarios.length > 0) {
                return processarIntervalos(result.horarios);
            }
            
            // Se não encontrou registro, retorna null para usar fallback
            return null;
            
        } catch (error) {
            console.error('Erro ao buscar horários:', error);
            return null;
        }
    }

    // Converte intervalos (ex: 08:00-11:30) em slots de 30 minutos
    function processarIntervalos(intervalos) {
        const slots = [];
        
        intervalos.forEach(({ inicio, fim, tipo }) => {
            let [horaInicio, minutoInicio] = inicio.split(':').map(Number);
            let [horaFim, minutoFim] = fim.split(':').map(Number);
            
            let horaAtual = horaInicio;
            let minutoAtual = minutoInicio;
            
            while (horaAtual < horaFim || (horaAtual === horaFim && minutoAtual < minutoFim)) {
                const horaStr = horaAtual.toString().padStart(2, '0');
                const minutoStr = minutoAtual.toString().padStart(2, '0');
                slots.push({
                    horario: `${horaStr}:${minutoStr}`,
                    periodo: tipo || 'padrao'
                });
                
                // Avança 30 minutos
                minutoAtual += 30;
                if (minutoAtual >= 60) {
                    minutoAtual -= 60;
                    horaAtual += 1;
                }
            }
        });
        
        return slots;
    }

    async function filtrarHorariosOcupados(medicoId, data, horarios) {
        try {
            const dataISO = data.toISOString().split('T')[0];
            const response = await fetch(`/agendar/api/horarios-agendados/?medico_id=${medicoId}&data=${dataISO}`);
            
            if (!response.ok) throw new Error('Erro ao buscar horários ocupados');
            
            const result = await response.json();
            const horariosOcupados = result.horarios_ocupados || [];
            
            return horarios.map(item => {
                // Extrai o horário corretamente
                const horarioStr = item.horario?.horario || item.horario || item;
                const estaOcupado = horariosOcupados.some(ocupado => 
                    ocupado === horarioStr || 
                    ocupado.replace(/^0/, '') === horarioStr.replace(/^0/, '')
                );
                
                return {
                    horario: horarioStr,
                    periodo: item.periodo || 'padrao',
                    disponivel: !estaOcupado
                };
            });
            
        } catch (error) {
            console.error('Erro ao filtrar horários:', error);
            return horarios.map(item => ({
                horario: item.horario?.horario || item.horario || item,
                periodo: item.periodo || 'padrao',
                disponivel: true
            }));
        }
    }


    function renderizarHorariosDisponiveis(horariosDisponiveis) {
        console.log('Dados para renderização:', horariosDisponiveis); // Debug detalhado
        elements.timeSlots.innerHTML = '';
        
        if (!horariosDisponiveis || horariosDisponiveis.length === 0) {
            elements.timeSlots.innerHTML = `
                <div class="no-slots-message">
                    <p>Não há horários disponíveis neste dia</p>
                </div>
            `;
            return;
        }
        
        // Cria container principal
        const container = document.createElement('div');
        container.className = 'horarios-container';
        
        // Cria grupo único (já que estamos usando horários padrão)
        const grupoDiv = document.createElement('div');
        grupoDiv.className = 'periodo-container';
        grupoDiv.innerHTML = '<h4>Horários Disponíveis</h4><div class="time-slots-container"></div>';
        const slotsContainer = grupoDiv.querySelector('.time-slots-container');
        
        // Renderiza cada horário
        horariosDisponiveis.forEach(item => {
            // Verifica se o item tem a estrutura correta
            const horario = item.horario?.horario || item.horario || item;
            const disponivel = item.disponivel !== false;
            
            const slotElement = document.createElement('div');
            slotElement.className = disponivel ? 'time-slot' : 'time-slot indisponivel';
            slotElement.textContent = typeof horario === 'object' ? horario.horario : horario;
            
            if (disponivel) {
                slotElement.addEventListener('click', function() {
                    document.querySelectorAll('.time-slot').forEach(s => s.classList.remove('selected'));
                    this.classList.add('selected');
                    state.selected.horario = typeof horario === 'object' ? horario.horario : horario;
                    updateUI();
                });
            } else {
                slotElement.title = 'Horário já agendado';
                slotElement.style.cursor = 'not-allowed';
            }
            
            slotsContainer.appendChild(slotElement);
        });
        
        container.appendChild(grupoDiv);
        elements.timeSlots.appendChild(container);
        
        // Adiciona evento ao botão de voltar
        document.querySelectorAll('.btn-voltar').forEach(btn => {
            btn.addEventListener('click', voltarEtapa);
        });
    }


    function updateUI() {
        // Atualizar indicadores de passo
        elements.stepIndicators.forEach((indicator, index) => {
            if (index < state.currentStep) {
                indicator.classList.add('active');
            } else {
                indicator.classList.remove('active');
            }
        });

        // Mostrar apenas o passo atual
        elements.steps.forEach(step => {
            if (parseInt(step.dataset.step) === state.currentStep) {
                step.classList.add('active');
            } else {
                step.classList.remove('active');
            }
        });

        // Atualizar resumo
        elements.summary.service.textContent = state.selected.servico || 'Não selecionado';
        elements.summary.doctor.textContent = state.selected.medico?.nome || 'Não selecionado';
        elements.summary.date.textContent = state.selected.data ? formatDate(state.selected.data) : 'Não selecionada';
        elements.summary.time.textContent = state.selected.horario || 'Não selecionado';

        // Habilitar/desabilitar botão de confirmação
        elements.confirmBtn.disabled = !validateSelection();

        document.querySelectorAll('.btn-voltar').forEach(btn => {
            btn.style.display = state.currentStep > 1 ? 'flex' : 'none';
        });
    }

    function validateSelection() {
        return state.selected.servico && 
               state.selected.medico && 
               state.selected.data && 
               state.selected.horario;
    }

    function formatDate(date) {
        if (!date) return '';
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        return date.toLocaleDateString('pt-BR', options);
    }

    // Torna a função global para poder ser chamada do HTML
    window.voltarEtapa = function() {
        if (state.currentStep > 1) {
            switch(state.currentStep) {
                case 2:
                    state.selected.servico = null;
                    document.querySelector('.service-card.selected')?.classList.remove('selected');
                    break;
                case 3:
                    state.selected.medico = null;
                    document.querySelector('.doctor-card.selected')?.classList.remove('selected');
                    break;
                case 4:
                    state.selected.data = null;
                    state.selected.horario = null;
                    document.querySelector('.calendar-day.selected')?.classList.remove('selected');
                    document.querySelector('.time-slot.selected')?.classList.remove('selected');
                    break;
            }
            
            state.currentStep--;
            updateUI();
            document.querySelector(`.step[data-step="${state.currentStep}"]`).scrollIntoView({
                behavior: 'smooth'
            });
        }
    };

    // Usando delegação de eventos (funciona mesmo se o botão for criado depois)
    document.addEventListener('click', function(e) {
        if (e.target.closest('.btn-voltar')) {
            voltarEtapa();
        }
    });
});