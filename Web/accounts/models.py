from django.db import models
from django.utils import timezone
import uuid
class Pessoa(models.Model):
    id_pessoa = models.AutoField(primary_key=True)
    email = models.CharField(max_length=255, null=False, unique=True)
    data_nascimento = models.DateField(null=False)
    nome = models.CharField(max_length=255, null=False)
    senha = models.CharField(max_length=255, null=False)
    cpf = models.CharField(max_length=14, null=False, unique=True)
    type = models.CharField(max_length=7, null=True, blank=True)
    sexo = models.CharField(null=False)
    is_verified = models.BooleanField(default=False, blank=True)
    
    class Meta:
        managed = False
        db_table = 'pessoa'

class Usuario(models.Model):
    id_pessoa = models.OneToOneField(
        Pessoa,
        on_delete=models.CASCADE,
        primary_key=True,
        db_column='id_pessoa' # Alterado para 'id_pessoa'
    )
    data_cadastro = models.DateTimeField(default=timezone.now, null=False)
    endereco = models.CharField(max_length=255, null=False)

    class Meta:
        managed = False
        db_table = 'usuario'

    # Propriedade para acessar email como se fosse campo direto
    @property
    def email(self):
        return self.id_pessoa.email

    # Propriedade para acessar senha como se fosse campo direto
    @property
    def senha(self):
        return self.id_pessoa.senha

class Medico(models.Model):
    id_pessoa = models.OneToOneField(
        Pessoa,
        on_delete=models.CASCADE,
        primary_key=True,
        db_column='id_pessoa'
    )
    crm = models.CharField(max_length=255, null=False)
    id_clinica = models.ForeignKey('Clinica', on_delete=models.SET_NULL, db_column='id_clinica', null=True, blank=True)
    tipo_medico = models.CharField(max_length=45, null=False, blank=True)
    
    class Meta:
        managed = False  # Adicionado para evitar migrações
        db_table = 'medico'

    @property
    def nome(self):
        return self.id_pessoa.nome

    @property
    def email(self):
        return self.id_pessoa.email
    
    @property
    def sexo(self):
        return self.id_pessoa.sexo

class Clinica(models.Model):
    id_clinica = models.AutoField(primary_key=True)
    cnpj = models.CharField(max_length=45)
    nome = models.CharField(max_length=45)
    numero = models.CharField(max_length=45)
    endereco = models.CharField(max_length=45)
    senha = models.CharField(max_length=255)

    class Meta:
        managed = False  # Adicionado para evitar migrações
        db_table = 'clinica'

class Horario(models.Model):
    id_horario = models.AutoField(primary_key=True)
    id_medico = models.ForeignKey(
        'Medico',
        on_delete=models.CASCADE,
        db_column='id_medico'
    )
    data = models.DateField()
    iniciomanha = models.TimeField(null=False, blank=False)
    fimmanha = models.TimeField(null=False, blank=False)
    iniciotarde = models.TimeField(null=False, blank=False)
    fimtarde = models.TimeField(null=False, blank=False)

    class Meta:
        db_table = 'horarios'
        managed = False  # remove se quiser que o Django gerencie migrações

    def __str__(self):
        return f"Horário de {self.id_medico.id_pessoa.nome} em {self.data}"



class Consulta(models.Model):
    id_consulta = models.AutoField(primary_key=True)

    id_usuario = models.ForeignKey(
        'Usuario',
        on_delete=models.CASCADE,
        db_column='id_usuario'
    )

    data_consulta = models.DateField()

    caminho_documentos = models.CharField(
        max_length=255,
        default="Sem documento",
        blank=True
    )

    id_medico = models.ForeignKey(
        'Medico',
        on_delete=models.CASCADE,
        db_column='id_medico'
    )

    type = models.CharField(
        max_length=255,
        db_column='type'
    )

    horario = models.TimeField(null=True, blank=True)

    estado = models.CharField(
        max_length=50,
        default='AGENDADA'
    )

    descricao = models.CharField(
        max_length=255,
        default='Nenhuma descrição',
        blank=True
    )

    motivo_cancelamento = models.CharField(
        max_length=255,
        default='Não cancelado',
        blank=True
    )

    class Meta:
        db_table = 'consulta'
        managed = False  # você está usando um banco já existente

    def __str__(self):
        return f"Consulta #{self.id_consulta} - {self.id_usuario.id_pessoa.nome} com {self.id_medico.id_pessoa.nome}"


class EmailVerificationToken(models.Model):
    # 'user' é a Foreign Key para o modelo 'Pessoa'
    # 'related_name' permite acessar o token a partir do objeto Pessoa (e.g., pessoa.email_verification_token)
    user = models.OneToOneField(
        Pessoa,
        on_delete=models.CASCADE,
        related_name='email_verification_token'
    )
    # O token em si. uuid.uuid4 gera um UUID aleatório e único.
    token = models.UUIDField(default=uuid.uuid4, editable=False, unique=True)
    # Registra quando o token foi criado, útil para definir um tempo de expiração
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"Token for {self.user.email} - {self.token}"
    
    class Meta:
        db_table = 'email_verification_token' # Nome da tabela no banco de dados