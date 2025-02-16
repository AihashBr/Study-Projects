namespace BackEnd.Application.Services
{
    public class ExemploService : IExemploService
    {
        public string GetMessage()
        {
            return "Mensagem de exemplo do GET1!";
        }

        public string PostMessage(string mensagem)
        {
            return $"Mensagem recebida: {mensagem}";
        }
    }
}
