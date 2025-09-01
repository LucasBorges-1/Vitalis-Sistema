from django.shortcuts import render, redirect, get_object_or_404
from datetime import datetime
from accounts.models import Pessoa, Usuario, EmailVerificationToken
from django.utils import timezone
import bcrypt
import re
from validate_docbr import CPF
from django.db import transaction
import uuid
import logging
from django.core.mail import send_mail # Importe esta biblioteca
from django.conf import settings # Importe esta para acessar as configurações de e-mail do settings.py
from django.template.loader import render_to_string # Para renderizar o template HTML do e-mail
from django.utils.html import strip_tags # Para remover tags HTML para a versão texto puro
from django.core.mail import EmailMultiAlternatives # Para enviar e-mails com múltiplas partes (texto e HTML)

logger = logging.getLogger(__name__)

# accounts/views.py

# ... (suas importações existentes, incluindo 'Pessoa', 'Usuario', 'bcrypt', 'validar_email') ...
# Certifique-se de importar o logger também:
import logging
logger = logging.getLogger(__name__)


def login(request):
    if request.method == 'GET':
        status = request.GET.get('status')
        return render(request, 'login.html', {'status': status})
    
    elif request.method == 'POST':
        # --- ATENÇÃO AQUI: Ajuste para 'email' se for o nome do campo no seu HTML ---
        email = request.POST.get('email') # MUDADO DE 'gmail' PARA 'email'
        senha = request.POST.get('senha')

        # 1. Validação inicial de campos vazios ou formato inválido
        # É bom ter uma mensagem genérica para não revelar qual campo está vazio.
        if not email or not senha:
            # Use status=5 para campos vazios, por exemplo, ou use o status=1 para email inválido
            return redirect('/accounts/login/?status=1') # Mensagem: "Email ou senha não fornecidos." ou "Email inválido."
        
        # Validação do formato do email
        if not validar_email(email):
            return redirect('/accounts/login/?status=1') # Mensagem: "Email inválido."

        # Validação de senha vazia ou muito curta (mantenha a consistência se já validou no cadastro)
        # É mais seguro tratar "email ou senha inválidos" como uma única mensagem para o usuário.
        # No entanto, se você quer especificamente a senha curta no login:
        if len(senha.strip()) <= 4: # Se você tem essa regra, mantenha. Caso contrário, remova ou integre com status=4.
            return redirect('/accounts/login/?status=2') # Mensagem: "Senha muito curta." (se você quer essa granularidade)

        try:
            # 2. Verifica se a Pessoa existe
            pessoa = Pessoa.objects.get(email=email)

            # 3. VERIFICAÇÃO DE CONTA VERIFICADA (Passo crucial!)
            if not pessoa.is_verified:
                logger.warning(f"Tentativa de login de conta não verificada: {email}")
                # Redireciona com um status específico para conta não verificada
                return redirect('/accounts/login/?status=not_verified')

            # 4. Verifica a senha usando bcrypt
            if bcrypt.checkpw(senha.encode('utf-8'), pessoa.senha.encode('utf-8')):
                # Se a senha estiver correta e a conta verificada:
                request.session['usuario_id'] = pessoa.id_pessoa # Ajuste para 'pessoa.id' se seu ID for apenas 'id' no modelo Pessoa
                logger.info(f"Usuário logado com sucesso: {email}")
                return redirect('home:main') # Redireciona para a home ou dashboard

            else:
                # Senha incorreta (mesmo que o email exista)
                logger.warning(f"Tentativa de login falhou para {email}: senha incorreta.")
                # Mensagem genérica para credenciais inválidas para segurança
                return redirect('/accounts/login/?status=4') # Mensagem: "Email ou senha inválidos."

        except Pessoa.DoesNotExist:
            # Email não encontrado no banco de dados
            logger.warning(f"Tentativa de login falhou: email '{email}' não encontrado.")
            # Mensagem genérica para credenciais inválidas para segurança
            return redirect('/accounts/login/?status=4') # Mensagem: "Email ou senha inválidos."

        except Exception as e:
            # Erro inesperado no processo de login
            logger.error(f"Erro inesperado no login para {email}: {str(e)}", exc_info=True)
            return redirect('/accounts/login/?status=99') # Mensagem: "Ocorreu um erro inesperado."
        

def registrar(request):
    if request.method == 'GET':
        status = request.GET.get('status')
        # Adicione 'email_sent_flag' ao contexto para o GET inicial também
        # (será False a menos que venha de um POST bem-sucedido)
        return render(request, 'registrar.html', {'status': status, 'email_sent_flag': False})
    
    elif request.method == 'POST':
        email = request.POST.get('email')
        data_nascimento_str = request.POST.get('data_nascimento')
        nome = request.POST.get('nome')
        senha = request.POST.get('senha')
        cpf = request.POST.get('cpf')
        endereco = request.POST.get('endereco')
        sexo = request.POST.get('sexo') 

        # --- Suas Validações Existentes ---
        if not validar_email(email):
            return render(request, 'registrar.html', {'status': '1', 'email_sent_flag': False})
        
        if Pessoa.objects.filter(email=email).exists():
            return render(request, 'registrar.html', {'status': '2', 'email_sent_flag': False})

        if bool(re.search(r'\d', nome)):
            return render(request, 'registrar.html', {'status': '3', 'email_sent_flag': False})

        if nome is None or len(nome.strip()) <= 3:
            return render(request, 'registrar.html', {'status': '4', 'email_sent_flag': False})
        
        cpf_limpo = "".join(filter(str.isdigit, cpf))

        if not CPF().validate(cpf_limpo):
            return render(request, 'registrar.html', {'status': '5', 'email_sent_flag': False})

        if Pessoa.objects.filter(cpf=cpf).exists():
            return render(request, 'registrar.html', {'status': '6', 'email_sent_flag': False})

        if senha is None or len(senha.strip()) <= 4:
            return render(request, 'registrar.html', {'status': '7', 'email_sent_flag': False})
        
        if not data_nascimento_str:
            return render(request, 'registrar.html', {'status': '8', 'email_sent_flag': False})

        # --- Validação para 'SEXO' ---
        if not sexo:
            return render(request, 'registrar.html', {'status': '10', 'email_sent_flag': False})
        
        sexo = sexo.upper() 
        if sexo not in ['M', 'F', 'O']:
            return render(request, 'registrar.html', {'status': '10', 'email_sent_flag': False})
        # --- FIM DA VALIDAÇÃO PARA 'SEXO' ---

        try:
            senha_hashed = bcrypt.hashpw(senha.encode('utf-8'), bcrypt.gensalt())
            senha_hashed_str = senha_hashed.decode('utf-8')
            
            cpf_formatado = formatandoCPF(cpf)
            cpf = cpf_formatado

            data_nascimento = datetime.strptime(data_nascimento_str, '%Y-%m-%d').date()

            with transaction.atomic():
                pessoa = Pessoa.objects.create(
                    email=email,
                    data_nascimento=data_nascimento,
                    nome=nome,
                    senha=senha_hashed_str,
                    cpf=cpf,
                    type='USUARIO',
                    sexo=sexo,
                    is_verified=False 
                )

                usuario = Usuario.objects.create(
                    id_pessoa=pessoa,
                    endereco=endereco
                )

                verification_token = uuid.uuid4() 

                EmailVerificationToken.objects.create(
                    user=pessoa,
                    token=verification_token
                )

                subject = 'Confirme sua conta na SaudeClinica'
                verification_link = request.build_absolute_uri(
                    f'/accounts/verificar/?token={verification_token}'
                )
                
                email_context = {
                    'nome': nome,
                    'verification_link': verification_link,
                }

                html_message = render_to_string('emails/email_verificacao.html', email_context)
                plain_message = strip_tags(html_message)
                
                from_email = settings.DEFAULT_FROM_EMAIL
                recipient_list = [email]

                msg = EmailMultiAlternatives(subject, plain_message, from_email, recipient_list)
                msg.attach_alternative(html_message, "text/html")
                
                msg.send(fail_silently=False)

            # --- MUDANÇA AQUI: Renderiza o template com a flag email_sent_flag = True ---
            request.session['usuario_id'] = None # Garante que não há ID de sessão de usuário logado
            return render(request, 'registrar.html', {'email_sent_flag': True}) # NOVO: Envia apenas a flag

        except Exception as e:
            logger.error(f"Erro no registro, criação do token ou envio de e-mail: {str(e)}", exc_info=True)
            print(f"ERRO DETALHADO: {str(e)}")
            return render(request, 'registrar.html', {'status': '99', 'email_sent_flag': False}) # NOVO: Retorna a flag False


def verificar_email(request):
    token_str = request.GET.get('token')

    if not token_str:
        return render(request, 'verificacao_status.html', {'status_code': 'token_missing', 'message': 'Token de verificação não fornecido.'})

    try:
        # Tenta converter a string do token para um UUID
        token_uuid = uuid.UUID(token_str)
    except ValueError:
        # Se a string não for um UUID válido
        return render(request, 'verificacao_status.html', {'status_code': 'invalid_format', 'message': 'Formato de token inválido.'})

    try:
        # Tenta encontrar o token no banco de dados
        verification_token_obj = get_object_or_404(EmailVerificationToken, token=token_uuid)

        with transaction.atomic():
            pessoa = verification_token_obj.user
            
            if pessoa.is_verified:
                # O usuário já está verificado, redireciona para login ou uma página de sucesso
                return render(request, 'verificacao_status.html', {'status_code': 'already_verified', 'message': 'Sua conta já foi verificada. Você pode fazer login agora.'})

            # Marca o usuário como verificado
            pessoa.is_verified = True
            pessoa.save()

            # Deleta o token de verificação após o uso (opcional, mas boa prática de segurança)
            verification_token_obj.delete()

        # Renderiza uma página de sucesso
        return render(request, 'verificacao_status.html', {'status_code': 'success', 'message': 'Sua conta foi verificada com sucesso! Você pode fazer login agora.'})

    except EmailVerificationToken.DoesNotExist:
        # O token não foi encontrado no banco de dados
        return render(request, 'verificacao_status.html', {'status_code': 'not_found', 'message': 'Token de verificação inválido ou já utilizado.'})
    except Exception as e:
        logger.error(f"Erro na verificação de e-mail para token {token_str}: {str(e)}", exc_info=True)
        return render(request, 'verificacao_status.html', {'status_code': 'error', 'message': f'Ocorreu um erro ao verificar sua conta: {e}. Por favor, tente novamente mais tarde.'})


def minha_conta(request):

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
    
    return render(request, 'conta_usuario.html', context)

def logout(request):
    if 'usuario_id' in request.session:
        del request.session['usuario_id']
    return redirect('home:main')


def validar_email(email):
    regex1 = r'^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$'
    regex2 = r'^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+\.[a-zA-Z]{2,}$'
    if re.match(regex1, email) or re.match(regex2, email):
        return True
    
    return False

def formatandoCPF(cpf):
    cpf_limpo = "".join(filter(str.isdigit, cpf))
    cpf_formatado = CPF().mask(cpf_limpo)
    return cpf_formatado