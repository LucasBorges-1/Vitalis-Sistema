from django.urls import path
from .views import agendar_consulta, get_medicos, AgendarConsultaView, historico_view, get_especialidades
from .views import get_horarios_medico, criar_agendamento, consultas_api, cancelar_consulta_view, get_horarios_agendados
app_name = 'agendamento'

urlpatterns = [
    path('', agendar_consulta, name='agendar'),
    path('confirmar/', AgendarConsultaView, name='confirmar'),
    path('historico/', historico_view , name='historico'),

    path('api/especialidades/', get_especialidades, name='api_especialidades'),
    path('api/medicos/', get_medicos, name='api_medicos'),
    path('api/horarios/', get_horarios_medico, name='api_horarios'),
    path('api/criar-agendamento/', criar_agendamento, name='api_criar_agendamento'),
    path('api/consultas/', consultas_api, name='api_consultas'),
    path('api/cancelar_consulta/', cancelar_consulta_view, name='cancelar_consulta'),
    path('api/horarios-agendados/', get_horarios_agendados, name='horarios_agendados'),
]