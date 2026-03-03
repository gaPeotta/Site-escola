from google import genai
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import os

# Inicializa FastAPI
app = FastAPI()

# Libera CORS (ajuste depois para produção)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Cliente Gemini
client = genai.Client(
    api_key=""
)

# System Instruction
SYSTEM_INSTRUCTION_Lading = """

1. Identificação e Fundadores
Instituição: Nexus School.

Segmentos: Ensino Fundamental 2 (6º ao 9º ano) e Ensino Médio.

Fundadores: Breno Gomes, Carlos Amaral, Lucas Almeida, Lucas Fabiano, Gabriel Peotta, Gabriel Vigna e Marcella Liberal.


2. Processos de Matrícula e Financeiro
Documentação: RG e CPF (aluno e responsáveis), Histórico Escolar original e Comprovante de Residência atualizado.

Valores (Mensalidades): Fundamental 2: R$ 2.200,00 | Ensino Médio: R$ 2.800,00.

Taxas Adicionais: Taxa de reserva de vaga (R$ 500,00 - dedutível na primeira mensalidade) e Taxa de Material Didático anual (R$ 1.800,00).

Bolsas e Descontos: 10% de desconto para o segundo irmão; Convênios com empresas locais (consultar lista na secretaria); Bolsas por Mérito de até 50% mediante prova de seleção anual (outubro).

Prazos: Rematrículas em Novembro. Transferências aceitas até o início do 3º bimestre.

3. Proposta Pedagógica e Metodologia
Metodologia: Sociointeracionista com forte viés em Aprendizagem Baseada em Projetos (PBL).

Línguas: Currículo de Inglês Intensivo (5 horas semanais), preparando para exames de Cambridge.

Tecnologia: Uso obrigatório de tablets (BYOD - Bring Your Own Device), Google for Education e laboratório de Robótica.

Avaliação: Sistema de notas de 0 a 10. Média para aprovação: 7,0. Composto por provas bimestrais, projetos práticos e avaliação formativa contínua. Recuperação paralela oferecida ao final de cada bimestre.

4. Estrutura Física e Diferenciais
Infraestrutura: Laboratórios de Ciências e Informática, Biblioteca com salas de estudo, Quadra Poliesportiva Coberta e Salas de Aula climatizadas com telas interativas.

Diferenciais Obrigatórios: * Robótica: Integrada à grade, focada em automação e lógica.

Educação Financeira: Disciplina semanal sobre gestão, poupança e investimentos.

5. Rotina, Segurança e Logística
Horários: * Entrada: 07h10 | Saída: 12h30 (Regular).

Tolerância de atraso: 10 minutos (máximo 3 vezes por mês).

Segurança: Portaria com reconhecimento facial e câmeras monitoradas 24h. Saída de alunos do Fundamental 2 apenas com responsáveis ou autorização digital via App. Alunos do Ensino Médio podem sair sozinhos com autorização assinada.

Alimentação: Cantina com cardápio nutricional (opções de almoço e lanches saudáveis). Permitido trazer lanche de casa. Proibido refrigerantes e doces em excesso.

Uniforme: Uso obrigatório de camiseta e calça/bermuda da escola. Tênis livre. Venda exclusiva na loja parceira "Nexus Wear" (online ou presencial).

6. Comunicação e Atendimento
Canais: App "Nexus Connect" (comunicação oficial), WhatsApp da Secretaria para urgências e E-mail para questões pedagógicas.

Reuniões: Individuais agendadas via App; Coletivas ao início de cada semestre.

Endereço: Av. da Tecnologia, 500 - Centro, São Paulo - SP.

Contatos: (11) 4002-8922 | contato@nexusschool.com.br.

Horário Atendimento: Segunda a Sexta, das 07h30 às 18h30.

7. Serviços Extras e Suporte
Extracurriculares (Opcionais): Futsal, Vôlei, Teatro, Xadrez e Clube de Debates (Terças e Quintas à tarde).

Período Integral: Disponível para Fundamental 2, com almoço incluso e auxílio nas tarefas.

Suporte Psicológico: Orientação Educacional disponível para suporte emocional e mediação de conflitos (Bullying, ansiedade, etc.).

"""

class Pergunta(BaseModel):
    mensagem: str

@app.post("/chat")
async def chat(pergunta: Pergunta):
    
    texto = pergunta.mensagem.lower()

    # # Filtro adicional no backend
    # if not any(p in texto for p in ['escola', 'curso', 'professor', 'matrícula', 'evento', 'ensino']):
    #     return {
    #         "resposta": "Este assistente responde apenas perguntas relacionadas à Escola Tecnológica Nova Geração."
    #     }

    try:
        response = client.models.generate_content(
            model="gemini-2.5-flash",
            contents=pergunta.mensagem,
            config={
                "system_instruction": SYSTEM_INSTRUCTION_Lading,
            }
        )

        return {"resposta": response.text}

    except Exception:
        return {"resposta": "Erro ao gerar resposta."}
