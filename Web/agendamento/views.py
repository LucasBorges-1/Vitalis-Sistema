from datetime import datetime
from django.utils import timezone
from venv import logger
from django.forms import ValidationError
from django.shortcuts import render, redirect
from django.http import JsonResponse
from accounts.models import Pessoa, Medico, Usuario, Horario, Consulta
from django.views.decorators.http import require_GET
from django.views.decorators.csrf import csrf_exempt
import json
from django.contrib.auth import get_user_model

#agendamento
def agendar_consulta(request):
    if 'usuario_id' not in request.session:
        return redirect('accounts:login')
    return render(request, 'agendamento/agendamento.html')

#agendamento
def get_medicos(request):
    # Obtém todos os médicos com os campos necessários
    medicos = Medico.objects.select_related('id_pessoa').all()
    
    # Formata os dados para JSON
    medicos_data = [
        {
            'id': medico.id_pessoa.id_pessoa,
            'nome': medico.id_pessoa.nome,
            'sexo': medico.id_pessoa.sexo,
            'especialidade': medico.tipo_medico,
            'crm': medico.crm
        }
        for medico in medicos
    ]
    
    return JsonResponse({'medicos': medicos_data})

#agendamento
def get_especialidades(request):
    # Obtém todas as especialidades distintas
    especialidades = Medico.objects.values_list('tipo_medico', flat=True).distinct()
    
    # Remove valores vazios e None, e converte para lista
    especialidades_lista = [esp for esp in especialidades if esp]
    
    return JsonResponse({'especialidades': especialidades_lista})


#agendamento
@csrf_exempt
@require_GET
def get_horarios_medico(request):
    try:
        medico_id = request.GET.get('medico_id')
        data_str = request.GET.get('data')
        
        if not medico_id or not data_str:
            return JsonResponse({'error': 'Parâmetros faltando'}, status=400)
        
        data = datetime.strptime(data_str, '%Y-%m-%d').date()
        
        horario = Horario.objects.filter(
            id_medico=medico_id,
            data=data
        ).first()
        
        if horario:
            # Verifica se médico não trabalha
            if (horario.iniciomanha is None and horario.fimmanha is None and
                horario.iniciotarde is None and horario.fimtarde is None):
                return JsonResponse({'medico_nao_trabalha': True})
            
            periodos = []
            
            # Período da manhã
            if horario.iniciomanha and horario.fimmanha:
                periodos.append({
                    'inicio': horario.iniciomanha.strftime('%H:%M'),
                    'fim': horario.fimmanha.strftime('%H:%M'),
                    'tipo': 'manha'
                })
            
            # Período da tarde
            if horario.iniciotarde and horario.fimtarde:
                periodos.append({
                    'inicio': horario.iniciotarde.strftime('%H:%M'),
                    'fim': horario.fimtarde.strftime('%H:%M'),
                    'tipo': 'tarde'
                })
            
            return JsonResponse({'horarios': periodos})
        
        return JsonResponse({'horarios': []})
    
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)
    
#agendamento
@csrf_exempt
def criar_agendamento(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'Método não permitido'}, status=405)

    try:
        # Verifica sessão manualmente
        if 'usuario_id' not in request.session:
            return JsonResponse({'error': 'Usuário não autenticado'}, status=401)

        usuario_id = request.session['usuario_id']

        # Verificando se existe usuario
        if not Pessoa.objects.get(id_pessoa=usuario_id):
            return JsonResponse({'error': 'Usuário inválido'}, status=400)
        
        # Parse dos dados
        dados = json.loads(request.body)
        
        # Validação básica
        campos_obrigatorios = ['medico_id', 'data', 'horario', 'especialidade']
        for campo in campos_obrigatorios:
            if campo not in dados:
                raise ValidationError(f'Campo obrigatório faltando: {campo}')

        # Validações de formato
        try:
            # Separa data e horário
            data_agendamento = datetime.strptime(dados['data'], '%Y-%m-%d').date()
            horario_agendamento = datetime.strptime(dados['horario'], '%H:%M').time()
            
            # Combina para validação de data futura
            data_hora_completa = datetime.combine(data_agendamento, horario_agendamento)
        except ValueError:
            raise ValidationError('Formato de data/horário inválido')

        # Validações de negócio
        if data_hora_completa < datetime.now():
            raise ValidationError('Não é possível agendar para datas/horários passados')

        medico = Medico.objects.filter(id_pessoa=dados['medico_id']).first()
        if not medico:
            raise ValidationError('Médico não encontrado')

        if not medico.tipo_medico.lower() == dados['especialidade'].lower():
            raise ValidationError('Médico não pertence a esta especialidade')

        # Verifica conflito de horário (agora verifica data E horário separadamente)
        if Consulta.objects.filter(
            id_medico=dados['medico_id'],
            data_consulta=data_agendamento,
            horario=horario_agendamento,
            estado='AGENDADA'
        ).exists():
            raise ValidationError('Horário já ocupado')

        # Cria a consulta
        consulta = Consulta.objects.create(
            id_usuario_id=usuario_id,
            id_medico=medico,
            data_consulta=data_agendamento,  # Apenas a data
            horario=horario_agendamento,     # Horário separado
            type=dados['especialidade'],
            estado='AGENDADA'
        )

        return JsonResponse({
            'status': 'success',
            'consulta_id': consulta.id_consulta,
            'data': data_agendamento.strftime('%d/%m/%Y'),
            'horario': horario_agendamento.strftime('%H:%M'),
            'medico': medico.id_pessoa.nome,
            'especialidade': dados['especialidade']
        })

    except json.JSONDecodeError:
        return JsonResponse({'error': 'JSON inválido'}, status=400)
    except ValidationError as e:
        return JsonResponse({'error': str(e)}, status=400)
    except Exception as e:
        return JsonResponse({'error': f'Erro interno: {str(e)}'}, status=500)

#agendamento
def AgendarConsultaView(request):
    # Implementação básica - substitua por lógica real depois
    if request.method == 'POST':
        return JsonResponse({'status': 'success', 'message': 'Agendamento realizado com sucesso!'})
    return JsonResponse({'status': 'error'}, status=400)

#historico
def historico_view(request):
    if request.method == 'GET':
        
        usuario_id = request.session.get('usuario_id') 

        if not usuario_id:
            return redirect('accounts:login')

        context = {}
        
        if usuario_id:
            try:
                # Primeiro verifica se a Pessoa existe
                pessoa = Pessoa.objects.get(id_pessoa=usuario_id)
                
                # Cria a estrutura básica do usuário com os dados da Pessoa
                usuario_data = {
                    'nome': pessoa.nome,
                    'email': pessoa.email,
                    'cpf': pessoa.cpf,
                    'data_nascimento': pessoa.data_nascimento.strftime('%d/%m/%Y') if pessoa.data_nascimento else None
                }
                
                # Depois tenta obter o Usuario relacionado para complementar os dados
                try:
                    usuario = Usuario.objects.get(id_pessoa=usuario_id)
                    usuario_data.update({
                        'endereco': usuario.endereco,
                        'data_cadastro': usuario.data_cadastro.strftime('%d/%m/%Y') if usuario.data_cadastro else None
                    })
                except Usuario.DoesNotExist:
                    # Se não encontrar o Usuario, usa valores padrão
                    usuario_data.update({
                        'endereco': 'Não informado',
                        'data_cadastro': None
                    })
                    # Remove a sessão pois o usuário está incompleto
                    del request.session['usuario_id']
                    
                context['usuario'] = usuario_data
                    
            except Pessoa.DoesNotExist:
                # Se a Pessoa não existir, limpa a sessão
                del request.session['usuario_id']

        return render(request, 'agendamento/historico.html', context)
    
#historico
def consultas_api(request):
    usuario_id = request.session.get('usuario_id')

    if not usuario_id:
        return JsonResponse({'error': 'Usuário não autenticado'}, status=401)

    try:
        usuario = Usuario.objects.get(id_pessoa=usuario_id)
    except Usuario.DoesNotExist:
        return JsonResponse({'error': 'Usuário inválido'}, status=404)

    consultas = Consulta.objects.filter(id_usuario=usuario).select_related('id_medico__id_pessoa')

    def map_status(estado):
        return {
            'AGENDADA': 'pending',
            'CONCLUIDA': 'completed',
            'CANCELADA': 'cancelled'
        }.get(estado.upper(), 'pending')

    dados = []
    for consulta in consultas:
        dados.append({
            'id': consulta.id_consulta,
            'medico': f"Dr. {consulta.id_medico.id_pessoa.nome}",
            'tipo': consulta.type,
            'data': f"{consulta.data_consulta.strftime('%d/%m/%Y')} - {consulta.horario.strftime('%H:%M') if consulta.horario else 'sem horário'}",
            'status': map_status(consulta.estado),
            'descricao': consulta.descricao or "Nenhuma descrição",
        })

    return JsonResponse({'consultas': dados})

#historico
@csrf_exempt  # ou use um token CSRF no fetch
def cancelar_consulta_view(request):
    if request.method == 'POST':
        try:
            data = json.loads(request.body)
            consulta_id = data.get('id_consulta')
            motivo = data.get('motivo')

            consulta = Consulta.objects.get(id_consulta=consulta_id)
            consulta.estado = 'CANCELADA'  # Verifique se é 'CANCELADA' ou 'cancelled'
            consulta.motivo_cancelamento = motivo
            consulta.save()

            return JsonResponse({
                'success': True, 
                'mensagem': 'Consulta cancelada com sucesso.',
                'consulta_id': consulta_id  # Adicione para depuração
            })
        except Consulta.DoesNotExist:
            return JsonResponse({
                'success': False, 
                'erro': 'Consulta não encontrada.'
            }, status=404)
        except Exception as e:
            return JsonResponse({
                'success': False, 
                'erro': str(e)
            }, status=500)
    return JsonResponse({
        'success': False, 
        'erro': 'Método inválido'
    }, status=405)


#agendamento
@require_GET
def get_horarios_agendados(request):
    try:
        medico_id = request.GET.get('medico_id')
        data_str = request.GET.get('data')
        
        if not medico_id or not data_str:
            return JsonResponse({'error': 'Parâmetros faltando'}, status=400)
        
        data = datetime.strptime(data_str, '%Y-%m-%d').date()
        
        # Busca consultas agendadas para o médico na data especificada
        consultas = Consulta.objects.filter(
            id_medico=medico_id,
            data_consulta=data,
            estado='AGENDADA'
        )
        
        # Extrai os horários no formato HH:MM
        horarios_ocupados = [
            consulta.horario.strftime('%H:%M')
            for consulta in consultas
            if consulta.horario
        ]
        
        return JsonResponse({
            'horarios_ocupados': horarios_ocupados,
            'status': 'success'
        })
    
    except Exception as e:
        return JsonResponse({
            'error': str(e),
            'status': 'error'
        }, status=500)