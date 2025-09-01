🏥 VITALIS-Sistema de Agendamento de Consultas Médicas.

Este projeto foi desenvolvido como Trabalho de Conclusão de Curso (TCC) e consiste em um sistema web de agendamento de consultas médicas.

A plataforma permite que pacientes realizem seu cadastro e tenham acesso à funcionalidade de agendar consultas.
Além disso, o sistema possui integração com um software desktop,que possibilita o médico cadastrado na clinica gerenciar essas consultas.

🚀 Tecnologias Utilizadas

Frontend: HTML, CSS, JavaScript

Backend: Python (Django)

Banco de Dados: PostgreSQL (hospedado no Supabase)

Outros: bcrypt, cryptography, dotenv, decouple

⚙️ Configuração do Ambiente
1️⃣ Criar e ativar o ambiente virtual
# Criar o ambiente virtual
python -m venv venv  

# Ativar o ambiente (Windows)
./venv/Scripts/Activate  

# Ativar o ambiente (Linux/Mac)
source venv/bin/activate

2️⃣ Instalar dependências
pip install django pymysql bcrypt validate-docbr cryptography python-decouple psycopg2-binary python-dotenv

3️⃣ Executar o servidor
# Servidor acessível na rede local
python manage.py runserver 0.0.0.0:8000

🌍 Acesso ao Projeto

Após rodar o servidor, acesse:
👉 http://localhost:8000 (na própria máquina)
👉 http://SEU-IP:8000 (em outros dispositivos da rede)

⚡ Esse sistema foi pensado para facilitar o agendamento médico e melhorar a comunicação entre pacientes e clínicas, unindo web e desktop em uma solução completa.
